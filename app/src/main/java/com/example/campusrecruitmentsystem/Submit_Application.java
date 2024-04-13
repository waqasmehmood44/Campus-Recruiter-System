package com.example.campusrecruitmentsystem;

public class Submit_Application {
    public String name, uri, rec_id, job_id,student_id, job_name, job_salary, job_location, job_desc, application_status, test_id;

    public Submit_Application() {
    }

    public Submit_Application(String name, String uri, String rec_id, String job_id, String student_id, String job_name, String job_salary, String job_location, String job_desc, String application_status, String test_id) {
        this.name = name;
        this.uri = uri;
        this.rec_id = rec_id;
        this.job_id = job_id;
        this.student_id = student_id;
        this.job_name = job_name;
        this.job_salary = job_salary;
        this.job_location = job_location;
        this.job_desc = job_desc;
        this.application_status = application_status;
        this.test_id = test_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getJob_salary() {
        return job_salary;
    }

    public void setJob_salary(String job_salary) {
        this.job_salary = job_salary;
    }

    public String getJob_location() {
        return job_location;
    }

    public void setJob_location(String job_location) {
        this.job_location = job_location;
    }

    public String getJob_desc() {
        return job_desc;
    }

    public void setJob_desc(String job_desc) {
        this.job_desc = job_desc;
    }

    public String getApplication_status() {
        return application_status;
    }

    public void setApplication_status(String application_status) {
        this.application_status = application_status;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }
}
