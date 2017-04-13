package com.geometry.chatprogramfinal.d_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.c_homePage.ChatMain_activity;
import com.geometry.chatprogramfinal.f_login.login_activity;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.geometry.chatprogramfinal.z_c_validate_input.validate_input;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class a_register_activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{

    private EditText                 email_from_layout, password_from_layout;
    private Button    log_in_button_from_layout, register_button_from_layout;
    private ProgressBar                              progressBar_from_layout;
    private TextView register_label_from_layout;

    private FirebaseAuth                                        firebaseAuth;
    String email = null;
    String password = null;






    ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Make this activity, full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_c_register);


        // Get Firebase Auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        register_label_from_layout = (TextView) findViewById(R.id.register_label_from_layout);
        // Construction of subviews
        log_in_button_from_layout = (Button) findViewById(R.id.log_in_button_from_layout);
        register_button_from_layout = (Button) findViewById(R.id.register_button_from_layout);

        email_from_layout = (EditText) findViewById(R.id.email_from_layout);
        password_from_layout = (EditText) findViewById(R.id.password_from_layout);
        progressBar_from_layout = (ProgressBar) findViewById(R.id.progressBar_from_layout);



        register_button_from_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                validate_input validate= new validate_input(a_register_activity.this,getApplicationContext());




                email = email_from_layout.getText().toString();
                password = password_from_layout.getText().toString();


                if(validate.isValidEmail(email) &&
                        validate.isValidPassword(password))

                {


                    progressBar_from_layout.setVisibility(View.VISIBLE);

                    // Create user in Firebase
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(a_register_activity.this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            helperFunctions_class.showToast(a_register_activity.this,"User Registration Finished");
                            progressBar_from_layout.setVisibility(View.GONE);

                            if (!task.isSuccessful())
                            {
                                          register_label_from_layout.setText("Registration Failed!!\n Please try again");
                            }
                            else
                            {
                                // helperFunctions_class.showToast(a_register_activity.this,"User Registration Successful");

                                // Authenticate the user
                                firebaseAuth.signInWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(a_register_activity.this, new OnCompleteListener<AuthResult>()
                                        {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task)
                                            {
                                                progressBar_from_layout.setVisibility(View.GONE);

                                                if(!task.isSuccessful())
                                                {
                                                    helperFunctions_class.showToast(a_register_activity.this,"User Registration Failed");
                                                    register_label_from_layout.setText("Registration Failed!!\n Please try again");

                                                }
                                                else
                                                {


                                                    FirebaseUser user = firebaseAuth.getCurrentUser();


                                                    if (user != null)
                                                    {
                                                        user.sendEmailVerification()
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task)
                                                                    {
                                                                        if (task.isSuccessful())
                                                                        {
                                                                            ChatMain_activity.UserName=null;
                                                                            ChatMain_activity.userId=null;
                                                                            FirebaseAuth.getInstance().signOut();
                                                                            helperFunctions_class.showToast(a_register_activity.this,"DOne Register!!!");

                                                                            Intent intent = new Intent(getApplicationContext(), login_activity.class);
                                                                            intent.putExtra("fromRegister","fromRegister");
                                                                            startActivity(intent);
                                                                            finish();

                                                                        }
                                                                    }
                                                                });
                                                    }



                                                }
                                            }
                                        });







                            }
                        }
                    });

                }
                else
                {
                    register_label_from_layout.setText("Registration Failed!!\n Please try again");

                }



            }
        });



        log_in_button_from_layout.setOnClickListener(new View.OnClickListener()
        {
                @Override
                public void onClick(View view)
                {

                        startActivity(new Intent(getApplicationContext(), login_activity.class));
                        finish();

                }
            });






    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar_from_layout.setVisibility(View.GONE);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }







}



