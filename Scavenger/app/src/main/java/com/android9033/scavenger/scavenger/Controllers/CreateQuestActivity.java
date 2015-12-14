package com.android9033.scavenger.scavenger.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android9033.scavenger.scavenger.Models.Quest;
import com.android9033.scavenger.scavenger.R;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergey on 12/14/15.
 */
public class CreateQuestActivity extends AppCompatActivity
{
    private EditText quest_name_edittext;
    private EditText quest_location_edittext;
    private EditText quest_bounty_edittext;
    private boolean is_public;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createquest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Create a new Quest");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        quest_name_edittext = (EditText) findViewById(R.id.quest_name_creation_input);
        quest_location_edittext = (EditText) findViewById(R.id.quest_location_creation_input);
        quest_bounty_edittext = (EditText) findViewById(R.id.quest_bounty_creation_input);

        Switch mSwitch = (Switch) findViewById(R.id.quest_public_toggle_creation_input);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    is_public = true;
                else
                    is_public = false;
            }
        });
    }
}
