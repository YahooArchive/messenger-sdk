/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.reqresp;

import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.util.YahooMessengerConstants;
import com.yahoo.messenger.util.HttpUtils;
import java.io.IOException;
import org.json.me.JSONException;
import org.json.me.JSONObject;


public class YahooMessengerGetCrumbReqResp extends YahooMessengerBaseReqResp {

    
    //  Response parameters
    
    private Integer loggedIn;


    /**
     * @return the loggedIn
     */
    public Integer getLoggedIn() {
        return loggedIn;
    }

    /**
     * @param loggedIn the loggedIn to set
     */
    public void setLoggedIn(Integer loggedIn) {
        this.loggedIn = loggedIn;
    }


    public void executeRequest()
        throws IOException, JSONException, MessengerException
    {

        //  Create the HTTP URL

        String uri = "http://" + getRequestServer() +
                        YahooMessengerConstants.sessionManagementURL
                        + "?" + getAuthentication().getOAuthParameters();
        
        String responseString =
                HttpUtils.performHttpGet(uri, getAuthentication());
        
        JSONObject response = new JSONObject(responseString);

        if (response != null) {
            unserializeJSONResponseParameters(response);
        } else {
            throw new MessengerException(MessengerException.UNKNOWN_SERVER_ERROR,
                    "Unknown server error");
        }

    }


    //  JSON Serialization methods

    public void unserializeJSONResponseParameters(JSONObject o)
        throws JSONException
    {
        //  Mandatory fields

        setCrumb(o.getString("crumb"));
        setLoggedIn(new Integer(o.getInt("loggedIn")));

    }


}
