package com.geometry.chatprogramfinal.h_user_list;

public class Item
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

    public Item()
    {

    }

    public Item(String firebaseUserId, String username, String status) {
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