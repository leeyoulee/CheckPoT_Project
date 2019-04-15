package com.culcheckpot.culcheckpot;

public class ProCheckList {

    int courseID;
    String ProDate;

    public ProCheckList(int courseID, String proDate) {
        this.courseID = courseID;
        ProDate = proDate;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getProDate() {
        return ProDate;
    }

    public void setProDate(String proDate) {
        ProDate = proDate;
    }
}