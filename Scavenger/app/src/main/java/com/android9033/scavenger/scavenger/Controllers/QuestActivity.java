package com.android9033.scavenger.scavenger.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android9033.scavenger.scavenger.Models.Landmark;
import com.android9033.scavenger.scavenger.Models.Quest;
import com.android9033.scavenger.scavenger.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by sergey on 12/14/15.
 */
public class QuestActivity extends AppCompatActivity
{
    private static ListView lv;
    private static ArrayAdapter<String> adapter;
    private static ArrayList<String> str;
    private static String questID;
    private static String quest_name;
    private static String quest_loc;
    private static ArrayList<String> landmarkIDs;

    private static Stack<Landmark> landmarks = new Stack<Landmark>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questinfo);
        landmarkIDs = new ArrayList<String>();
        str = new ArrayList<String>();
        //quest_name = new String();
        //quest_loc = new String();

        questID = getIntent().getStringExtra("questID");

        ParseQuery<Quest> quest_query = new ParseQuery<Quest>("Quest2");
        quest_query.whereEqualTo("objectId", questID);

        quest_query.findInBackground(new FindCallback<Quest>() {
            @Override
            public void done(List<Quest> objects, ParseException e) {
                if (e == null)
                {
                    for (Quest q : objects)
                    {
                        quest_name = q.getString("name");
                        Log.i("Something", "Added a name: " + q.getString("name"));
                        quest_loc = q.getString("location");
                        for (int i = 0; i < q.getList("landmarks").size(); i++)
                            landmarkIDs.add((String) q.getList("landmarks").get(i));
                        //landmarkIDs = q.getList("landmarks");
                    }

                    ParseQuery<Landmark> landmark_query = new ParseQuery<Landmark>("Landmark");
                    landmark_query.findInBackground(new FindCallback<Landmark>() {
                        @Override
                        public void done(List<Landmark> objects, ParseException e) {
                            if (e == null)
                            {
                                for (Landmark l : objects)
                                {
                                    if (landmarkIDs.contains(l.getObjectId()))
                                    {
                                        landmarks.push(l);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });

        int index = 0;
        for (int i = 0; i < 10; i++)
        {
            if (!landmarks.isEmpty())
            {
                index++;
                Landmark l = landmarks.pop();
                str.add(l.getString("name"));
                adapter.notifyDataSetChanged();
            }
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(quest_name);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView name_view = (TextView) findViewById(R.id.quest_info_name);
        name_view.setText(quest_loc);

        lv = (ListView) findViewById(R.id.quest_landmarks_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, str);
        lv.setAdapter(adapter);

        //str = new ArrayList<String>();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getApplicationContext(), LandmarkActivity.class);
                mIntent.putExtra("landmarkID", landmarks.get(position).getID());
                startActivity(mIntent);
            }
        });
    }

}
