package com.geometry.chatprogramfinal.j_chatMainWindow;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.geometry.chatprogramfinal.R;
import com.geometry.chatprogramfinal.c_homePage.ChatMain_activity;
import com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class;
import com.geometry.chatprogramfinal.z_d_Parcelable.chatData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.geometry.chatprogramfinal.z_b_utility_functions.helperFunctions_class.showToast;


public class ChatActivity extends AppCompatActivity
{
    private Button                                               sendBtn;
    private EditText                                          messageTxt;
    private                                     RecyclerView recycleView;
    Handler                                                      handler;
    chatData                                                    chatdata;
    Uri selectedImageUri                          = Uri.parse("noImage");
    DatabaseReference                         databaseReference_userChat;
    DatabaseReference                        databaseReference_groupChat;

    LinearLayoutManager mManager;

    ///Menu
    boolean text_to_speech=false,clear_image=false;

    private RecyclerView.Adapter mAdapter;

    ////////////////////////////////////////////
      List<String> id_entry = new ArrayList<>();
      HashMap<String, ChatMessage_data_model_class> currentItemsLinkedHmap = new  HashMap<String, ChatMessage_data_model_class>();



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
                }
                else
                {
                    text_to_speech = false;
                }

                if(ChatMain_activity.TOAST_CONTROL)
                    helperFunctions_class.showToast(ChatActivity.this,"Inside Speech Menu");
                return true;




            case R.id.menu_clear_image:


                if(!clear_image)
                {
                    clear_image = true;
                }
                else
                {
                    clear_image = false;
                }



                if(ChatMain_activity.TOAST_CONTROL)
                    helperFunctions_class.showToast(ChatActivity.this,"Clear Image");

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

        sendBtn = (Button) findViewById(R.id.sendBtn);
        messageTxt = (EditText) findViewById(R.id.messageTxt);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);



////////////////////////////////////////////////////////////////////////////////
///Deciding chat mode
        chatdata = getIntent().getParcelableExtra("chatData");

        databaseReference_groupChat = FirebaseDatabase.getInstance()
                    .getReference().child("groupChat").child(chatdata.getChat_id());
        databaseReference_userChat = FirebaseDatabase.getInstance()
                    .getReference().child("userChat").child(chatdata.getChat_id());

        if(chatdata.getGroup_chat_flag()==0)
          setRecyclerView(databaseReference_groupChat);
        else
            setRecyclerView(databaseReference_userChat);


////////////////////////////////////////////////////////////////////////////////

        setTitle("Chatting as " +ChatMain_activity.UserName);


        //Refference to Main thread looper
        handler = new Handler(getApplicationContext().getMainLooper());

////////////////////////////////////////////////////////////////////////////////
          mManager = new LinearLayoutManager(this);

        recycleView.setLayoutManager(mManager);








        mAdapter = new c_group_recycler_view_adapter_class(currentItemsLinkedHmap,id_entry,ChatActivity.this);
        //mAdapter = new c_group_recycler_view_adapter_class(Items);

        // Scroll to bottom on new messages
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver()
        {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount)
            {
               // mManager.smoothScrollToPosition(recycleView, null, currentItemsLinkedHmap.size());
            }
        });
        recycleView.setAdapter(mAdapter);


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

                                        sendChat(message);
                                    }
                                }

                        );




                    }
                }).start();


            }
        });





    }
    void setRecyclerView(DatabaseReference ref)
    {

        ref.addChildEventListener(new ChildEventListener()
        {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                ChatMessage_data_model_class adatamodelclass = dataSnapshot.getValue(ChatMessage_data_model_class.class);
                 id_entry.add(dataSnapshot.getKey().toString());
                 currentItemsLinkedHmap.put(dataSnapshot.getKey().toString(), adatamodelclass);

                if(ChatMain_activity.TOAST_CONTROL)
                helperFunctions_class.showToast(ChatActivity.this,"Name =>"+ adatamodelclass.getSender_id());

                mAdapter.notifyDataSetChanged();
                recycleView.smoothScrollToPosition(currentItemsLinkedHmap.size() - 1);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {

                ChatMessage_data_model_class adatamodelclass = dataSnapshot.getValue(ChatMessage_data_model_class.class);

                if(adatamodelclass.getSender_id().equals(ChatMain_activity.userId))
                {
                    ChatMain_activity.UserName = adatamodelclass.getUser_name();
                }

                //Fire base id never changes,user name
                 currentItemsLinkedHmap.put(dataSnapshot.getKey().toString(), adatamodelclass);


                mAdapter.notifyDataSetChanged();
                if(ChatMain_activity.TOAST_CONTROL)
                helperFunctions_class.showToast(ChatActivity.this,"a_data_group_model_class changed =>"+ adatamodelclass.getUser_name());


             }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

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
                    currentItemsLinkedHmap.remove(dataSnapshot.getKey().toString());
                    mAdapter.notifyDataSetChanged();
                    if(ChatMain_activity.TOAST_CONTROL)
                        helperFunctions_class.showToast(ChatActivity.this, "a_data_group_model_class Deleted =>" + adatamodelclass.getUser_name());
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }


    private void sendChat(String message)
    {
        ChatMessage_data_model_class chat;



        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String mydate = sdf.format(new Date());

        //String MakeChatId = FirebaseAuth.getInstance().getCurrentUser().getUid()+ UUID.randomUUID().toString()+Myd



        int zero=0;
        showToast(ChatActivity.this,Integer.toString(chatdata.getGroup_chat_flag()));

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
