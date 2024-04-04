package com.example.finalexam.user.model;

import java.util.ArrayList;

public class User {
    private String userId;

    //thông tin liên hệ
    private String name,role,phoneNumber,email,address,gender,DOB;

    private ArrayList<String> education;
    private ArrayList<String> jobExperiment;
    private ArrayList<String> degree;
    private ArrayList<String> skills;
    private String careerGoals;
    private String socialActivities;
    private ArrayList<String> hobbies;
    private ArrayList<String> achievement;
    private String moreInfo;

    public User() {
    }
    public User(String userId) {
        this.userId=userId;
        this.name = null;
        this.role = null;
        this.phoneNumber = null;
        this.email = null;
        this.address = null;
        this.gender = null;
        this.DOB = null;
        this.education = null;
        this.jobExperiment = null;
        this.degree = null;
        this.skills = null;
        this.careerGoals = null;
        this.socialActivities = null;
        this.hobbies = null;
        this.achievement = null;
        this.moreInfo = null;
    }

    public User(String userId, String name, String role, String phoneNumber, String email, String address,
                String gender, String DOB, ArrayList<String> education, ArrayList<String> jobExperiment,
                ArrayList<String> degree, ArrayList<String> skills, String careerGoals, String socialActivities,
                ArrayList<String> hobbies, ArrayList<String> achievement, String moreInfo)
    {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.DOB = DOB;
        this.education = education;
        this.jobExperiment = jobExperiment;
        this.degree = degree;
        this.skills = skills;
        this.careerGoals = careerGoals;
        this.socialActivities = socialActivities;
        this.hobbies = hobbies;
        this.achievement = achievement;
        this.moreInfo = moreInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public ArrayList<String> getEducation() {
        return education;
    }

    public void setEducation(ArrayList<String> education) {
        this.education = education;
    }

    public ArrayList<String> getJobExperiment() {
        return jobExperiment;
    }

    public void setJobExperiment(ArrayList<String> jobExperiment) {
        this.jobExperiment = jobExperiment;
    }

    public ArrayList<String> getDegree() {
        return degree;
    }

    public void setDegree(ArrayList<String> degree) {
        this.degree = degree;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public String getCareerGoals() {
        return careerGoals;
    }

    public void setCareerGoals(String careerGoals) {
        this.careerGoals = careerGoals;
    }

    public String getSocialActivities() {
        return socialActivities;
    }

    public void setSocialActivities(String socialActivities) {
        this.socialActivities = socialActivities;
    }

    public ArrayList<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(ArrayList<String> hobbies) {
        this.hobbies = hobbies;
    }

    public ArrayList<String> getAchievement() {
        return achievement;
    }

    public void setAchievement(ArrayList<String> achievement) {
        this.achievement = achievement;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }
}
