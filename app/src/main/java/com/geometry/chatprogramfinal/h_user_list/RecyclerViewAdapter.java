package com.geometry.chatprogramfinal.h_user_list;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geometry.chatprogramfinal.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder>
{

    private List<String> id_entry = new ArrayList<>();
    LinkedHashMap <String, Item> currentItemsLinkedHmap = new LinkedHashMap<String, Item>();

   // public RecyclerViewAdapter(List<Item> items) { mDatasetD = items; }
   public RecyclerViewAdapter(LinkedHashMap<String, Item> currentItemsLinkedHmap,List<String> id_entry )
   {
       this.id_entry = id_entry;
       this.currentItemsLinkedHmap =currentItemsLinkedHmap;
   }
    @Override
    public  ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_cardview_a_b_userlist_adapter_class, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
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

     //   holder.name.setText( currentItemsLinkedHmap.get(id_entry.get(position)).g );
       // holder.online.setText(currentItemsLinkedHmap.get(id_entry.get(position)).toString());


    }

    @Override
    public int getItemCount() {
        return currentItemsLinkedHmap.size();
    }
}
