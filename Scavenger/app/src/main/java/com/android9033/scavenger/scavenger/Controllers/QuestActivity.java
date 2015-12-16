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
    private ListView lv;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> str = new ArrayList<String>();
    private static String questID;
    private String quest_name;
    private String quest_loc;
    private ArrayList<String> landmarkIDs = new ArrayList<String>();
    private TextView name_view;

    Stack<Landmark> landmarks = new Stack<Landmark>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questinfo);
        name_view = (TextView) findViewById(R.id.quest_info_name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Quest.");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        lv = (ListView) findViewById(R.id.quest_landmarks_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, str);
        lv.setAdapter(adapter);

        questID = getIntent().getStringExtra("questID");

        ParseQuery<Quest> quest_query = new ParseQuery<Quest>("Quest2");
        //ParseQuery<Quest> quest_query = ParseQuery.getQuery("Quest2");
        /*try {
            List<Quest> result = quest_query.find();
            for (Quest q : result)
            {
                if (q.getObjectId() == questID)
                {
                    quest_name = q.getString("name");
                    Log.i("Something", "Added a name: " + q.getString("name"));
                    quest_loc = q.getString("location");
                    for (int i = 0; i < q.getList("landmarks").size(); i++)
                        landmarkIDs.add((String) q.getList("landmarks").get(i));
                    //landmarkIDs = q.getList("landmarks");
                }
            }
        } catch (ParseException e) {
            //e.printStackTrace();
            Log.e("SCVNGR", "Exception: " + e.toString());
        }*/

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
        /*quest_query.getInBackground(questID, new GetCallback<Quest>() { // no idea why this does not work
            @Override
            public void done(Quest object, ParseException e) {
                if (e == null) {
                    quest_name = object.getString("name");
                    quest_loc = object.getString("location");
                    landmarkIDs = object.getList("landmarks");
                }

                ParseQuery<Landmark> landmarks_query = new ParseQuery<Landmark>("Landmark");
                for (String s : landmarkIDs) {
                    landmarks_query.getInBackground(s, new GetCallback<Landmark>() {
                        @Override
                        public void done(Landmark object, ParseException e) {
                            if (e == null) {
                                //str.add(object.getString("name"));
                                landmarks.push(object);
                            }
                        }
                    });
                }

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


            }
        });*/


        name_view.setText(quest_name);

        //str = new ArrayList<String>();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getApplicationContext(), LandmarkActivity.class);
                mIntent.putExtra("landmarkID", landmarks.get(position).getID());
            }
        });

    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_questinfo, container, false);
        str = new ArrayList<String>();
        lv = (ListView) findViewById(R.id.quest_landmarks_view);
        adapter = new ArrayAdapter<String>(this.getApplicationContext(),android.R.layout.simple_expandable_list_item_1, str);
        lv.setAdapter(adapter);

        return view;
    }*/
}
