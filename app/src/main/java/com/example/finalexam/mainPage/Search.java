package com.example.finalexam.mainPage;

import static com.example.finalexam.mainPage.MainPage.getId;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.finalexam.R;
import com.example.finalexam.databinding.ActivitySearchBinding;
import com.example.finalexam.job.JobDetail;
import com.example.finalexam.job.adapter.JobAdapter;
import com.example.finalexam.job.model.Job;

import java.util.ArrayList;

public class Search extends AppCompatActivity implements JobAdapter.OnJobClickListener {
    ActivitySearchBinding binding;
    private Button btnBack;
    ArrayList<Job> jobList,searchList;
    JobAdapter mAdapter;
    RecyclerView rvSearchList;
    SearchView searchView;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnBack=binding.btnBack;
        rvSearchList=binding.searchList;
        searchView=binding.search;

        Bundle b=getIntent().getExtras();
        if(b==null) return;
        jobList= (ArrayList<Job>) b.get("jobList");
        rvSearchList.setLayoutManager(new LinearLayoutManager(this));

        mAdapter=new JobAdapter(new ArrayList<>(),  this);
        rvSearchList.setAdapter(mAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void filter(String keyword){
        ArrayList<Job> a=new ArrayList<>();
        for(Job j:jobList){
            if(j.getTitle().toLowerCase().contains(keyword.toLowerCase())){
                a.add(j);
            }
        }
        mAdapter.setJobs(a);
    }


    @Override
    public void onJobClick(Job job) {
        Intent intent = new Intent(Search.this, JobDetail.class);
        intent.putExtra("job_id", job.getJobId());
        intent.putExtra("job_title", job.getTitle());
        intent.putExtra("job_company", job.getCompany());
        userId =MainPage.getId();
        intent.putExtra("user_id", userId);
        startActivity(intent);
    }
}