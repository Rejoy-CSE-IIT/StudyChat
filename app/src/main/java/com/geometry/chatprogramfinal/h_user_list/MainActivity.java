package com.geometry.chatprogramfinal.h_user_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener
{

    private List<String> id_entry = new ArrayList<>();
    LinkedHashMap <String, Item> currentItemsLinkedHmap = new LinkedHashMap<String, Item>();


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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        this.mLayoutManager = new LinearLayoutManager(this);
        this.mRecyclerView.setLayoutManager(mLayoutManager);


        currentDBUrl = (TextView) findViewById(R.id.currentDB);

        mAdapter = new RecyclerViewAdapter(currentItemsLinkedHmap,id_entry);
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                 pushValue();
                Snackbar.make(view, "New item added to Firebase", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void setFirebaseValueListener()
    {


        myFirebaseRef = FirebaseDatabase.getInstance().getReference().child("GroupChatIds");
        helperFunctions_class.showToast(MainActivity.this,"Children =>"+myFirebaseRef.getKey());

        myFirebaseRef.addChildEventListener(new ChildEventListener()
        {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                Item item = dataSnapshot.getValue(Item.class);
                id_entry.add(item.getFirebaseUserId());
                currentItemsLinkedHmap.put(item.getFirebaseUserId(), item);
                helperFunctions_class.showToast(MainActivity.this,"Name =>"+item.getUsername());
                mAdapter.notifyDataSetChanged();
             }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {

                Item item = dataSnapshot.getValue(Item.class);
               // item.setDisplay(false);
                if(item.getFirebaseUserId().equals(ChatMain_activity.userId))
                {
                    ChatMain_activity.UserName = item.getUsername();


                }
                //Fire base id never changes,user name
                currentItemsLinkedHmap.put(item.getFirebaseUserId(), item);
                mAdapter.notifyDataSetChanged();
                helperFunctions_class.showToast(MainActivity.this,"Item changed =>"+item.getUsername());


                /*
                Item item = dataSnapshot.getValue(Item.class);
                //   Item item = new Item(itemSnapshot.getKey(), itemSnapshot.child("name").getValue(String.class));
                Item itemT = new Item(item.getFirebaseUserId(),item.getUsername(),item.getStatus() );
                //currentItemsDict.add(itemT);
                helperFunctions_class.showToast(MainActivity.this,"Name =>"+itemT.getUsername());
                helperFunctions_class.showToast(MainActivity.this,"Item Changed =>"+item.getUsername());
*/
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

                Item item = dataSnapshot.getValue(Item.class);

                if(item.getFirebaseUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                {
                    ChatMain_activity.userId = null;
                    ChatMain_activity.UserName = null;
                    call_login(item);
                    finish();


                }
                else {
                    item.setDisplay(false);
                    currentItemsLinkedHmap.put(item.getFirebaseUserId(), item);
                    mAdapter.notifyDataSetChanged();
                    helperFunctions_class.showToast(MainActivity.this, "Item Deleted =>" + item.getUsername());
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


    private void call_login(Item item)
    {

        ChatMain_activity.UserName=null;
        ChatMain_activity.userId=null;




        if( item.getLoginType().equals("Normal"))
        {

            helperFunctions_class.showToast(MainActivity.this, "Current user deleted Google Login!!!");

            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>()
                    {
                        @Override
                        public void onResult(@NonNull Status status)
                        {
                            helperFunctions_class.showToast(MainActivity.this, "BBDOne googlesign out!!!");

                            FirebaseAuth.getInstance().signOut();


                            startActivity(new Intent(MainActivity.this, login_activity.class));
                            finish();

                        }
                    });

        }
        else
        {
            helperFunctions_class.showToast(MainActivity.this, "user deleted  Normal out!!!");

            startActivity(new Intent(MainActivity.this, login_activity.class));
            finish();
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
