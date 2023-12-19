package com.android.call_app.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.android.call_app.Fragments.CallFragment;
import com.android.call_app.Fragments.HistoryCallFragment;
import com.android.call_app.Fragments.ChatFragment;
import com.android.call_app.Fragments.SettingFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CallFragment();
            case 1:
                return new HistoryCallFragment();
            case 2:
                return new ChatFragment();
            case 3:
                return new SettingFragment();
            default:
                return new CallFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Call";
            case 1:
                return "History Call";
            case 2:
                return "Chat";
            case 3:
                return "Setting";
        }
        return "";
    }
}
