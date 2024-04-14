package com.example.campusrecruitmentsystem.StudentTest;

public class student_test_model {
    String question_text,option_1,option_2,option_3,correct_ans;

    public student_test_model() {
    }

    public student_test_model(String question_text, String option_1, String option_2, String option_3, String correct_ans) {
        this.question_text = question_text;
        this.option_1 = option_1;
        this.option_2 = option_2;
        this.option_3 = option_3;
        this.correct_ans = correct_ans;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getOption_1() {
        return option_1;
    }

    public void setOption_1(String option_1) {
        this.option_1 = option_1;
    }

    public String getOption_2() {
        return option_2;
    }

    public void setOption_2(String option_2) {
        this.option_2 = option_2;
    }

    public String getOption_3() {
        return option_3;
    }

    public void setOption_3(String option_3) {
        this.option_3 = option_3;
    }

    public String getCorrect_ans() {
        return correct_ans;
    }

    public void setCorrect_ans(String correct_ans) {
        this.correct_ans = correct_ans;
    }
}
