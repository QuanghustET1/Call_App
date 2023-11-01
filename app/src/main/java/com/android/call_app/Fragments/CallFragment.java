package com.android.call_app.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.call_app.Activities.MainActivity;
import com.android.call_app.Db.HistoryCall;
import com.android.call_app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class CallFragment extends Fragment {
    private String userID;
    private TextView userID_display;
    private EditText inputUserIDInvite;
    private ZegoSendCallInvitationButton voiceCallBtn, videoCallBtn;
    private DatabaseReference mDataHistoryCall;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call, container, false);
        this.userID_display = view.findViewById(R.id.userID_display);
        this.inputUserIDInvite = view.findViewById(R.id.inputUserIDInvite);
        this.voiceCallBtn = view.findViewById(R.id.voiceCallBtn);
        this.videoCallBtn = view.findViewById(R.id.videoCallBtn);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.userID = MainActivity.getUserID();
        this.mDataHistoryCall = FirebaseDatabase.getInstance().getReference("historyCall");
        setView();
        initVoiceBtn();
        initVideoBtn();
    }

    private void initVoiceBtn() {
        this.voiceCallBtn.setIsVideoCall(false);
        this.voiceCallBtn.setResourceID("zego_uikit_call");
        this.voiceCallBtn.setOnClickListener(v -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date();
            String dateTime = formatter.format(date);
            String targetUserID = String.valueOf(this.inputUserIDInvite.getText());
            CallFragment.this.mDataHistoryCall.child(MainActivity.getUserID()+"_"+targetUserID+"_"+dateTime).setValue(new HistoryCall(targetUserID, dateTime));
            this.voiceCallBtn.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID)));
        });
    }
    private void initVideoBtn() {
        this.videoCallBtn.setIsVideoCall(true);
        this.videoCallBtn.setResourceID("zego_uikit_call");
        this.videoCallBtn.setOnClickListener(v -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date();
            String dateTime = formatter.format(date);
            String targetUserID = String.valueOf(this.inputUserIDInvite.getText());
            CallFragment.this.mDataHistoryCall.child(MainActivity.getUserID()+"_"+targetUserID+"_"+dateTime).setValue(new HistoryCall(targetUserID, dateTime));
            this.videoCallBtn.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID)));
        });
    }

    private void setView() {
        this.userID_display.setText(this.userID);
    }
}
