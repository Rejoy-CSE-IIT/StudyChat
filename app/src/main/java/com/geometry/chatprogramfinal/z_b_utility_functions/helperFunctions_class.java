package com.geometry.chatprogramfinal.z_b_utility_functions;


import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.Toast;

import com.geometry.chatprogramfinal.c_homePage.ChatMain_activity;

public class helperFunctions_class
{



    public static boolean hasText(TextInputLayout inputLayout)
    {
        return !inputLayout.getEditText().getText().toString().trim().equals("");
    }

    public static String getText(TextInputLayout inputLayout)
    {
        return inputLayout.getEditText().getText().toString().trim();
    }

    public static void showToast(Context context, String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }




    public static void  set_user_statics(String UserName,String userId,Boolean loggedIn)
    {


        ChatMain_activity.UserName = UserName;
        ChatMain_activity.userId =userId;
        ChatMain_activity.loggedIn = loggedIn;
    }
    public static float getScreenWidth(Activity activity)
    {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float pxWidth = outMetrics.widthPixels;
        return pxWidth;
    }

    public static float getScreenHeight(Activity activity)
    {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float pxHeight = outMetrics.heightPixels;
        return pxHeight;
    }



}
