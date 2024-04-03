package com.example.finalexam.job;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalexam.R;
import com.example.finalexam.job.model.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JobDetail extends AppCompatActivity {

    private TextView jobIdTextView, jobTitleTextView, jobCompanyTextView, jobOppTextView, jobAddressTextView,
            jobExpTextView, jobDegreeTextView, jobDesTextView, jobSalaryTextView;
    private Button applyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_job_detail);

        jobIdTextView = findViewById(R.id.job_id_text_view);
        jobTitleTextView = findViewById(R.id.job_title_text_view);
        jobCompanyTextView = findViewById(R.id.job_company_text_view);
        jobOppTextView = findViewById(R.id.job_opp_text_view);
        jobAddressTextView = findViewById(R.id.job_address_text_view);
        jobExpTextView = findViewById(R.id.job_exp_text_view);
        jobDegreeTextView = findViewById(R.id.job_degree_text_view);
        jobDesTextView = findViewById(R.id.job_des_text_view);
        jobSalaryTextView = findViewById(R.id.job_salary_text_view);
        applyButton = findViewById(R.id.apply_button);
        //Demo
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String jobId = extras.getString("job_id");
            String jobTitle = extras.getString("job_title");
            String jobCompany = extras.getString("job_company");

            jobIdTextView.setText("JobID: " + jobId);
            jobTitleTextView.setText("Title: " + jobTitle);
            jobCompanyTextView.setText("Company: " + jobCompany);

            DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("jobs");
            jobRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Job job = dataSnapshot.getValue(Job.class);
                            if (job != null) {
                                jobOppTextView.setText("Opportunity: " + job.getOpp());
                                jobAddressTextView.setText("Address: " + job.getAddress());
                                jobExpTextView.setText("Experience: " + job.getExp());
                                jobDegreeTextView.setText("Degree: " + job.getDegree());
                                jobDesTextView.setText("Description: " + job.getDes());
                                jobSalaryTextView.setText("Salary: " + String.valueOf(job.getSalary()));
                            }
                        }
                    } else {
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}