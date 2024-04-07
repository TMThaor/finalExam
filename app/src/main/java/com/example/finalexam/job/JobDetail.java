package com.example.finalexam.job;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalexam.R;
import com.example.finalexam.job.model.Job;
import com.example.finalexam.mainPage.MainPage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
            if(jobId!=null){
                DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("jobs").child(jobId);
                jobRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Job job = snapshot.getValue(Job.class);
                            if (job != null) {
                                // Hiển thị thông tin công việc trên giao diện
                                jobOppTextView.setText("Opportunity: " + job.getOpp());
                                jobAddressTextView.setText("Address: " + job.getShortAddress());
                                jobExpTextView.setText("Experience: " + job.getExp());
                                jobDegreeTextView.setText("Role: " + job.getRole());
                                jobDesTextView.setText("Description: " + job.getDescription());
                                jobSalaryTextView.setText("Salary: " + String.valueOf(job.getSalary()));
                            }
                        } else {
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy userID từ Intent
                String userId = getIntent().getStringExtra("user_id");

                if (userId != null) {
                    Bundle extras = getIntent().getExtras();
                    if (extras != null) {
                        String jobId = extras.getString("job_id");

                        // Kiểm tra xem người dùng đã apply công việc này chưa
                        DatabaseReference applyJobRef = FirebaseDatabase.getInstance().getReference("ApplyJob").child(userId).child(jobId);
                        applyJobRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    // Người dùng đã apply công việc này
                                    Toast.makeText(JobDetail.this, "Bạn đã apply công việc này rồi!", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Người dùng chưa apply công việc này, thực hiện lưu thông tin
                                    applyJobRef.setValue(true)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(JobDetail.this, "Ứng tuyển thành công!", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(JobDetail.this, "Đã xảy ra lỗi khi ứng tuyển!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Xử lý khi có lỗi xảy ra trong quá trình truy vấn Firebase
                                Toast.makeText(JobDetail.this, "Đã xảy ra lỗi khi kiểm tra apply job!", Toast.LENGTH_SHORT).show();
                                Log.e("JobDetail", "Error checking apply job: " + error.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }
}