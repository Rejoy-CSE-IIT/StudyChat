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
import com.geometry.chatprogramfinal.h_Users_List.UserData;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatMain_activity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener
{
    public static String                                                    UserName=null;
    public static String                                                      userId=null;

    public static boolean                                                    loggedIn = false;
    private FirebaseAuth                                        firebaseAuth;
    Button logout_button_xml;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    Intent intentfromOther;
    DatabaseReference chatIdatverification;
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

           intentfromOther = getIntent();

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


        logout_button_xml =(Button) findViewById(R.id.logout_button_xml);





/*

 */



        if(  FirebaseAuth.getInstance().getCurrentUser()  ==null)
        {
            startActivity(new Intent(ChatMain_activity.this, login_activity.class));
            finish();
        }


        if(intentfromOther.hasExtra("googleSignIn")||intentfromOther.hasExtra("normalLogin"))
        {
            email_address_from_xml.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
            display_name_from_xml.setText(ChatMain_activity.UserName);

        }








        //Make log out
        logout_button_xml.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                chatIdatLogin= FirebaseDatabase.getInstance()
                        .getReference().child("GroupChatIds").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                progressBar_from_layout.setVisibility(View.VISIBLE);

                UserData userData = new UserData(ChatMain_activity.userId,ChatMain_activity.UserName,"OFFLINE");
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




        if(intentfromOther.hasExtra("googleSignIn"))
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
