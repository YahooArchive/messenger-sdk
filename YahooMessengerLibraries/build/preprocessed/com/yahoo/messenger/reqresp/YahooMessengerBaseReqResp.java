/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.reqresp;

import com.yahoo.messenger.exception.HttpException;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.util.YahooMessengerAuthentication;
import com.yahoo.messenger.util.YahooMessengerConstants;
import java.io.IOException;
import org.json.me.JSONException;
import org.json.me.JSONObject;


public abstract class YahooMessengerBaseReqResp {

    private String requestServer;
    private YahooMessengerAuthentication authentication;

    //  Request uri parameters common to all subclasses

    protected String crumb;
    protected String sessionID;


    /**
     * @return the requestServer
     */
    public String getRequestServer() {

        if (requestServer != null)
            return requestServer;
        else
            return YahooMessengerConstants.messengerServerURL;

    }

    /**
     * @param requestServer the request server to set
     */
    public void setRequestServer(String requestServer) {
        this.requestServer = requestServer;
    }

    /**
     * @return the authentication
     */
    public YahooMessengerAuthentication getAuthentication() {
        return authentication;
    }

    /**
     * @param authentication the authentication to set
     */
    public void setAuthentication(YahooMessengerAuthentication authentication) {
        this.authentication = authentication;
    }

    /**
     * @return the crumb
     */
    public String getCrumb() {
        return crumb;
    }

    /**
     * @param crumb the crumb to set
     */
    public void setCrumb(String crumb) {
        this.crumb = crumb;
    }

    /**
     * @return the sessionID
     */
    public String getSessionID() {
        return sessionID;
    }

    /**
     * @param sessionID the sessionID to set
     */
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }


    //  Abstract class that must be overridden in a subclass.

    public abstract void executeRequest()
        throws IOException, JSONException, HttpException, MessengerException;


    //  JSON request serialization method. Override in a subclass if
    //  necessary.

    public JSONObject serializeJSONRequestParameters()
        throws JSONException
    {

        return null;

    }

    //  JSON response serialization method. Override in a subclass if
    //  necessary.

    public void unserializeJSONResponseParameters(JSONObject o)
        throws JSONException
    {

    }



}
