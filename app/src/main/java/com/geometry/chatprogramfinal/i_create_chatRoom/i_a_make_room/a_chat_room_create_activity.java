package com.geometry.chatprogramfinal.i_create_chatRoom.i_a_make_room;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.i_create_chatRoom.i_b_ListGroup.b_group_list_activity;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class a_chat_room_create_activity extends AppCompatActivity
{
    Button logout_button_xml,user_chat_Button_xml,create_chatroom_button_from_layout;
    EditText chatroomname_from_layout;
    TextView chatroomcreate_label_from_layout;
    DatabaseReference CreateChatGroup_location;
    Button  grouplist;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_chat_room_create_activity);
        grouplist =(Button) findViewById(R.id.grouplist);

        create_chatroom_button_from_layout =(Button) findViewById(R.id.create_chatroom_button_from_layout);

        chatroomname_from_layout =(EditText) findViewById(R.id.chatroomname_from_layout);
        chatroomcreate_label_from_layout =(TextView) findViewById(R.id.chatroomcreate_label_from_layout);

        create_chatroom_button_from_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                DatabaseReference addUserInfo;



                CreateChatGroup_location = FirebaseDatabase.getInstance()
                        .getReference().child("GroupName").child(chatroomname_from_layout.getText().toString().toLowerCase());
                CreateChatGroup_location.addListenerForSingleValueEvent(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        if (dataSnapshot.exists())
                        {

                            helperFunctions_class.showToast(a_chat_room_create_activity.this, "Please type new id");
                            chatroomcreate_label_from_layout.setText("Error !! Chat  Id already Exists \n try again");


                        }
                        else
                        {
                            b_group_data_model group_data = new b_group_data_model(chatroomname_from_layout.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getUid()
                            + TimeUnit.NANOSECONDS.toMicros(System.nanoTime())
                            );


                            CreateChatGroup_location.setValue(group_data,
                                    new DatabaseReference.CompletionListener()
                                    {

                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                                        {


                                            chatroomcreate_label_from_layout.setText("New group Id created  ");

/*
                                            CreateChatGroup_location.setValue(group_data,
                                                    new DatabaseReference.CompletionListener()
                                                    {

                                                        @Override
                                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                                                        {

                                                           call_home_page();
                                                        }
                                                    });*/

                                        }
                                    });

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {

                    }
                });




            }
        });

        //Make log out
        grouplist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(a_chat_room_create_activity.this, b_group_list_activity.class);
                startActivity(intent);
            }
        });
    }



}

