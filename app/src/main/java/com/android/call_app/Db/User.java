package com.android.call_app.Db;

import java.io.Serializable;

public class User implements Serializable {
    private String usernameID;
    private String userpassword;

    public User() {
    }

    public User(String usernameID, String userpassword) {
        this.usernameID = usernameID;
        this.userpassword = userpassword;
    }

    public String getUsernameID() {
        return usernameID;
    }

    public void setUsernameID(String usernameID) {
        this.usernameID = usernameID;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }
}
