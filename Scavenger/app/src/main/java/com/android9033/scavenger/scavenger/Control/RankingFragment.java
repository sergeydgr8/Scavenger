package com.android9033.scavenger.scavenger.Control;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android9033.scavenger.scavenger.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import com.android9033.scavenger.scavenger.Model.Quest;

/**
 * Created by yirongshao on 11/21/15.
 */
/*class userpoint{
    int user;
    int point;
    userpoint(int user,int point){
        user=user;
        point=point;
    }
}
*/
public class RankingFragment extends Fragment {

    private  ListView lv;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> str;
    PriorityQueue<ParseUser> points=new PriorityQueue<ParseUser>(10,new Comparator<ParseUser>(){
        @Override
        public int compare(ParseUser user1,ParseUser user2){
            int first=Integer.parseInt(user1.getString("point"));
            int second=Integer.parseInt(user2.getString("point"));
            return second-first;
        }
    });

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

*/      ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_ranking, container, false);
        str = new ArrayList<String>();
        lv = (ListView) view.findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1, str);
        lv.setAdapter(adapter);

        ParseQuery<ParseUser> query=ParseUser.getQuery();

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null) {
                    for (ParseUser user : objects) {
                        points.offer(user);
                        //System.out.println(user.getString("point"));
                    }
                }
               // Quest quest = ParseObject.get
                //quest.setName("firstquest");
                //System.out.println(quest.getName());
                //ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_ranking, container, false);
                int index =0;
                for (int tt = 0; tt < 10; tt++){
                    if(!points.isEmpty()) {
                        index++;
                        ParseUser user=points.poll();
                        str.add(index+".  "+ user.getString("point")+": "+user.getString("username"));
                        adapter.notifyDataSetChanged();
                    }
                }


            }
        });

       // quest.setName("firstquest");
//        System.out.println(points.get(1));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });



        return view;
    }
}
