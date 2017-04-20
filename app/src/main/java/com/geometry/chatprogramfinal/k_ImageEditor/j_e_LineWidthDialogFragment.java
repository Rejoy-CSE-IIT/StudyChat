package com.geometry.chatprogramfinal.k_ImageEditor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.geometry.chatprogramfinal.R;


// class for the Select Line Width dialog
public class j_e_LineWidthDialogFragment extends DialogFragment
{
    private ImageView widthImageView;

    // create an AlertDialog and return it
    @Override
    public Dialog onCreateDialog(Bundle bundle)
    {

        // create the dialog
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        View lineWidthDialogView =
                getActivity().getLayoutInflater().inflate(
                        R.layout.fragment_j_e__line_width_dialog, null);
        builder.setView(lineWidthDialogView); // add GUI to dialog

        // set the AlertDialog's message
        builder.setTitle(R.string.title_line_width_dialog);

        // get the ImageView
        widthImageView = (ImageView) lineWidthDialogView.findViewById(
                R.id.widthImageView);

        // configure widthSeekBar
        final j_c_DoodleView doodleView = ImageEditor.doodleView;
        final SeekBar widthSeekBar = (SeekBar)
                lineWidthDialogView.findViewById(R.id.widthSeekBar);
        widthSeekBar.setOnSeekBarChangeListener(lineWidthChanged);
        widthSeekBar.setProgress(doodleView.getLineWidth());

        // add Set Line Width Button
        builder.setPositiveButton(R.string.button_set_line_width,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        doodleView.setLineWidth(widthSeekBar.getProgress());
                    }
                }
        );

        return builder.create(); // return dialog
    }

    // return a reference to the MainActivityFragment
    /*
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

        //j_b_MainActivityFragment fragment = getDoodleFragment();


    }

    // tell MainActivityFragment that dialog is no longer displayed
    @Override
    public void onDetach()
    {
        super.onDetach();

        //  j_b_MainActivityFragment fragment = getDoodleFragment();


    }

    // OnSeekBarChangeListener for the SeekBar in the width dialog
    private final SeekBar.OnSeekBarChangeListener lineWidthChanged =
            new SeekBar.OnSeekBarChangeListener()
            {

                final Bitmap bitmap = Bitmap.createBitmap(
                        400, 100, Bitmap.Config.ARGB_8888);
                final Canvas canvas = new Canvas(bitmap); // draws into bitmap

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    // configure a Paint object for the current SeekBar value
                    Paint p = new Paint();
                    p.setColor(
                            ImageEditor.doodleView.getDrawingColor());
                    p.setStrokeCap(Paint.Cap.ROUND);
                    p.setStrokeWidth(progress);

                    // erase the bitmap and redraw the line
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        bitmap.eraseColor(
                                getResources().getColor(android.R.color.transparent,
                                        getContext().getTheme()));
                    }
                    canvas.drawLine(30, 50, 370, 50, p);
                    widthImageView.setImageBitmap(bitmap);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {} // required

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {} // required
            };
}
