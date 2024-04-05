package com.example.finalexam.job.model;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class jobRepository {
    private static ArrayList<Job> jobList;

    public jobRepository(ArrayList<Job> jobList){
        for(Job job:jobList){
            this.jobList.add(job);
        }
    }

    public jobRepository() {
    }

    public static ArrayList<Job> getJobList(){
        return jobList;
    }

    public static void setJobList(ArrayList<Job> jobList){
        jobRepository.jobList=jobList;
    }

    public void addJob(Job job){
        this.jobList.add(job);
    }

    public ArrayList<Job> filterByTitle(String key){
        ArrayList<Job> jobs=new ArrayList<>();
        for(Job job:jobList){
            if(job.getTitle().contains(key))
                jobs.add(job);
        }
        return jobs;
    }

}
