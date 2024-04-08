package com.example.finalexam.mainPage.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalexam.Company.adapter.CompanyAdapter;
import com.example.finalexam.Company.model.Company;
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
    private RecyclerView jobRecyclerView,comRecyclerView;
    private ArrayList<Job> jobList;
    private ArrayList<Company> comList;
    private JobAdapter jAdapter;
    private CompanyAdapter cAdapter;
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

        //đổ dữ liệu lên recyclerView job
        jobRecyclerView = view.findViewById(R.id.rvJob);
        searchBar=view.findViewById(R.id.search_bar);
//        jobRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),4);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        jobRecyclerView.setLayoutManager(gridLayoutManager);
        jobList = new ArrayList<>();


//        initJobs();
        initData();
        jAdapter = new JobAdapter(jobList, new IClickItemJobListener() {
            @Override
            public void onClickItemJob(Job job) {
                goToDetail(job);
            }
        });
        jobRecyclerView.setAdapter(jAdapter);

        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(requireContext(), Search.class);
                startActivity(intent);
            }
        });

    //Đổ dữ liệu lên recyclerView Company
        comRecyclerView=view.findViewById(R.id.rvCompany);
        comRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        comList=new ArrayList<>();
        cAdapter=new CompanyAdapter();
        comRecyclerView.setAdapter(cAdapter);

        return view;
    }

//    private void initJobs() {
//        DatabaseReference jobsRef = FirebaseDatabase.getInstance().getReference("jobs");
//        //Demo
//        Job job1 = new Job("123", "ABC Company", "Software Engineer", "Full-time", "Ha Noi", "3 years", "Staff","IT", "Description", "null","null","null",5000.0);
//        Job job2 = new Job("124", "XYZ Corporation", "Data Analyst", "Part-time", "TP. HCM", "2 years", "Staff","IT", "Description","null","null","null", 4000.0);
//        Job job3 = new Job("125", "123 Enterprises", "Project Manager", "Full-time", "Ha Noi", "5 years", "Manager","IT", "Description","null","null","null", 6000.0);
//        Job job4 = new Job("126", "FPT Software", "Software Engineer", "Full-time", "Ha Noi", "3 years", "Staff","IT", "Description","null","null","null", 5000.0);
//        Job job5 = new Job("127", "Viettel", "Nhân viên chăm sóc khách hàng", "Part-time", "TP. HCM", "2 years", "Staff","IT", "Description","null","null","null", 4000.0);
//        Job job6 = new Job("128", "StarBuck", "Quản lý cửa hàng", "Full-time", "Ha Noi", "5 years", "Manager","IT", "Description","null","null","null", 6000.0);
//        jobsRef.child(job1.getJobId()).setValue(job1);
//        jobsRef.child(job2.getJobId()).setValue(job2);
//        jobsRef.child(job3.getJobId()).setValue(job3);
//        jobsRef.child(job4.getJobId()).setValue(job4);
//        jobsRef.child(job5.getJobId()).setValue(job5);
//        jobsRef.child(job6.getJobId()).setValue(job6);
//    }

    private void initData() {
        DatabaseReference jobsRef = FirebaseDatabase.getInstance().getReference("jobs");
        jobsRef.limitToFirst(12).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jobList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Job job= snapshot.getValue(Job.class);
                    jobList.add(job);
                }
                // Cập nhật adapter của RecyclerView với danh sách mới
                jAdapter.setJobs(jobList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        DatabaseReference comRef=FirebaseDatabase.getInstance().getReference("companies");
        comRef.limitToFirst(6).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comList = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()) {
                    Company company= s.getValue(Company.class);
                    comList.add(company);
                }
                // Cập nhật adapter của RecyclerView với danh sách mới
                cAdapter.setCompanies(comList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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