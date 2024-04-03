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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homepageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homepageFragment extends Fragment implements jobAdapter.OnJobClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private List<Job> jobList;
    private jobAdapter adapter;
    public homepageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homepageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homepageFragment newInstance(String param1, String param2) {
        homepageFragment fragment = new homepageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        Job job1 = new Job("123", "ABC Company", "Software Engineer", "Full-time", "123 Main St", "3 years", "Bachelor", "Description", 5000.0);
        Job job2 = new Job("124", "XYZ Corporation", "Data Analyst", "Part-time", "456 Elm St", "2 years", "Bachelor", "Description", 4000.0);
        Job job3 = new Job("125", "123 Enterprises", "Project Manager", "Full-time", "789 Oak St", "5 years", "Master", "Description", 6000.0);

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
                    String jobid = snapshot.child("jobid").getValue(String.class);
                    String title = snapshot.child("title").getValue(String.class);
                    String company = snapshot.child("company").getValue(String.class);
                    Job job = new Job(); // Sử dụng constructor mặc định
                    job.setJobid(jobid);
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
        intent.putExtra("job_id", job.getJobid());
        intent.putExtra("job_title", job.getTitle());
        intent.putExtra("job_company", job.getCompany());
        startActivity(intent);
    }
}