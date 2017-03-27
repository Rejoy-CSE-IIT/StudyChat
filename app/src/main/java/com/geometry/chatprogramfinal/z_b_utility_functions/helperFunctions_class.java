package com.geometry.chatprogramfinal.z_b_utility_functions;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.Toast;

public class helperFunctions_class
{




    public static void showToast(Context context, String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public static float getScreenWidth(Activity activity)
    {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
       // float pxWidth = outMetrics.widthPixels;
        return outMetrics.widthPixels;

    }
}
