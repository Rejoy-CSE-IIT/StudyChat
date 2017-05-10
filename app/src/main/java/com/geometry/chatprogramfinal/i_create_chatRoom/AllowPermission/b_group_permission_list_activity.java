package com.geometry.chatprogramfinal.i_create_chatRoom.AllowPermission;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.c_homePage.ChatMain_activity;
import com.geometry.chatprogramfinal.i_create_chatRoom.i_a_make_room.GroupPermission_class;
import com.geometry.chatprogramfinal.i_create_chatRoom.i_a_make_room.b_group_data_model;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

//http://stackoverflow.com/questions/37390864/how-to-delete-from-firebase-realtime-database
public class b_group_permission_list_activity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener
{

    public  List<String> id_entry = new ArrayList<>();
    public  List<b_group_data_model> Group_entry = new ArrayList<>();

    LinkedHashMap <String, GroupPermission_class> currentItemsLinkedHmap = new LinkedHashMap<String, GroupPermission_class>();


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Query applesQuery;
    ChildEventListener childEventListener;
    DatabaseReference myFirebaseRef;
    // private                                     c_chat_recycler_view_adapter_class recycleView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.homepagemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {







            case R.id.homepage:




                Intent intent = new Intent(b_group_permission_list_activity.this, ChatMain_activity.class);
                startActivity(intent);






                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_group_list_activity);
        setTitle("Pending Permissions");

        this.mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        this.mLayoutManager = new LinearLayoutManager(this);
        this.mRecyclerView.setLayoutManager(mLayoutManager);









        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view)
            {
                d_group_permission_view_holder_class holder = (d_group_permission_view_holder_class) mRecyclerView.getChildViewHolder(view);
               // holder.na.setText("Clicked!");
                /*
                holder.groupName.setOnClickListener(holder.onClickListenerApprove);
                holder.JoinGroup.setOnClickListener(holder.onClickListenerNameJoin);
                holder.LeaveGroup.setOnClickListener(holder.onClickListenerNameLeave);*/
            }

            @Override
            public void onChildViewDetachedFromWindow(View view)
            {
                   d_group_permission_view_holder_class holder = (d_group_permission_view_holder_class) mRecyclerView.getChildViewHolder(view);

             /*  holder.groupName.setOnClickListener(null);
                holder.JoinGroup.setOnClickListener(null);
                holder.LeaveGroup.setOnClickListener(null);*/

               // holder.JoinGroup.setOnClickListener(null);
              //  holder.onClickListenerNameJoin =null;



            }

        });
    }

    protected void onStart()
    {
        super.onStart();
        //Toolbar scrollView = (Toolbar) findViewById(R.id.scrollView);
//        mAdapter.notifyDataSetChanged();


    }

    public void setFirebaseValueListener()
    {

        myFirebaseRef = FirebaseDatabase.getInstance().getReference();
        //helperFunctions_class.showToast(b_group_list_activity.this,"Children =>"+myFirebaseRef.getKey());
          applesQuery =myFirebaseRef.child("Permission_Group").orderByChild("owner").equalTo(ChatMain_activity.userId);

        childEventListener= new ChildEventListener()
        {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {

                GroupPermission_class adatamodelclass = dataSnapshot.getValue(GroupPermission_class.class);
                  id_entry.add(dataSnapshot.getKey());
                  currentItemsLinkedHmap.put(dataSnapshot.getKey(), adatamodelclass);
                helperFunctions_class.showToast(b_group_permission_list_activity.this,"Group Name =>"+ adatamodelclass.getGroupName()+"owner"+adatamodelclass.getUserName());
                  mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {


                GroupPermission_class adatamodelclass = dataSnapshot.getValue(GroupPermission_class.class);
                id_entry.add(dataSnapshot.getKey());
                currentItemsLinkedHmap.put(dataSnapshot.getKey(), adatamodelclass);
                helperFunctions_class.showToast(b_group_permission_list_activity.this,"Group Name =>"+ adatamodelclass.getGroupName()+"owner"+adatamodelclass.getUserName());
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {


                GroupPermission_class adatamodelclass = dataSnapshot.getValue(GroupPermission_class.class);


                adatamodelclass.setDisplay(false);
                currentItemsLinkedHmap.put(dataSnapshot.getKey(), adatamodelclass);
                mAdapter.notifyDataSetChanged();
              //  helperFunctions_class.showToast(b_group_permission_list_activity.this, "a_data_group_model_class Deleted =>" + adatamodelclass.getGroup_name());


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };

        applesQuery.addChildEventListener(childEventListener);


    }


    @Override
    public void onBackPressed()
    {
        // super.onBackPressed();
    //    finish();
        //thats it
    }
    @Override
    public void onPause()
    {
        if(ChatMain_activity.TOAST_CONTROL)
            helperFunctions_class.showToast(b_group_permission_list_activity.this,"Inside Pause Chat");
        super.onPause();
        applesQuery.removeEventListener(childEventListener);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        mAdapter = new c_group_recycler_view_permission_adapter_class(currentItemsLinkedHmap,id_entry);
        mRecyclerView.setAdapter(mAdapter);




        setFirebaseValueListener();
    }
}
