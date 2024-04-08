package com.example.finalexam.Company.model;

import android.net.Uri;

public class Company {

    private String companyId;
    private String companyName;
    private String logo;
    private String contact;
    private String address;
    private String category;

    public Company() {
    }

    public Company(String companyId,String companyName, String logo, String contact, String address, String category) {
        this.companyId = companyId;
        this.companyName=companyName;
        this.logo = logo;
        this.contact = contact;
        this.address = address;
        this.category = category;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
}
