package com.geometry.chatprogramfinal.t_a_analytics;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.google.firebase.analytics.FirebaseAnalytics;

public class a_analytics extends AppCompatActivity
{

    private FirebaseAnalytics firebase_analytics;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_analytics);


        // Obtain the Firebase Analytics instance.
        firebase_analytics = FirebaseAnalytics.getInstance(this);
        //Sets whether analytics collection is enabled for this app on this device.
        firebase_analytics.setAnalyticsCollectionEnabled(true);
        //Sets the minimum engagement time required before starting a session.
        // /The default value is 10000 (10 seconds). Let's make it 20 seconds.
        firebase_analytics.setMinimumSessionDuration(20000);


        if(AppFirstTime() == 1)
        {
            ShowFeedBackDialogue();
            ChangeAppFirstTimeFlag(2); // To show the dialog later
        }
        else
        {
            ChangeAppFirstTimeFlag(1);
        }
    }

    // Checks if the app start for the first time
    private int AppFirstTime()
    {
        // If this method returns 1, it means first time app open
        // If it returns another number other than 1, it means this is not first time.
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        int defaultValue = sharedPref.getInt("first_open", 0);

        return defaultValue;
    }

    private void ChangeAppFirstTimeFlag(int number)
    {
        // writes to the sharedpreference
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("first_open", number);
        editor.commit();
    }

    private void ShowFeedBackDialogue()
    {
        CharSequence feedbackOptions[] = new CharSequence[] {"Positive", "Negative"};

        final Bundle bundle = new Bundle();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you have positive feedback about the App?");
        builder.setItems(feedbackOptions, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case 0:
                        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, which);
                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Positive");
                        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "text");
                        firebase_analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                        //Sets a user property to a given value.
                        firebase_analytics.setUserProperty("userFeedback", "Positive");
                        helperFunctions_class.showToast(a_analytics.this,"Thank you for the Positive feed back");

                        break;
                    case 1:
                        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, which);
                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Negative");
                        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "text");
                        firebase_analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                        //Sets a user property to a given value.
                        firebase_analytics.setUserProperty("userFeedback", "Negative");
                        helperFunctions_class.showToast(a_analytics.this,"Sorry for the inconvenience. We will improve project");
                }
            }
        });
        builder.show();
    }

    private void TestUpload()
    {

        int i=0;
        i=i+1;
        System.out.println(i);

        int k=0;

        System.out.println(k);
    }
}
