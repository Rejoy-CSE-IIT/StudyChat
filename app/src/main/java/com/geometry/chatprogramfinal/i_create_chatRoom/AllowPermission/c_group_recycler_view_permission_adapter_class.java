package com.geometry.chatprogramfinal.i_create_chatRoom.AllowPermission;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.i_create_chatRoom.i_a_make_room.GroupPermission_class;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class c_group_recycler_view_permission_adapter_class extends RecyclerView.Adapter<d_group_permission_view_holder_class>
{

    private List<String> id_entry = new ArrayList<>();
    LinkedHashMap <String, GroupPermission_class> currentItemsLinkedHmap = new LinkedHashMap<String, GroupPermission_class>();

   // public c_chat_recycler_view_adapter_class(List<a_data_group_model_class> items) { mDatasetD = items; }
   public c_group_recycler_view_permission_adapter_class(LinkedHashMap<String, GroupPermission_class> currentItemsLinkedHmap, List<String> id_entry )
   {
       this.id_entry = id_entry;
       this.currentItemsLinkedHmap =currentItemsLinkedHmap;
   }
    @Override
    public d_group_permission_view_holder_class onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_cardview_a_e_group_permission_adapter_class, parent, false);

        d_group_permission_view_holder_class vh = new d_group_permission_view_holder_class(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final d_group_permission_view_holder_class holder, final int position)
    {



        Log.d("TEST VIEW HOLDER", position+"onCreate called");

        final GroupPermission_class data = currentItemsLinkedHmap.get(id_entry.get(position).toString());

        if(data.isDisplay())
        {
            holder.GroupName.setText(data.getGroupName());
            holder.UserName.setText(data.getUserName());


            holder.onClickListenerApprove = new View.OnClickListener()
            {
                @Override
                public void onClick(final View v)
                {
                    holder.Data_Listner= FirebaseDatabase.getInstance()
                            .getReference().child("Permission_Group").child(data.getGroupName()+data.getUserId());

                    holder.Data_Listner.addListenerForSingleValueEvent(new ValueEventListener()
                    {


                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {

                            if(!dataSnapshot.exists())
                            {

                                helperFunctions_class.showToast(v.getContext(),"No data System Error");
                                // holder.JoinData_Listner.addListenerForSingleValueEvent(null);
                            }
                            else
                            {
                                GroupPermission_class data = new GroupPermission_class();
                                data = dataSnapshot.getValue(GroupPermission_class.class);
                                if(data.getStatus()==1)
                                {
                                   data.setStatus(2);
                                    data.setDisplay(false);

                                    holder.Data_Listner.setValue(data,
                                            new DatabaseReference.CompletionListener() {

                                                @Override
                                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                                                {

                                                    // call_home_page();

                                                 //   if(ChatMain_activity.TOAST_CONTROL)
                                                        helperFunctions_class.showToast(v.getContext(),"Assigned Permission");

                                                }
                                            });

                                }
                                else
                                {
                                    helperFunctions_class.showToast(v.getContext(),"Error");
                                }
                                //  holder.JoinData_Listner.addListenerForSingleValueEvent(null);
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            };

            holder.Approve.setOnClickListener(holder.onClickListenerApprove);

        }
        else
        {
            holder.GroupListCard.setVisibility(View.GONE);
        }




    }

    @Override
    public int getItemCount() {
        return currentItemsLinkedHmap.size();
    }
}
