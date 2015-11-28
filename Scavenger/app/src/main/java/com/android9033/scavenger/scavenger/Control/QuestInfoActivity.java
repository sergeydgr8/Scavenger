package com.android9033.scavenger.scavenger.Control;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android9033.scavenger.scavenger.Model.Quest;
import com.android9033.scavenger.scavenger.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by yirongshao on 11/27/15.
 */
public class QuestInfoActivity extends AppCompatActivity {

    private GoogleMap myMap;
    private String description;
    private ParseGeoPoint geoPoint;
    private Button found;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questinfo);

        // Set up the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Scavenger");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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


        TextView name = (TextView) findViewById(R.id.name);

        String out = getIntent().getStringExtra("1");
        //System.out.print("Out: " + out);
        name.setText(out);

        ParseQuery<Quest> query=new ParseQuery<Quest>("Quest");
        query.whereEqualTo("name", out);
        query.findInBackground(new FindCallback<Quest>() {
            @Override
            public void done(List<Quest> objects, ParseException e) {
                if (e == null) {
                    for (Quest quest : objects) {
                        description = quest.getString("description");
                        geoPoint = quest.getParseGeoPoint("geopoint");
                        System.out.println(description);
                        System.out.println(geoPoint);
                    }
                }
                TextView desc = (TextView) findViewById(R.id.description);
                desc.setText(description);

                LatLng latLng = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
                myMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));

            }
        });

        found = (Button) findViewById(R.id.btnFoundit);

    }

    public void onClickFound(View view){
        Location myLocation = myMap.getMyLocation();
        double latDiff = Math.pow((geoPoint.getLatitude() - myLocation.getLatitude()), 2);
        double lngDiff = Math.pow((geoPoint.getLongitude() - myLocation.getLongitude()),2);
        double r = Math.sqrt(latDiff +lngDiff);

        if (r < 0.001){
            Toast.makeText(QuestInfoActivity.this, "Success! And you got 2 points!", Toast.LENGTH_SHORT)
                    .show();

        } else{
            Toast.makeText(QuestInfoActivity.this, "Oops, Find It Again! ", Toast.LENGTH_LONG)
                    .show();
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
