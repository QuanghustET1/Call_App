package com.android.call_app.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.call_app.Activities.MainActivity;
import com.android.call_app.Adapters.historyItemCallAdapter;
import com.android.call_app.Db.HistoryCall;
import com.android.call_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryCallFragment extends Fragment {
    private RecyclerView historyCall;
    private DatabaseReference dataHistoryCall;
    private TextView noItemHis;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_call, container, false);
        this.historyCall = view.findViewById(R.id.historyCall);
        noItemHis = view.findViewById(R.id.noItemHis);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.dataHistoryCall = FirebaseDatabase.getInstance().getReference("historyCall");
        initView();
    }

    private void initView() {
        this.historyCall.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        this.dataHistoryCall.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<HistoryCall> dataHistory = new ArrayList<>();
                for(DataSnapshot data : snapshot.getChildren()){
                    if(data.getKey().split("_")[0].equals(MainActivity.getUserID())){
                        HistoryCall dataItem = new HistoryCall(data.getValue(HistoryCall.class).getCall_ID(), data.getValue(HistoryCall.class).getCall_Time());
                        dataHistory.add(dataItem);
                    }
                }
                for(HistoryCall historyCall : dataHistory){
                    Log.d("hisitem", historyCall.getCall_ID());
                }
                if(dataHistory.size() == 0){
                    noItemHis.setText("History Call is Empty");
                }
                HistoryCallFragment.this.historyCall.setAdapter(new historyItemCallAdapter(dataHistory));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("errr", error.getMessage());
            }
        });
    }
}
