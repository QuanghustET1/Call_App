package com.android.call_app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.call_app.Db.Message;
import com.android.call_app.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class chatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Message> listMessage;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public chatMessageAdapter(ArrayList<Message> listMessage) {
        this.listMessage = listMessage;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1){
            return new ViewholderSend(LayoutInflater.from(parent.getContext()).inflate(R.layout.send_msg_layout,parent, false));
        }
        else {
            return new ViewholderReceive(LayoutInflater.from(parent.getContext()).inflate(R.layout.receive_msg_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == 1){
            ViewholderSend viewholderSend = (ViewholderSend) holder;
            viewholderSend.msg_send_item.setText(listMessage.get(position).getMessage());
            viewholderSend.sendMessageTime.setText(listMessage.get(position).getDateTime());

        }
        else {
            ViewholderReceive viewholderReceive = (ViewholderReceive) holder;
            viewholderReceive.msg_receive_item.setText(listMessage.get(position).getMessage());
            viewholderReceive.timeMessage.setText(listMessage.get(position).getDateTime());
        }
    }

    @Override
    public int getItemCount() {
        return listMessage.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(listMessage.get(position).getUserSend().equals(firebaseAuth.getCurrentUser().getEmail().split("@")[0])){
            return 1;
        }
        else return 2;
    }
    class ViewholderSend extends RecyclerView.ViewHolder{
        private TextView msg_send_item;
        private TextView sendMessageTime;
        public ViewholderSend(@NonNull View itemView) {
            super(itemView);
            this.msg_send_item = itemView.findViewById(R.id.msg_send_item);
            this.sendMessageTime = itemView.findViewById(R.id.timeMessageSend);
        }
    }
    class ViewholderReceive extends RecyclerView.ViewHolder{
        private TextView msg_receive_item;
        private TextView timeMessage;
        public ViewholderReceive(@NonNull View itemView) {
            super(itemView);
            this.msg_receive_item = itemView.findViewById(R.id.msg_receive_item);
            this.timeMessage = itemView.findViewById(R.id.timeMessageReceive);
        }
    }
}