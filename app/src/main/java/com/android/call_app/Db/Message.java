package com.android.call_app.Db;

public class Message {
    private String dateTime;
    private String message;
    private String userSend;

    public Message(){

    }

    public Message(String dateTime, String message, String userSend) {
        this.message = message;
        this.dateTime = dateTime;
        this.userSend = userSend;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getUserSend() {
        return userSend;
    }

    public void setUserSend(String userSend) {
        this.userSend = userSend;
    }
}
