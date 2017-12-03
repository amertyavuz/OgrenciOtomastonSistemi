package com.company.model;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.PriorityQueue;

public class Lesson {

    private String lessonName ;
    private int lessonID;
    private int teacherID;

    public int getDersi_veren_ID() {
        return teacherID;
    }

    public void setDersi_veren_ID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getLessonID() {
        return lessonID;
    }

    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

}
