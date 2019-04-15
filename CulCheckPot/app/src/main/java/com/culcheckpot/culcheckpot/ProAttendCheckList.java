package com.culcheckpot.culcheckpot;

public class ProAttendCheckList {
        String userName;
        String userNum;
        String attendText;

    public ProAttendCheckList(String userName, String userNum, String attendText) {
        this.userName = userName;
        this.userNum = userNum;
        this.attendText = attendText;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getAttendText() {
        return attendText;
    }

    public void setAttendText(String attendText) {
        this.attendText = attendText;
    }
}
