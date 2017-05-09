package com.geometry.chatprogramfinal.i_create_chatRoom.i_a_make_room;

/**
 * Created by Rijoy on 5/9/2017.
 */

public class GroupPermission_class {
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    String  userId;
    int Status;
}
