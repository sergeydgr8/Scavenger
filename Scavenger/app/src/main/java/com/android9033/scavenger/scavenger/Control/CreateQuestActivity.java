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
import android.widget.EditText;

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
import com.parse.SaveCallback;

import java.io.IOException;
import java.util.List;

import com.android9033.scavenger.scavenger.Model.Quest;

/**
 * Created by yirongshao on 11/21/15.
 */
public class CreateQuestActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private EditText questName;

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
            myMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        }

    }

    public void submitQuest(){
        String name = questName.getText().toString();
        Quest quest=new Quest();
        quest.setName(name);
        quest.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng sydney = new LatLng(-34, 151);
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
