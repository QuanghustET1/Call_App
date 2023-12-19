package com.android.call_app.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.call_app.Activities.ChatActivity;
import com.android.call_app.Db.Message;
import com.android.call_app.Fragments.ChatFragment;
import com.android.call_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listContactUserAdapter extends RecyclerView.Adapter<listContactUserAdapter.ViewHolder> {
    private ArrayList<String> listUserContact;
    private DatabaseReference lastMsg;
    private Context context;
    private FirebaseAuth firebaseAuth;
    String currentUser;
    public listContactUserAdapter(ArrayList<String> listUserContact, Context context){
        this.listUserContact = listUserContact;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.currentUser = firebaseAuth.getCurrentUser().getEmail().split("@")[0];
        this.lastMsg = FirebaseDatabase.getInstance().getReference("chat");
        this.context = context;
    }
    @NonNull
    @Override
    public listContactUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_chat_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull listContactUserAdapter.ViewHolder holder, int position) {
        holder.usernameTargetItem.setText(listUserContact.get(position));
        holder.contactUserItem.setOnClickListener(view -> {
            context.startActivity(new Intent(context, ChatActivity.class).putExtra("userChat", listUserContact.get(position)));
        });
        DatabaseReference databaseReference = this.lastMsg.child("room_"+this.currentUser).child(this.listUserContact.get(position));
        databaseReference.addValueEventListener(new ValueEventListener() {
            String lastMsg = "";
            String userSend = "";
            String timeMsg = "";
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    lastMsg = dataSnapshot.getValue(Message.class).getMessage();
                    userSend = dataSnapshot.getValue(Message.class).getUserSend();
                    timeMsg = dataSnapshot.getValue(Message.class).getDateTime();
                }
                if(lastMsg.length() > 10){
                    lastMsg = lastMsg.substring(0, 10)+"...";
                }
                if(userSend.equals(currentUser)){
                    holder.lastMsg.setText("You: "+lastMsg);
                }
                else {
                    holder.lastMsg.setText(lastMsg);
                }
                holder.timeMsg.setText(timeMsg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listUserContact.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView usernameTargetItem;
        private ConstraintLayout contactUserItem;
        private TextView lastMsg;
        private TextView timeMsg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.usernameTargetItem = itemView.findViewById(R.id.usernameTargetItem);
            this.contactUserItem = itemView.findViewById(R.id.contactUserItem);
            this.lastMsg = itemView.findViewById(R.id.lastMsg);
            this.timeMsg = itemView.findViewById(R.id.timeMsg);
        }
    }
}
