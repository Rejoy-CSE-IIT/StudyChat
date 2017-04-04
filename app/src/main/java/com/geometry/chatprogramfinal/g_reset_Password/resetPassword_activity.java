package com.geometry.chatprogramfinal.g_reset_Password;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.f_login.login_activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetPassword_activity extends AppCompatActivity {
    // Intialisation of the subviews
    private EditText inputEmail;
    private Button btnReset;
    private ProgressBar progressBar;
    private TextView forget_msg_label_from_xml;


    // Initialisation of FirebaseAuth variable
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_reset_password);


        // Construction of subviews
        inputEmail = (EditText) findViewById(R.id.email);
        forget_msg_label_from_xml = (TextView) findViewById(R.id.forget_msg_label_from_xml);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Get the instance of FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Set the on click listener to the btnReset button
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the user entered email address
                String email = inputEmail.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(
                            getApplicationContext(),
                            "Enter your email address!",
                            Toast.LENGTH_LONG
                    ).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful())
                                {

                                    progressBar.setVisibility(View.GONE);
                                    Intent intent = new Intent(getApplicationContext(), login_activity.class);
                                    intent.putExtra("fromReset","fromRegister");
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    progressBar.setVisibility(View.GONE);
                                    forget_msg_label_from_xml.setText("Failed to send reset email! Try again later!");

                                }
                            }
                        });
            }
        });

    }

}
