/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.manager;

import com.yahoo.messenger.manager.data.SessionData;
import com.yahoo.messenger.data.json.Contact;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.reqresp.YahooMessengerMessageReqResp;
import java.io.IOException;
import org.json.me.JSONException;


public class YahooMessengerMessageManager {

    private static YahooMessengerMessageManager instance;

    public static YahooMessengerMessageManager getInstance() {

        if (instance == null) {
            instance = new YahooMessengerMessageManager();
        }

        return instance;
    }

    protected YahooMessengerMessageManager() {

    }

    public void sendMessage(Contact contact, String message)
        throws IOException, MessengerException
    {

        try {

            if (contact.getNetwork() == null)
                contact.setNetwork("yahoo");
            
            SessionData currentSessionData = YahooMessengerLoginManager.getInstance().
                    getCurrentSessionData();

            YahooMessengerMessageReqResp sendMessageRequest = new YahooMessengerMessageReqResp();

            sendMessageRequest.setRequestServer(currentSessionData.getRequestServer());
            sendMessageRequest.setAuthentication(currentSessionData.getAuthentication());
            sendMessageRequest.setSessionID(currentSessionData.getSessionID());
            sendMessageRequest.setCrumb(currentSessionData.getCrumb());

            sendMessageRequest.setNetwork(contact.getNetwork());
            sendMessageRequest.setTargetID(contact.getId());
            sendMessageRequest.setMessage(message);

            sendMessageRequest.executeRequest();


        } catch (JSONException e) {
            throw new MessengerException(MessengerException.JSON_PARSER_EXCEPTION);
        }
 
    }


}
