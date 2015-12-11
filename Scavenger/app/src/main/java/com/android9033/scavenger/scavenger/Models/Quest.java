package com.android9033.scavenger.scavenger.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by sergey on 12/11/15.
 */

// TODO: Complete implementation

@ParseClassName("Quest")
public class Quest extends ParseObject
{
    public String get_id() { return getString("objectId"); }
    public String get_name() { return getString("name"); }

}
