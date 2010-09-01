/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.data.json;

import org.json.me.JSONException;
import org.json.me.JSONObject;


public class User {

    private String id;
    private String network;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the network
     */
    public String getNetwork() {
        return network;
    }

    /**
     * @param network the network to set
     */
    public void setNetwork(String network) {
        this.network = network;
    }

    
    //   JSON Serialization methods

    public void unserializeJSON(JSONObject o)
        throws JSONException
    {

        //  Mandatory fields

        setId(o.getString("id"));

        //  Optional fields

        if (o.has("network"))
            setNetwork(o.getString("network"));

    }

    public JSONObject serializeJSON()
        throws JSONException
    {

        JSONObject o = new JSONObject();

        //  Mandatory items

        o.put("id", getId());

        //  Optional items

        if (getNetwork() != null)
            o.put("network", getNetwork());

        return o;
    }
}
