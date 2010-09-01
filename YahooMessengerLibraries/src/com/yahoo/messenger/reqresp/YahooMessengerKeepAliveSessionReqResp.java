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


public class YahooMessengerKeepAliveSessionReqResp extends YahooMessengerBaseReqResp {


    //  Request uri parameters

    private String notifyServerToken;


     /**
     * @return the notifyServerToken
     */
    public String getNotifyServerToken() {
        return notifyServerToken;
    }

    /**
     * @param notifyServerToken the notifyServerToken to set
     */
    public void setNotifyServerToken(String notifyServerToken) {
        this.notifyServerToken = notifyServerToken;
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
                YahooMessengerConstants.sessionManagementURL +
                "?"+YahooMessengerConstants.putSuffix +
                "&c="+getCrumb()+"&sid="+getSessionID();

        //  Perform the actual call to the server

        if (getNotifyServerToken() != null) {
            url += "&notifyServerToken="+getNotifyServerToken();
        }

        //  Add authentication parameters

        url += getAuthentication().getOAuthParameters();

        //  Perform the actual call to the server

        String resultString =
                HttpUtils.performHttpGet(url,
                    getAuthentication());


    }


}
