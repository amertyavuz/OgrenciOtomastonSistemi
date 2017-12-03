package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {

    private int teacherID;

    private List<Lesson> alinanDersler =new ArrayList<>();

    public List<Lesson> getAlinanDersler() {
        return alinanDersler;
    }

    public void setAlinanDersler(List<Lesson> alinanDersler) {
        this.alinanDersler = alinanDersler;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }
}
