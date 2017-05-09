package com.geometry.chatprogramfinal.i_create_chatRoom.i_b_ListGroup;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.c_homePage.ChatMain_activity;
import com.geometry.chatprogramfinal.i_create_chatRoom.i_a_make_room.GroupPermission_class;
import com.geometry.chatprogramfinal.i_create_chatRoom.i_a_make_room.b_group_data_model;
import com.geometry.chatprogramfinal.j_chatMainWindow.ChatActivity;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.geometry.chatprogramfinal.z_d_Parcelable.chatData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;




public class c_group_recycler_view_adapter_class extends RecyclerView.Adapter<d_group_view_holder_class>
{

    private List<String> id_entry = new ArrayList<>();
    LinkedHashMap <String, b_group_data_model> currentItemsLinkedHmap = new LinkedHashMap<String, b_group_data_model>();

   // public c_chat_recycler_view_adapter_class(List<a_data_group_model_class> items) { mDatasetD = items; }
   public c_group_recycler_view_adapter_class(LinkedHashMap<String, b_group_data_model> currentItemsLinkedHmap, List<String> id_entry )
   {
       this.id_entry = id_entry;
       this.currentItemsLinkedHmap =currentItemsLinkedHmap;
   }
    @Override
    public d_group_view_holder_class onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_cardview_a_c_group_adapter_class, parent, false);

        d_group_view_holder_class vh = new d_group_view_holder_class(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final d_group_view_holder_class holder, final int position)
    {

        Log.d("TEST VIEW HOLDER", position+"onCreate called");

        /*
        if(currentItemsLinkedHmap.get(id_entry.get(position).toString()).isDisplay()) {

            String name = currentItemsLinkedHmap.get(id_entry.get(position).toString()).getGroup_name();
            //String online = currentItemsLinkedHmap.get(id_entry.get(position).toString()).getStatus();
           // holder.online.setText(online);
            holder.groupName.setText(name);
        }
        else
        {
          holder.GroupListCard.setVisibility(View.GONE);
        }*/

        final b_group_data_model data = currentItemsLinkedHmap.get(id_entry.get(position).toString());

        //String name = currentItemsLinkedHmap.get(id_entry.get(position).toString()).getGroup_name();
        //String online = currentItemsLinkedHmap.get(id_entry.get(position).toString()).getStatus();
        // holder.online.setText(online);
        String name = data.getGroup_name();
        holder.groupName.setText(name);
        Log.d("TEST_VIEW_HOLDER", position+"group Name"+name);

        holder.onClickListenerName = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /// button click event
                String groupId = id_entry.get(position);

                if(ChatMain_activity.TOAST_CONTROL)
                    helperFunctions_class.showToast(v.getContext(),data.getOwner()+"data--Main"+ChatMain_activity.userId+"posi"+position);

                if(data.getOwner().equals(ChatMain_activity.userId))
                {

                    if(ChatMain_activity.TOAST_CONTROL)
                        helperFunctions_class.showToast(v.getContext(),"  Permission::"+groupId);
                    chatData chatdata = new chatData(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            groupId
                            , 0

                    );
                    Intent openDetailIntent = new Intent(v.getContext(), ChatActivity.class);
                    openDetailIntent.putExtra("chatData", chatdata);
                    v.getContext().startActivity(openDetailIntent);
                }
                else
                {
                    if(ChatMain_activity.TOAST_CONTROL)
                        helperFunctions_class.showToast(v.getContext(),"No Permission");
                }


            }
        };

        holder.groupName.setOnClickListener(holder.onClickListenerName);


        holder.onClickListenerNameJoin =  new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                if(data.getOwner().equals(ChatMain_activity.userId))
                {
                    helperFunctions_class.showToast(v.getContext(),"You are the owner of the group. \n You can directly enter the group");

                }
                else
                {
                    DatabaseReference chatIdatLogin;
                    chatIdatLogin= FirebaseDatabase.getInstance()
                            .getReference().child("Permission_Group").child(data.getGroup_name());
                    GroupPermission_class groupPermission_class = new GroupPermission_class();
                    groupPermission_class.setStatus(1);
                    groupPermission_class.setUserId(ChatMain_activity.userId);
                    groupPermission_class.setUserName(ChatMain_activity.UserName);
                    chatIdatLogin.setValue(groupPermission_class,
                            new DatabaseReference.CompletionListener() {

                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                                {

                                    // call_home_page();

                                    if(ChatMain_activity.TOAST_CONTROL)
                                        helperFunctions_class.showToast(v.getContext(),"Done Join Request");

                                }
                            });
                }
                if(ChatMain_activity.TOAST_CONTROL)
                    helperFunctions_class.showToast(v.getContext(),"Join Group Name");

            }
        };
        holder.JoinGroup.setOnClickListener(holder.onClickListenerNameJoin);

        //holder.JoinGroup.setOnClickListener();


        holder.LeaveGroup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(ChatMain_activity.TOAST_CONTROL)
                    helperFunctions_class.showToast(v.getContext(),"Leave Select Group Name");

            }
        });
     //   holder.groupName.setText( currentItemsLinkedHmap.get(id_entry.get(position)).g );
       // holder.online.setText(currentItemsLinkedHmap.get(id_entry.get(position)).toString());


    }

    @Override
    public int getItemCount() {
        return currentItemsLinkedHmap.size();
    }
}
