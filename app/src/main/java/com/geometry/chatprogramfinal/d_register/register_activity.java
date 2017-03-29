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

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.f_login.login_activity;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.geometry.chatprogramfinal.z_c_validate_input.validate_input;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register_activity extends AppCompatActivity
{

    private EditText                 email_from_layout, password_from_layout;
    private Button    log_in_button_from_layout, register_button_from_layout;
    private ProgressBar                              progressBar_from_layout;


    private FirebaseAuth                                        firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Make this activity, full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_c_register);


        // Get Firebase Auth instance
        firebaseAuth = firebaseAuth.getInstance();

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
                validate_input validate= new validate_input(register_activity.this,getApplicationContext());



                String email = email_from_layout.getText().toString();
                String password = password_from_layout.getText().toString();
                if(validate.isValidEmail(email) && validate.isValidPassword(password))
                {
                    progressBar_from_layout.setVisibility(View.VISIBLE);

                    // Create user in Firebase
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(register_activity.this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            // helperFunctions_class.showToast(register_activity.this,"User Registration Finished");
                            progressBar_from_layout.setVisibility(View.GONE);

                            if (!task.isSuccessful())
                            {

                                helperFunctions_class.showToast(register_activity.this,"User Registration Failed");
                            }
                            else
                            {
                                helperFunctions_class.showToast(register_activity.this,"User Registration Successful");
                                startActivity(new Intent(getApplicationContext(), login_activity.class));
                                finish();

                            }
                        }
                    });
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar_from_layout.setVisibility(View.GONE);
    }




}
