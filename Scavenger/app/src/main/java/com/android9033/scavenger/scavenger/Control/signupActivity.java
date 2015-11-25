package com.android9033.scavenger.scavenger.Control;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android9033.scavenger.scavenger.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by yirongshao on 10/31/15.
 */
public class signupActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText repassword;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Sign Up");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        email = (EditText) findViewById(R.id.email2);
        password = (EditText) findViewById(R.id.password2);
        repassword = (EditText) findViewById(R.id.repassword);

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                validate();
                createUser();
            }
        });

    }

    // This method check the validation od sign up data
    private void validate() {
        boolean validationError = false;
        StringBuilder validationErrorMessage =
                new StringBuilder();
        if (isEmpty(email)) {
            validationError = true;
            validationErrorMessage.append(getResources().getString(R.string.error_blank_username));
        }
        if (isEmpty(password)) {
            if (validationError) {
                validationErrorMessage.append(getResources().getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getResources().getString(R.string.error_blank_password));
        }
        if (!isMatching(password, repassword)) {
            if (validationError) {
                validationErrorMessage.append(getResources().getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getResources().getString(
                    R.string.error_mismatched_passwords));
        }
        validationErrorMessage.append(getResources().getString(R.string.error_end));

        // If there is a validation error, display the error
        if (validationError) {
            Toast.makeText(signupActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    // Create a new user in Parse
    private void createUser(){
        ParseUser user = new ParseUser();
        user.setUsername(email.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());

        // Call the Parse signup method
        user.signUpInBackground(new SignUpCallback() {

            @Override
            public void done(ParseException e) {
                //    dlg.dismiss();
                if (e != null) {
                    // Show the error message
                    Toast.makeText(signupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    // Start an intent for the user's main screen
                    Intent intent = new Intent(signupActivity.this, MyActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    // This method check if two password entered are same
    private boolean isMatching(EditText password, EditText repassword) {
        if (password.getText().toString().equals(repassword.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    // This method check if EditText is empty
    private boolean isEmpty(EditText text) {
        if (text.getText().toString().trim().length() > 0){
            return false;
        } else {
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
        if (id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

}
