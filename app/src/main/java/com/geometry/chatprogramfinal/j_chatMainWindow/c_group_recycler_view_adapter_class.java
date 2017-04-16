package com.geometry.chatprogramfinal.j_chatMainWindow;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geometry.chatprogramfinal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class c_group_recycler_view_adapter_class extends RecyclerView.Adapter<d_group_view_holder_class>
{

    private List<String> id_entry = new ArrayList<>();
    HashMap<String, ChatMessage_data_model_class> currentItemsLinkedHmap = new HashMap<String, ChatMessage_data_model_class>();
    Activity activity;

   // public c_group_recycler_view_adapter_class(List<a_data_group_model_class> items) { mDatasetD = items; }
  // public c_group_recycler_view_adapter_class(LinkedHashMap<String, ChatMessage_data_model_class> currentItemsLinkedHmap, List<String> id_entry )
    public c_group_recycler_view_adapter_class(HashMap<String, ChatMessage_data_model_class> currentItemsLinkedHmap, List<String> id_entry, Activity activity)
    {
        this.id_entry = id_entry;
        this.currentItemsLinkedHmap =currentItemsLinkedHmap;

        this.activity=activity;

    }
    @Override
    public d_group_view_holder_class onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_cardview_a_d_chat_adapter_class, parent, false);

        d_group_view_holder_class vh = new d_group_view_holder_class(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(d_group_view_holder_class holder, final int position)
    {
        //String name = currentItemsLinkedHmap.get(id_entry.get(position).toString()).getMessage();
        //String online = currentItemsLinkedHmap.get(id_entry.get(position).toString()).getStatus();
        // holder.online.setText(online);


        //holder.name.setText(name);

     //   holder.name.setText( currentItemsLinkedHmap.get(id_entry.get(position)).g );
       // holder.online.setText(currentItemsLinkedHmap.get(id_entry.get(position)).toString());
        holder.bind(currentItemsLinkedHmap.get(id_entry.get(position).toString()));


    }

    @Override
    public int getItemCount() {
        return currentItemsLinkedHmap.size();
    }


}
