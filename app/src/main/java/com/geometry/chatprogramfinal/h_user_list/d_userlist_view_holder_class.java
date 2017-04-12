package com.geometry.chatprogramfinal.h_user_list;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.geometry.chatprogramfinal.R;

public class d_userlist_view_holder_class extends RecyclerView.ViewHolder
{
    public TextView name;
    public TextView online;
    public CardView userListCard;
   // public ImageButton mDeleteBtn;

    public d_userlist_view_holder_class(View v)
    {
        super(v);
        name = (TextView) v.findViewById(R.id.name);
        online = (TextView) v.findViewById(R.id.online);
        userListCard = (CardView) v.findViewById(R.id.userListCard);
      //  mDeleteBtn = (ImageButton) v.findViewById(R.id.delete_btn);
    }
}