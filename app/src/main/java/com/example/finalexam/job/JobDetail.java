package com.example.finalexam.job;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class JobDetail extends AppCompatActivity {

    private ImageView ivLogoCompany;
    private TextView  jobTitleTextView, jobCompanyTextView, jobOppTextView, jobAddressTextView,
            jobExpTextView,  jobDesTextView, jobSalaryTextView, jobRequirement, jobBenefit, jobShortAddress,
            jobExpTextView1,jobQuantityTextView,jobRoleTextView,jobTimeTextView;
    private Button applyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_job_detail);

        ivLogoCompany=findViewById(R.id.ivLogoCompany);
        jobTitleTextView = findViewById(R.id.job_title_text_view);
        jobCompanyTextView = findViewById(R.id.job_company_text_view);
        jobSalaryTextView=findViewById(R.id.job_salary_text_view);
        jobShortAddress=findViewById(R.id.job_short_address_text_view);
        jobExpTextView1=findViewById(R.id.job_exp1_text_view);
        jobExpTextView = findViewById(R.id.job_exp_text_view);
        jobOppTextView = findViewById(R.id.job_opp_text_view);
        jobQuantityTextView=findViewById(R.id.job_quantity_text_view);
        jobRoleTextView=findViewById(R.id.job_role_text_view);
        jobDesTextView = findViewById(R.id.job_des_text_view);
        jobRequirement = findViewById(R.id.job_requirement_text_view);
        jobBenefit = findViewById(R.id.job_benefit_text_view);
        jobAddressTextView = findViewById(R.id.job_address_text_view);
        jobTimeTextView =findViewById(R.id.job_time_text_view);

        applyButton = findViewById(R.id.apply_button);
        //Demo
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Job job=(Job) extras.get("object_job");

            FirebaseStorage storage=FirebaseStorage.getInstance();
            StorageReference storageRef=storage.getReference();
            storageRef.child("logos/"+job.getCompanyLogo()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(JobDetail.this).load(uri).into(ivLogoCompany);
                }
            });
            jobTitleTextView.setText(job.getTitle());
            jobCompanyTextView.setText(job.getCompany());
            jobSalaryTextView.setText( String.valueOf(job.getSalary()));
            jobShortAddress.setText(job.getShortAddress());
            jobExpTextView1.setText(job.getExp());
            jobExpTextView.setText("  Kinh nghiệm: "+job.getExp());
            jobOppTextView.setText("  Hình thức: " + job.getOpp());
            jobQuantityTextView.setText("  Số lượng tuyển: "+job.getQuantity());
            jobRoleTextView.setText("Cấp bậc: "+job.getRole());
            jobDesTextView.setText(job.getDescription());
            jobRequirement.setText(job.getRequirement());
            jobBenefit.setText(job.getBenefit());
            jobAddressTextView.setText(job.getAddress());
            jobTimeTextView.setText(job.getTime());

        }
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy userID từ Intent
                String userId = MainPage.getId();

                if (userId != null) {
                    Bundle extras = getIntent().getExtras();
                    if (extras != null) {
                        Job job=(Job) extras.get("object_job");
                        String jobId = job.getJobId();

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