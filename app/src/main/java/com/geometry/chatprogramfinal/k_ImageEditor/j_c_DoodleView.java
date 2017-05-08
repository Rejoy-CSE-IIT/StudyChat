package com.geometry.chatprogramfinal.k_ImageEditor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
//https://learnpainless.com/android/scrollview/create-custom-scrollview-to-enable-or-disable-scrolling
public class j_c_DoodleView extends View {

    private j_c_DoodleView doodleView; // handles touch events and draws
    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;
    private boolean dialogOnScreen = false;

    // value used to determine whether user shook the device to erase
    private static final int ACCELERATION_THRESHOLD = 100000;

    // used to identify the request for using external storage, which
    // the save image feature needs
    private static final int SAVE_IMAGE_PERMISSION_REQUEST_CODE = 1;

    // used to determine whether user moved a finger enough to draw again
    private static final float                                                 TOUCH_TOLERANCE = 10;
    // drawing area for displaying or saving
    public Bitmap bitmap;
    // used to to draw on the bitmap
    public Canvas bitmapCanvas;
    // used to draw bitmap onto screen
    private final Paint paintScreen;
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    Activity activity;

    // used to draw lines onto bitmap
    private final Paint                                                                   paintLine;
    public boolean  string_on_text=false;
    public String  StringValue="TEST";
    public boolean noupdate =false;

    // Maps of current Paths being drawn and Points in those Paths
    public final Map<Integer, Path> pathMap                                      = new HashMap<>();
    public final Map<Integer, Point> previousPointMap                            = new HashMap<>();

    // returns the DoodleView
    public j_c_DoodleView getDoodleView()
    {
        return doodleView;
    }

    // indicates whether a dialog is displayed
    public void setDialogOnScreen(boolean visible)
    {
        dialogOnScreen = visible;
    }
    // DoodleView constructor initializes the DoodleView
    public j_c_DoodleView(Context context, AttributeSet attrs)
    {

        super(context, attrs); // pass context to View's constructor
        paintScreen = new Paint(); // used to display bitmap onto screen

        // set the initial display settings for the painted line
        paintLine = new Paint();
        paintLine.setAntiAlias(true); // smooth edges of drawn line
        paintLine.setColor(Color.BLACK); // default color is black
        paintLine.setStyle(Paint.Style.STROKE); // solid line
        paintLine.setStrokeWidth(5); // set the default line width
        paintLine.setStrokeCap(Paint.Cap.ROUND); // rounded line ends

    }

    // creates Bitmap and Canvas based on View's size
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH)
    {
       if(!noupdate)
       {
           bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                    Bitmap.Config.ARGB_8888);
            bitmapCanvas = new Canvas(bitmap);
           bitmap.eraseColor(Color.WHITE); // erase the Bitmap with white*/
        }

        noupdate =false;
    }

    public  void initiallize()
    {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
      //  bitmap.eraseColor(Color.WHITE);
    }

    // clear the painting
    public void clear()
    {
        pathMap.clear(); // remove all paths
        previousPointMap.clear(); // remove all previous points
        bitmap.eraseColor(Color.WHITE); // clear the bitmap
        invalidate(); // refresh the screen
    }

    // set the painted line's color
    public void setDrawingColor(int color)
    {
        paintLine.setColor(color);
    }

    // return the painted line's color
    public int getDrawingColor()
    {
        return paintLine.getColor();
    }

    // set the painted line's width
    public void setLineWidth(int width)
    {
        paintLine.setStrokeWidth(width);
    }

    // return the painted line's width
    public int getLineWidth()
    {
        return (int) paintLine.getStrokeWidth();
    }

    // perform custom drawing when the DoodleView is refreshed on screen
    @Override
    protected void onDraw(Canvas canvas)
    {
        // draw the background screen
        canvas.drawBitmap(bitmap, 0, 0, paintScreen);

        // for each path currently being drawn
        for (Integer key : pathMap.keySet())
            canvas.drawPath(pathMap.get(key), paintLine); // draw line
    }

    // handle touch event
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        /*

        getAction() returns a pointer id and an event (i.e., up, down, move) information.

        getActionMasked() returns just an event (i.e., up, down, move) information.
        Other info is masked out.

         */



        int action            = event.getActionMasked(); // event type
        int actionIndex       = event.getActionIndex(); // pointer (i.e., finger)
        if(!string_on_text)
        {
            // determine whether touch started, ended or is moving
            if (action == MotionEvent.ACTION_DOWN ||
                    action == MotionEvent.ACTION_POINTER_DOWN) {
                touchStarted(event.getX(actionIndex), event.getY(actionIndex),
                        event.getPointerId(actionIndex));
            } else if (action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_POINTER_UP) {
                touchEnded(event.getPointerId(actionIndex));
            } else {
                touchMoved(event);
            }
        }

        else
        {

            // determine whether touch started, ended or is moving
            if (action == MotionEvent.ACTION_DOWN ||
                    action == MotionEvent.ACTION_POINTER_DOWN)
            {
                ImageEditor.scrollView.setScrolling(false); // to disable scrolling


            }
            else if (action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_POINTER_UP)
            {

                                ImageEditor.scrollView.setScrolling(true); // to disable scrolling

            }
            else
            {

            }


            paintScreen.setTextSize(72);

            bitmapCanvas.drawText(StringValue, event.getX(actionIndex), event.getY(actionIndex), paintScreen);

        }
        invalidate(); // redraw
        return true;
    }




    // called when the user touches the screen
    private void touchStarted(float x, float y, int lineID)
    {

        Path path; // used to store the path for the given touch id
        Point point; // used to store the last point in path

        ImageEditor.scrollView.setScrolling(false); // to disable scrolling





        ImageEditor.scrollView.setScrolling(false); // to disable scrolling

        // if there is already a path for lineID
        if (pathMap.containsKey(lineID))
        {
            path = pathMap.get(lineID); // get the Path
            path.reset(); // resets the Path because a new touch has started
            point = previousPointMap.get(lineID); // get Path's last point

            System.out.print("LINE FIRST");
        }
        else
        {
            path = new Path();
            pathMap.put(lineID, path); // add the Path to Map
            point = new Point(); // create a new Point
            previousPointMap.put(lineID, point); // add the Point to the Map
        }

        // move to the coordinates of the touch
        path.moveTo(x, y);
        point.x = (int) x;
        point.y = (int) y;
    }

    // called when the user drags along the screen
    private void touchMoved(MotionEvent event)
    {
        // for each of the pointers in the given MotionEvent
        for (int i = 0; i < event.getPointerCount(); i++)
        {
            // get the pointer ID and pointer index
            int pointerID = event.getPointerId(i);
            int pointerIndex = event.findPointerIndex(pointerID);

            // if there is a path associated with the pointer
            if (pathMap.containsKey(pointerID))
            {
                // get the new coordinates for the pointer
                float newX = event.getX(pointerIndex);
                float newY = event.getY(pointerIndex);

                // get the path and previous point associated with
                // this pointer
                Path path = pathMap.get(pointerID);
                Point point = previousPointMap.get(pointerID);

                // calculate how far the user moved from the last update
                float deltaX = Math.abs(newX - point.x);
                float deltaY = Math.abs(newY - point.y);

                // if the distance is significant enough to matter
                if (deltaX >= TOUCH_TOLERANCE || deltaY >= TOUCH_TOLERANCE)
                {
                    // move the path to the new location
                    /*
                    //http://stackoverflow.com/questions/23046315/android-how-can-i-use-quadto-in-path-class-correctly

                    quadTo(float x1, float y1, float x2, float y2) Add a quadratic bezier from the last point, approaching control
                     point (x1,y1),
                     and ending at (x2,y2).. lineTo(float x, float y)Add a line from the last point to the specified point (x,y).
                     */
                    path.quadTo(point.x, point.y, (newX + point.x) / 2,
                            (newY + point.y) / 2);

                    // store the new coordinates
                    point.x = (int) newX;
                    point.y = (int) newY;

                }
            }
        }
    }

    // called when the user finishes a touch
    private void touchEnded(int lineID)
    {
        Path path = pathMap.get(lineID); // get the corresponding Path
        bitmapCanvas.drawPath(path, paintLine); // draw to bitmapCanvas
        path.reset(); // reset the Path
        ImageEditor.scrollView.setScrolling(true); // to disable scrolling
    }


    /*
    // save the current image to the Gallery
    public void saveImage()
    {
        // use "Doodlz" followed by current time as the image groupName
        final String groupName = "Doodlz" + System.currentTimeMillis() + ".jpg";

        // insert the image on the device
        String location = MediaStore.Images.Media.insertImage
                (
                getContext().getContentResolver(), bitmap, groupName,
                "Doodlz Drawing");

    }*/


}
