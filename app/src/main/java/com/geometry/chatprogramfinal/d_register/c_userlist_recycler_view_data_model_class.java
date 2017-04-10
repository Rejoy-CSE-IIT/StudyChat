package com.geometry.chatprogramfinal.d_register;


public class c_userlist_recycler_view_data_model_class
{
    public String getFirebaseUserId() {
        return firebaseUserId;
    }

    public void setFirebaseUserId(String firebaseUserId) {
        this.firebaseUserId = firebaseUserId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String firebaseUserId;

    private String Username;
    private String status="OFF LINE";



    public c_userlist_recycler_view_data_model_class(String firebaseUserId, String Username, String status )
    {

        this.firebaseUserId = firebaseUserId;
        this.Username = Username;
        this.status = status;

    }

    public c_userlist_recycler_view_data_model_class()
    {

    }






}
