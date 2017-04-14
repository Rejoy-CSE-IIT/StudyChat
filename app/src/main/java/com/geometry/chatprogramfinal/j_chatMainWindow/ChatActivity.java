package com.geometry.chatprogramfinal.j_chatMainWindow;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.c_homePage.ChatMain_activity;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.geometry.chatprogramfinal.z_d_Parcelable.chatData;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ChatActivity extends AppCompatActivity
{
    private Button sendBtn;
    private EditText messageTxt;
    private RecyclerView recycleView;
    Handler handler;
    chatData chatdata;
    Uri selectedImageUri= Uri.parse("noImage");


    DatabaseReference databaseReference_userChat;
    DatabaseReference databaseReference_groupChat;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_j_chat);

        sendBtn = (Button) findViewById(R.id.sendBtn);
        messageTxt = (EditText) findViewById(R.id.messageTxt);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);

        chatdata = getIntent().getParcelableExtra("chatData");


        //Refference to Main thread looper
        handler = new Handler(getApplicationContext().getMainLooper());

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

                                        sendChat(message);
                                    }
                                }

                        );




                    }
                }).start();


            }
        });
    }

    private void sendChat(String message)
    {
        ChatMessage_data_model_class chat;



        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String mydate = sdf.format(new Date());



        int zero=0;
        helperFunctions_class.showToast(ChatActivity.this,Integer.toString(chatdata.getGroup_chat_flag()));

        if(chatdata.getGroup_chat_flag()==0)
        {
          //  helperFunctions_class.showToast(ChatActivity.this,"true");
            ChatMessage_data_model_class chatD = new   ChatMessage_data_model_class(
                    ChatMain_activity.userId,
                    "groupChat",
                    "groupChat",
                    selectedImageUri.toString(),
                    message,
                    ChatMain_activity.UserName,
                    mydate);
            databaseReference_groupChat = FirebaseDatabase.getInstance()
                    .getReference().child("groupChat").child(chatdata.getChat_id());

            databaseReference_groupChat.push().setValue(chatD,
                    new DatabaseReference.CompletionListener() {

                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                        {
                            selectedImageUri= Uri.parse("noImage");
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
                    ChatMain_activity.UserName,
                    mydate);

            databaseReference_userChat = FirebaseDatabase.getInstance()
                    .getReference().child("userChat").child(chatdata.getChat_id());


            databaseReference_userChat.push().setValue(chatD,
                    new DatabaseReference.CompletionListener() {

                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                        {

                            selectedImageUri= Uri.parse("noImage");
                        }
                    });



        }





/*


                databaseRef.push()
                        .setValue(chat );



        mRecyclerViewAdapter.notifyDataSetChanged();*/

    }
}
