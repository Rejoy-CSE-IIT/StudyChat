package com.geometry.chatprogramfinal.k_ImageEditor;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.geometry.chatprogramfinal.R;

// class for the Erase Image dialog
public class j_f_EraseImageDialogFragment extends DialogFragment
{
    // create an AlertDialog and return it
    @Override
    public Dialog onCreateDialog(Bundle bundle)
    {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());


        // set the AlertDialog's message
        builder.setMessage(R.string.message_erase);

        // add Erase Button
        builder.setPositiveButton(R.string.button_erase,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        ImageEditor.doodleView.clear(); // clear image
                    }
                }
        );

        // add cancel Button
        builder.setNegativeButton(android.R.string.cancel, null);
        return builder.create(); // return dialog
    }

    /*
    // gets a reference to the MainActivityFragment
    private j_b_MainActivityFragment getDoodleFragment()
    {
        return (j_b_MainActivityFragment)  getFragmentManager().findFragmentById(
                R.id.doodleFragment);
    }*/

    // tell MainActivityFragment that dialog is now displayed
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        // j_b_MainActivityFragment fragment = getDoodleFragment();

    }

    // tell MainActivityFragment that dialog is no longer displayed
    @Override
    public void onDetach()
    {
        super.onDetach();

        //   j_b_MainActivityFragment fragment = getDoodleFragment();


    }
}
