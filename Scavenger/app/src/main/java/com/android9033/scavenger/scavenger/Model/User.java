package com.android9033.scavenger.scavenger.Model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by orchumandz on 11/24/2015.
 */
@ParseClassName("User")
public class User extends ParseUser{
    public String getPoint(){
        return getString("point");
    }
    public void addPoint(){

    }
}
