package com.zealoit.eqz.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.zealoit.eqz.Fragment.ClosedFragment;
import com.zealoit.eqz.Fragment.OpensalonFragment;

public class HelpDeskAdapter extends FragmentStatePagerAdapter {


    private Context myContext;
    int totalTabs;

    public HelpDeskAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                OpensalonFragment homeFragment = new OpensalonFragment();
                return homeFragment;
            case 1:
                ClosedFragment sportFragment = new ClosedFragment();
                return sportFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
