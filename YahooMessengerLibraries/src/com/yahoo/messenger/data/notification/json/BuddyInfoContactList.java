/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.data.notification.json;

import java.util.Vector;
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;


public class BuddyInfoContactList {

    private BuddyInfoContact[] buddyInfoContacts;

    /**
     * @return the groups
     */
    public BuddyInfoContact[] getBuddyInfoContacts() {
        return buddyInfoContacts;
    }

    /**
     * @param groups the groups to set
     */
    public void setBuddyInfoContacts(BuddyInfoContact[] buddyInfoContacts) {
        this.buddyInfoContacts = buddyInfoContacts;
    }


    //  JSON Serialization methods

    public void unserializeJSON(JSONArray a)
        throws JSONException
    {

        Vector v = new Vector();

        //  Mandatory fields

        for (int i = 0; i < a.length(); i++) {

            JSONObject o = a.getJSONObject(i);

                BuddyInfoContact c = new BuddyInfoContact();
                c.unserializeJSON(o);
                v.addElement(c);

        }

        buddyInfoContacts = new BuddyInfoContact[v.size()];
        v.copyInto(buddyInfoContacts);

    }

}
