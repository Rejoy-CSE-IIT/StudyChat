package com.geometry.chatprogramfinal.c_homePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.d_register.b_register_details;
import com.geometry.chatprogramfinal.f_login.login_activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChatMain_activity extends AppCompatActivity
{
    public static String                                                    UserName=null;
    public static String                                                      userId=null;

    public static boolean                                                    loggedIn = false;
    private FirebaseAuth                                        firebaseAuth;
    Button logout_button_xml;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_chat_main);

        // Get Firebase Auth instance
        firebaseAuth = firebaseAuth.getInstance();

        logout_button_xml =(Button) findViewById(R.id.logout_button_xml);

        Intent intent = getIntent();
        if(!intent.hasExtra("fromRegisterDetails"))
        {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null)
            {

                startActivity(new Intent(ChatMain_activity.this, b_register_details.class));
                finish();
            }
            else
            {
                startActivity(new Intent(ChatMain_activity.this, login_activity.class));
                finish();
            }

        }





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
                    FirebaseAuth.getInstance().signOut();

                    startActivity(new Intent(ChatMain_activity.this, login_activity.class));
                    finish();



            }
        });

    }
}
