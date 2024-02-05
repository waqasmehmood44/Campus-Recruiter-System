package com.example.campusrecruitmentsystem.Student_Job_Apply;

public class pdfClass {
    public String name, uri;

    public pdfClass(String name, String uri) {
        this.name = name;
        this.uri = uri;
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
}
