package com.android9033.scavenger.scavenger.Control;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android9033.scavenger.scavenger.R;
import com.parse.ParseUser;

/**
 * Created by yirongshao on 11/21/15.
 */
public class RankingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ParseUser curUser=ParseUser.getCurrentUser();
        String point=curUser.getString("point");

        System.out.println(point);
        return inflater.inflate(R.layout.fragment_ranking, container, false);
    }
}
