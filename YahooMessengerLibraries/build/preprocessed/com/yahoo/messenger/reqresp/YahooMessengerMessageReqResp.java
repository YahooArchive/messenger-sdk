/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.reqresp;

import com.yahoo.messenger.exception.HttpException;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.util.YahooMessengerConstants;
import com.yahoo.messenger.util.HttpUtils;
import java.io.IOException;
import org.json.me.JSONException;
import org.json.me.JSONObject;


public class YahooMessengerMessageReqResp extends YahooMessengerBaseReqResp
{

    //  Request URI parameters

    private String network;
    private String targetId;

    //  Request parameters

    private String message;
    private String sendAs;


    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the sendAs
     */
    public String getSendAs() {
        return sendAs;
    }

    /**
     * @param sendAs the sendAs to set
     */
    public void setSendAs(String sendAs) {
        this.sendAs = sendAs;
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

    /**
     * @return the targetId
     */
    public String getTargetID() {
        return targetId;
    }

    /**
     * @param targetId the targetId to set
     */
    public void setTargetID(String targetId) {
        this.targetId = targetId;
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

        if (getNetwork() == null)
            throw new MessengerException(MessengerException.NO_NETWORK_GIVEN);

        if (getTargetID() == null)
            throw new MessengerException(MessengerException.NO_TARGET_ID_GIVEN);

        String url = "http://" + getRequestServer() + 
            YahooMessengerConstants.messageManagementURL +
            "/" + getNetwork() + "/" + getTargetID() +
            "?c=" + getCrumb() + "&sid=" + getSessionID();

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

        o.put("message", getMessage());

        //  Optional items

        if (getSendAs() != null)
            o.put("sendAs", getSendAs());

        return o;

    }


}
