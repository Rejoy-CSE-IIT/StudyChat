package com.geometry.chatprogramfinal.k_ImageEditor;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.SeekBar;

import com.geometry.chatprogramfinal.R;


// class for the Select Color dialog
public class j_d_ColorDialogFragment extends DialogFragment
{
    private SeekBar                                   alphaSeekBar;
    private SeekBar                                     redSeekBar;
    private SeekBar                                    greenSeekBar;
    private SeekBar                                     blueSeekBar;
    private View                                          colorView;
    private int                                               color;

    public j_d_ColorDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    /*
    public static j_d_ColorDialogFragment newInstance(String title)
    {
        j_d_ColorDialogFragment frag = new j_d_ColorDialogFragment();

        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }*/

/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.j_d_colordialogfragment, container);
    }*/

    /*
        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
        {
            super.onViewCreated(view, savedInstanceState);


            // get the color SeekBars and set their onChange listeners
            alphaSeekBar = (SeekBar) view.findViewById(
                    R.id.alphaSeekBar);
            redSeekBar = (SeekBar) view.findViewById(
                    R.id.redSeekBar);
            greenSeekBar = (SeekBar) view.findViewById(
                    R.id.greenSeekBar);
            blueSeekBar = (SeekBar) view.findViewById(
                    R.id.blueSeekBar);
            colorView = view.findViewById(R.id.colorView);



            // register SeekBar event listeners
            alphaSeekBar.setOnSeekBarChangeListener(colorChangedListener);
            redSeekBar.setOnSeekBarChangeListener(colorChangedListener);
            greenSeekBar.setOnSeekBarChangeListener(colorChangedListener);
            blueSeekBar.setOnSeekBarChangeListener(colorChangedListener);




            // Fetch arguments from bundle and set title
            String title = getArguments().getString("title");
            getDialog().setTitle(title);
            // Show soft keyboard automatically and request focus to field
          //  mEditText.requestFocus();
            getDialog().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }*/
    // OnSeekBarChangeListener for the SeekBars in the color dialog
    private final SeekBar.OnSeekBarChangeListener colorChangedListener =
            new SeekBar.OnSeekBarChangeListener()
            {
                // display the updated color
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser)
                {

                    if (fromUser) // user, not program, changed SeekBar progress
                        color = Color.argb(alphaSeekBar.getProgress(),
                                redSeekBar.getProgress(), greenSeekBar.getProgress(),
                                blueSeekBar.getProgress());
                    colorView.setBackgroundColor(color);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {} // required

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {} // required
            };


    @Override
    public Dialog onCreateDialog(Bundle bundle)
    {

        // create dialog
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        View colorDialogView = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_j_d__color_dialog, null);
        builder.setView(colorDialogView); // add GUI to dialog

        // set the AlertDialog's message
        builder.setTitle(R.string.title_color_dialog);

        // get the color SeekBars and set their onChange listeners
        alphaSeekBar = (SeekBar) colorDialogView.findViewById(
                R.id.alphaSeekBar);
        redSeekBar = (SeekBar) colorDialogView.findViewById(
                R.id.redSeekBar);
        greenSeekBar = (SeekBar) colorDialogView.findViewById(
                R.id.greenSeekBar);
        blueSeekBar = (SeekBar) colorDialogView.findViewById(
                R.id.blueSeekBar);
        colorView = colorDialogView.findViewById(R.id.colorView);

        // register SeekBar event listeners
        alphaSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        redSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        greenSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        blueSeekBar.setOnSeekBarChangeListener(colorChangedListener);

        // use current drawing color to set SeekBar values
        final j_c_DoodleView doodleView = ImageEditor.doodleView;
        color = doodleView.getDrawingColor();
        alphaSeekBar.setProgress(Color.alpha(color));
        redSeekBar.setProgress(Color.red(color));
        greenSeekBar.setProgress(Color.green(color));
        blueSeekBar.setProgress(Color.blue(color));

        // add Set Color Button
        builder.setPositiveButton(R.string.button_set_color,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        doodleView.setDrawingColor(color);
                    }
                }
        );

        return builder.create(); // return dialog


    }

    /*
    // gets a reference to the MainActivityFragment
    private j_b_MainActivityFragment getDoodleFragment()
    {
        return (j_b_MainActivityFragment) getFragmentManager().findFragmentById(
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
        //  j_b_MainActivityFragment fragment = getDoodleFragment();


    }




}