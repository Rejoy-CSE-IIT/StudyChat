package com.geometry.chatprogramfinal.i_create_chatRoom.i_a_make_room;

/**
 * Created by Rijoy on 4/11/2017.
 */

public class b_group_data_model
{
    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    String group_name;
    String owner;

    public b_group_data_model(String group_name, String Owner)
    {
        this.group_name = group_name;
        this.owner = Owner;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    boolean display=true;
    public b_group_data_model()
    {

    }
}
