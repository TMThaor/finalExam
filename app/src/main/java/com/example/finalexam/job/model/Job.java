package com.example.finalexam.job.model;

public class Job {
    String jobId;

    //Thông tin vắn tắt
    String  company, title, opp, shortAddress, exp, role;

    //Thông tin chi tiết
    String description,requirement,benefit,address;
    //opp: hình thức tuyển dụng (partime, fulltime, intern)
    double salary;

    public Job() {
    }

    public Job(String jobId, String company, String title, String opp, String shortAddress, String exp, String role, String description, double salary, String requirement, String benefit, String address) {
        this.jobId = jobId;
        this.company = company;
        this.title = title;
        this.opp = opp;
        this.shortAddress = shortAddress;
        this.exp = exp;
        this.role = role;
        this.description = description;
        this.requirement = requirement;
        this.benefit = benefit;
        this.address = address;
        this.salary = salary;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
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

    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getRole() {
            return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
