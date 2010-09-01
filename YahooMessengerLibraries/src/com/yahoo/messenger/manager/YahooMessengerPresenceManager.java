/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.manager;

import com.yahoo.messenger.manager.data.SessionData;
import com.yahoo.messenger.data.json.Presence;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.reqresp.YahooMessengerGetPresenceReqResp;
import com.yahoo.messenger.reqresp.YahooMessengerSetPresenceReqResp;
import java.io.IOException;
import org.json.me.JSONException;


public class YahooMessengerPresenceManager {

    private static YahooMessengerPresenceManager instance;

    private Presence currentPresence;

    public static YahooMessengerPresenceManager getInstance() {

        if (instance == null) {
            instance = new YahooMessengerPresenceManager();
        }

        return instance;
    }

    protected YahooMessengerPresenceManager() {
        currentPresence = new Presence();
    }

    /**
     * @return the currentPresence
     */
    public Presence getCurrentPresence() {
        return currentPresence;
    }

    /**
     * @param currentPresence the currentPresence to set
     */
    public void setCurrentPresence(Presence newPresence) {
        this.currentPresence = newPresence;
    }


    public Presence getPresenceInformation()
        throws IOException, MessengerException
    {

        try {

            SessionData currentSessionData = YahooMessengerLoginManager.getInstance().
                    getCurrentSessionData();

            YahooMessengerGetPresenceReqResp getPresenceRequest = new YahooMessengerGetPresenceReqResp();

            getPresenceRequest.setRequestServer(currentSessionData.getRequestServer());
            getPresenceRequest.setAuthentication(currentSessionData.getAuthentication());
            getPresenceRequest.setSessionID(currentSessionData.getSessionID());
            getPresenceRequest.setCrumb(currentSessionData.getCrumb());

            getPresenceRequest.executeRequest();

            currentPresence = getPresenceRequest.getPresence();

            return currentPresence;

        } catch (JSONException e) {
            throw new MessengerException(MessengerException.JSON_PARSER_EXCEPTION);
        }
 
    }

    public void setPresenceInformation(Presence presence)
        throws IOException, MessengerException
    {

        try {

            if (presence.getPresenceMessage().equals(""))
                presence.setPresenceMessage(null);

            SessionData currentSessionData = YahooMessengerLoginManager.getInstance().
                    getCurrentSessionData();

            YahooMessengerSetPresenceReqResp setPresenceRequest = new YahooMessengerSetPresenceReqResp();

            setPresenceRequest.setAuthentication(currentSessionData.getAuthentication());
            setPresenceRequest.setSessionID(currentSessionData.getSessionID());
            setPresenceRequest.setCrumb(currentSessionData.getCrumb());

            setPresenceRequest.setPresence(presence);

            setPresenceRequest.executeRequest();

            this.currentPresence = presence;

        } catch (JSONException e) {
            throw new MessengerException(MessengerException.JSON_PARSER_EXCEPTION);
        }

    }




}
