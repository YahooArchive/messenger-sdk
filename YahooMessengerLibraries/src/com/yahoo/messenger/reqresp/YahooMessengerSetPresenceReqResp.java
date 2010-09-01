/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.reqresp;

import com.yahoo.messenger.data.json.Presence;
import com.yahoo.messenger.exception.HttpException;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.util.YahooMessengerConstants;
import com.yahoo.messenger.util.HttpUtils;
import java.io.IOException;
import org.json.me.JSONException;
import org.json.me.JSONObject;


public class YahooMessengerSetPresenceReqResp extends YahooMessengerBaseReqResp {


    //  Request body parameters

    private Presence presence;


    public YahooMessengerSetPresenceReqResp() {
        presence = new Presence();
    }

    /**
     * @return the presence
     */
    public Presence getPresence() {
        return presence;
    }

    /**
     * @param presence the presence to set
     */
    public void setPresence(Presence presence) {
        this.presence = presence;
    }

    
    public void executeRequest()
        throws IOException, JSONException, HttpException, MessengerException
    {

        //  Verify mandatory parameters

        if (getCrumb() == null)
            throw new MessengerException(MessengerException.NO_CRUMB_GIVEN,
                    "No crumb given");

        if (getSessionID() == null)
            throw new MessengerException(MessengerException.NO_SESSION_ID_GIVEN,
                    "No session ID given");


        //  Create the HTTP URL

        String url = "http://" + getRequestServer() +
            YahooMessengerConstants.presenceManagementURL +
            "?"+YahooMessengerConstants.putSuffix +
            "&c="+getCrumb()+"&sid="+getSessionID();

        //  Add authentication parameters

        url += getAuthentication().getOAuthParameters();

        //  Perform the actual call to the server

        String responseString =
            HttpUtils.performHttpPost(
                url, getAuthentication(), serializeJSONRequestParameters().toString());

    }


    //  JSON serialization methods

    public JSONObject serializeJSONRequestParameters()
        throws JSONException
    {
        JSONObject o = new JSONObject();

        //  Mandatory items

        o.put("presenceState", getPresence().getPresenceState());

        //  Optional items

        if (getPresence().getPresenceMessage() != null) {
            o.put("presenceMessage", getPresence().getPresenceMessage());
        }

        return o;

    }


}
