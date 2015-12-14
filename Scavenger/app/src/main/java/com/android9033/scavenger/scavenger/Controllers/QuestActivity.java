package com.android9033.scavenger.scavenger.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android9033.scavenger.scavenger.Models.Landmark;
import com.android9033.scavenger.scavenger.Models.Quest;
import com.android9033.scavenger.scavenger.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
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
    private String objectId;
    private String quest_name;
    private String quest_loc;
    private List<String> landmarkIDs;

    ArrayList<Landmark> landmarks = new ArrayList<Landmark>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questinfo);

        objectId = getIntent().getStringExtra("questID");

        ParseQuery<Quest> quest_query = new ParseQuery<Quest>("Quest2");
        quest_query.getInBackground(objectId, new GetCallback<Quest>() {
            @Override
            public void done(Quest object, ParseException e) {
                if (e == null) {
                    quest_name = object.getString("name");
                    quest_loc = object.getString("location");
                    landmarkIDs = object.getList("landmarks");

                    ParseQuery<Landmark> landmarks_query = new ParseQuery<Landmark>("Landmark");
                    for (String s : landmarkIDs) {
                        landmarks_query.getInBackground(s, new GetCallback<Landmark>() {
                            @Override
                            public void done(Landmark object1, ParseException e1) {
                                if (e1 == null) {
                                    str.add(object1.getString("name"));
                                }
                            }
                        });
                    }
                }
            }
        });

        //str = new ArrayList<String>();
        lv = (ListView) findViewById(R.id.quest_landmarks_view);
        adapter = new ArrayAdapter<String>(this.getApplicationContext(), android.R.layout.simple_expandable_list_item_1, str);
        lv.setAdapter(adapter);

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
