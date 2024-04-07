package com.example.finalexam.job.model;

import java.util.ArrayList;

public class JobRepository {
    private static ArrayList<Job> jobList;

    public JobRepository(ArrayList<Job> jobList){
        for(Job job:jobList){
            this.jobList.add(job);
        }
    }

    public JobRepository() {
    }

    public static ArrayList<Job> getJobList(){
        return jobList;
    }

    public static void setJobList(ArrayList<Job> jobList){
        JobRepository.jobList=jobList;
    }

    public void addJob(Job job){
        this.jobList.add(job);
    }

    public Job getJobById(String id){
        for ( Job j : jobList) {
            if (id == j.getJobId())
                return j;
        }
        return  null;
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
