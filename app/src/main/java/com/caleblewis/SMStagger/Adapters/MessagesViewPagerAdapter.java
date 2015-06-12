package com.caleblewis.SMStagger.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.caleblewis.SMStagger.Fragments.MessagesFragment;

public class MessagesViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence tabTitles[];
    int numbOfTabs;

    public MessagesViewPagerAdapter(FragmentManager fm) {
        super(fm);

        this.tabTitles = new String[]{"Scheduled Messages", "Sent Messages"};
        this.numbOfTabs = 2;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();

        if(position == 0)
        {
            bundle.putString("type", "scheduled");
        }
        else
        {
            bundle.putString("type", "sent");
        }

        Fragment f = new MessagesFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return numbOfTabs;
    }
}
