package com.geometry.chatprogramfinal.h_Users_List;


public class UserData
{
    public String getFirebaseUserId() {
        return firebaseUserId;
    }

    public void setFirebaseUserId(String firebaseUserId) {
        this.firebaseUserId = firebaseUserId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String firebaseUserId;

    private String userid;
    private String status="OFF LINE";



    public UserData(String firebaseUserId, String userid, String status )
    {

        this.firebaseUserId = firebaseUserId;
        this.userid = userid;
        this.status = status;

    }

    public UserData()
    {

    }






}
