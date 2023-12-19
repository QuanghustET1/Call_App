package com.android.call_app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.call_app.Adapters.ViewPagerAdapter;
import com.android.call_app.Db.User;
import com.android.call_app.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private static String userSession;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.firebaseAuth = FirebaseAuth.getInstance();
        checkStateLogin();
        initView();
        setView();
    }


    private void initService(String userID) {
        Application application = getApplication();
        long appID = 1690058828;
        String appSign = "839c6f2bb708e909243df09697830d91cba34d62712efd83ef93b34b845391ae";
        String userName = userID;

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();

        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, userID, userName,callInvitationConfig);
    }
    private void checkStateLogin() {
        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        else {
            userSession = firebaseAuth.getCurrentUser().getEmail().split("@")[0];
            initService(userSession);
        }
    }


    public static String getUserID(){
        return userSession;
    }

    private void setView() {
        this.mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        this.mTablayout.setupWithViewPager(this.mViewPager);
    }

    private void initView() {
        this.mTablayout = findViewById(R.id.tabLayout);
        this.mViewPager = findViewById(R.id.viewPager);
    }
}