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


public class GroupList {

    private Group[] groups;

    /**
     * @return the groups
     */
    public Group[] getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(Group[] groups) {
        this.groups = groups;
    }


    //  JSON serialization methods

    public void unserializeJSON(JSONArray a)
        throws JSONException
    {

        Vector v = new Vector();

        //  Mandatory fields

        for (int i = 0; i < a.length(); i++) {

            JSONObject o = a.getJSONObject(i);
            Group c = new Group();
            c.unserializeJSON(o.getJSONObject("group"));
            v.addElement(c);
        }

        groups = new Group[v.size()];
        v.copyInto(groups);

    }

    public JSONArray serializeJSON()
        throws JSONException
    {

        JSONArray a = new JSONArray();

        //  Mandatory items

        for (int i = 0; i < groups.length; i++) {
            Group c = groups[i];
            JSONObject o = new JSONObject();
            o.put("group", c.serializeJSON());
            a.put(o);
        }

        return a;
    }
}
