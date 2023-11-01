package com.android.call_app.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.call_app.Db.User;
import com.android.call_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameInputLogin;
    private EditText passwordInputLogin;
    private AppCompatButton loginBtn;
    private TextView toSignUp;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDatabase = FirebaseDatabase.getInstance().getReference("dataUser");
        initView();
        toSignUpPage();
        loginProcess();
    }
    private void initService(String userID) {
        Application application = getApplication(); // Android's application context
        long appID = 1978147676;   // yourAppID
        String appSign = "3bfc8708af97aaa44b2ca0d80f0d0bfce91436aa3c39bfb774bb1738a37001de";  // yourAppSign
        String userName = userID;

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
        callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true;
        ZegoNotificationConfig notificationConfig = new ZegoNotificationConfig();
        notificationConfig.sound = "zego_uikit_sound_call";
        notificationConfig.channelID = "CallInvitation";
        notificationConfig.channelName = "CallInvitation";
        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, userID, userName,callInvitationConfig);
    }

    private void loginProcess() {
        this.loginBtn.setOnClickListener(view -> {
            String username = String.valueOf(LoginActivity.this.usernameInputLogin.getText());
            String password = String.valueOf(LoginActivity.this.passwordInputLogin.getText());
            final boolean[] check = {false};
            LoginActivity.this.mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot userSnapshot : snapshot.getChildren()){
                        User userTemp = userSnapshot.getValue(User.class);
                        if (userTemp.getUsernameID().equals(username) && userTemp.getUserpassword().equals(password)){
                            check[0] = true;
                            break;
                        }
                    }
                    if(check[0] == true){
                        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                        initService(username);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("userSession", new User(username, password)));
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }

    private void toSignUpPage() {
        this.toSignUp.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });
    }

    private void initView() {
        this.usernameInputLogin = findViewById(R.id.usernameInputLogin);
        this.passwordInputLogin = findViewById(R.id.passwordInputLogin);
        this.loginBtn = findViewById(R.id.loginBtn);
        this.toSignUp = findViewById(R.id.toSignUp);
    }
}