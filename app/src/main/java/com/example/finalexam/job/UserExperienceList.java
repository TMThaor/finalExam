package com.example.finalexam.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.Company.model.Company;
import com.example.finalexam.R;
import com.example.finalexam.job.adapter.ExperienceAdapter;
import com.example.finalexam.job.model.Experience;
import com.example.finalexam.my_interface.IRemoveUserExp;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserExperienceList extends AppCompatActivity {

    private String userId;
    RecyclerView rcvExp;
    private Button btnAddExp,btnBack;
    ArrayList<Experience> listExp;
    ExperienceAdapter mAdapter;

    @Override
    protected void onDestroy() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("myObject", listExp); // myObject là đối tượng cần trả về
        setResult(Activity.RESULT_OK, resultIntent);
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_experience_list);
        Bundle bundle=getIntent().getExtras();
        userId=bundle.get("userId").toString();
        listExp =new ArrayList<>();
        initData();
        rcvExp=findViewById(R.id.rcvExperience);
        rcvExp.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        mAdapter=new ExperienceAdapter(listExp, new IRemoveUserExp() {
            @Override
            public void removeUserExp(Experience e) {
                deleteExp(e);
            }
        });
        rcvExp.setAdapter(mAdapter);

        btnAddExp=findViewById(R.id.btnAddMoreExp);
        btnAddExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserExperienceList.this,FormAddExperience.class);
                intent.putExtra("key",listExp.size());
                startActivity(intent);
            }
        });

        btnBack=findViewById(R.id.btnBackExpList);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("myObject", listExp); // myObject là đối tượng cần trả về
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

    }
    private void deleteExp(Experience e){
        DatabaseReference expRef=FirebaseDatabase.getInstance().getReference("CV").child(userId).child("jobExperiment").child(e.getExpId());
        expRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                initData();
            }
        });
    }
    private void initData(){

        DatabaseReference expRef= FirebaseDatabase.getInstance().getReference("CV").child(userId).child("jobExperiment");
        expRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listExp = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()) {
                    Experience e= s.getValue(Experience.class);
                    e.setExpId(s.getKey());
                    listExp.add(e);
                }
                // Cập nhật adapter của RecyclerView với danh sách mới
                mAdapter.setData(listExp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}