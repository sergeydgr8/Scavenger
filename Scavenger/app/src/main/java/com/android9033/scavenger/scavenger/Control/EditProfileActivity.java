package com.android9033.scavenger.scavenger.Control;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.android9033.scavenger.scavenger.R;

import java.io.File;

/**
 * Created by yirongshao on 11/29/15.
 */
public class EditProfileActivity extends AppCompatActivity {

    private Uri imageCaptureUri;
    private ImageView mImageView;
    Button editImage;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        // Set up the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Edit Profile");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final String[] items = new String[] {"Take a picture", "Choose from SD card"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment.getExternalStorageDirectory(), "tmp" + String.valueOf(System.currentTimeMillis())+".jpg");
                    imageCaptureUri = Uri.fromFile(file);
                    try {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCaptureUri);
                        intent.putExtra("return data", true);
                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    dialog.cancel();
                }else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
                }
            }
        });

        final AlertDialog dialog = builder.create();

        mImageView = (ImageView) findViewById(R.id.img);
        editImage = (Button) findViewById(R.id.btnEdit);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


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
