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


public class ContactList {

    private Contact[] contacts;

    /**
     * @return the contacts
     */
    public Contact[] getContacts() {
        return contacts;
    }

    /**
     * @param contacts the contacts to set
     */
    public void setContacts(Contact[] contacts) {
        this.contacts = contacts;
    }


    public void unserializeJSON(JSONArray a)
        throws JSONException
    {

        Vector v = new Vector();

        //  Mandatory fields

        for (int i = 0; i < a.length(); i++) {

            JSONObject o = a.getJSONObject(i);
            Contact c = new Contact();
            c.unserializeJSON(o.getJSONObject("contact"));
            v.addElement(c);
        }

        contacts = new Contact[v.size()];
        v.copyInto(contacts);

    }

    public JSONArray serializeJSON()
        throws JSONException
    {

        JSONArray a = new JSONArray();

        //  Mandatory items

        for (int i = 0; i < contacts.length; i++) {
            Contact c = contacts[i];
            JSONObject o = new JSONObject();
            o.put("contact", c.serializeJSON());
            a.put(o);
        }

        return a;
    }
}
