package com.geometry.chatprogramfinal.f_login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.d_register.a_register_activity;
import com.geometry.chatprogramfinal.d_register.b_register_details;
import com.geometry.chatprogramfinal.g_reset_Password.resetPassword_activity;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.geometry.chatprogramfinal.z_c_validate_input.validate_input;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class login_activity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener
{
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private SignInButton googleSignBtn;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;

    private TextView login_label_from_layout;
    // Initialisation of subviews
    private EditText email_from_layout, password_from_layout;
    private Button register_from_layout, login_from_layout, reset_password_from_layout;
    private ProgressBar progressBar_from_layout;







    // Initialisation of FirebaseAuth variable
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Make this activity, full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_e_login);


        // Get FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();


        // Construction of subviews
        login_label_from_layout = (TextView) findViewById(R.id.login_label_from_layout);
        email_from_layout = (EditText) findViewById(R.id.email_from_layout);
        password_from_layout = (EditText) findViewById(R.id.password_from_layout);
        progressBar_from_layout = (ProgressBar) findViewById(R.id.progressBar_from_layout);
        register_from_layout = (Button) findViewById(R.id.register_from_layout);
        login_from_layout = (Button) findViewById(R.id.login_from_layout);
        reset_password_from_layout = (Button) findViewById(R.id.reset_password_from_layout);

        googleSignBtn = (SignInButton) findViewById( R.id.google_sign_in_button);



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

        googleSignBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v)
            {
                progressBar_from_layout.setVisibility(View.VISIBLE);
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        Intent intent = getIntent();
        if(intent.hasExtra("fromRegister"))
        {
            login_label_from_layout.setText("Registration is successful \n Please verify from email   & Login");
        }


        if(intent.hasExtra("fromReset"))
        {
            login_label_from_layout.setText("Password Changed \n Please Login");
        }

        if(intent.hasExtra("notVerified"))
        {
            login_label_from_layout.setText("Login failed..Please verify   ");
        }



        // set the click listener to start sign up activity on click.
        register_from_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(login_activity.this, a_register_activity.class));
                finish();
            }
        });

        // set the click listener to start password reset activity
        reset_password_from_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(login_activity.this, resetPassword_activity.class));
                finish();
            }
        });

        // set the click listener.
        // send the email and password to verify the login detaisl.
        login_from_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Get the email and password from user inputs
                String email = email_from_layout.getText().toString();
                final String password = password_from_layout.getText().toString();
                validate_input validate= new validate_input(login_activity.this,getApplicationContext());

                if(validate.isValidEmail(email) && validate.isValidPassword(password))
                {

                    progressBar_from_layout.setVisibility(View.VISIBLE);

                    // Authenticate the user
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(login_activity.this, new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    progressBar_from_layout.setVisibility(View.GONE);

                                    if(!task.isSuccessful())
                                    {
                                        helperFunctions_class.showToast(login_activity.this,"User Login Failed");
                                        login_label_from_layout.setText("Login Failed!!\n Please try again");
                                        progressBar_from_layout.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        progressBar_from_layout.setVisibility(View.GONE);

                                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


                                        helperFunctions_class.showToast(login_activity.this, "User Login Success");

                                        startActivity(new Intent(login_activity.this, b_register_details.class));
                                        finish();


                                    }
                                }
                            });
                }
                else
                {
                    login_label_from_layout.setText("Login Failed!!\n Please try again");
                }



            }
        });

        firebaseAuth.signOut();


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
                helperFunctions_class.showToast(login_activity.this,"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX!!!");

                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                helperFunctions_class.showToast(login_activity.this,"ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ!!!");

            }
            else
            {
                login_label_from_layout.setText("Google Login Failed!!\n Please try again");
                progressBar_from_layout.setVisibility(View.GONE);
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct)
    {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
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
                        if (!task.isSuccessful())
                        {
                          //  Log.w(TAG, "signInWithCredential", task.getException());
                         //   Toast.makeText(GoogleSignInActivity.this, "Authentication failed.",
                              //      Toast.LENGTH_SHORT).show();
                            helperFunctions_class.showToast(login_activity.this,"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!!!");

                            login_label_from_layout.setText("Google Login Failed!!\n Please try again");

                        }
                        else
                        {

                            //It is verified account
                            Intent intent = new Intent(getApplicationContext(), b_register_details.class);
                            intent.putExtra("googleSignIn", "googleSignIn");
                            startActivity(intent);
                            finish();


                        }

                    }
                });
    }
    // [END auth_with_google]


}
