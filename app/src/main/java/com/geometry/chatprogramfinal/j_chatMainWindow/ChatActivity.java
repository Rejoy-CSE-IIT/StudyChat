package com.geometry.chatprogramfinal.j_chatMainWindow;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.c_homePage.ChatMain_activity;
import com.geometry.chatprogramfinal.k_ImageEditor.ImageEditor;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.geometry.chatprogramfinal.z_d_Parcelable.chatData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity implements TextToSpeech.OnInitListener
{

    ChildEventListener childEventListener=null;
     public static boolean print_array=false;
    public static boolean block_upload=false;
      int width;
      int height;
    private Button                                               sendBtn;
    private ImageButton imageBtn;
    private EditText                                          messageTxt;
    Handler                                                      handler;
    chatData                                                    chatdata;
    Uri selectedImageUri                          = Uri.parse("noImage");
    //DatabaseReference                         databaseReference_userChat;
  // DatabaseReference                        databaseReference_groupChat;
    DatabaseReference databaseReference_Ref_final;
    private static final int RC_PHOTO_PICKER = 1;
    LinearLayoutManager mManager;
    TextToSpeech TtoS;
    ///Menu
    boolean text_to_speech=false, notification =false;

    private RecyclerView.Adapter mAdapter;

    ////////////////////////////////////////////

    FirebaseStorage storage;
    StorageReference storageRef;


    StorageReference photoRef;
    ProgressDialog pd;
    private                                     RecyclerView recycleView;
   // private                                     c_chat_recycler_view_adapter_class recycleView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {

            case R.id.menu_text_to_speech:

                if(!text_to_speech)
                {
                    text_to_speech = true;
                    item.setTitle("Text To Speech :: ON ");
                }
                else
                {
                    text_to_speech = false;
                    item.setTitle("Text To Speech :: OFF");
                }

                if(ChatMain_activity.TOAST_CONTROL)
                    helperFunctions_class.showToast(ChatActivity.this,"Inside Speech Menu");
                return true;




            case R.id.notification:


                if(!notification)
                {
                    item.setTitle("Notification :: ON ");
                    notification = true;
                }
                else
                {
                    item.setTitle("Notification :: OFF ");
                    notification = false;
                }



                if(ChatMain_activity.TOAST_CONTROL)
                    helperFunctions_class.showToast(ChatActivity.this,"notification Image");

                return true;

            case R.id.testArray:


                if(!ChatActivity.print_array)
                {
                    item.setTitle("Test Print :: ON ");
                    ChatActivity.print_array = true;
                }
                else
                {
                    item.setTitle("Test Print :: OFF ");
                    ChatActivity.print_array = false;
                }



                if(ChatMain_activity.TOAST_CONTROL)
                    helperFunctions_class.showToast(ChatActivity.this,"notification Image");

                return true;

            case R.id.homepage:




                Intent intent = new Intent(ChatActivity.this, ChatMain_activity.class);
                startActivity(intent);






                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_j_chat);

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading Image For Chat \n Please wait....");
        imageBtn    = (ImageButton) findViewById(R.id.imageBtn);
        sendBtn     = (Button) findViewById(R.id.sendBtn);
        messageTxt  = (EditText) findViewById(R.id.messageTxt);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);

        TtoS        =  new TextToSpeech(ChatActivity.this, ChatActivity.this,"com.google.android.tts");
        if(ChatMain_activity.TOAST_CONTROL)
            helperFunctions_class.showToast(ChatActivity.this,"  Create Chat");


    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        if(ChatMain_activity.TOAST_CONTROL)
        helperFunctions_class.showToast(ChatActivity.this,"  ReStart Chat");
        //first clear the recycler view so items are not populated twice
     //   recyclerAdapter.clear();


    }
    @Override
    protected void onResume()
    {
        super.onResume();
        if(ChatMain_activity.TOAST_CONTROL)
        helperFunctions_class.showToast(ChatActivity.this,"Inside Resume Chat 1");
       // c_chat_recycler_view_adapter_class.ChatMessages.clear();

        ////////////////////////////////////////////////////////////////////////////////
///Deciding chat mode

    //   if(!ChatActivity.block_upload)
       {

            storage = FirebaseStorage.getInstance();
            chatdata = getIntent().getParcelableExtra("chatData");
           if(ChatMain_activity.TOAST_CONTROL)
               helperFunctions_class.showToast(ChatActivity.this,"Inside Resume Chat 2");
/*
            databaseReference_groupChat = FirebaseDatabase.getInstance()
                    .getReference().child("groupChat").child(chatdata.getChat_id());
            databaseReference_userChat = FirebaseDatabase.getInstance()
                    .getReference().child("userChat").child(chatdata.getChat_id());
*/


        /*
        mManager.setReverseLayout(false);
       mManager.setStackFromEnd(true);*/
            mManager = new LinearLayoutManager( this);
            recycleView.setLayoutManager(mManager);


            mAdapter = new c_chat_recycler_view_adapter_class( ChatActivity.this);
            recycleView.setAdapter(mAdapter);
            //mAdapter.id_entry.clear();
            // mAdapter.currentItemsLinkedHmap.clear();
            //mAdapter.id
            //recycleView.setAdapter(mAdapter);
/*
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver()
        {
            public void onItemRangeInserted(int positionStart, int itemCount)
            {
                recycleView.smoothScrollToPosition(mAdapter.getItemCount());
            }
        });*/
            c_chat_recycler_view_adapter_class.ChatMessages= new ArrayList<>();


            if(chatdata.getGroup_chat_flag()==0) {
                databaseReference_Ref_final = FirebaseDatabase.getInstance()
                        .getReference().child("groupChat").child(chatdata.getChat_id());

                // setRecyclerView(databaseReference_groupChat);
            }
            else {
                //setRecyclerView(databaseReference_userChat);
                databaseReference_Ref_final = FirebaseDatabase.getInstance()
                        .getReference().child("userChat").child(chatdata.getChat_id());

            }
           setRecyclerView();

            storageRef = storage.getReference("chat_photos");




////////////////////////////////////////////////////////////////////////////////

            setTitle("Chatting as " +ChatMain_activity.UserName );


            //Refference to Main thread looper
            handler = new Handler(getApplicationContext().getMainLooper());

////////////////////////////////////////////////////////////////////////////////
            ;









            //mAdapter = new c_chat_recycler_view_adapter_class(Items);

            // Scroll to bottom on new messages



////////////////////////////////////////////////////////////////////////////////


            sendBtn.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {


                    new Thread( new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            final String message = messageTxt.getText().toString();


                            handler.post(

                                    new Runnable()
                                    {
                                        public void run()
                                        {
                                            messageTxt.setText("");

                                            sendChat(message,0,0);
                                        }
                                    }

                            );




                        }
                    }).start();


                }
            });


            imageBtn.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {

                    ChatActivity.block_upload=true;
                    Intent in = new Intent(getApplicationContext(), ImageEditor.class);
                     startActivityForResult(in, RC_PHOTO_PICKER);



                }
            });


        }

        ChatActivity.block_upload=false;





    }
    void setRecyclerView()
    {
        /*
        if(childEventListener!=null)
        {

            databaseReference_Ref_final.removeEventListener(childEventListener);
        }*/

        childEventListener=new ChildEventListener()
        {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                ChatMessage_data_model_class adatamodelclass = dataSnapshot.getValue(ChatMessage_data_model_class.class);


                float screenwidth_half= helperFunctions_class.getScreenWidth(ChatActivity.this )/2.0f;
                float Height = ((float)screenwidth_half* (float)adatamodelclass.getHeight() ) /(float)adatamodelclass.getWidth();
                adatamodelclass.setWidth((int)screenwidth_half);
                adatamodelclass.setHeight((int)Height);
                String Msg_test = "::"+dataSnapshot.getKey().toString()+"::"+adatamodelclass.getSender_id();
                //adatamodelclass.setMessage(adatamodelclass.getMessage()+Msg_test);
                adatamodelclass.setMessage(adatamodelclass.getMessage() );
                //  c_chat_recycler_view_adapter_class.currentItemsLinkedHmap.put(dataSnapshot.getKey().toString(), adatamodelclass);
                c_chat_recycler_view_adapter_class.ChatMessages.add(adatamodelclass);
                Log.d("PARENT", "Parent Node =>" +dataSnapshot.getKey().toString() +"Sender ID=>"+"("+adatamodelclass.getMessage()+")"+ adatamodelclass.getSender_id());


                if(ChatMain_activity.TOAST_CONTROL)
                    helperFunctions_class.showToast(ChatActivity.this,"Message"+ adatamodelclass.getMessage());


                //http://programmerguru.com/android-tutorial/android-text-to-speech-example/
                //https://www.quora.com/How-do-I-change-a-male-voice-to-a-female-voice-What-are-some-Android-codes-or-apps
                //http://www.androidauthority.com/google-text-to-speech-engine-659528/

                if(text_to_speech )
                {
                    TextToSpeechFunction(adatamodelclass.getMessage());
                }

                if(notification && !(ChatMain_activity.userId.equals(adatamodelclass.getSender_id())))
                {
                    NotificationManager notificationManager = (NotificationManager) ChatActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);

//Define sound URI
                    Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    String message = "New Message Arrived";
                    NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.drawable.chat_icon)
                            .setContentTitle("Buddy Chat")
                            .setContentText(message)
                            .setSound(soundUri); //This sets the sound to play

//Display notification
                    notificationManager.notify(0, mBuilder.build());
                }
                //mAdapter.notifyDataSetChanged();
                //recycleView.smoothScrollToPosition(c_chat_recycler_view_adapter_class.currentItemsLinkedHmap.size() - 1);
                mAdapter.notifyItemInserted(c_chat_recycler_view_adapter_class.ChatMessages.size() - 1);
                recycleView.smoothScrollToPosition(c_chat_recycler_view_adapter_class.ChatMessages.size() - 1);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
/*
                ChatMessage_data_model_class adatamodelclass = dataSnapshot.getValue(ChatMessage_data_model_class.class);

                if(adatamodelclass.getSender_id().equals(ChatMain_activity.userId))
                {
                    ChatMain_activity.UserName = adatamodelclass.getUser_name();
                }

                //Fire base id never changes,user groupName
                c_chat_recycler_view_adapter_class.currentItemsLinkedHmap.put(dataSnapshot.getKey().toString(), adatamodelclass);


                mAdapter.notifyDataSetChanged();
                if(ChatMain_activity.TOAST_CONTROL)
                helperFunctions_class.showToast(ChatActivity.this,"a_data_group_model_class changed =>"+ adatamodelclass.getUser_name());

*/
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {
/*
                ChatMessage_data_model_class adatamodelclass = dataSnapshot.getValue(ChatMessage_data_model_class.class);

                if(adatamodelclass.getSender_id().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                {
                    ChatMain_activity.userId = null;
                    ChatMain_activity.UserName = null;

                    finish();


                }
                else
                {
                    adatamodelclass.setDisplay(false);
                    c_chat_recycler_view_adapter_class.currentItemsLinkedHmap.remove(dataSnapshot.getKey().toString());
                    mAdapter.notifyDataSetChanged();
                    if(ChatMain_activity.TOAST_CONTROL)
                        helperFunctions_class.showToast(ChatActivity.this, "a_data_group_model_class Deleted =>" + adatamodelclass.getUser_name());
                }*/
                helperFunctions_class.showToast(ChatActivity.this, "Group Deleted");

                Intent intent = new Intent(ChatActivity.this, ChatMain_activity.class);
                startActivity(intent);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        databaseReference_Ref_final.addChildEventListener(childEventListener);

    }


    private void sendChat(String message,int W,int H)
    {
        ChatMessage_data_model_class chat;



        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String mydate = sdf.format(new Date());

        //String MakeChatId = FirebaseAuth.getInstance().getCurrentUser().getUid()+ UUID.randomUUID().toString()+Myd



        int zero=0;
       // showToast(ChatActivity.this,Integer.toString(chatdata.getGroup_chat_flag()));

        if(chatdata.getGroup_chat_flag()==0)
        {
          //  helperFunctions_class.showToast(ChatActivity.this,"true");
            ChatMessage_data_model_class chatD = new   ChatMessage_data_model_class(
                    ChatMain_activity.userId,
                    "groupChat",
                    "groupChat",
                    selectedImageUri.toString(),
                    message,
                    ChatMain_activity.UserName,W,H);

            databaseReference_Ref_final.push().setValue(chatD,
                    new DatabaseReference.CompletionListener() {

                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                        {

                            selectedImageUri= Uri.parse("noImage");
                           // mAdapter.notifyDataSetChanged();
                        }
                    });

        }
        else
        {
           // helperFunctions_class.showToast(ChatActivity.this,"false");
            ChatMessage_data_model_class chatD = new   ChatMessage_data_model_class(
                    ChatMain_activity.userId,
                    chatdata.getTarget_id(),
                    "userChat",
                    selectedImageUri.toString(),
                    message,
                    ChatMain_activity.UserName,W,H);



            databaseReference_Ref_final.push().setValue(chatD,
                    new DatabaseReference.CompletionListener() {

                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                        {

                            selectedImageUri= Uri.parse("noImage");
                          //  mAdapter.notifyDataSetChanged();
                        }
                    });



        }





/*


                databaseRef.push()
                        .setValue(chat );



        mRecyclerViewAdapter.notifyDataSetChanged();*/

    }

//http://stackoverflow.com/questions/9815245/android-text-to-speech-male-voice
    public   void TextToSpeechFunction(String speech)
    {


            TtoS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);


    }

    @Override
    public void onDestroy()
    {

        TtoS.shutdown();
        super.onDestroy();
    }

    @Override
    public void onPause()
    {
        if(ChatMain_activity.TOAST_CONTROL)
            helperFunctions_class.showToast(ChatActivity.this,"Inside Pause Chat");
        super.onPause();
        databaseReference_Ref_final.removeEventListener(childEventListener);
    }

    @Override
    public void onInit(int status)
    {

        if (status == TextToSpeech.SUCCESS)
        {
            TtoS.setLanguage(Locale.US);





          //  TtoS.setVoice()
            // ChatActivity.TextToSpeechFunction(messageTxt.getText().toString());
        }



    }

///http://www.thecrazyprogrammer.com/2017/02/android-upload-image-firebase-storage-tutorial.html
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {



        //Recieved result from image picker
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK)
        {
          //  int size =c_chat_recycler_view_adapter_class.ChatMessages.size();
        //    c_chat_recycler_view_adapter_class.ChatMessages.clear();
        //    mAdapter.notifyItemRangeRemoved(0, size);
            if(ChatMain_activity.TOAST_CONTROL)
                  helperFunctions_class.showToast(ChatActivity.this,"Acitivity Reult Chat");

              width = data.getIntExtra("width", 0);
             height = data.getIntExtra("height", 0);

            Log.d("TEST_2", "Width =>" + width+"Height=>"+height);
            pd.show();

            selectedImageUri = data.getData();
            if(selectedImageUri!=null)
            {
                long time_date = new Date().getTime();
                String name = Integer.toString((int)time_date);
                 StorageReference childRef = storageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child( selectedImageUri.getLastPathSegment()+name) ;

                //uploading the image
                UploadTask uploadTask = childRef.putFile(selectedImageUri);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        pd.dismiss();
                        Toast.makeText(ChatActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        selectedImageUri =taskSnapshot.getDownloadUrl();

                        new Thread( new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                final String message = messageTxt.getText().toString();


                                handler.post(

                                        new Runnable()
                                        {
                                            public void run()
                                            {
                                                messageTxt.setText("");

                                               sendChat(message,width,height);
                                            }
                                        }

                                );




                            }
                        }).start();
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        pd.dismiss();
                        Toast.makeText(ChatActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {

                new Thread( new Runnable()
                {
                    @Override
                    public void run()
                    {
                        final String message = messageTxt.getText().toString();


                        handler.post(

                                new Runnable()
                                {
                                    public void run()
                                    {
                                        messageTxt.setText("");

                                        sendChat(message,0,0);
                                    }
                                }

                        );




                    }
                }).start();

            }
             //ChatActivity.block_upload=false;

        }

    }



    @Override
    public void onBackPressed() { }


}
