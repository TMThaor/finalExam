package com.example.finalexam.job.model;

import java.io.Serializable;

public class Job implements Serializable {
    String jobId;
    String companyLogo;

    //Thông tin vắn tắt
    String  company, title, opp, shortAddress, exp, role,category;
    int quantity;

    //Thông tin chi tiết
    String description,requirement,benefit,address,time;
    //opp: hình thức tuyển dụng (partime, fulltime, intern)
    double salary;

    public Job() {
    }

    public Job(String jobId, String company,String companyLogo, String title, String opp, String shortAddress, String exp, String role, String category,int quantity, String description, String requirement, String benefit, String address, double salary,String time) {
        this.jobId = jobId;
        this.company = company;
        this.companyLogo=companyLogo;
        this.title = title;
        this.opp = opp;
        this.shortAddress = shortAddress;
        this.exp = exp;
        this.role = role;
        this.category = category;
        this.quantity=quantity;
        this.description = description;
        this.requirement = requirement;
        this.benefit = benefit;
        this.address = address;
        this.salary = salary;
        this.time=time;
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

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
