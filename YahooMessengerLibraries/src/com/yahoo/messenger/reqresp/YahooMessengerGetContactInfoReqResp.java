/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.reqresp;

import com.yahoo.messenger.data.json.Contact;
import com.yahoo.messenger.exception.HttpException;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.util.HttpUtils;
import java.io.IOException;
import org.json.me.JSONException;
import org.json.me.JSONObject;


public class YahooMessengerGetContactInfoReqResp extends YahooMessengerBaseReqResp
{

    //  Request URI parameters

    private String uri;

    //  Request parameters

    private String fields;

    //  Return parameter
    
    private Contact contact;


    public YahooMessengerGetContactInfoReqResp() {
        contact = new Contact();
    }

    /**
     * @return the network
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param network the network to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the fields
     */
    public String getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(String fields) {
        this.fields = fields;
    }

    /**
     * @return the contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(Contact contact) {
        this.contact = contact;
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

        if (getUri() == null)
            throw new MessengerException(MessengerException.NO_NETWORK_GIVEN,
                    "No uri given");


        String url = "http://" + getUri() +
            "?c=" + getCrumb() + "&sid=" + getSessionID();

        if (getFields() != null)
            url += "&fields=" + getFields();

        //  Add authentication parameters

        url += getAuthentication().getOAuthParameters();

        //  Perform the actual call to the server

        String responseString =
            HttpUtils.performHttpGet(
                url, getAuthentication());


        JSONObject response = new JSONObject(responseString);

        if (response != null) {
            unserializeJSONResponseParameters(response.getJSONObject("contact"));

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

        getContact().unserializeJSON(o);

    }


}
