package com.geometry.chatprogramfinal.i_create_chatRoom.i_b_ListGroup;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.geometry.chatprogramfinal.R;
import com.google.firebase.database.DatabaseReference;

public class d_group_view_holder_class extends RecyclerView.ViewHolder
{
    public TextView groupName;
    public TextView JoinGroup;
    public TextView LeaveGroup;
    public CardView GroupListCard;

    public View.OnClickListener onClickListenerName =null;
    public View.OnClickListener onClickListenerNameJoin =null;
    public View.OnClickListener onClickListenerNameLeave =null;
    DatabaseReference JoinData_Listner;
    DatabaseReference LeaveData_Listner;
   // public ImageButton mDeleteBtn;

    public d_group_view_holder_class(View v)
    {
        super(v);
        groupName = (TextView) v.findViewById(R.id.groupName);
        JoinGroup  = (TextView) v.findViewById(R.id.JoinGroup);
        LeaveGroup = (TextView) v.findViewById(R.id.LeaveGroup);

        GroupListCard = (CardView) v.findViewById(R.id.GroupListCard);
      //  mDeleteBtn = (ImageButton) v.findViewById(R.id.delete_btn);
    }
}