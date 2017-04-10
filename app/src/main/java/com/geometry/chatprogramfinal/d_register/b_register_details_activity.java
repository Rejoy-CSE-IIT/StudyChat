package com.geometry.chatprogramfinal.d_register;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.c_homePage.ChatMain_activity;
import com.geometry.chatprogramfinal.f_login.login_activity;
import com.geometry.chatprogramfinal.h_user_list.Item;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class b_register_details_activity extends AppCompatActivity
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
    FirebaseUser firebaseUser;
    private FirebaseAuth                                        firebaseAuth;
    Intent intent;
    Intent intentfromOther;

    String UserName,  userId;
    Boolean loggedIn;

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
        // Get Firebase Auth instance
        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();



        chatIdatLogin= FirebaseDatabase.getInstance()
                .getReference().child("GroupChatIds").child(FirebaseAuth.getInstance().getCurrentUser().getUid());




        if( !firebaseUser.isEmailVerified())
        {
            sendVerificationEmail();

        }


        chatIdatLogin.addListenerForSingleValueEvent(new ValueEventListener()
        {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                if(!dataSnapshot.exists())
                {
                    helperFunctions_class.showToast(b_register_details_activity.this,"user id is new");
                    userIdMsgWindow.setVisibility(View.GONE);
                    userIdWindow.setVisibility(View.VISIBLE);

                    chatId_label_from_layout.setText("Enter new Chat user Id");



                }
                else
                {
                     helperFunctions_class.showToast(b_register_details_activity.this,"user id aleady exists");
                     SystemClock.sleep(1000);

                     Item userData = dataSnapshot.getValue(Item.class);

                     UserName = userData.getUsername();
                     userId = userData.getFirebaseUserId();
                     loggedIn=true;
                     userData.setStatus("ONLINE");
                     userData.setLoginType("Normal");
                     chatIdatLogin.setValue(userData,
                            new DatabaseReference.CompletionListener() {

                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                                {

                                    call_home_page();
                                }
                            });

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
                    chatIdatverification.addListenerForSingleValueEvent(new ValueEventListener() {


                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {

                            if (dataSnapshot.exists())
                            {
                                helperFunctions_class.showToast(b_register_details_activity.this, "Please type new id");
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
                                                UserName = chatid_from_layout.getText().toString();
                                                userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                loggedIn=true;
                                              //  c_userlist_recycler_view_data_model_class userData = new c_userlist_recycler_view_data_model_class(userId,UserName,"ONLINE");
                                               //  Item userData = dataSnapshot.getValue(Item.class);
                                                Item userData = new Item(userId,UserName,"ONLINE");
                                                userData.setLoginType("Normal");

                                                chatIdatLogin.setValue(userData,
                                                        new DatabaseReference.CompletionListener()
                                                        {

                                                            @Override
                                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                                                            {

                                                                call_home_page();
                                                            }
                                                        });

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
    }

    public void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null)
        {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {


                                call_Login_page();
                            }
                        }
                    });
        }

    }

    private void call_home_page()
    {
        intent = new Intent(getApplicationContext(), ChatMain_activity.class);

        helperFunctions_class.set_user_statics( UserName,  userId,  loggedIn);



        intent.putExtra("normalLogin","normalLogin");
        startActivity(intent);
        finish();

    }

    private void call_Login_page()
    {
        helperFunctions_class.showToast(b_register_details_activity.this,"Check Registration Vertification mail ");
        helperFunctions_class.set_user_statics(  null,  null,  false);
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), login_activity.class);
        intent.putExtra("notVerified", "notVerified");
        startActivity(intent);
        finish();

    }
}
