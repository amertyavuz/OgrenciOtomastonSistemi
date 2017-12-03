package com.company.model;

public class Notes extends Lesson {

    private int lessonID;
    private int studentID;
    private int note1;
    private int note2;



    public int getLessonID(){
        return lessonID;
    }

    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getNote1() {
        return note1;
    }

    public void setNote1(int note1) {
        this.note1 = note1;
    }

    public int getNote2() {
        return note2;
    }

    public void setNote2(int note2) {
        this.note2 = note2;
    }
}
