package com.android9033.scavenger.scavenger.Control;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android9033.scavenger.scavenger.R;

/**
 * Created by yirongshao on 10/31/15.
 */
public class signupActivity extends Activity {

    private EditText email;
    private EditText password;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = (EditText) findViewById(R.id.email2);
        password = (EditText) findViewById(R.id.password2);

        Button submit = (Button) findViewById(R.id.submit);

    }

    private boolean isEmpty(EditText text) {
        if (text.getText().toString().trim().length() > 0){
            return false;
        } else {
            return true;
        }
    }

}
