/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.reqresp;

import com.yahoo.messenger.exception.HttpException;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.util.HttpUtils;
import com.yahoo.messenger.util.StringUtils;
import com.yahoo.messenger.util.YahooMessengerConstants;
import java.io.IOException;
import org.json.me.JSONException;


public class YahooMessengerGetPARTReqResp {

    //  Request parameters

    private String username;
    private String password;
    private String consumerKey;

    //  Response parameters

    private String requestToken;


    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the consumerKey
     */
    public String getConsumerKey() {
        return consumerKey;
    }

    /**
     * @param consumerKey the consumerKey to set
     */
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    /**
     * @return the requestToken
     */
    public String getRequestToken() {
        return requestToken;
    }

    /**
     * @param requestToken the requestToken to set
     */
    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }


    public void executeRequest()
        throws IOException, JSONException, HttpException, MessengerException
    {

        //  Verify mandatory parameters

        if (getUsername() == null)
            throw new MessengerException(MessengerException.NO_USERNAME_GIVEN,
                    "No username given");

        if (getPassword() == null)
            throw new MessengerException(MessengerException.NO_PASSWORD_GIVEN,
                    "No password given");

        if (getConsumerKey() == null)
            throw new MessengerException(MessengerException.NO_CONSUMER_KEY_GIVEN,
                    "No consumer key given");

        //  Create the HTTP URL

        String url = YahooMessengerConstants.partTokenGetURL + "?" +
                "&login="+getUsername()+"&passwd="+getPassword()+
                "&oauth_consumer_key="+getConsumerKey();

        //  Perform the actual call to the server

        String resultString =
                HttpUtils.performHttpGet(url);

        //  Divide each of the lines into its own string, separating by '\n'

        String[] nameValuePairs = StringUtils.tokenize(resultString, '\n', false);

        //  The request token is the first line that is returned. At this point,
        //  quickly remove the first 13 characters: the "RequestToken=" portion of
        //  the string. We didn't use the StringUtils function because it is too slow
        //  on a mobile platform for such a large string.

        String rt = nameValuePairs[0].substring(13, nameValuePairs[0].length());
        setRequestToken(rt);


    }



 
}