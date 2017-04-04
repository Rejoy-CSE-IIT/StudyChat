package com.geometry.chatprogramfinal.d_register;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.c_homePage.ChatMain_activity;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class b_register_details extends AppCompatActivity
{
    EditText chatid_from_layout;
    LinearLayout userIdWindow;
    LinearLayout userIdMsgWindow;
    ProgressBar progressBar_from_layout;
    ProgressBar progressBar_from_layout_msg;
    Button create_chatId_button_from_layout;

    TextView chatId_label_from_layout;


    DatabaseReference chatIdatLogin;
    DatabaseReference chatIdatverification;
    DatabaseReference addUserInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_b_register_details);

        chatid_from_layout                  = (EditText) findViewById(R.id.chatid_from_layout);
        userIdWindow                        = (LinearLayout) findViewById(R.id.userIdWindow);
        userIdMsgWindow                     = (LinearLayout) findViewById(R.id.userIdMsgWindow);
        progressBar_from_layout             = (ProgressBar) findViewById(R.id.progressBar_from_layout);
        progressBar_from_layout_msg         = (ProgressBar) findViewById(R.id.progressBar_msg_from_layout);
        create_chatId_button_from_layout    = (Button) findViewById(R.id.create_chatId_button_from_layout);
        chatId_label_from_layout            = (TextView) findViewById(R.id.chatId_label_from_layout);

        userIdWindow.setVisibility(View.GONE);


          chatIdatLogin= FirebaseDatabase.getInstance()
                .getReference().child("GroupChatIds").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        chatIdatLogin.addListenerForSingleValueEvent(new ValueEventListener()
        {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                if(!dataSnapshot.exists())
                {
                    helperFunctions_class.showToast(b_register_details.this,"user id is new");
                    userIdMsgWindow.setVisibility(View.GONE);
                    userIdWindow.setVisibility(View.VISIBLE);

                }
                else
                {
                   helperFunctions_class.showToast(b_register_details.this,"user id aleady exists");
                    SystemClock.sleep(1000);

                    ChatMain_activity.loggedIn=true;
                    startActivity(new Intent(b_register_details.this, ChatMain_activity.class));
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Make log out
        create_chatId_button_from_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                DatabaseReference addUserInfo;

                chatIdatverification = FirebaseDatabase.getInstance()
                        .getReference().child("GroupChatIdVerification").child(chatid_from_layout.getText().toString().toLowerCase());
                chatIdatverification.addListenerForSingleValueEvent(new ValueEventListener()
                {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        if(dataSnapshot.exists())
                        {
                            helperFunctions_class.showToast(b_register_details.this,"Please type new id");
                            chatId_label_from_layout.setText("Error !! Chat Id already Exists \n try again");


                        }
                        else
                        {

                            chatIdatverification.setValue(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                    new DatabaseReference.CompletionListener()
                                    {

                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                                        {

                                            chatIdatLogin.setValue(chatid_from_layout.getText().toString().toLowerCase(),
                                                    new DatabaseReference.CompletionListener()
                                                    {

                                                        @Override
                                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                                                        {
                                                            ChatMain_activity.loggedIn=true;
                                                       startActivity(new Intent(b_register_details.this, ChatMain_activity.class));
                                                            finish();

                                                        }
                                                    });

                                        }
                                    });

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });
    }
}