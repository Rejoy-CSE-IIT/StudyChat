package com.geometry.chatprogramfinal.h_user_list;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.c_homePage.ChatMain_activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class c_userlist_recycler_view_adapter_class extends RecyclerView.Adapter<d_userlist_view_holder_class>
{

    private List<String> id_entry = new ArrayList<>();
    LinkedHashMap <String, a_data_model_class> currentItemsLinkedHmap = new LinkedHashMap<String, a_data_model_class>();

   // public c_chat_recycler_view_adapter_class(List<a_data_group_model_class> items) { mDatasetD = items; }
   public c_userlist_recycler_view_adapter_class(LinkedHashMap<String, a_data_model_class> currentItemsLinkedHmap, List<String> id_entry )
   {
       this.id_entry = id_entry;
       this.currentItemsLinkedHmap =currentItemsLinkedHmap;
   }
    @Override
    public d_userlist_view_holder_class onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_cardview_a_b_userlist_adapter_class, parent, false);

        d_userlist_view_holder_class vh = new d_userlist_view_holder_class(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(d_userlist_view_holder_class holder, final int position)
    {

        if(currentItemsLinkedHmap.get(id_entry.get(position).toString()).getFirebaseUserId().equals(ChatMain_activity.userId))
        {
           holder.userListCard.setVisibility(View.GONE);
        }
        else

        {
            if(currentItemsLinkedHmap.get(id_entry.get(position).toString()).isDisplay()) {

                String name = currentItemsLinkedHmap.get(id_entry.get(position).toString()).getUsername();
                String online = currentItemsLinkedHmap.get(id_entry.get(position).toString()).getStatus();
                holder.online.setText(online);
                holder.name.setText(name);
            }
            else
            {
                holder.userListCard.setVisibility(View.GONE);
            }
        }



     //   holder.groupName.setText( currentItemsLinkedHmap.get(id_entry.get(position)).g );
       // holder.online.setText(currentItemsLinkedHmap.get(id_entry.get(position)).toString());


    }

    @Override
    public int getItemCount() {
        return currentItemsLinkedHmap.size();
    }
}
