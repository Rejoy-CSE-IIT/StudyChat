package com.geometry.chatprogramfinal.c_homePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.f_login.login_activity;
import com.geometry.chatprogramfinal.h_user_list.a_data_model_class;
import com.geometry.chatprogramfinal.h_user_list.b_user_list_activity;
import com.geometry.chatprogramfinal.i_create_chatRoom.AllowPermission.b_group_permission_list_activity;
import com.geometry.chatprogramfinal.i_create_chatRoom.i_a_make_room.a_chat_room_create_activity;
import com.geometry.chatprogramfinal.i_create_chatRoom.i_b_ListGroup.b_group_list_activity;
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
    public static String                                                         UserName=null;
    public static String                                                           userId=null;
 //   public static boolean                                                     loggedIn = false;

    Button             group_permission_from_xml,  logout_button_xml,user_chat_Button_xml,creategroup_from_xml,grouplist;
    GoogleApiClient                                                           mGoogleApiClient;
    GoogleSignInOptions                                                                    gso;
    Intent                                                                     intentfromOther;

    LinearLayout                                                                    button_grp;
    TextView                                       email_address_from_xml,display_name_from_xml;

    DatabaseReference                                                             chatIdatLogin;

    ProgressBar                                                         progressBar_from_layout;


    public static boolean TOAST_CONTROL=true;

    public ValueEventListener valueEventListener=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_chat_main);


        progressBar_from_layout = (ProgressBar) findViewById(R.id.progressBar_from_layout);

        email_address_from_xml =(TextView) findViewById(R.id.email_address_from_xml);
        display_name_from_xml =(TextView) findViewById(R.id.display_name_from_xml);
        button_grp =(LinearLayout) findViewById(R.id.button_grp);
        if(ChatMain_activity.TOAST_CONTROL)
            helperFunctions_class.showToast(ChatMain_activity.this,"Acitivity Create  Main");



    }

    protected void onStart()
    {

        if(ChatMain_activity.TOAST_CONTROL)
            helperFunctions_class.showToast(ChatMain_activity.this,"Acitivity Create  Main");

        super.onStart();

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
        user_chat_Button_xml =(Button) findViewById(R.id.user_chat_Button_xml);
        creategroup_from_xml =(Button) findViewById(R.id.creategroup_from_xml);
        group_permission_from_xml = (Button) findViewById(R.id.group_permission_from_xml);
        grouplist =(Button) findViewById(R.id.grouplist);





        if(  FirebaseAuth.getInstance().getCurrentUser()!=null)
        {



            if(ChatMain_activity.TOAST_CONTROL)
                helperFunctions_class.showToast(ChatMain_activity.this,"Some one already Logged in Going to find out who ::"+FirebaseAuth.getInstance().getCurrentUser().getUid());

            if(!(intentfromOther.hasExtra("googleSignIn")||intentfromOther.hasExtra("normalLogin")))
            {

                OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
                if (opr.isDone())
                {

                    intentfromOther.putExtra("googleSignInD", "googleSignInD");
                    if(ChatMain_activity.TOAST_CONTROL)
                        helperFunctions_class.showToast(ChatMain_activity.this,"Detected Google Sign In");
                }
                else
                {
                    intentfromOther.putExtra("normalLoginD", "normalLoginD");
                    if(ChatMain_activity.TOAST_CONTROL)
                        helperFunctions_class.showToast(ChatMain_activity.this,"Detected Normal Sign In or no user");
                }
            }

            chatIdatLogin= FirebaseDatabase.getInstance()
                    .getReference().child("ChatIds").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


            if(intentfromOther.hasExtra("googleSignIn")||intentfromOther.hasExtra("normalLogin"))
            {
                if(ChatMain_activity.TOAST_CONTROL)
                    helperFunctions_class.showToast(ChatMain_activity.this,"Figured Out Who Logged in ::"+FirebaseAuth.getInstance().getCurrentUser().getEmail());
                email_address_from_xml.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                display_name_from_xml.setText(ChatMain_activity.UserName.toString());



                // email_address_from_xml.setText("Hello test Email");

            }
            else
            {
                button_grp.setVisibility(View.GONE);
                progressBar_from_layout.setVisibility(View.VISIBLE);
                email_address_from_xml.setText("loading..................");
                display_name_from_xml.setText("loading..................");

                if(valueEventListener!=null)
                {
                    chatIdatLogin.removeEventListener(valueEventListener);
                }
                valueEventListener = new ValueEventListener()
                {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        button_grp.setVisibility(View.VISIBLE);
                        progressBar_from_layout.setVisibility(View.GONE);

                        a_data_model_class userData = dataSnapshot.getValue(a_data_model_class.class);
                        email_address_from_xml.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        display_name_from_xml.setText(userData.getUsername().toString());

                        ChatMain_activity.UserName =userData.getUsername().toString();
                        ChatMain_activity.userId=userData.getFirebaseUserId().toString();

                        logout_button_xml.setEnabled(true);



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                chatIdatLogin.addListenerForSingleValueEvent(valueEventListener);

            }



        }
        else
        {

            if(ChatMain_activity.TOAST_CONTROL)
                helperFunctions_class.showToast(ChatMain_activity.this,"  Null user");

            ChatMain_activity.UserName=null;
            ChatMain_activity.userId=null;
            startActivity(new Intent(ChatMain_activity.this, login_activity.class));
            finish();

        }








        user_chat_Button_xml.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                progressBar_from_layout.setVisibility(View.VISIBLE);



                Intent intent = new Intent(ChatMain_activity.this, b_user_list_activity.class);
                startActivity(intent);




            }
        });

        //Make log out
        creategroup_from_xml.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(ChatMain_activity.this, a_chat_room_create_activity.class);
                startActivity(intent);
            }
        });

        //Make log out
        group_permission_from_xml.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(ChatMain_activity.this, b_group_permission_list_activity.class);
                startActivity(intent);
            }
        });



//Make log out
        grouplist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(ChatMain_activity.this, b_group_list_activity.class);
                startActivity(intent);
            }
        });


        //Make log out
        logout_button_xml.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                progressBar_from_layout.setVisibility(View.VISIBLE);

                //  a_data_group_model_class userData = new a_data_group_model_class(ChatMain_activity.userId,ChatMain_activity.UserName,"OFFLINE");
                DatabaseReference offline_ref = chatIdatLogin.child("status");
                offline_ref.setValue("OFFLINE",
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
    @Override
    public void onBackPressed()
    {

        //thats it
    }
    private void call_login()
    {

        ChatMain_activity.UserName=null;
        ChatMain_activity.userId=null;




        if(intentfromOther.hasExtra("googleSignIn")||intentfromOther.hasExtra("googleSignInD"))
        {
            if(ChatMain_activity.TOAST_CONTROL)
                helperFunctions_class.showToast(ChatMain_activity.this,"  Null user");
            if(ChatMain_activity.TOAST_CONTROL)
            helperFunctions_class.showToast(ChatMain_activity.this, "AAADOne googlesign out!!!");

            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>()
                    {
                        @Override
                        public void onResult(@NonNull Status status)
                        {
                            if(ChatMain_activity.TOAST_CONTROL)
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
            if(ChatMain_activity.TOAST_CONTROL)
                helperFunctions_class.showToast(ChatMain_activity.this,"  Null user");
            if(ChatMain_activity.TOAST_CONTROL)
            helperFunctions_class.showToast(ChatMain_activity.this, "cccOne googlesign out!!!");

            startActivity(new Intent(ChatMain_activity.this, login_activity.class));
            finish();

        }

    }

    private void loadDataFromIntent()
    {
        /*
        if (getIntent().getExtras() != null)
        {
            Person person = getIntent().getParcelableExtra(PERSON);
            if (person != null)
            {
                mName.setText(person.getName());
                mSurname.setText(person.getSurname());
                mAge.setText(String.valueOf(person.getAge()).toString());
            }
        }*/
    }
}
