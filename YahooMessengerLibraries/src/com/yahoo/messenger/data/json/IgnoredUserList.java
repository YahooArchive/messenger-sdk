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


public class IgnoredUserList {

    private IgnoredUser[] ignoredUsers;

    /**
     * @return the ignoredUsers
     */
    public IgnoredUser[] getIgnoredUsers() {
        return ignoredUsers;
    }

    /**
     * @param ignoredUsers the ignoredUsers to set
     */
    public void setIgnoredUsers(IgnoredUser[] ignoredUsers) {
        this.ignoredUsers = ignoredUsers;
    }


    //  JSON Serialization methods

    public void unserializeJSON(JSONArray a)
        throws JSONException
    {

        Vector v = new Vector();

        //  Mandatory fields

        for (int i = 0; i < a.length(); i++) {

            JSONObject o = a.getJSONObject(i);
            IgnoredUser c = new IgnoredUser();
            c.unserializeJSON(o.getJSONObject("ignoredUser"));
            v.addElement(c);
        }

        ignoredUsers = new IgnoredUser[v.size()];
        v.copyInto(ignoredUsers);

    }

    public JSONArray serializeJSON()
        throws JSONException
    {

        JSONArray a = new JSONArray();

        //  Mandatory items

        for (int i = 0; i < ignoredUsers.length; i++) {
            IgnoredUser c = ignoredUsers[i];
            JSONObject o = new JSONObject();
            o.put("ignoredUser", c.serializeJSON());
            a.put(o);
        }

        return a;
    }
}
