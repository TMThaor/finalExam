package com.example.finalexam.mainPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.R;
import com.example.finalexam.job.JobDetail;
import com.example.finalexam.job.adapter.JobAdapter;
import com.example.finalexam.job.model.Job;
import com.example.finalexam.my_interface.IClickItemJobListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MoreJob extends AppCompatActivity {
    private JobAdapter jAdapter;
    private RecyclerView jobRecyclerView;
    private Button btnBack;
    private ArrayList<Job> jobList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_job);
        jobRecyclerView = findViewById(R.id.rvMoreJob);
        btnBack=findViewById(R.id.btnBackMoreJob);
        jobRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        jobList = new ArrayList<>();
        initData();

        jAdapter = new JobAdapter(this,jobList, new IClickItemJobListener() {
            @Override
            public void onClickItemJob(Job job) {
                goToDetail(job);
            }
        });
        jobRecyclerView.setAdapter(jAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initData() {
        DatabaseReference jobsRef = FirebaseDatabase.getInstance().getReference("jobs");
        jobsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jobList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Job job = snapshot.getValue(Job.class);
                    jobList.add(job);
                }
                // Cập nhật adapter của RecyclerView với danh sách mới
                jAdapter.setJobs(jobList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void goToDetail(Job job) {
        Intent intent = new Intent(this, JobDetail.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("object_job",job);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}