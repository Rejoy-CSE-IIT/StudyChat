package com.geometry.chatprogramfinal.j_chatMainWindow;

import java.util.Date;

/**
 * Created by Rijoy on 4/13/2017.
 */

public class ChatMessage_data_model_class
{

    public ChatMessage_data_model_class(String sender_id, String reciever_id, String message_Type, String imageurl, String message, String username,int width,int height) {
        this.sender_id = sender_id;
        this.reciever_id = reciever_id;
        this.message_Type = message_Type;
        image_url = imageurl;
        this.message = message;
        user_name = username;
        messageTime = new Date().getTime();
        this.width=width;
        this.height=height;
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


    int width;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    int height;
    String sender_id;
    String reciever_id;
    //Either "userChat" or "GroupChat"
    String message_Type ="userChat";
    String image_url =null;
    String message;
    String user_name;

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    long messageTime;

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    boolean display =true;
    public ChatMessage_data_model_class()
    {

    }

}
