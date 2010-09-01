/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.data.json;

import java.util.Vector;
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;


public class PreferenceList {

    private Preference[] preferences;

    /**
     * @return the preferences
     */
    public Preference[] getIgnoredUsers() {
        return preferences;
    }

    /**
     * @param preferences the preferences to set
     */
    public void setIgnoredUsers(Preference[] preferences) {
        this.preferences = preferences;
    }


    public void unserializeJSON(JSONArray a)
        throws JSONException
    {

        Vector v = new Vector();

        //  Mandatory fields

        for (int i = 0; i < a.length(); i++) {

            JSONObject o = a.getJSONObject(i);
            Preference c = new Preference();
            c.unserializeJSON(o.getJSONObject("preference"));
            v.addElement(c);
        }

        preferences = new Preference[v.size()];
        v.copyInto(preferences);

    }

    public JSONArray serializeJSON()
        throws JSONException
    {

        JSONArray a = new JSONArray();

        //  Mandatory items

        for (int i = 0; i < preferences.length; i++) {
            Preference c = preferences[i];
            JSONObject o = new JSONObject();
            o.put("preference", c.serializeJSON());
            a.put(o);
        }

        return a;
    }
}
