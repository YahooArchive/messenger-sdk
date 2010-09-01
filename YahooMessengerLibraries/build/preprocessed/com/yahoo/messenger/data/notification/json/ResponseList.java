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


public class ResponseList {

    private Response[] responses;

    /**
     * @return the groups
     */
    public Response[] getResponses() {
        return responses;
    }

    /**
     * @param groups the groups to set
     */
    public void setResponse(Response[] responses) {
        this.responses = responses;
    }


    //  JSON Serialization methods
    
    public void unserializeJSON(JSONArray a)
        throws JSONException
    {

        Vector v = new Vector();

        //  Mandatory fields

        for (int i = 0; i < a.length(); i++) {

            JSONObject o = a.getJSONObject(i);

            if (o.has("message")) {
                Message c = new Message();
                c.unserializeJSON(o.getJSONObject("message"));
                v.addElement(c);
            } if (o.has("buddyInfo")) {
                BuddyInfo c = new BuddyInfo();
                c.unserializeJSON(o.getJSONObject("buddyInfo"));
                v.addElement(c);
            }
        }

        responses = new Response[v.size()];
        v.copyInto(responses);

    }

}
