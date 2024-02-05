package com.example.campusrecruitmentsystem.Student_Job_Apply;

public class pdfClass {
    public String name, uri, rec_id, job_id,student_id;

    public pdfClass(String name, String uri, String rec_id, String job_id, String student_id) {
        this.name = name;
        this.uri = uri;
        this.rec_id = rec_id;
        this.job_id = job_id;
        this.student_id = student_id;
    }

    public pdfClass() {
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
}
