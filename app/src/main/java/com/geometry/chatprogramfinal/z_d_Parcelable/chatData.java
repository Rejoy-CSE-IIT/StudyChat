package com.geometry.chatprogramfinal.z_d_Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by Rijoy on 4/13/2017.
 */
//http://alexzh.com/uncategorized/passing-object-by-intent/
public class chatData implements Parcelable
{

  //  private String groupName;
  //  private String surname;
  //  private int age;

    public String getSend_id() {
        return send_id;
    }

    public void setSend_id(String send_id) {
        this.send_id = send_id;
    }

    public String getTarget_id() {
        return target_id;
    }

    public void setTarget_id(String target_id) {
        this.target_id = target_id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    String send_id;
    String target_id;
    String chat_id;
    int group_chat_flag;

    public int getGroup_chat_flag() {
        return group_chat_flag;
    }

    public void setGroup_chat_flag(int group_chat_flag) {
        this.group_chat_flag = group_chat_flag;
    }








    protected chatData(Parcel in)
    {
        send_id = in.readString();
        target_id = in.readString();
        chat_id = in.readString();
        group_chat_flag=in.readInt();
      //  age = in.readInt();
    }
    public chatData(String send_id, String target_id,int group_chat_flag)
    {
        this.send_id = send_id;
        this.target_id = target_id;

       // this.chat_id = chat_id;
        if(group_chat_flag==0)
        {
            this.chat_id = this.target_id;
        }
        else
        {
            String[] target_dest_id_maker = new String[2];
            target_dest_id_maker[0] = this.send_id;
            target_dest_id_maker[1] = this.target_id;
            Arrays.sort(target_dest_id_maker);
            this.chat_id = target_dest_id_maker[0] + target_dest_id_maker[1];

        }
        this.group_chat_flag = group_chat_flag;
    }
    public static final Creator<chatData> CREATOR = new Creator<chatData>()
    {
        @Override
        public chatData createFromParcel(Parcel in)
        {
            return new chatData(in);
        }
        @Override
        public chatData[] newArray(int size)
        {
            return new chatData[size];
        }
    };
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(chat_id);
        dest.writeString(target_id);
        dest.writeString(chat_id);
        dest.writeInt(group_chat_flag);
    }

}
