package com.example.finalexam.appliedJob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.finalexam.R;
import com.example.finalexam.appliedJob.adapter.AppliedJobAdapter;
import com.example.finalexam.job.JobDetail;
import com.example.finalexam.job.adapter.jobAdapter;
import com.example.finalexam.job.model.Job;
import com.example.finalexam.mainPage.MainPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppliedJob extends AppCompatActivity implements AppliedJobAdapter.OnJobClickListener{
    private String userID;
    private RecyclerView recyclerView;

    private AppliedJobAdapter appliedJobAdapter;
    private List<Job> appliedJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_job);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String userId = getIntent().getStringExtra("user_id");
        if (userId != null) {
            Bundle extras = getIntent().getExtras();
            DatabaseReference appliedJobRef = FirebaseDatabase.getInstance().getReference("ApplyJob").child(userId);

            appliedJobs = new ArrayList<>(); // Khởi tạo danh sách công việc đã ứng tuyển
            appliedJobAdapter = new AppliedJobAdapter(appliedJobs, AppliedJob.this); // Khởi tạo adapter
            recyclerView.setAdapter(appliedJobAdapter);
            // Lắng nghe sự kiện thay đổi trên node "ApplyJob" của người dùng
            appliedJobRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    appliedJobs.clear(); // Xóa danh sách công việc đã ứng tuyển hiện tại
                    // Duyệt qua từng child node để lấy các jobID đã ứng tuyển
                    for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                        String jobId = jobSnapshot.getKey(); // Lấy jobID
                        // Truy vấn dữ liệu của công việc từ node "jobs" tương ứng với jobId
                        DatabaseReference jobsRef = FirebaseDatabase.getInstance().getReference("jobs").child(jobId);
                        jobsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    String title = snapshot.child("title").getValue(String.class);
                                    String company = snapshot.child("company").getValue(String.class);
                                    Job appliedJob = new Job();
                                    appliedJob.setJobId(jobId);
                                    appliedJob.setTitle(title);
                                    appliedJob.setCompany(company);
                                    appliedJobs.add(appliedJob); // Thêm công việc vào danh sách
                                    appliedJobAdapter.notifyDataSetChanged(); // Cập nhật adapter
                                }
                                appliedJobAdapter.setAppliedJobs(appliedJobs);
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Xử lý lỗi nếu có
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Xử lý lỗi nếu có
                }
            });
        }
    }

    public void onAppliedJobClick(Job appliedjob) {
        Intent intent = new Intent(this, JobDetail.class);
        intent.putExtra("job_id", appliedjob.getJobId());
        intent.putExtra("job_title", appliedjob.getTitle());
        intent.putExtra("job_company", appliedjob.getCompany());
        startActivity(intent);
    }
}