package com.geometry.chatprogramfinal.k_ImageEditor;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.k_ImageEditor.ImageFilters.ImageFilters;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.geometry.chatprogramfinal.z_e_MyScrollView.MyScrollView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import id.zelory.compressor.Compressor;
import id.zelory.compressor.FileUtil;

import static com.geometry.chatprogramfinal.R.id.crop;

public class ImageEditor extends AppCompatActivity
{

    public static j_c_DoodleView                      doodleView;                              // handles touch events and draws
    public static boolean SCROLLABLE                    =true;
    private Button ImageOnText;
    private EditText TextOnImageEditText;
    private Uri mImageUri=null;
    public static Bitmap bitmap_P=null;
    private static final int      REQUEST_WRITE_STORAGE = 112;
    private static final int           REQUEST_OPEN_GALLEY =0;
    private static String              TAG = "PermissionDemo";

   // ScrollView scrollView;
   public int I=0;
   public  static  MyScrollView  scrollView;
    File actualImage_file;

    Handler handler;
    public ProgressDialog progressDialog=null;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_image_editor);
        // get reference to the DoodleView
        doodleView = (j_c_DoodleView) findViewById(R.id.doodleView);
        doodleView.setActivity(ImageEditor.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageOnText         =  (Button)  findViewById(R.id.selectText);
        TextOnImageEditText = (EditText)   findViewById(R.id.messageTxt);
       // scrollView = (ScrollView)   findViewById(R.id.editorView);

         scrollView = (MyScrollView) findViewById(R.id.editorView);

        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                progressDialog.dismiss();
            }
        };


        ImageOnText.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                doodleView.StringValue=TextOnImageEditText.getText().toString();
                I=(I+1)%100;

                if(I%2==1)
                {
                    ImageOnText.setText("Select\n Draw");
                    ImageOnText.setBackground(getResources().getDrawable(R.drawable.button_bg_round_0));
                    doodleView.string_on_text = true;
                }
                else
                {

                    ImageOnText.setText("Select\n Text");
                    ImageOnText.setBackground(getResources().getDrawable(R.drawable.button_bg_round_1));
                    doodleView.string_on_text = false;

                }



            }
        });


        //scrollView.setScrolling(true); // to disable scrolling

/*

        scrollView.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                // TODO Auto-generated method stub
                return ImageEditor.SCROLLABLE;
            }
        });*/












    }




    // confirm whether image should be erase
    private void confirmErase()
    {
        // j_f_EraseImageDialogFragment fragment = new j_f_EraseImageDialogFragment();
        // fragment.show(fragment.getFragmentManager(),"erase dialog");


        FragmentManager fm = getSupportFragmentManager();

        j_f_EraseImageDialogFragment frag = new j_f_EraseImageDialogFragment();
        //j_d_ColorDialogFragment j_d_colorDialogfragment = j_d_ColorDialogFragment.newInstance("Change Color");
        frag.show(fm, "erase");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.doodle_fragment_menu, menu);
        return true;
    }

    // handle choice from options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {



        // switch based on the MenuItem id
        switch (item.getItemId())
        {

            case R.id.applyImage:
                ApplyImage();
                return true; // consume the menu event*/


            case R.id.delete_drawing:
                confirmErase();
              //  bitmap_P.eraseColor(Color.WHITE);
                return true; // consume the menu event

            case R.id.color:

                FragmentManager fm = getSupportFragmentManager();

                j_d_ColorDialogFragment frag = new j_d_ColorDialogFragment();
                //j_d_ColorDialogFragment j_d_colorDialogfragment = j_d_ColorDialogFragment.newInstance("Change Color");
                frag.show(fm, "fragment_edit_name");

                return true; // consume the menu event



            case R.id.applyImageToDoodle:
                ApplyImageToDoodle(); // check permission and save current image
                return true; // consume the menu event*/



            case R.id.line_width:

                FragmentManager FL = getSupportFragmentManager();

                j_e_LineWidthDialogFragment fragL = new j_e_LineWidthDialogFragment();
                //j_d_ColorDialogFragment j_d_colorDialogfragment = j_d_ColorDialogFragment.newInstance("Change Color");
                fragL.show(FL, "fragment_edit_name");
            return true; // consume the menu event*/

               case crop:
                   CropImage(); // check permission and save current image
                return true; // consume the menu event*/



            case R.id.effect_grayscale:
                showProcessDialog("Image Processing","Applying grey Scle plz wait");


                new Thread( new Runnable()
                {
                    @Override
                    public void run()
                    {




                        handler.post(

                                new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        ImageFilters imgFilterG;

                                        //

                                        imgFilterG = new ImageFilters();

                                        bitmap_P =imgFilterG.applyGreyscaleEffect( doodleView.bitmap) ;
                                        apply_image_view();
                                        doodleView.invalidate();
                                        //or me a call to invalidate() only refresh the view and a call to requestLayout()
                                        // refresh the view and compute the size of the view in the screen.
                                        doodleView.requestLayout();

                                        handler.sendEmptyMessage(0);


                                        //
                                    }
                                }

                        );


                        //Handler handler;
                        //handler = new Handler(getActivity().getApplicationContext().getMainLooper());








                    }
                }).start();
                return true;

            case R.id.effect_black:

                /*
                ImageFilters imgFilter_B;
                imgFilter_B = new ImageFilters();

                bitmap_P =imgFilter_B.applyBlackFilter( bitmap_P) ;
                apply_image_view();
                doodleView.invalidate();
                //or me a call to invalidate() only refresh the view and a call to requestLayout()
                // refresh the view and compute the size of the view in the screen.
                doodleView.requestLayout();*/






                showProcessDialog("Image Processing","Applying  light dark  plz wait");


                new Thread( new Runnable()
                {
                    @Override
                    public void run()
                    {




                        handler.post(

                                new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        ImageFilters imgFilterB;

                                        //

                                        imgFilterB = new ImageFilters();

                                        bitmap_P =imgFilterB.applyBlackFilter(  doodleView.bitmap) ;
                                        apply_image_view();
                                        doodleView.invalidate();
                                        //or me a call to invalidate() only refresh the view and a call to requestLayout()
                                        // refresh the view and compute the size of the view in the screen.
                                        doodleView.requestLayout();

                                        handler.sendEmptyMessage(0);


                                        //
                                    }
                                }

                        );


                        //Handler handler;
                        //handler = new Handler(getActivity().getApplicationContext().getMainLooper());








                    }
                }).start();



                return true;


            case R.id.effect_boost_1:



/*
                ImageFilters imgFilter_BB;

                imgFilter_BB = new ImageFilters();

                bitmap_P =imgFilter_BB.applyBoostEffect( bitmap_P, 1, 40) ;
                apply_image_view();
                doodleView.invalidate();
                //or me a call to invalidate() only refresh the view and a call to requestLayout()
                // refresh the view and compute the size of the view in the screen.
                doodleView.requestLayout();*/



                showProcessDialog("Image Processing","Applying  Boost effect plz  wait");


                new Thread( new Runnable()
                {
                    @Override
                    public void run()
                    {




                        handler.post(

                                new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        ImageFilters imgFilterBB;

                                        //

                                        imgFilterBB = new ImageFilters();

                                        bitmap_P =imgFilterBB.applyBoostEffect(  doodleView.bitmap, 1, 40) ;
                                        apply_image_view();
                                        doodleView.invalidate();
                                        //or me a call to invalidate() only refresh the view and a call to requestLayout()
                                        // refresh the view and compute the size of the view in the screen.
                                        doodleView.requestLayout();

                                        handler.sendEmptyMessage(0);


                                        //
                                    }
                                }

                        );


                        //Handler handler;
                        //handler = new Handler(getActivity().getApplicationContext().getMainLooper());








                    }
                }).start();


                return true;


            case R.id.Brightness:

                /*
                ImageFilters imgFilter_BT;
                imgFilter_BT = new ImageFilters();

                bitmap_P =imgFilter_BT.applyBrightnessEffect( bitmap_P, 80) ;
                apply_image_view();
                doodleView.invalidate();
                //or me a call to invalidate() only refresh the view and a call to requestLayout()
                // refresh the view and compute the size of the view in the screen.
                doodleView.requestLayout();*/
                showProcessDialog("Image Processing","Applying Brightness effect plz  wait");


                new Thread( new Runnable()
                {
                    @Override
                    public void run()
                    {




                        handler.post(

                                new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        ImageFilters imgFilterBT;

                                        //

                                        imgFilterBT = new ImageFilters();

                                        bitmap_P =imgFilterBT.applyBrightnessEffect(  doodleView.bitmap, 80) ;
                                        apply_image_view();
                                        doodleView.invalidate();
                                        //or me a call to invalidate() only refresh the view and a call to requestLayout()
                                        // refresh the view and compute the size of the view in the screen.
                                        doodleView.requestLayout();

                                        handler.sendEmptyMessage(0);


                                        //
                                    }
                                }

                        );


                        //Handler handler;
                        //handler = new Handler(getActivity().getApplicationContext().getMainLooper());








                    }
                }).start();


                return true;

            case R.id.hue:
/*
                ImageFilters imgFilter_H;
                imgFilter_H = new ImageFilters();

                bitmap_P =imgFilter_H.applyHueFilter( bitmap_P, 2) ;
                apply_image_view();
                doodleView.invalidate();
                //or me a call to invalidate() only refresh the view and a call to requestLayout()
                // refresh the view and compute the size of the view in the screen.
                doodleView.requestLayout();*/

                showProcessDialog("Image Processing","Applying Paint effect plz  wait");


                new Thread( new Runnable()
                {
                    @Override
                    public void run()
                    {




                        handler.post(

                                new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        ImageFilters imgFilterP;

                                        //

                                        imgFilterP = new ImageFilters();

                                        bitmap_P =imgFilterP.applyHueFilter(  doodleView.bitmap, 2);
                                        apply_image_view();
                                        doodleView.invalidate();
                                        //or me a call to invalidate() only refresh the view and a call to requestLayout()
                                        // refresh the view and compute the size of the view in the screen.
                                        doodleView.requestLayout();

                                        handler.sendEmptyMessage(0);


                                        //
                                    }
                                }

                        );


                        //Handler handler;
                        //handler = new Handler(getActivity().getApplicationContext().getMainLooper());








                    }
                }).start();


                return true;






        }

        return super.onOptionsItemSelected(item);
    }

    // requests for the permission needed for saving the image if
    // necessary or saves the image if the app already has permission

    private void ApplyImageToDoodle()
    {
        float screenwidth_half=0.0f;
        float Height=0.0f;

        try
        {
            bitmap_P =  doodleView.bitmap;
            /*
             screenwidth_half= helperFunctions_class.getScreenWidth(ImageEditor.this )/2.0f;
              Height = ((float)screenwidth_half* (float)bitmap_P.getHeight() ) /(float)bitmap_P.getWidth();
*/



            getImageUri();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




        Intent in = new Intent();

        in.putExtra("width",(int) bitmap_P.getWidth());
        in.putExtra("height",(int) bitmap_P.getHeight());
        Log.d("TEST_1", "Width =>" + screenwidth_half+"Height=>"+Height);
        in.setData(mImageUri);
        setResult(RESULT_OK, in);
        finish();

    }

    private void CropImage()
    {

        try
        {
            bitmap_P =  doodleView.bitmap;
            getImageUri( );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }






        //CropImage.activity(mImageUri).start(getActivity().getApplicationContext(), this);
        Intent intent = CropImage.activity(mImageUri)
                .getIntent(getApplicationContext());
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);


    }
    private void ApplyImage()
    {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_OPEN_GALLEY);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent_resultdata)
    {

        super.onActivityResult(requestCode, resultCode, intent_resultdata);

        // Check which request we're responding to
        if (requestCode == REQUEST_OPEN_GALLEY && resultCode ==RESULT_OK)
        {

           // helperFunctions_class.showToast(ImageEditor.this, "CheckA");

            if(intent_resultdata!=null)
            {
             //   helperFunctions_class.showToast(ImageEditor.this, "CheckB");
                mImageUri = intent_resultdata.getData();

                try {
                    final File actualImage;
                    actualImage = FileUtil.from(this, mImageUri);
                    bitmap_P = Compressor.getDefault(this).compressToBitmap(actualImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //   helperFunctions_class.showToast(ImageEditor.this, "CheckC");

                try
                {
                    //bitmap_P = MediaStore.Images.Media.getBitmap( getApplicationContext().getContentResolver(), mImageUri);
                //    helperFunctions_class.showToast(ImageEditor.this, "CheckD"+"::"+mImageUri);

                    /*
                    actualImage_file = FileUtil.from(this, mImageUri);
               //     helperFunctions_class.showToast(ImageEditor.this, "CheckE");
                    bitmap_P = Compressor.getDefault(this).compressToBitmap(actualImage_file);*/

/*

                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(
                                mImageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    bitmap_P = BitmapFactory.decodeStream(imageStream);


                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap_P.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    try {
                        stream.close();
                        stream = null;
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
*/
                   // helperFunctions_class.showToast(ImageEditor.this, "CheckF");


                    apply_image_view();

                    //Generally, invalidate() means 'redraw on screen' and results to a call of the view's onDraw() method.
                    // So if something changes and it needs to be reflected on screen, you need to call invalidate().
                    // However, for built-in widgets you rarely, if ever, need to call it yourself.
                    // When you change the state of a widget, internal code will call invalidate()
                    // as necessary and your change will be reflected on screen. For example,
                    // if you call TextView.setText(), after doing a lot of internal processing
                    // (will the text fit on screen, does it need to be ellipsised, etc.),
                    // TextView will call invalidate() before setText() returns. Similarly for other widgets.
                    doodleView.invalidate();
                    //or me a call to invalidate() only refresh the view and a call to requestLayout()
                    // refresh the view and compute the size of the view in the screen.
                    doodleView.requestLayout();


                }



                catch (Exception e)
                {
                    e.printStackTrace();
                }



            }
        }

        if (requestCode ==  CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  && resultCode ==RESULT_OK)
        {



            CropImage.ActivityResult result = CropImage.getActivityResult(intent_resultdata);



            //mImageUri = intent_resultdata.getData();
            mImageUri = result.getUri();

            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(
                        mImageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            bitmap_P = BitmapFactory.decodeStream(imageStream);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap_P.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            try {
                stream.close();
                stream = null;
            } catch (IOException e) {

                e.printStackTrace();
            }

            apply_image_view();
            doodleView.invalidate();
            doodleView.requestLayout();














        }





    }



    private void apply_image_view()

    {
        doodleView.noupdate=true;
        float screenwidth= helperFunctions_class.getScreenWidth(ImageEditor.this )/1.3f;
       // helperFunctions_class.showToast(ImageEditor.this, "Check"+screenwidth);


        float Height = 0;

        float Width  = 0;

        LinearLayout.LayoutParams parms=null;

        Height = ((float)screenwidth * (float)bitmap_P.getHeight() ) /(float)bitmap_P.getWidth();
        Width =screenwidth;
        //helperFunctions_class.showToast(ImageEditor.this, "W+H:: "+Width+"::"+Height+"Width high");


            // newHeight = (screenWidth * decodedByte.getHeight()) / decodedByte.getWidth();


/*
            if(bitmap_P.getWidth()< bitmap_P.getHeight())
            {
                 Height = ((float)doodleView.getWidth() * (float)bitmap_P.getHeight() ) /(float)bitmap_P.getWidth();
                 Width =doodleView.getWidth();
                helperFunctions_class.showToast(ImageEditor.this, "W+H:: "+Width+"::"+Height+"Height high");

                // Width = (screenHieight * bitmap_P.getWidth()  ) /bitmap_P.getHeight();
               // Height =  screenHieight;
              //  helperFunctions_class.showToast(ImageEditor.this, "W+H:: "+Width+"::"+Height+"Height high");

            }
            else
            {

            }
*/







        parms = new LinearLayout.LayoutParams((int)Width,(int)Height);

        parms.gravity= Gravity.CENTER_HORIZONTAL;
        this.doodleView.setLayoutParams(parms);
        Bitmap bitmapD =  Bitmap.createScaledBitmap(bitmap_P, (int)Width,(int)Height, false);
        doodleView.bitmap= bitmapD.copy(Bitmap.Config.ARGB_8888, true);
              //  = bitmapT.copy(Bitmap.Config.ARGB_8888, true);
       // doodleView.bitmap = Bitmap.createScaledBitmap(bitmap_P, (int)Width,(int)Height, false);
        doodleView.bitmapCanvas = new Canvas( doodleView.bitmap);
        doodleView.pathMap.clear(); // remove all paths
        doodleView.previousPointMap.clear(); // remove all previous points



        // doodleView.getLayoutParams().width = bitmap_P.getWidth();

       //  Bitmap bitmapT = Bitmap.createScaledBitmap(bitmap_P,(int) new_Width, (int)Height, true);
       //  bitmap_P = bitmapT.copy(Bitmap.Config.ARGB_8888, true);
        //doodleView.clear();
       // doodleView.bitmap = bitmap_P;
        //doodleView.initiallize();
       // getImageUri();
         //doodleView.bitmap = bitmapT.copy(Bitmap.Config.ARGB_8888, true);
       // Glide.with(this).load(mImageUri).into(doodleView);

        //doodleView.pathMap.clear(); // remove all paths
       // doodleView.previousPointMap.clear(); // remove all previous points



    }









    // returns the DoodleView
    public j_c_DoodleView getDoodleView()
    {
        return doodleView;
    }


    public  void  getImageUri(  )
    {
        int permission = ContextCompat.checkSelfPermission(getApplicationContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED)
        {
            Log.i(TAG, "Permission to record denied");

            if (ActivityCompat.shouldShowRequestPermissionRationale(ImageEditor.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setMessage("Permission to access Gallery")
                        .setTitle("Permission required");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        Log.i(TAG, "Clicked");
                        makeRequest();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
            else
            {
                makeRequest();
            }
        }
        else
        {
            getUriafterPermission();
        }

    }

    protected void makeRequest()
    {
        ActivityCompat.requestPermissions(ImageEditor.this,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_WRITE_STORAGE);
    }

    public  void  getUriafterPermission( )
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap_P.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage( getContentResolver(), bitmap_P, "Title", null);
        mImageUri = Uri.parse(path);
        //  return Uri.parse(path);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE:
            {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED)
                {

                    Log.i(TAG, "Permission has been denied by user");

                }
                else
                {



                    // Log.i(TAG, "Permission has been granted by user");
                    // Uri test = getImageUri(ac_DrawImage.this,BitmapfromDrawable);
                    // StoreImage(image);
                    //
                    // Toast.makeText(ac_DrawImage.this, "URI",Toast.LENGTH_LONG).show();
                    getUriafterPermission();



                }
                return;
            }
        }
    }


    private void showProcessDialog(String title,String message)
    {
        progressDialog = new ProgressDialog(ImageEditor.this);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
    }


}
