/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.manager;

import com.yahoo.messenger.reqresp.YahooMessengerCreateSessionReqResp;
import com.yahoo.messenger.reqresp.YahooMessengerGetCrumbReqResp;
import com.yahoo.messenger.manager.data.SessionData;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.reqresp.YahooMessengerDeleteSessionReqResp;
import com.yahoo.messenger.reqresp.YahooMessengerExchangePARTForOAuthReqResp;
import com.yahoo.messenger.reqresp.YahooMessengerGetPARTReqResp;
import com.yahoo.messenger.reqresp.YahooMessengerKeepAliveSessionReqResp;
import com.yahoo.messenger.reqresp.YahooMessengerValidateSessionReqResp;
import com.yahoo.messenger.util.YahooMessengerAuthentication;
import com.yahoo.messenger.util.YahooMessengerYTLoginUtilities;
import java.io.IOException;
import org.json.me.JSONException;


public class YahooMessengerLoginManager {


    private static YahooMessengerLoginManager instance;

    private String currentUsername;
    private SessionData currentSessionData;

    public static YahooMessengerLoginManager getInstance() {

        if (instance == null) {
            instance = new YahooMessengerLoginManager();
        }

        return instance;
    }

    protected YahooMessengerLoginManager() {
        currentSessionData = new SessionData();
    }

    /**
     * @return the currentSessionData
     */
    public SessionData getCurrentSessionData() {
        return currentSessionData;
    }

    /**
     * @param currentSessionData the currentSessionData to set
     */
    public void setCurrentSessionData(SessionData currentSessionData) {
        this.currentSessionData = currentSessionData;
    }

    /**
     * @return the currentUsername
     */
    public String getCurrentUsername() {
        return currentUsername;
    }

    /**
     * @param currentUsername the currentUsername to set
     */
    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public void performLoginOAuth(String username, String password, String consumerKey, String consumerSecret)
        throws IOException, MessengerException
    {

        String partToken = "";

        //  First, connect to the OAuth server and request a Pre-Approved Request Token (PART)
        //  given the username, password, and consumerKey that was assigned to you by
        //  developer.yahoo.com.

        try {

            YahooMessengerGetPARTReqResp getPartRequest = new YahooMessengerGetPARTReqResp();
            getPartRequest.setUsername(username);
            getPartRequest.setPassword(password);
            getPartRequest.setConsumerKey(consumerKey);

            getPartRequest.executeRequest();

            partToken = getPartRequest.getRequestToken();

        } catch (JSONException e) {
            throw new MessengerException(MessengerException.JSON_PARSER_EXCEPTION);
        }

        //  Next, exchange the PART for a set of OAuth authentication parameters, which will
        //  be stored in a YahooMessengerAuthentication object. This object will be used
        //  to append URI authorization params to each Messenger IM API call.

        try {

            YahooMessengerExchangePARTForOAuthReqResp exchangePartReqResp = new YahooMessengerExchangePARTForOAuthReqResp();
            exchangePartReqResp.setConsumerKey(consumerKey);
            exchangePartReqResp.setSignatureMethod("PLAINTEXT");
            exchangePartReqResp.setNonce("123456");
            exchangePartReqResp.setTimestamp((System.currentTimeMillis()/1000)+"");  // Seconds, not milliseconds
            exchangePartReqResp.setSignature(consumerSecret+"%26");
            exchangePartReqResp.setVerifier("Boasvd78");
            exchangePartReqResp.setVersion("1.0");
            exchangePartReqResp.setToken(partToken);

            exchangePartReqResp.executeRequest();

            currentSessionData.setAuthentication(exchangePartReqResp.getAuthenticationToken());


        } catch (JSONException e) {
            throw new MessengerException(MessengerException.JSON_PARSER_EXCEPTION);
        }


        //  Next, perform a login to get the crumb, which is stored in the
        //  currentSessionData


        try {

            YahooMessengerGetCrumbReqResp getCrumbRequest = new YahooMessengerGetCrumbReqResp();
            getCrumbRequest.setAuthentication(currentSessionData.getAuthentication());

            getCrumbRequest.executeRequest();

            currentSessionData.setCrumb(getCrumbRequest.getCrumb());
            currentSessionData.setLoggedIn(getCrumbRequest.getLoggedIn());

        } catch (JSONException e) {
            throw new MessengerException(MessengerException.JSON_PARSER_EXCEPTION);
        }


        //  If the loggedIn value that comes back is set to 1, it means that the user
        //  is already logged in somewhere else. It is up to the client implementation
        //  to handle this as it sees fit.

        if (currentSessionData.getLoggedIn().intValue() == 1) {
            //  Do something or nothing
        }

        //  Next, obtain a session ID for the user, as well as other login data.

        try {

            YahooMessengerCreateSessionReqResp createSessionRequest = new YahooMessengerCreateSessionReqResp();

            createSessionRequest.setAuthentication(currentSessionData.getAuthentication());
            createSessionRequest.setCrumb(currentSessionData.getCrumb());
            createSessionRequest.setPresenceState(new Integer(0));

            createSessionRequest.executeRequest();

            currentSessionData.setSessionID(createSessionRequest.getSessionID());
            currentSessionData.setRequestServer(createSessionRequest.getServer());
            currentSessionData.setNotifyServer(createSessionRequest.getNotifyServer());

        } catch (JSONException e) {
            throw new MessengerException(MessengerException.JSON_PARSER_EXCEPTION);
        }

        setCurrentUsername(username);

    }


    public void performLoginYTCookie(String username, String password)
        throws IOException, MessengerException
    {

        //  Contact the login server and attempt to get a pw token

        String token = YahooMessengerYTLoginUtilities.
                performLoginGetPwToken(username, password);

        //  Once we have the pw token, login to get a TY cookie

        YahooMessengerYTLoginUtilities.
                performLoginGetYTCookie(currentSessionData, token);

        //  Then perform a login to get the crumb, which is stored in the
        //  currentSessionData

        currentSessionData.setAuthentication(
                new YahooMessengerAuthentication(currentSessionData.getCookie()));

        try {

            YahooMessengerGetCrumbReqResp getCrumbRequest = new YahooMessengerGetCrumbReqResp();
            getCrumbRequest.setAuthentication(currentSessionData.getAuthentication());

            getCrumbRequest.executeRequest();

            currentSessionData.setCrumb(getCrumbRequest.getCrumb());
            currentSessionData.setLoggedIn(getCrumbRequest.getLoggedIn());

        } catch (JSONException e) {
            throw new MessengerException(MessengerException.JSON_PARSER_EXCEPTION);
        }


        //  If the loggedIn value that comes back is set to 1, it means that the user
        //  is already logged in somewhere else. It is up to the client implementation
        //  to handle this as it sees fit.
        
        if (currentSessionData.getLoggedIn().intValue() == 1) {
            //  Do something or nothing
        }

        //  Next, obtain a session ID for the user, as well as other login data.

        try {

            YahooMessengerCreateSessionReqResp createSessionRequest = new YahooMessengerCreateSessionReqResp();

            createSessionRequest.setAuthentication(currentSessionData.getAuthentication());
            createSessionRequest.setCrumb(currentSessionData.getCrumb());
            createSessionRequest.setPresenceState(new Integer(0));

            createSessionRequest.executeRequest();

            currentSessionData.setSessionID(createSessionRequest.getSessionID());
            currentSessionData.setRequestServer(createSessionRequest.getServer());
            currentSessionData.setNotifyServer(createSessionRequest.getNotifyServer());

        } catch (JSONException e) {
            throw new MessengerException(MessengerException.JSON_PARSER_EXCEPTION);
        }

        setCurrentUsername(username);

    }

    public void performLogout()
        throws IOException, MessengerException
    {

        try {

            YahooMessengerDeleteSessionReqResp deleteSessionRequest = new YahooMessengerDeleteSessionReqResp();
            deleteSessionRequest.setRequestServer(currentSessionData.getRequestServer());
            deleteSessionRequest.setAuthentication(currentSessionData.getAuthentication());
            deleteSessionRequest.setSessionID(currentSessionData.getSessionID());
            deleteSessionRequest.setCrumb(currentSessionData.getCrumb());

            deleteSessionRequest.executeRequest();

        } catch (JSONException e) {
            throw new MessengerException(MessengerException.JSON_PARSER_EXCEPTION);
        }

        setCurrentUsername(null);

    }

    public void validateLogin()
        throws IOException, MessengerException
    {

        try {

            YahooMessengerValidateSessionReqResp validateSessionRequest = new YahooMessengerValidateSessionReqResp();

            validateSessionRequest.setRequestServer(currentSessionData.getRequestServer());
            validateSessionRequest.setAuthentication(currentSessionData.getAuthentication());
            validateSessionRequest.setSessionID(currentSessionData.getSessionID());
            validateSessionRequest.setCrumb(currentSessionData.getCrumb());

            validateSessionRequest.executeRequest();

        } catch (JSONException e) {
            throw new MessengerException(MessengerException.JSON_PARSER_EXCEPTION);
        }
    }

    public void sendKeepAlive()
        throws IOException, MessengerException
    {

        try {

            YahooMessengerKeepAliveSessionReqResp keepAliveSessionRequest = new YahooMessengerKeepAliveSessionReqResp();

            keepAliveSessionRequest.setRequestServer(currentSessionData.getRequestServer());
            keepAliveSessionRequest.setAuthentication(currentSessionData.getAuthentication());
            keepAliveSessionRequest.setSessionID(currentSessionData.getSessionID());
            keepAliveSessionRequest.setCrumb(currentSessionData.getCrumb());

            keepAliveSessionRequest.executeRequest();

        } catch (JSONException e) {
            throw new MessengerException(MessengerException.JSON_PARSER_EXCEPTION);
        }
    }


}
