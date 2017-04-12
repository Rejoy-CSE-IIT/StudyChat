package com.geometry.chatprogramfinal.i_create_chatRoom.i_b_ListGroup;

public class a_data_group_model_class
{
    public String getFirebaseUserId()
    {
        return firebaseUserId;
    }

    public void setFirebaseUserId(String firebaseUserId) {
        this.firebaseUserId = firebaseUserId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public a_data_group_model_class()
    {

    }

    public a_data_group_model_class(String firebaseUserId, String username, String status) {
        this.firebaseUserId = firebaseUserId;
        Username = username;
        this.status = status;
    }

    private String firebaseUserId;

    private String Username;
    private String status="OFF LINE";

    public String getLoginType() {
        return LoginType;
    }

    public void setLoginType(String loginType) {
        LoginType = loginType;
    }

    private String LoginType="Normal";

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    private boolean display=true;
}