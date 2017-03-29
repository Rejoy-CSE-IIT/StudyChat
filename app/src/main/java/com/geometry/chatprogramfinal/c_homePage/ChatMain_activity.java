package com.geometry.chatprogramfinal.c_homePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.d_register.register_activity;

public class ChatMain_activity extends AppCompatActivity
{

    public static boolean                                                    loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_chat_main);

        if(!loggedIn)
        {
            startActivity(new Intent(ChatMain_activity.this, register_activity.class));
            finish();
        }
    }
}
