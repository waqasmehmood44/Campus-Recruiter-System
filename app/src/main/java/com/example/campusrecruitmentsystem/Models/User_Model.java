package com.example.campusrecruitmentsystem.Models;

public class User_Model {
    String name, contact_no, address, department, user_type, userId, user_email;

    public User_Model(String name, String contact_no, String address, String department, String user_type, String userId, String user_email) {
        this.name = name;
        this.contact_no = contact_no;
        this.address = address;
        this.department = department;
        this.user_type = user_type;
        this.userId = userId;
        this.user_email = user_email;
    }

    public User_Model() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
