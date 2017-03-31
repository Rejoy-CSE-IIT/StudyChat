package com.geometry.chatprogramfinal.z_c_validate_input;

import android.app.Activity;
import android.content.Context;

import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rijoy on 3/29/2017.
 */

public class validate_input
{
    public int                                                               min_userid_length =3;
    public int                                                             max_userid_length =100;

    public int                                                               min_password_length =6;
    public int                                                             max_password_length =100;
    Activity                                                                                activity;
    Context                                                                                      ctx;
    boolean IdExists=false;
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


    public boolean checkIdExists(String userid)
    {
        DatabaseReference databaseRef;
        databaseRef= FirebaseDatabase.getInstance()
                .getReference().child("userId_List").child(userid.toLowerCase());

       // databaseRef.h
        //IdExists=false;
    //  ValueEventListener checkid_lister;

   /*

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    // TODO: handle the case where the data already exists
                    helperFunctions_class.showToast(ctx,"User Id  is already taken");


                }
                else
                {
                    // TODO: handle the case where the data does not yet exist
                    IdExists=true;
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }


        });

      //  databaseRef.addValueEventListener(checkid_lister);
        //databaseRef.removeEventListener(checkid_lister);
*/


        return  IdExists;

    }

    public boolean isValidUserId(String userid)
    {
        if (userid != null && userid.length() >= min_userid_length &&  userid.length() <= max_userid_length )
        {
            return true;
        }

        if(userid.length() < min_userid_length)
            helperFunctions_class.showToast(this.ctx,"UserId must be of minimum length "+min_userid_length);
        if(userid.length() > max_userid_length)
            helperFunctions_class.showToast(this.ctx,"UserId length cant exceed "+max_userid_length);
        return false;
    }


}
