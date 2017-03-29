package com.geometry.chatprogramfinal.z_c_validate_input;

import android.app.Activity;
import android.content.Context;

import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rijoy on 3/29/2017.
 */

public class validate_input
{


    public int                                                               min_password_length =6;
    public int                                                             max_password_length =100;
    Activity                                                                                activity;
    Context                                                                                      ctx;

    public validate_input(Activity activity, Context ctx)
    {
        this.activity = activity;
        this.ctx = ctx;
    }






    // validating email id
    public boolean isValidEmail(String email)
    {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        boolean isvalid=  matcher.matches();
        if(!isvalid)
            helperFunctions_class.showToast(this.ctx,"Please Enter Valid Email");
        return isvalid;
    }

    // validating password with retype password
    public boolean isValidPassword(String pass)
    {
        if (pass != null && pass.length() >= min_password_length &&  pass.length() <= max_password_length )
        {
            return true;
        }

        if(pass.length() < min_password_length)
          helperFunctions_class.showToast(this.ctx,"Password must be of minimum length "+min_password_length);
        if(pass.length() > max_password_length)
            helperFunctions_class.showToast(this.ctx,"Password length cant exceed "+max_password_length);
        return false;
    }


}
