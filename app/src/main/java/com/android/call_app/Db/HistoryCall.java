package com.android.call_app.Db;

public class HistoryCall {
    private String call_ID;
    private String call_Time;

    public HistoryCall() {
    }

    public HistoryCall(String call_ID, String call_Time) {
        this.call_ID = call_ID;
        this.call_Time = call_Time;
    }

    public String getCall_ID() {
        return call_ID;
    }

    public void setCall_ID(String call_ID) {
        this.call_ID = call_ID;
    }

    public String getCall_Time() {
        return call_Time;
    }

    public void setCall_Time(String call_Time) {
        this.call_Time = call_Time;
    }
}
