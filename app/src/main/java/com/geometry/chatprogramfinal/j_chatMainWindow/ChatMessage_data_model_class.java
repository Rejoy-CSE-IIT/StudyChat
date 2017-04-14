package com.geometry.chatprogramfinal.j_chatMainWindow;

/**
 * Created by Rijoy on 4/13/2017.
 */

public class ChatMessage_data_model_class
{
    public ChatMessage_data_model_class(String sender_id, String reciever_id, String message_Type, String imageurl, String message, String username, String datetime) {
        this.sender_id = sender_id;
        this.reciever_id = reciever_id;
        this.message_Type = message_Type;
        image_url = imageurl;
        this.message = message;
        user_name = username;
        date_time = datetime;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReciever_id() {
        return reciever_id;
    }

    public void setReciever_id(String reciever_id) {
        this.reciever_id = reciever_id;
    }

    public String getMessage_Type() {
        return message_Type;
    }

    public void setMessage_Type(String message_Type) {
        this.message_Type = message_Type;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    String sender_id;
    String reciever_id;
    //Either "userChat" or "GroupChat"
    String message_Type ="userChat";
    String image_url =null;
    String message;
    String user_name;
    String date_time;
    public ChatMessage_data_model_class()
    {

    }

}
