/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.data.json;

import org.json.me.JSONException;
import org.json.me.JSONObject;


public class ClientCapability {

    private String clientCapability;

    public ClientCapability() {
        
    }

    /**
     * @return the clientCapability
     */
    public String getClientCapability() {
        return clientCapability;
    }

    /**
     * @param clientCapability the clientCapability to set
     */
    public void setClientCapability(String clientCapability) {
        this.clientCapability = clientCapability;
    }


    //   JSON Serialization methods

    public void unserializeJSON(JSONObject o)
        throws JSONException
    {

        //  Mandatory fields

        setClientCapability(o.getString("clientCapability"));


    }

    public JSONObject serializeJSON()
        throws JSONException
    {

        JSONObject o = new JSONObject();

        //  Mandatory items

        o.put("clientCapability", getClientCapability());

        return o;
    }


}
