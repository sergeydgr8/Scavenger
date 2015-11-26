package com.android9033.scavenger.scavenger.Control;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android9033.scavenger.scavenger.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import com.android9033.scavenger.scavenger.Model.Quest;

/**
 * Created by yirongshao on 11/21/15.
 */
public class RankingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ParseUser curUser=ParseUser.getCurrentUser();
        String point=curUser.getString("point");
        System.out.println(point);

        ParseQuery<Quest> query2=new ParseQuery<Quest>("Quest");
/*
        Quest quest=new Quest();
        quest.setName("second");
        quest.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

            }
        });

        ParseQuery<Quest> query=new ParseQuery<Quest>("Quest");
        query.findInBackground(new FindCallback<Quest>() {
            @Override
            public void done(List<Quest> objects, ParseException e) {
                for(Quest quest:objects){
                    System.out.println(quest.getName());
                }
               // Quest quest = ParseObject.get
                //quest.setName("firstquest");
                //System.out.println(quest.getName());
            }
        });
*/
       // quest.setName("firstquest");

        return inflater.inflate(R.layout.fragment_ranking, container, false);
    }
}
