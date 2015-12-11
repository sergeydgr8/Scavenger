package com.android9033.scavenger.scavenger.Controllers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * Created by yirongshao on 11/21/15.
 */
public class FragmentPageAdapter extends FragmentPagerAdapter {
    //private String tabTitles[] = new String[]{"Quests", "Ranking", "You"};
    //private Context context;
    int mNumOfTabs;

    public FragmentPageAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new QuestsListFragment();
            case 1:
                return new RankingFragment();
            case 2:
                return new YouFragment();
            default:
                break;
        }
        return null;
        //return QuestsListFragment.newInstance(position+1);
    }

    @Override
    public int getCount(){
        return mNumOfTabs;
    }

    //@Override
    //public CharSequence getPageTitle(int position){
    //    return tabTitles[position];
    //}
}