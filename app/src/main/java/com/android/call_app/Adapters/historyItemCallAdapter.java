package com.android.call_app.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.call_app.Activities.MainActivity;
import com.android.call_app.Db.HistoryCall;
import com.android.call_app.Fragments.CallFragment;
import com.android.call_app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoAcceptCallInvitationButton;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class historyItemCallAdapter extends RecyclerView.Adapter<historyItemCallAdapter.ViewHolder> {
    private ArrayList<HistoryCall> dataHistoryCall;
    private DatabaseReference mDataHistoryCall;
    public historyItemCallAdapter(ArrayList<HistoryCall> dataHistoryCall){
        this.dataHistoryCall = dataHistoryCall;
        this.mDataHistoryCall = FirebaseDatabase.getInstance().getReference("historyCall");
    }
    @NonNull
    @Override
    public historyItemCallAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull historyItemCallAdapter.ViewHolder holder, int position) {
        holder.userIDTarget.setText(dataHistoryCall.get(position).getCall_ID());
        holder.dateTimeCall.setText(dataHistoryCall.get(position).getCall_Time());
        holder.revoiceCallBtn.setIsVideoCall(false);
        holder.revoiceCallBtn.setOnClickListener(view -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date();
            String dateTime = formatter.format(date);
            String targetUserID = dataHistoryCall.get(position).getCall_ID();
            historyItemCallAdapter.this.mDataHistoryCall.child(MainActivity.getUserID()+"_"+targetUserID+"_"+dateTime).setValue(new HistoryCall(targetUserID, dateTime));
            holder.revoiceCallBtn.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID)));
        });
        holder.revideoCallBtn.setIsVideoCall(true);
        holder.revideoCallBtn.setOnClickListener(view -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date();
            String dateTime = formatter.format(date);
            String targetUserID = dataHistoryCall.get(position).getCall_ID();
            historyItemCallAdapter.this.mDataHistoryCall.child(MainActivity.getUserID()+"_"+targetUserID+"_"+dateTime).setValue(new HistoryCall(targetUserID, dateTime));
            holder.revideoCallBtn.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID)));
        });
    }

    @Override
    public int getItemCount() {
        return dataHistoryCall.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userIDTarget;
        private TextView dateTimeCall;
        private ZegoSendCallInvitationButton revoiceCallBtn;
        private ZegoSendCallInvitationButton revideoCallBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.userIDTarget = itemView.findViewById(R.id.userIDTarget);
            this.dateTimeCall = itemView.findViewById(R.id.dateTimeCall);
            this.revoiceCallBtn = itemView.findViewById(R.id.revoiceCallBtn);
            this.revideoCallBtn = itemView.findViewById(R.id.revideoCallBtn);
        }
    }
}
