package com.geometry.chatprogramfinal.i_create_chatRoom.i_b_ListGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.c_homePage.ChatMain_activity;
import com.geometry.chatprogramfinal.f_login.login_activity;
import com.geometry.chatprogramfinal.i_create_chatRoom.i_a_make_room.b_group_data_model;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class b_group_list_activity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener
{

    private List<String> id_entry = new ArrayList<>();
    LinkedHashMap <String, b_group_data_model> currentItemsLinkedHmap = new LinkedHashMap<String, b_group_data_model>();


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    DatabaseReference myFirebaseRef;



    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_group_list_activity);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("List of Groups");

        this.mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        this.mLayoutManager = new LinearLayoutManager(this);
        this.mRecyclerView.setLayoutManager(mLayoutManager);



        mAdapter = new c_group_recycler_view_adapter_class(currentItemsLinkedHmap,id_entry);
        mRecyclerView.setAdapter(mAdapter);


        // [START config_signin]
        // Configure Google Sign In
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        setFirebaseValueListener();


    }

    public void setFirebaseValueListener()
    {


        myFirebaseRef = FirebaseDatabase.getInstance().getReference().child("GroupName");
        helperFunctions_class.showToast(b_group_list_activity.this,"Children =>"+myFirebaseRef.getKey());

        myFirebaseRef.addChildEventListener(new ChildEventListener()
        {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {

                b_group_data_model adatamodelclass = dataSnapshot.getValue(b_group_data_model.class);
                id_entry.add(adatamodelclass.getOwner());
                currentItemsLinkedHmap.put(adatamodelclass.getOwner(), adatamodelclass);
                helperFunctions_class.showToast(b_group_list_activity.this,"Name =>"+ adatamodelclass.getGroup_name());
                mAdapter.notifyDataSetChanged();
             }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {

                b_group_data_model adatamodelclass = dataSnapshot.getValue(b_group_data_model.class);
               // adatamodelclass.setDisplay(false);

                //Fire base id never changes,user name
                currentItemsLinkedHmap.put(adatamodelclass.getOwner(), adatamodelclass);
                mAdapter.notifyDataSetChanged();
                helperFunctions_class.showToast(b_group_list_activity.this,"a_data_group_model_class changed =>"+ adatamodelclass.getGroup_name());


                /*
                a_data_group_model_class adatamodelclass = dataSnapshot.getValue(a_data_group_model_class.class);
                //   a_data_group_model_class adatamodelclass = new a_data_group_model_class(itemSnapshot.getKey(), itemSnapshot.child("name").getValue(String.class));
                a_data_group_model_class itemT = new a_data_group_model_class(adatamodelclass.getFirebaseUserId(),adatamodelclass.getUsername(),adatamodelclass.getStatus() );
                //currentItemsDict.add(itemT);
                helperFunctions_class.showToast(b_group_list_activity.this,"Name =>"+itemT.getUsername());
                helperFunctions_class.showToast(b_group_list_activity.this,"a_data_group_model_class Changed =>"+adatamodelclass.getUsername());
*/
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

                b_group_data_model adatamodelclass = dataSnapshot.getValue(b_group_data_model.class);


                    adatamodelclass.setDisplay(false);
                    currentItemsLinkedHmap.put(adatamodelclass.getOwner(), adatamodelclass);
                    mAdapter.notifyDataSetChanged();
                    helperFunctions_class.showToast(b_group_list_activity.this, "a_data_group_model_class Deleted =>" + adatamodelclass.getGroup_name());


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    public void pushValue()
    {
        Map<String, Object> item = new HashMap<String, Object>();
        //item.put("name", "item "+ (currentItemsDict.size() + 1));
        myFirebaseRef.push().setValue(item);
    }


    private void call_login(a_data_group_model_class adatamodelclass)
    {

        ChatMain_activity.UserName=null;
        ChatMain_activity.userId=null;




        if( adatamodelclass.getLoginType().equals("Normal"))
        {

            helperFunctions_class.showToast(b_group_list_activity.this, "Current user deleted Google Login!!!");

            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>()
                    {
                        @Override
                        public void onResult(@NonNull Status status)
                        {
                            helperFunctions_class.showToast(b_group_list_activity.this, "BBDOne googlesign out!!!");

                            FirebaseAuth.getInstance().signOut();


                            startActivity(new Intent(b_group_list_activity.this, login_activity.class));
                            finish();

                        }
                    });

        }
        else
        {
            helperFunctions_class.showToast(b_group_list_activity.this, "user deleted  Normal out!!!");

            startActivity(new Intent(b_group_list_activity.this, login_activity.class));
            finish();
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
