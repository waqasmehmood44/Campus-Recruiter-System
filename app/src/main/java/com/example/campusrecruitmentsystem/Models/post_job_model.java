package com.example.campusrecruitmentsystem.Models;

public class post_job_model {
    String job, salary, eligibility, location, description,rec_id, job_id;


    public post_job_model(String job, String salary, String eligibility, String location, String description, String rec_id, String job_id) {
        this.job = job;
        this.salary = salary;
        this.eligibility = eligibility;
        this.location = location;
        this.description = description;
        this.rec_id = rec_id;
        this.job_id = job_id;
    }

    public post_job_model() {
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRec_id() {
        return rec_id;
    }

    public void setRec_id(String rec_id) {
        this.rec_id = rec_id;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }
}
