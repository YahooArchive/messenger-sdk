/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.data.json;

import org.json.me.JSONException;
import org.json.me.JSONObject;


public class Presence {

    public static final int PRESENCE_STATE_OFFLINE = -1;
    public static final int PRESENCE_STATE_ONLINE = 0;
    public static final int PRESENCE_STATE_BUSY = 2;
    public static final int PRESENCE_STATE_IDLE = 999;

    private Integer presenceState;
    private String presenceMessage;
    private String clientType;

    /**
     * @return the presenceState
     */
    public Integer getPresenceState() {
        return presenceState;
    }

    /**
     * @param presenceState the presenceState to set
     */
    public void setPresenceState(Integer presenceState) {
        this.presenceState = presenceState;
    }

    /**
     * @return the presenceMessage
     */
    public String getPresenceMessage() {
        return presenceMessage;
    }

    /**
     * @param presenceMessage the presenceMessage to set
     */
    public void setPresenceMessage(String presenceMessage) {
        this.presenceMessage = presenceMessage;
    }

    /**
     * @return the clientType
     */
    public String getClientType() {
        return clientType;
    }

    /**
     * @param clientType the clientType to set
     */
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }


    //   JSON Serialization methods

    public void unserializeJSON(JSONObject o)
        throws JSONException
    {

        //  Mandatory fields

        setPresenceState(new Integer(o.getInt("presenceState")));

        //  Optional fields

        if (o.has("presenceMessage"))
            setPresenceMessage(o.getString("presenceMessage"));
        if (o.has("clientType"))
            setClientType(o.getString("clientType"));

    }

    public JSONObject serializeJSON()
        throws JSONException
    {

        JSONObject o = new JSONObject();

        //  Mandatory items

        o.put("presenceState", getPresenceState());

        //  Optional items

        if (getPresenceMessage() != null)
            o.put("presenceMessage", getPresenceMessage());
        if (getClientType() != null)
            o.put("clientType", getClientType());

        return o;
    }
}
