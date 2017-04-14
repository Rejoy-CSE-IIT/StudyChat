package com.geometry.chatprogramfinal.d_register;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.c_homePage.ChatMain_activity;
import com.geometry.chatprogramfinal.f_login.login_activity;
import com.geometry.chatprogramfinal.h_user_list.a_data_model_class;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class c_register_details_google_signIn_activity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener
{
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private SignInButton googleSignBtn;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    String UserName,  userId;
    Boolean loggedIn;


    EditText chatid_from_layout;
    LinearLayout userIdWindow;
    LinearLayout userIdMsgWindow;
    ProgressBar progressBar_from_layout;
    ProgressBar progressBar_from_layout_msg;
    Button create_chatId_button_from_layout;

    TextView chatId_label_from_layout;


    DatabaseReference chatIdatverification;
    DatabaseReference chatIdatLogin;
    FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_a_register_details_google_sign_in_activity);

        chatid_from_layout                  = (EditText) findViewById(R.id.chatid_from_layout);
        userIdWindow                        = (LinearLayout) findViewById(R.id.userIdWindow);
        userIdMsgWindow                     = (LinearLayout) findViewById(R.id.userIdMsgWindow);
        progressBar_from_layout             = (ProgressBar) findViewById(R.id.progressBar_from_layout);
        progressBar_from_layout_msg         = (ProgressBar) findViewById(R.id.progressBar_msg_from_layout);
        create_chatId_button_from_layout    = (Button) findViewById(R.id.create_chatId_button_from_layout);
        chatId_label_from_layout            = (TextView) findViewById(R.id.chatId_label_from_layout);

        userIdWindow.setVisibility(View.GONE);

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

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);


        //Make log out
        create_chatId_button_from_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                DatabaseReference addUserInfo;



                chatIdatverification = FirebaseDatabase.getInstance()
                        .getReference().child("ChatIdVerification").child(chatid_from_layout.getText().toString().toLowerCase());
                chatIdatverification.addListenerForSingleValueEvent(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        if (dataSnapshot.exists())
                        {
                          //  helperFunctions_class.showToast(b_register_details_activity.this, "Please type new id");
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
                                            a_data_model_class userData = new a_data_model_class(userId,UserName,"ONLINE");


                                            userData.setLoginType("google");
                                           // c_userlist_recycler_view_data_model_class userData = new c_userlist_recycler_view_data_model_class(userId,UserName,"ONLINE");
                                            chatIdatLogin.setValue(userData,
                                                    new DatabaseReference.CompletionListener() {

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




    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess())
            {
                helperFunctions_class.showToast(c_register_details_google_signIn_activity.this,"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX!!!");

                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                 firebaseAuthWithGoogle(account);
                helperFunctions_class.showToast(c_register_details_google_signIn_activity.this,"ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ!!!");

            }
            else
            {
                call_Login_page();
            }
        }
    }
    // [END onactivityresult]


    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct)
    {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
          FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {

                        progressBar_from_layout.setVisibility(View.GONE);
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful())
                        {
                            chatIdatLogin= FirebaseDatabase.getInstance()
                                    .getReference().child("ChatIds").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


                            chatIdatLogin.addListenerForSingleValueEvent(new ValueEventListener()
                            {


                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot)
                                {

                                    if(!dataSnapshot.exists())
                                    {
                                        helperFunctions_class.showToast(c_register_details_google_signIn_activity.this,"user id is new");
                                        userIdMsgWindow.setVisibility(View.GONE);
                                        userIdWindow.setVisibility(View.VISIBLE);

                                        chatId_label_from_layout.setText("Enter new Chat user Id");



                                    }
                                    else
                                    {
                                        helperFunctions_class.showToast(c_register_details_google_signIn_activity.this,"user id aleady exists");
                                        SystemClock.sleep(1000);
                                        a_data_model_class userData = dataSnapshot.getValue(a_data_model_class.class);
                                       // c_userlist_recycler_view_data_model_class userData = dataSnapshot.getValue(c_userlist_recycler_view_data_model_class.class);




                                        UserName = userData.getUsername();
                                        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        loggedIn=true;
                                        //userData = new c_userlist_recycler_view_data_model_class(userId,UserName,"ONLINE");

                                        userData.setStatus("ONLINE");
                                        userData.setLoginType("google");
                                        DatabaseReference UserStatics;
                                        UserStatics= FirebaseDatabase.getInstance()
                                                .getReference().child("ChatIds").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                        UserStatics.setValue(userData,
                                                new DatabaseReference.CompletionListener()
                                                {

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

                        }
                        else
                        {


                            FirebaseAuth.getInstance().signOut();
                            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                    new ResultCallback<Status>()
                                    {
                                        @Override
                                        public void onResult(@NonNull Status status)
                                        {
                                           // helperFunctions_class.showToast(c_register_details_google_signIn_activity.this, "DOne googlesign out!!!");

                                            call_Login_page();

                                        }
                                    });




                        }

                    }
                });
    }
    // [END auth_with_google]*/


    private void call_home_page()
    {
        intent = new Intent(getApplicationContext(), ChatMain_activity.class);

        helperFunctions_class.set_user_statics( UserName,  userId,  loggedIn);



            intent.putExtra("googleSignIn","googleSignIn");



        startActivity(intent);
        finish();

    }


    private void call_Login_page()
    {
        //helperFunctions_class.showToast(c_register_details_google_signIn_activity.this,"Check Registration Vertification mail ");
        helperFunctions_class.set_user_statics(  null,  null,  false);
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), login_activity.class);
        intent.putExtra("from_google_Login_error", "from_google_Login_error");
        startActivity(intent);
        finish();

    }

}
