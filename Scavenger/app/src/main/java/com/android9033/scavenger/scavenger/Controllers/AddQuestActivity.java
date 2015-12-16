package com.android9033.scavenger.scavenger.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android9033.scavenger.scavenger.Models.Quest;
import com.android9033.scavenger.scavenger.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * Created by sergey on 12/14/15.
 */
public class AddQuestActivity extends AppCompatActivity
{
    private EditText questID_input;
    private ListView lv;
    private ArrayList<String> str;
    private ArrayAdapter<String> adapter;
    private Stack<Quest> quests = new Stack<Quest>();
    private Button find_quest_by_ID;
    private ArrayList<String> questIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Add a Quest.");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        str = new ArrayList<String>();
        questIDs = new ArrayList<String>();
        lv = (ListView) findViewById(R.id.add_quest_list);
        questID_input = (EditText) findViewById(R.id.add_quest_by_id_input);
        find_quest_by_ID = (Button) findViewById(R.id.add_quest_by_id_button);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, str);
        lv.setAdapter(adapter);

        ParseQuery<Quest> quest_query = new ParseQuery<Quest>("Quest2");
        quest_query.findInBackground(new FindCallback<Quest>() {
            @Override
            public void done(List<Quest> objects, ParseException e) {
                if (e == null)
                {
                    for (Quest q : objects)
                    {
                        if (q.getBoolean("public")) {
                            quests.push(q);
                            //str.add(0, q.getString("name"));
                        }
                    }
                }
                int index = 0;
                for (int i = 0; i < 10; i++)
                {
                    if (!quests.isEmpty())
                    {
                        index++;
                        Quest q = quests.pop();
                        str.add(q.getString("name") + " :: " + q.getObjectId());
                        questIDs.add(q.getObjectId());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /*public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_addquest, container, false);

        str = new ArrayList<String>();
        lv = (ListView) findViewById(R.id.add_quest_list);
        questID_input = (EditText) findViewById(R.id.add_quest_by_id_input);
        find_quest_by_ID = (Button) findViewById(R.id.add_quest_by_id_button);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, str);

        ParseQuery<Quest> quest_query = new ParseQuery<Quest>("Quest2");
        quest_query.findInBackground(new FindCallback<Quest>() {
            @Override
            public void done(List<Quest> objects, ParseException e) {
                if (e == null)
                {
                    for (Quest q : objects)
                    {
                        if (q.getBoolean("public")) {
                            quests.push(q);
                            //str.add(0, q.getString("name"));
                        }
                    }
                }
                int index = 0;
                for (int i = 0; i < 10; i++)
                {
                    if (!quests.isEmpty())
                    {
                        index++;
                        Quest q = quests.pop();
                        str.add(q.getString("name"));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        return view;
    }*/

}
