package com.geometry.chatprogramfinal.h_user_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.c_homePage.ChatMain_activity;
import com.geometry.chatprogramfinal.f_login.login_activity;
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

public class b_user_list_activity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener
{

    private List<String> id_entry = new ArrayList<>();
    LinkedHashMap <String, a_data_model_class> currentItemsLinkedHmap = new LinkedHashMap<String, a_data_model_class>();


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    DatabaseReference myFirebaseRef;
    private TextView currentDBUrl;


    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_userlist_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("user List");
        this.mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        this.mLayoutManager = new LinearLayoutManager(this);
        this.mRecyclerView.setLayoutManager(mLayoutManager);


        currentDBUrl = (TextView) findViewById(R.id.currentDB);

        mAdapter = new c_userlist_recycler_view_adapter_class(currentItemsLinkedHmap,id_entry);
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


        myFirebaseRef = FirebaseDatabase.getInstance().getReference().child("GroupChatIds");
        helperFunctions_class.showToast(b_user_list_activity.this,"Children =>"+myFirebaseRef.getKey());

        myFirebaseRef.addChildEventListener(new ChildEventListener()
        {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                a_data_model_class adatamodelclass = dataSnapshot.getValue(a_data_model_class.class);
                id_entry.add(adatamodelclass.getFirebaseUserId());
                currentItemsLinkedHmap.put(adatamodelclass.getFirebaseUserId(), adatamodelclass);
                helperFunctions_class.showToast(b_user_list_activity.this,"Name =>"+ adatamodelclass.getUsername());
                mAdapter.notifyDataSetChanged();
             }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {

                a_data_model_class adatamodelclass = dataSnapshot.getValue(a_data_model_class.class);
               // adatamodelclass.setDisplay(false);
                if(adatamodelclass.getFirebaseUserId().equals(ChatMain_activity.userId))
                {
                    ChatMain_activity.UserName = adatamodelclass.getUsername();


                }
                //Fire base id never changes,user name
                currentItemsLinkedHmap.put(adatamodelclass.getFirebaseUserId(), adatamodelclass);
                mAdapter.notifyDataSetChanged();
                helperFunctions_class.showToast(b_user_list_activity.this,"a_data_group_model_class changed =>"+ adatamodelclass.getUsername());


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

                a_data_model_class adatamodelclass = dataSnapshot.getValue(a_data_model_class.class);

                if(adatamodelclass.getFirebaseUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                {
                    ChatMain_activity.userId = null;
                    ChatMain_activity.UserName = null;
                    call_login(adatamodelclass);
                    finish();


                }
                else {
                    adatamodelclass.setDisplay(false);
                    currentItemsLinkedHmap.put(adatamodelclass.getFirebaseUserId(), adatamodelclass);
                    mAdapter.notifyDataSetChanged();
                    helperFunctions_class.showToast(b_user_list_activity.this, "a_data_group_model_class Deleted =>" + adatamodelclass.getUsername());
                }

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


    private void call_login(a_data_model_class adatamodelclass)
    {

        ChatMain_activity.UserName=null;
        ChatMain_activity.userId=null;




        if( adatamodelclass.getLoginType().equals("Normal"))
        {

            helperFunctions_class.showToast(b_user_list_activity.this, "Current user deleted Google Login!!!");

            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>()
                    {
                        @Override
                        public void onResult(@NonNull Status status)
                        {
                            helperFunctions_class.showToast(b_user_list_activity.this, "BBDOne googlesign out!!!");

                            FirebaseAuth.getInstance().signOut();


                            startActivity(new Intent(b_user_list_activity.this, login_activity.class));
                            finish();

                        }
                    });

        }
        else
        {
            helperFunctions_class.showToast(b_user_list_activity.this, "user deleted  Normal out!!!");

            startActivity(new Intent(b_user_list_activity.this, login_activity.class));
            finish();
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
