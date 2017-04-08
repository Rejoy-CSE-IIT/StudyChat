package com.geometry.chatprogramfinal.h_Users_List;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.a_TestPage.c_option_recycler_view_data_model_class;

import java.util.ArrayList;

public class a_userlist_recycler_view_adapter_class extends RecyclerView.Adapter<b_userlist_recycler_view_view_holder_class>
{

    private ArrayList<c_option_recycler_view_data_model_class> dataSet;
    Context ctx;



    public a_userlist_recycler_view_adapter_class(ArrayList<c_option_recycler_view_data_model_class> data, Context ctx)
    {
        this.dataSet = data;
        this.ctx =ctx;
    }

    @Override
    public b_userlist_recycler_view_view_holder_class onCreateViewHolder(ViewGroup parent,
                                                                         int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_a_a_option_recycler_view_adapter_class, parent, false);



        b_userlist_recycler_view_view_holder_class myViewHolder = new b_userlist_recycler_view_view_holder_class(view,ctx);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final b_userlist_recycler_view_view_holder_class holder, final int listPosition)
    {
        holder.bind(dataSet.get(listPosition));



    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
