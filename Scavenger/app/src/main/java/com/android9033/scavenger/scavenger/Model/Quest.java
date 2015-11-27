package com.android9033.scavenger.scavenger.Model;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by orchumandz on 11/25/2015.
 */
@ParseClassName("Quest")
public class Quest extends ParseObject {
    public String getName(){
        return getString("name");
    }
    public void setName(String name){
        put("name",name);
    }
    public boolean getStage(){
        return getBoolean("stage");
    }
    public void setStage(boolean stage){
        put("stage",stage);
    }
    public void setGeo(ParseGeoPoint geo){
        put("geopoint",geo);
    }
    public void setDes(String des){put("description",des);}
}
