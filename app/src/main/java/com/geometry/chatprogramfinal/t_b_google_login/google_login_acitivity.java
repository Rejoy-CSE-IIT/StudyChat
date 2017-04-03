package com.geometry.chatprogramfinal.t_b_google_login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.geometry.chatprogramfinal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class google_login_acitivity extends AppCompatActivity
{
    // Initialize FirebaseAuth and AuthStateListener
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    // Choose an arbitrary request code value
    private static final int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login_acitivity);

        // Obtain the FirebaseAuth instance
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // Get the Firebase User
                FirebaseUser user = firebaseAuth.getCurrentUser();
                // Check whether user has logged in or not
                if (user != null) {
                    // User is logged in
                    Toast.makeText(google_login_acitivity.this, "You're now signed in. Welcome to FirebaseHero!.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // User is not logged in
                    //User is not logged in
                    startActivityForResult( AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled( false).setProviders( AuthUI.EMAIL_PROVIDER, AuthUI.GOOGLE_PROVIDER,AuthUI.FACEBOOK_PROVIDER,AuthUI.TWITTER_PROVIDER) .build(), RC_SIGN_IN);

                }
            }
        };
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener( mAuthStateListener);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        mFirebaseAuth.addAuthStateListener( mAuthStateListener);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/ Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id)
        {
            case R.id.action_sign_out:
                // Sign Out menu item click
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected( item);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
}
