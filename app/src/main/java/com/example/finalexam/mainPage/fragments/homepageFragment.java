package com.example.finalexam.mainPage.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalexam.R;
import com.example.finalexam.job.JobDetail;
import com.example.finalexam.job.adapter.jobAdapter;
import com.example.finalexam.job.model.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class homepageFragment extends Fragment implements jobAdapter.OnJobClickListener{

    private RecyclerView recyclerView;
    private List<Job> jobList;
    private jobAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        jobList = new ArrayList<>();

        adapter = new jobAdapter(jobList, this);
        recyclerView.setAdapter(adapter);
//        initJobs();
        initData();
        return view;
    }
    private void initJobs() {
        DatabaseReference jobsRef = FirebaseDatabase.getInstance().getReference("jobs");
        //Demo
        Job job1 = new Job("123", "ABC Company", "Software Engineer", "Full-time", "123 Main St", "3 years", "Bachelor", "Description", 5000.0,"null","null","null");
        Job job2 = new Job("124", "XYZ Corporation", "Data Analyst", "Part-time", "456 Elm St", "2 years", "Bachelor", "Description", 4000.0,"null","null","null");
        Job job3 = new Job("125", "123 Enterprises", "Project Manager", "Full-time", "789 Oak St", "5 years", "Master", "Description", 6000.0,"null","null","null");

        jobsRef.child("job1").setValue(job1);
        jobsRef.child("job2").setValue(job2);
        jobsRef.child("job3").setValue(job3);
    }

    private void initData() {
        DatabaseReference jobsRef = FirebaseDatabase.getInstance().getReference("jobs");
        jobsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Job> jobs = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String jobId = snapshot.child("jobid").getValue(String.class);
                    String title = snapshot.child("title").getValue(String.class);
                    String company = snapshot.child("company").getValue(String.class);
                    Job job = new Job(); // Sử dụng constructor mặc định
                    job.setJobId(jobId);
                    job.setTitle(title);
                    job.setCompany(company);
                    jobs.add(job);
                }
                // Cập nhật adapter của RecyclerView với danh sách mới
                adapter.setJobs(jobs);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onJobClick(Job job) {
        Intent intent = new Intent(requireContext(), JobDetail.class);
        intent.putExtra("job_id", job.getJobId());
        intent.putExtra("job_title", job.getTitle());
        intent.putExtra("job_company", job.getCompany());
        startActivity(intent);
    }
}