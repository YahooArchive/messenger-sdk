/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.reqresp;

import com.yahoo.messenger.data.json.ContactCollection;
import com.yahoo.messenger.exception.HttpException;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.util.YahooMessengerConstants;
import com.yahoo.messenger.util.HttpUtils;
import java.io.IOException;
import org.json.me.JSONException;
import org.json.me.JSONObject;


public class YahooMessengerGetContactsReqResp extends YahooMessengerBaseReqResp
{

    //  Request URI parameters

    private String fields;

    //  Response parameters

    private ContactCollection contactCollection;


    public YahooMessengerGetContactsReqResp() {

        contactCollection = new ContactCollection();

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
     * @return the contactCollection
     */
    public ContactCollection getContactCollection() {
        return contactCollection;
    }

    /**
     * @param contactCollection the contactCollection to set
     */
    public void setContactCollection(ContactCollection contactCollection) {
        this.contactCollection = contactCollection;
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
                YahooMessengerConstants.contactListManagementURL +
                "?c="+getCrumb()+"&sid="+getSessionID();

        url+="&fields=%2Baddressbook&fields=%2Bpresence";
       // if (getFields() != null) {
       //     url += "&fields="+getFields();
       // }

        //  Add authentication parameters

        url += getAuthentication().getOAuthParameters();

        //  Perform the actual call to the server

        String responseString =
            HttpUtils.performHttpGet(
                url, getAuthentication());
        
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

        getContactCollection().unserializeJSON(o);

    }


}
