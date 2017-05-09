package com.geometry.chatprogramfinal.i_create_chatRoom.i_a_make_room;

/**
 * Created by Rijoy on 5/9/2017.
 */

public class GroupPermission_class
{






    String                       userId;
    int                          Status;
    String                     UserName;
    String                      GroupName;

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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    String                      Owner;
}
