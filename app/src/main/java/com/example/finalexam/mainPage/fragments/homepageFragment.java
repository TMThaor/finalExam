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
import com.example.finalexam.mainPage.MoreJob;
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
    private TextView moreJob;



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
        initData();//Khởi tạo dữ liệu từ firebase
        jAdapter = new JobAdapter(requireContext(),jobList, new IClickItemJobListener() {
            @Override
            public void onClickItemJob(Job job) {
                goToDetail(job);
            }
        });
        jobRecyclerView.setAdapter(jAdapter);

    //Set sự kiện click cho tìm kiếm
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
        cAdapter=new CompanyAdapter(requireContext());
        comRecyclerView.setAdapter(cAdapter);


    //Set sự kiện cho nút xem thêm ở phần Việc làm hấp dẫn
        moreJob=view.findViewById(R.id.tvMoreJob);
        moreJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), MoreJob.class);
                startActivity(intent);
            }
        });
        return view;
    }

//    private void initJobs() {
//        DatabaseReference jobsRef = FirebaseDatabase.getInstance().getReference("jobs");
//        //Demo
//        Job job1 = new Job("123", "Công ty cổ phần Koffmann","logoKoffmann.png", "Software Engineer", "Full-time", "Ha Noi", "3 years", "Staff","IT",5, "Description", "null","null","null",5000.0,"từ thứ 2 đến thứ 7 hàng tuần: 8h00 - 17h00");
//        Job job2 = new Job("124", "Công ty TNHH Sailin", "logoSailun.png","Quản lý xưởng", "Full-time", "TP. HCM", "2 years", "Manager","Sản xuất",2, "Description","null","null","null", 6000.0,"từ thứ 2 đến thứ 7 hàng tuần: 8h00 - 17h00");
//        Job job3 = new Job("125", "Tập đoàn VinGroup","logoVingroup.jpg", "Project Manager", "Full-time", "Ha Noi", "5 years", "Manager","IT",1,"Description","null","null","null", 6000.0,"từ thứ 2 đến thứ 7 hàng tuần: 8h00 - 17h00");
//        Job job4 = new Job("126", "FPT Software","logoFpt.png", "Software Engineer", "Full-time", "Ha Noi", "3 years", "Staff","IT",5, "Description","null","null","null", 5000.0,"từ thứ 2 đến thứ 7 hàng tuần: 8h00 - 17h00");
//        Job job5 = new Job("127", "Viettel","logoViettel.jpg", "Nhân viên chăm sóc khách hàng", "Part-time", "TP. HCM", "2 years", "Staff","IT", "Description","null","null","null", 4000.0,"từ thứ 2 đến thứ 7 hàng tuần: Sáng từ 8h-12h hoặc chiều từ 13h-17h");
//        Job job6 = new Job("128", "StarBucks","logoStarBucks.png", "Quản lý cửa hàng", "Full-time", "Ha Noi", "5 years", "Manager","Dịch vụ",1, "Description","null","null","null", 6000.0,"từ thứ 2 đến thứ 7 hàng tuần: 8h00 - 17h00");
//        jobsRef.child(job1.getJobId()).setValue(job1);
//        jobsRef.child(job2.getJobId()).setValue(job2);
//        jobsRef.child(job3.getJobId()).setValue(job3);
//        jobsRef.child(job4.getJobId()).setValue(job4);
//        jobsRef.child(job5.getJobId()).setValue(job5);
//        jobsRef.child(job6.getJobId()).setValue(job6);
//    }

    private void initData() {
        DatabaseReference jobsRef = FirebaseDatabase.getInstance().getReference("jobs");
        jobsRef.orderByChild("salary").startAfter(4999.9).limitToFirst(12).addValueEventListener(new ValueEventListener() {
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

    //Khởi tạo dữ liệu cho rvCompany
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