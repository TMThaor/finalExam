package com.example.finalexam.job.model;

public class Job {
    String jobid, company, title, opp, address, exp, degree, des;
    double salary;

    public Job() {
    }
    public Job(String jobid, String company, String title, String opp, String address, String exp, String degree, String des, double salary) {
        this.jobid = jobid;
        this.company = company;
        this.title = title;
        this.opp = opp;
        this.address = address;
        this.exp = exp;
        this.degree = degree;
        this.des = des;
        this.salary = salary;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOpp() {
        return opp;
    }

    public void setOpp(String opp) {
        this.opp = opp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
