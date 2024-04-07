package com.example.finalexam.mainPage.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalexam.R;
import com.example.finalexam.job.JobDetail;
import com.example.finalexam.job.adapter.JobAdapter;
import com.example.finalexam.job.model.Job;
import com.example.finalexam.mainPage.MainPage;
import com.example.finalexam.mainPage.Search;
import com.example.finalexam.my_interface.IClickItemJobListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class homepageFragment extends Fragment{

    private String userId;
    private RecyclerView recyclerView;
    private ArrayList<Job> jobList;
    private JobAdapter adapter;
    private TextView searchBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        recyclerView = view.findViewById(R.id.rvJob);
        searchBar=view.findViewById(R.id.search_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        jobList = new ArrayList<>();


//        initJobs();
        initData();
        adapter = new JobAdapter(jobList, new IClickItemJobListener() {
            @Override
            public void onClickItemJob(Job job) {
                goToDetail(job);
            }
        });
        recyclerView.setAdapter(adapter);

        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(requireContext(), Search.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("jobList",jobList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

//    private void initJobs() {
//        DatabaseReference jobsRef = FirebaseDatabase.getInstance().getReference("jobs");
//        //Demo
//        Job job1 = new Job("123", "ABC Company", "Software Engineer", "Full-time", "123 Main St", "3 years", "Bachelor", "Description", 5000.0,"null","null","null");
//        Job job2 = new Job("124", "XYZ Corporation", "Data Analyst", "Part-time", "456 Elm St", "2 years", "Bachelor", "Description", 4000.0,"null","null","null");
//        Job job3 = new Job("125", "123 Enterprises", "Project Manager", "Full-time", "789 Oak St", "5 years", "Master", "Description", 6000.0,"null","null","null");
//
//        jobsRef.child(job1.getJobId()).setValue(job1);
//        jobsRef.child(job2.getJobId()).setValue(job2);
//        jobsRef.child(job3.getJobId()).setValue(job3);
//    }

    private void initData() {
        DatabaseReference jobsRef = FirebaseDatabase.getInstance().getReference("jobs");
        jobsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jobList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Job job= snapshot.getValue(Job.class);
                    jobList.add(job);
                }
                // Cập nhật adapter của RecyclerView với danh sách mới
                adapter.setJobs(jobList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void goToDetail(Job job) {
        Intent intent = new Intent(requireContext(), JobDetail.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("object_job",job);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}