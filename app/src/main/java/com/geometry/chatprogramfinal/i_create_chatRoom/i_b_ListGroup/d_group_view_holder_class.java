package com.geometry.chatprogramfinal.i_create_chatRoom.i_b_ListGroup;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.geometry.chatprogramfinal.R;

public class d_group_view_holder_class extends RecyclerView.ViewHolder
{
    public TextView name;

    public CardView GroupListCard;
   // public ImageButton mDeleteBtn;

    public d_group_view_holder_class(View v)
    {
        super(v);
        name = (TextView) v.findViewById(R.id.name);

        GroupListCard = (CardView) v.findViewById(R.id.GroupListCard);
      //  mDeleteBtn = (ImageButton) v.findViewById(R.id.delete_btn);
    }
}