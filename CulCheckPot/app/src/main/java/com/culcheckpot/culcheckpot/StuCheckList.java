package com.culcheckpot.culcheckpot;

public class StuCheckList {

    int courseID;
    String StuDate;
    String attendText;

    public StuCheckList(int courseID, String stuDate, String attendText) {
        this.courseID = courseID;
        StuDate = stuDate;
        this.attendText = attendText;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getStuDate() {
        return StuDate;
    }

    public void setStuDate(String stuDate) {
        StuDate = stuDate;
    }

    public String getAttendText() {
        return attendText;
    }

    public void setAttendText(String attendText) {
        this.attendText = attendText;
    }
}