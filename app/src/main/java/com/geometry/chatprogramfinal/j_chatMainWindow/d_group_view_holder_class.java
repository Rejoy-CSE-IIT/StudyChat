package com.geometry.chatprogramfinal.j_chatMainWindow;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.c_homePage.ChatMain_activity;

public class d_group_view_holder_class extends RecyclerView.ViewHolder
{

    View mView;

    public TextView chatmessage_S ;
    public TextView chatuser_S;
    public TextView chat_time_S  ;
    public ImageView pic_S   ;
    public RelativeLayout Send;


    public TextView chatmessage_R ;
    public TextView chatuser_R;
    public TextView chat_time_R  ;
    public ImageView pic_R   ;
    public RelativeLayout  Receive;

    public d_group_view_holder_class(View v)
    {
        super(v);
        mView = itemView;

        Send = (RelativeLayout)itemView.findViewById(R.id.send);
        Receive = (RelativeLayout)itemView.findViewById(R.id.receive);

        chatmessage_S           = (TextView)  itemView.findViewById(R.id.chatmessage_S);
        chatuser_S              = (TextView)  itemView.findViewById(R.id.chatuser_S);
        chat_time_S             = (TextView)  itemView.findViewById(R.id.chat_time_S);
        pic_S                   = (ImageView) itemView.findViewById(R.id.chat_photo_S);


        chatmessage_R           = (TextView)  itemView.findViewById(R.id.chatmessage_R);
        chatuser_R              = (TextView)  itemView.findViewById(R.id.chatuser_R);
        chat_time_R             = (TextView)  itemView.findViewById(R.id.chat_time_R);
        pic_R                   = (ImageView) itemView.findViewById(R.id.chat_photo_R);
    }

    public void bind(ChatMessage_data_model_class chat)
    {

        // Send.setVisibility(View.GONE);

        if(!chat.isDisplay())
        {
            Receive.setVisibility(View.GONE);
            Send.setVisibility(View.GONE);
            return;
        }


        if (chat.getSender_id().equals(ChatMain_activity.userId))
        {

            Receive.setVisibility(View.GONE);
            if(chat.getImage_url().equals("noImage"))
            {
                pic_S.setVisibility(View.GONE);

            }
            else
            {

/*
                Glide.with((Activity) mView.getContext())
                        .load(chat.getImage_url())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .override(chat.getWidth(), chat.getHeight())
                        .into(pic_S);*/







            }
            chatmessage_S.setText(chat.getMessage());
            chatuser_S.setText(chat.getUser_name());
            chat_time_S.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", chat.getMessageTime()));




        }
        else
        {
            Send.setVisibility(View.GONE);

            if(chat.getImage_url().equals("noImage"))
            {
                pic_R.setVisibility(View.GONE);
            }
            else
            {
                // System.out.print("HAI");
/*
                Glide.with((Activity) mView.getContext())
                        .load(chat.getPicId())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .override(chat.getWidth(), chat.getHeight())
                        .into(pic_R);*/

            }




            chatmessage_R.setText(chat.getMessage());
            chatuser_R.setText(chat.getUser_name());
            chat_time_R.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", chat.getMessageTime()));









        }





    }//
}