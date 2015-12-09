package com.android9033.scavenger.scavenger.Control;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
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

import com.android9033.scavenger.scavenger.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.android9033.scavenger.scavenger.Model.Quest;

/**
 * Created by yirongshao on 11/21/15.
 */
public class CreateQuestActivity extends AppCompatActivity {

    private GoogleMap myMap;
    private EditText questName;
    private EditText questDescription;
    private EditText questPoint;
    private ParseGeoPoint geoPoint;
    private boolean isPublic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createquest);

        // Set up the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Create a new Quest");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        questName = (EditText) findViewById(R.id.etName);
        questDescription = (EditText) findViewById(R.id.etDescriptoin);
        questPoint = (EditText) findViewById(R.id.etPoint);


        Switch mSwitch = (Switch) findViewById(R.id.switch1);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isPublic = true;
                }else{
                    isPublic = false;
                }
            }
        });

        // Set up Google Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                myMap = googleMap;
                myMap.setMyLocationEnabled(true);
                myMap.getUiSettings().setZoomControlsEnabled(true);
            }
        });

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitQuest();
            }
        });

    }

    // Search the location typed in
    public void onSearch(View view){
        EditText etLocation = (EditText) findViewById(R.id.etLandmark);
        String location = etLocation.getText().toString();
        List<Address> addressList = null;
        if (location != null || !location.equals("")){
            Geocoder geocoder = new Geocoder(this);
            try{
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e){
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            myMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
            geoPoint = new ParseGeoPoint(address.getLatitude(), address.getLongitude());

        }

    }

    public void submitQuest(){
        if (checkValid()) {
            String name = questName.getText().toString();
            Quest quest = new Quest();
            quest.setName(name);
            quest.put("userfinished", new ArrayList<String>());
            quest.setGeo(geoPoint);
            quest.setStage(isPublic);
            quest.setDes(questDescription.getText().toString());
            quest.put("questpoint",questPoint.getText().toString());
            quest.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                }
            });
            ParseUser curUser=ParseUser.getCurrentUser();
            List<String> createlist=curUser.getList("create");
            createlist.add(name);
            curUser.put("create", createlist);
            curUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });
            Toast.makeText(CreateQuestActivity.this, "Created", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public boolean checkValid(){
        if (isEmpty(questName)){
            Toast.makeText(CreateQuestActivity.this, "Qest Name is Empty", Toast.LENGTH_LONG)
                    .show();
            return false;
        } else if (isEmpty(questDescription)){
            Toast.makeText(CreateQuestActivity.this, "Qest Description is Empty", Toast.LENGTH_LONG)
                    .show();
            return false;
        } else if (geoPoint == null){
            Toast.makeText(CreateQuestActivity.this, "Please set a location", Toast.LENGTH_LONG)
                    .show();
            return false;
        } else {
            return true;
        }

    }

    public boolean isEmpty(EditText et){
        if (et.getText().toString().trim().length() > 0){
            return false;
        }else {
            return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }


}
