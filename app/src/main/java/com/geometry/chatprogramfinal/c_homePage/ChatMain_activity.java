package com.geometry.chatprogramfinal.c_homePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.f_login.login_activity;
import com.geometry.chatprogramfinal.h_Users_List.c_userlist_recycler_view_data_model_class;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatMain_activity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener
{
    public static String                                                    UserName=null;
    public static String                                                      userId=null;

    public static boolean                                                    loggedIn = false;

    Button logout_button_xml;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    Intent intentfromOther;

    TextView     email_address_from_xml,display_name_from_xml;

    DatabaseReference chatIdatLogin;

    private ProgressBar progressBar_from_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_chat_main);


        progressBar_from_layout = (ProgressBar) findViewById(R.id.progressBar_from_layout);

        email_address_from_xml =(TextView) findViewById(R.id.email_address_from_xml);
        display_name_from_xml =(TextView) findViewById(R.id.display_name_from_xml);




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


        intentfromOther = getIntent();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone())
        {

            intentfromOther.putExtra("googleSignInD", "googleSignInD");

            helperFunctions_class.showToast(ChatMain_activity.this,"Detected Google Sign In");
        }
        else
        {
            intentfromOther.putExtra("googleSignInD", "normalLoginD");
            helperFunctions_class.showToast(ChatMain_activity.this,"Detected Normal Sign In");
        }

        logout_button_xml =(Button) findViewById(R.id.logout_button_xml);





/*

 */



        if(  FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            helperFunctions_class.showToast(ChatMain_activity.this,"Not Null user ::"+FirebaseAuth.getInstance().getCurrentUser().getUid());



            chatIdatLogin= FirebaseDatabase.getInstance()
                    .getReference().child("GroupChatIds").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


            if(intentfromOther.hasExtra("googleSignIn")||intentfromOther.hasExtra("normalLogin"))
            {
                helperFunctions_class.showToast(ChatMain_activity.this,"Ssecond Not Null user ::"+FirebaseAuth.getInstance().getCurrentUser().getEmail());
                email_address_from_xml.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                display_name_from_xml.setText(ChatMain_activity.UserName.toString());

                // email_address_from_xml.setText("Hello test Email");

            }
            else
            {
                logout_button_xml.setVisibility(View.GONE);
                email_address_from_xml.setText("loading..................");
                display_name_from_xml.setText("loading..................");
                chatIdatLogin.addListenerForSingleValueEvent(new ValueEventListener()
                {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        logout_button_xml.setVisibility(View.VISIBLE);


                        c_userlist_recycler_view_data_model_class userData = dataSnapshot.getValue(c_userlist_recycler_view_data_model_class.class);
                        email_address_from_xml.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        display_name_from_xml.setText(userData.getUsername().toString());

                        ChatMain_activity.UserName =userData.getUsername().toString();
                        ChatMain_activity.userId=userData.getFirebaseUserId().toString();

                        logout_button_xml.setEnabled(true);

                    /*
                         ChatMain_activity.userId =FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        ChatMain_activity.UserName =userData.getUsername().toString();
                        ChatMain_activity.userId=email_address_from_xml.setText();
                         display_name_from_xml.setText(userData.getUsername().toString());*/

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }



        }
        else
        {

            startActivity(new Intent(ChatMain_activity.this, login_activity.class));
            finish();
            helperFunctions_class.showToast(ChatMain_activity.this,"  Null user");

        }












        //Make log out
        logout_button_xml.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                        progressBar_from_layout.setVisibility(View.VISIBLE);

                c_userlist_recycler_view_data_model_class userData = new c_userlist_recycler_view_data_model_class(ChatMain_activity.userId,ChatMain_activity.UserName,"OFFLINE");
                chatIdatLogin.setValue(userData,
                        new DatabaseReference.CompletionListener() {

                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                            {

                                call_login();
                            }
                        });

/*
                chatIdatverification = FirebaseDatabase.getInstance()
                        .getReference().child("GroupChatIdVerification").child(chatid_from_layout.getText().toString().toLowerCase());

*/


            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void call_login()
    {

        ChatMain_activity.UserName=null;
        ChatMain_activity.userId=null;




        if(intentfromOther.hasExtra("googleSignIn")||intentfromOther.hasExtra("googleSignInD"))
        {

            helperFunctions_class.showToast(ChatMain_activity.this, "AAADOne googlesign out!!!");

            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>()
                    {
                        @Override
                        public void onResult(@NonNull Status status)
                        {
                            helperFunctions_class.showToast(ChatMain_activity.this, "BBDOne googlesign out!!!");

                            FirebaseAuth.getInstance().signOut();

                            progressBar_from_layout.setVisibility(View.GONE);
                            startActivity(new Intent(ChatMain_activity.this, login_activity.class));
                            finish();

                        }
                    });

        }
        else
        {
            helperFunctions_class.showToast(ChatMain_activity.this, "cccOne googlesign out!!!");

            startActivity(new Intent(ChatMain_activity.this, login_activity.class));
            finish();
        }

    }
}
