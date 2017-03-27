package com.geometry.chatprogramfinal.a_TestPage;


/*

//http://stackoverflow.com/questions/27609442/how-to-get-the-sha-1-fingerprint-certificate-in-android-studio-for-debug-mode
    //firebase files updated lates


 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.b_analytics.a_analytics;
import com.geometry.chatprogramfinal.z_a_recyler_listener.recyclerTouchListener_class;

import java.util.ArrayList;

public class e_option_selector_recycler_view_activity extends AppCompatActivity
{
    //Static Files

    public static boolean                                              TESTING_MODE =true;
    private static RecyclerView.Adapter                                            adapter;
    private RecyclerView.LayoutManager                                       layoutManager;
    private static RecyclerView                                               recyclerView;
    private static ArrayList<c_option_recycler_view_data_model_class> data;
    static View.OnClickListener                                          myOnClickListener;
    private static ArrayList<Integer>                                         removedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_e_option_selector_recycler_view_activity);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);



        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);


        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());




        recyclerView.addOnItemTouchListener(new recyclerTouchListener_class(getApplicationContext(), recyclerView, new recyclerTouchListener_class.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {

                Intent intent;
                c_option_recycler_view_data_model_class dataE = data.get(position);

                switch(dataE.getOptionName())
                {


                    case "Chat App":

                        if(e_option_selector_recycler_view_activity.TESTING_MODE)
                            Toast.makeText(e_option_selector_recycler_view_activity.this, "ChatApp"+dataE.getOptionName(), Toast.LENGTH_LONG).show();


                        intent = new Intent(e_option_selector_recycler_view_activity.this, e_option_selector_recycler_view_activity.class);
                        startActivity(intent);

                        break;


                    case "Analytics":

                        if(e_option_selector_recycler_view_activity.TESTING_MODE)
                            Toast.makeText(e_option_selector_recycler_view_activity.this, "Analytics"+dataE.getOptionName(), Toast.LENGTH_LONG).show();


                        intent = new Intent(e_option_selector_recycler_view_activity.this, a_analytics.class);
                        startActivity(intent);

                        break;



                    //Toast.makeText(getApplicationContext(), dataE.getOptionName() + " is selected!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        data = new ArrayList<c_option_recycler_view_data_model_class>();

        for (int i = 0; i < b_option_recycler_view_data_store_class.nameArray.length; i++)
        {
            data.add(new c_option_recycler_view_data_model_class( b_option_recycler_view_data_store_class.nameArray[i]));
        }



        adapter = new a_option_recycler_view_adapter_class(data,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }










}
