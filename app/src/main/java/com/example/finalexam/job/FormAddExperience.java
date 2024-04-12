package com.example.finalexam.job;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalexam.R;
import com.example.finalexam.job.model.Experience;
import com.example.finalexam.mainPage.MainPage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FormAddExperience extends AppCompatActivity {

    private DatePickerDialog dateStartPickerDialog,dateEndPickerDialog;
    private Button dateStartButton,dateEndButton,btnSaveExp;
    private EditText edtRole,edtCompanyName,edtDes;
    private String key;
    private int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_add_experience);
        String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        key=timeStamp;
        edtRole=findViewById(R.id.edtExpRole);
        edtCompanyName=findViewById(R.id.edtExpNameCompany);
        edtDes=findViewById(R.id.edtExpDes);

        initDatePicker();
        dateStartButton = findViewById(R.id.startDate);
        dateStartButton.setText(getTodaysDate());
        dateEndButton=findViewById(R.id.endDate);
        dateEndButton.setText(getTodaysDate());
        dateStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=0;
                openDateStartPicker(v);
            }
        });
        dateEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=1;
                openDateEndPicker(v);
            }
        });
        btnSaveExp=findViewById(R.id.btnSaveExp);
        btnSaveExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddExpToDB();
                finish();
            }
        });
    }

    private void AddExpToDB(){
        DatabaseReference userExpRef=FirebaseDatabase.getInstance().getReference("CV").child(MainPage.getId()).child("jobExperiment");
        Experience exp=new Experience();
        exp.setRole(edtRole.getText().toString());
        exp.setCompanyName(edtCompanyName.getText().toString());
        exp.setDescription(edtDes.getText().toString());
        exp.setTimeStart(dateStartButton.getText().toString());
        exp.setTimeEnd(dateEndButton.getText().toString());
        userExpRef.child(key).setValue(exp);
    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                if(check==0) dateStartButton.setText(date);
                else dateEndButton.setText(date);

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        dateStartPickerDialog = new DatePickerDialog(FormAddExperience.this, dateSetListener, year, month, day);
        dateEndPickerDialog = new DatePickerDialog(FormAddExperience.this, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return day+" / "+month+" / "+year;
    }

    public void openDateStartPicker(View view)
    {
        dateStartPickerDialog.show();
    }
    public void openDateEndPicker(View view)
    {
        dateEndPickerDialog.show();
    }
}