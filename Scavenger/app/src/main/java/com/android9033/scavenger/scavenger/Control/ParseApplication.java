package com.android9033.scavenger.scavenger.Control;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import object.Quest;

/**
 * Created by yirongshao on 11/2/15.
 */
public class ParseApplication extends Application {
    public void onCreate(){
        super.onCreate();
        ParseObject.registerSubclass(Quest.class);
        // Initial the Parse
        Parse.initialize(this, "G5Wr8k19kZZBUxM2LzN68Y7eR9uV6z4TfdpGjZCm",
                "DUKYRPAh6C0nhRAO0Esp4hj33qI3tWjGWWA9nAGL");
    }
}
