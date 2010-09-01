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


public class ClientCapabilityList {

    private ClientCapability[] clientCapabilities;

    /**
     * @return the clientCapabilities
     */
    public ClientCapability[] getClientCapabilitys() {
        return clientCapabilities;
    }

    /**
     * @param clientCapabilities the clientCapabilities to set
     */
    public void setClientCapabilitys(ClientCapability[] clientCapabilities) {
        this.clientCapabilities = clientCapabilities;
    }


    public void unserializeJSON(JSONArray a)
        throws JSONException
    {

        Vector v = new Vector();

        //  Mandatory fields

        for (int i = 0; i < a.length(); i++) {

            JSONObject o = a.getJSONObject(i);
            ClientCapability c = new ClientCapability();
            c.unserializeJSON(o.getJSONObject("clientCapability"));
            v.addElement(c);
        }

        clientCapabilities = new ClientCapability[v.size()];
        v.copyInto(clientCapabilities);

    }

    public JSONArray serializeJSON()
        throws JSONException
    {

        JSONArray a = new JSONArray();

        //  Mandatory items

        for (int i = 0; i < clientCapabilities.length; i++) {
            ClientCapability c = clientCapabilities[i];
            JSONObject o = new JSONObject();
            o.put("clientCapability", c.serializeJSON());
            a.put(o);
        }

        return a;
    }
}
