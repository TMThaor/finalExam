package com.example.finalexam.job.model;

import java.io.Serializable;

public class Experience implements Serializable {
    private String expId,role,companyName,description;
    private String timeStart,timeEnd;

    public Experience() {
    }

    public Experience(String expId,String role, String companyName, String description, String timeStart, String timeEnd) {
        this.expId=expId;
        this.role = role;
        this.companyName = companyName;
        this.description = description;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getExpId() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId = expId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
