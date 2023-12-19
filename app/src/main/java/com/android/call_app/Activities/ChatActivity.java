package com.android.call_app.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.call_app.Adapters.chatMessageAdapter;
import com.android.call_app.Db.HistoryCall;
import com.android.call_app.Db.Message;
import com.android.call_app.Fragments.CallFragment;
import com.android.call_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    private String user;
    private String client;
    private FirebaseAuth firebaseAuth;
    private TextView userTarget;
    private ZegoSendCallInvitationButton sendVoiceCallBtn_Chat;
    private ZegoSendCallInvitationButton sendVideoCallBtn_Chat;
    private RecyclerView chatMessage;
    private EditText inputMsg;
    private ImageView sendMsg;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("chat");
        getClient();
        getUser();
        initView();
        renderChatView();
        sendMessageChat();
        callBtnListener();
        videoCallBtnListener();
    }

    private void videoCallBtnListener() {
        this.sendVideoCallBtn_Chat.setIsVideoCall(true);
        this.sendVideoCallBtn_Chat.setResourceID("zego_uikit_call");
        this.sendVideoCallBtn_Chat.setOnClickListener(v -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date();
            String dateTime = formatter.format(date);
            String targetUserID = String.valueOf(this.client);
            this.sendVideoCallBtn_Chat.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID)));
        });
    }

    private void callBtnListener() {
        this.sendVoiceCallBtn_Chat.setIsVideoCall(false);
        this.sendVoiceCallBtn_Chat.setResourceID("zego_uikit_call");
        this.sendVoiceCallBtn_Chat.setOnClickListener(v -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date();
            String dateTime = formatter.format(date);
            String targetUserID = String.valueOf(this.client);
            this.sendVoiceCallBtn_Chat.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID)));
        });
    }

    private void sendMessageChat() {
        this.sendMsg.setOnClickListener(view -> {
            final int[] status = {0};
            String message = String.valueOf(ChatActivity.this.inputMsg.getText());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date();
            String dateTime = formatter.format(date);
            Message m = new Message(dateTime, message, ChatActivity.this.user);
            DatabaseReference db = ChatActivity.this.databaseReference.child("room_"+ChatActivity.this.user).child(ChatActivity.this.client);
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String count = "0";
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        count = dataSnapshot.getKey();
                    }
                    if(status[0] == 0){
                        if(!m.getMessage().equals("")){
                            ChatActivity.this.databaseReference.child("room_"+ChatActivity.this.user).child(ChatActivity.this.client).child(String.valueOf(Integer.valueOf(count)+1)).setValue(m);
                        }
                        ChatActivity.this.inputMsg.setText("");
                        status[0] = 1;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            DatabaseReference db1 = ChatActivity.this.databaseReference.child("room_"+ChatActivity.this.client).child(ChatActivity.this.user);
            db1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String count = "0";
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        count = dataSnapshot.getKey();
                    }
                    if(status[0] == 1){
                        if(!m.getMessage().equals("")){
                            ChatActivity.this.databaseReference.child("room_"+ChatActivity.this.client).child(ChatActivity.this.user).child(String.valueOf(Integer.valueOf(count)+1)).setValue(m);
                        }
                        ChatActivity.this.inputMsg.setText("");
                        status[0] = 2;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }

    private void renderChatView() {
        this.userTarget.setText(this.client);
        DatabaseReference db = databaseReference.child("room_"+this.user).child(this.client);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<com.android.call_app.Db.Message> messageArrayList = new ArrayList<>();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    messageArrayList.add(dataSnapshot.getValue(com.android.call_app.Db.Message.class));
                }
                ChatActivity.this.chatMessage.setLayoutManager(new LinearLayoutManager(ChatActivity.this, LinearLayoutManager.VERTICAL, false));
                ChatActivity.this.chatMessage.setAdapter(new chatMessageAdapter(messageArrayList));
                ChatActivity.this.chatMessage.scrollToPosition(ChatActivity.this.chatMessage.getAdapter().getItemCount()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initView() {
        this.userTarget = findViewById(R.id.userTarget);
        this.sendVoiceCallBtn_Chat = findViewById(R.id.sendVoiceCallBtn_Chat);
        this.sendVideoCallBtn_Chat = findViewById(R.id.sendVideoCallBtn_Chat);
        this.chatMessage = findViewById(R.id.chatMessage);
        this.inputMsg = findViewById(R.id.inputMsg);
        this.sendMsg = findViewById(R.id.sendMsg);
    }

    private void getUser() {
        this.user = firebaseAuth.getCurrentUser().getEmail().split("@")[0];
    }

    private void getClient() {
        this.client = getIntent().getStringExtra("userChat");
    }
}