package com.android.call_app.Activities;

import androidx.appcompat.app.AppCompatActivity;
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
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private static User userSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.userSession = (User) getIntent().getSerializableExtra("userSession");
        initView();
        setView();
//        initService(MainActivity.userSession.getUsernameID());
    }

//    private void initService(String userID) {
//        Application application = getApplication(); // Android's application context
//        long appID = 1978147676;   // yourAppID
//        String appSign = "3bfc8708af97aaa44b2ca0d80f0d0bfce91436aa3c39bfb774bb1738a37001de";  // yourAppSign
//        String userName = userID;
//
//        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
//        callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true;
//        ZegoNotificationConfig notificationConfig = new ZegoNotificationConfig();
//        notificationConfig.sound = "zego_uikit_sound_call";
//        notificationConfig.channelID = "CallInvitation";
//        notificationConfig.channelName = "CallInvitation";
//        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, userID, userName,callInvitationConfig);
//    }

    public static String getUserID(){
        return userSession.getUsernameID();
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