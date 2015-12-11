package com.android9033.scavenger.scavenger.Controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android9033.scavenger.scavenger.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by yirongshao on 11/29/15.
 */
public class EditProfileActivity extends AppCompatActivity {

    private Uri imageCaptureUri;
    private ImageView mImageView;
    private  Button editImage;
    private Bitmap cameraImg;

    private EditText editName;
    private EditText editEmail;
    private ParseUser curUser = ParseUser.getCurrentUser();

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



        mImageView = (ImageView) findViewById(R.id.img);
        editImage = (Button) findViewById(R.id.btnEdit);


        ParseFile profile=curUser.getParseFile("image");
        profile.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                if(e==null){
                    Bitmap profile=BitmapFactory.decodeByteArray(data,0,data.length);
                    mImageView.setImageBitmap(profile);
                }
            }
        });
        // Users can change their profile photo
        changePhoto();

        editName = (EditText) findViewById(R.id.editname);
        editEmail = (EditText) findViewById(R.id.editemail);


        String name = curUser.getUsername();
        editName.setText(name);
        editEmail.setText(curUser.getEmail());


        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitChange();
                Intent mIntent = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(mIntent);
            }
        });


    }

    private void submitChange() {

        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        cameraImg.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] data2=stream.toByteArray();
        ParseFile photofile=new ParseFile("profile",data2);
        curUser.put("image", photofile);

        curUser.put("username",editName.getText().toString());
        curUser.put("email",editEmail.getText().toString());
        curUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

            }
        });
        Toast.makeText(EditProfileActivity.this, "Success", Toast.LENGTH_LONG)
                .show();

    }

    private void changePhoto() {
        final String[] items = new String[] {"Take a picture", "Choose from Gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    /*Calendar cal = Calendar.getInstance();
                    File photofile = new File(Environment.getExternalStorageDirectory(), (cal.getTimeInMillis() + ".jpg"));
                    if (!photofile.exists()) {
                        try {
                            photofile.createNewFile();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        photofile.delete();
                        try {
                            photofile.createNewFile();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                    System.out.println(photofile.getName());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photofile));
                    */

                    startActivityForResult(intent, PICK_FROM_CAMERA);

                    dialog.cancel();
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    File pictireDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    String picPath = pictireDirectory.getPath();
                    Uri data = Uri.parse(picPath);
                    intent.setDataAndType(data, "image/*");
                    startActivityForResult(intent, PICK_FROM_FILE);
                }
            }
        });

        final AlertDialog dialog = builder.create();

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        String path = "";
        if (requestCode == PICK_FROM_FILE){
            imageCaptureUri = data.getData();
            InputStream inputStream;
            try{
                inputStream = getContentResolver().openInputStream(imageCaptureUri);
                cameraImg = BitmapFactory.decodeStream(inputStream);

                mImageView.setImageBitmap(cameraImg);

            } catch (FileNotFoundException e){
                e.printStackTrace();
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_SHORT).show();
            }

        }else {
            //path = imageCaptureUri.getPath();
            //Bitmap bitmap = BitmapFactory.decodeFile(path);
            cameraImg = (Bitmap) data.getExtras().get("data");

           // Calendar cal = Calendar.getInstance();
            //File photofile = new File(Environment.getExternalStorageDirectory(), (cal.getTimeInMillis() + ".jpg"));

            //System.out.println(photofile.getName());

            mImageView.setImageBitmap(cameraImg);
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
