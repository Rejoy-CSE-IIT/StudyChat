package com.geometry.chatprogramfinal.a_TestPage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.geometry.chatprogramfinal.R;

public class d_option_recycler_view_view_holder_class extends  RecyclerView.ViewHolder
{

    TextView name;

    View view;


    final Context ctx;



    public d_option_recycler_view_view_holder_class(View itemView, final Context ctx)
    {
        super(itemView);
        this.view =itemView;
        this.ctx=ctx;






    }


    public void bind(final c_option_recycler_view_data_model_class data)
    {

        this.name = (TextView) itemView.findViewById(R.id.name);


        name.setText(data.getOptionName());





    }

}
