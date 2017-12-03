package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {

    private int studentID;
    private List<Lesson> alinanDersler =new ArrayList<>();

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public List<Lesson> getAlinanDersler() {
        return alinanDersler;
    }

    public void setAlinanDersler(List<Lesson> alinanDersler) {
        this.alinanDersler = alinanDersler;
    }
}
