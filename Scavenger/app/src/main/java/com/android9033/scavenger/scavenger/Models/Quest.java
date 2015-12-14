package com.android9033.scavenger.scavenger.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import org.json.JSONArray;

/**
 * Created by sergey on 12/11/15.
 */

// TODO: Complete implementation

@ParseClassName("Quest2")
public class Quest extends ParseObject
{
    public String get_id() { return getString("objectId"); }
    public String get_name() { return getString("name"); }
    public String get_city() { return getString("city"); }
    public JSONArray get_landmark_ids() { return getJSONArray("landmarks"); }
    public boolean is_public() { return getBoolean("public"); }
    public int get_point_bounty() { return getInt("bounty"); }
}
