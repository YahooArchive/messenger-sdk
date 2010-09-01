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


public class UserList {

    private User[] users;

    /**
     * @return the users
     */
    public User[] getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(User[] Users) {
        this.users = Users;
    }


    public void unserializeJSON(JSONArray a)
        throws JSONException
    {

        Vector v = new Vector();

        //  Mandatory fields

        for (int i = 0; i < a.length(); i++) {

            JSONObject o = a.getJSONObject(i);
            User c = new User();
            c.unserializeJSON(o.getJSONObject("user"));
            v.addElement(c);
        }

        users = new User[v.size()];
        v.copyInto(users);

    }

    public JSONArray serializeJSON()
        throws JSONException
    {

        JSONArray a = new JSONArray();

        //  Mandatory items

        for (int i = 0; i < users.length; i++) {
            User c = users[i];
            JSONObject o = new JSONObject();
            o.put("user", c.serializeJSON());
            a.put(o);
        }

        return a;
    }
}
