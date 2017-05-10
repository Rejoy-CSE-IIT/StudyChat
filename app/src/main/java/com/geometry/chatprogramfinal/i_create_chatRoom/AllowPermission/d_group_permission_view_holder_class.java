package com.geometry.chatprogramfinal.i_create_chatRoom.AllowPermission;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.geometry.chatprogramfinal.R;

public class d_group_permission_view_holder_class extends RecyclerView.ViewHolder
{
    public TextView GroupName;
    public TextView UserName;
    public CardView GroupListCard;





    public d_group_permission_view_holder_class(View v)
    {
        super(v);
        GroupName = (TextView) v.findViewById(R.id.GroupName);
        UserName  = (TextView) v.findViewById(R.id.UserName);


        GroupListCard = (CardView) v.findViewById(R.id.GroupListCard);
      //  mDeleteBtn = (ImageButton) v.findViewById(R.id.delete_btn);
    }
}