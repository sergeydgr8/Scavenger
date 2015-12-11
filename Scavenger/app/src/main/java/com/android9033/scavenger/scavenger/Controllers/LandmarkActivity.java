package com.android9033.scavenger.scavenger.Controllers;

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

import com.android9033.scavenger.scavenger.Models.Landmark;
import com.android9033.scavenger.scavenger.R;
import com.google.android.gms.common.api.GoogleApiClient;
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
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by yirongshao on 11/27/15.
 */
public class LandmarkActivity extends AppCompatActivity {

    private GoogleMap myMap;
    GoogleApiClient mGoogleApiClient;
    private String description;
    private ParseGeoPoint geoPoint;
    private Button found;
    private String out;
    private String questpoint;

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

        out = getIntent().getStringExtra("1");
        //System.out.print("Out: " + out);
        name.setText(out);

        ParseQuery<Landmark> query=new ParseQuery<Landmark>("Landmark");
        query.whereEqualTo("name", out);
        query.findInBackground(new FindCallback<Landmark>() {
            @Override
            public void done(List<Landmark> objects, ParseException e) {
                if (e == null) {
                    for (Landmark landmark : objects) {
                        description = landmark.getString("description");
                        geoPoint = landmark.getParseGeoPoint("geopoint");
                        System.out.println(description);
                        System.out.println(geoPoint);
                    }
                }
                TextView desc = (TextView) findViewById(R.id.description);
                desc.setText(description);
                System.out.print(geoPoint.getLatitude() + ", " + geoPoint.getLongitude());

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

        if (r < 0.0005){
            ParseQuery<Landmark> Qquery=new ParseQuery<Landmark>("Landmark");
            Qquery.whereEqualTo("name", out);
            Qquery.findInBackground(new FindCallback<Landmark>() {
                @Override
                public void done(List<Landmark> objects, ParseException e) {
                    if (e == null) {
                        for (Landmark landmark : objects) {
                            questpoint = landmark.getString("questpoint");

                        }
                    }

                    Toast.makeText(LandmarkActivity.this, "Success! And you got " + questpoint + " points!", Toast.LENGTH_SHORT).show();
                    ParseUser curUser = ParseUser.getCurrentUser();
                    int oldPoint = Integer.parseInt(curUser.getString("point"));
                    System.out.println(oldPoint);
                    curUser.put("point", Integer.toString(oldPoint + Integer.parseInt(questpoint)));
                    List<String> completelist = curUser.getList("complete");
                    completelist.add(out);
                    curUser.put("complete", completelist);
                    curUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                        }
                    });

                }
            });







            ParseQuery<Landmark> query=new ParseQuery<Landmark>("Landmark");
            query.whereEqualTo("name", out);
            query.findInBackground(new FindCallback<Landmark>() {
                @Override
                public void done(List<Landmark> objects, ParseException e) {
                    if (e == null) {
                        for (Landmark landmark : objects) {
                            //System.out.println(landmark.getList("userfinished"));
                            List userlist = landmark.getList("userfinished");
                            userlist.add(ParseUser.getCurrentUser().getUsername());
                            landmark.put("userfinished", userlist);
                            landmark.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                }
                            });
                        }
                    }


                }
            });


            //ParseUser curUser2=ParseUser.getCurrentUser();


        } else{
            Toast.makeText(LandmarkActivity.this, "Oops, Find It Again! ", Toast.LENGTH_LONG)
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
