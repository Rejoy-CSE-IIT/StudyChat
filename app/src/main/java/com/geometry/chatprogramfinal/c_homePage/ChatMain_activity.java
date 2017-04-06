package com.geometry.chatprogramfinal.c_homePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.f_login.login_activity;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

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
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_chat_main);

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
        if(!ChatMain_activity.loggedIn)
        {

        }*/


        //Make log out
        logout_button_xml.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                        /*
                    mRef= FirebaseDatabase.getInstance()
                            .getReference().child("UserInfo").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                    logout=true;
                    mRef.setValue(new UserData(ChatMain.userId, ChatMain.UserName, "OFF LINE"));*/

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
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
