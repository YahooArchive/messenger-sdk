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
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;


public class YahooMessengerValidateSessionReqResp extends YahooMessengerBaseReqResp {

    
    //  Response parameters
    
    private String primaryLoginID;
    private String[] profileLoginIDs;


    /**
     * @return the primaryLoginID
     */
    public String getPrimaryLoginID() {
        return primaryLoginID;
    }

    /**
     * @param primaryLoginID the primaryLoginID to set
     */
    public void setPrimaryLoginID(String primaryLoginID) {
        this.primaryLoginID = primaryLoginID;
    }

    /**
     * @return the profLoginIDs
     */
    public String[] getProfileLoginIDs() {
        return profileLoginIDs;
    }

    /**
     * @param profLoginIDs the profLoginIDs to set
     */
    public void setProfileLoginIDs(String[] profileLoginIDs) {
        this.profileLoginIDs = profileLoginIDs;
    }



    public void executeRequest()
        throws IOException, JSONException, HttpException, MessengerException
    {
        //  Create the HTTP URL

        String url = "http://" + getRequestServer() +
                YahooMessengerConstants.sessionManagementURL +
                "?c="+getCrumb()+"&sid="+getSessionID();

        //  Add authentication parameters

        url += getAuthentication().getOAuthParameters();

        //  Perform the actual call to the server

        String responseString =
                HttpUtils.performHttpGet(
                    url, getAuthentication());

        //  Parse the JSON response

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

        //  Mandatory JSON responses

        setPrimaryLoginID(o.getString("primaryLoginId"));

        //  Optional JSON responses

        if (o.has("profileLoginIds"))
        {
            JSONArray profileLoginIdsJSONArray = o.getJSONArray("profileLoginIds");
            String[] profLoginIDs = new String[profileLoginIdsJSONArray.length()];
            for (int i = 0; i < profileLoginIdsJSONArray.length(); i++) {
                profLoginIDs[i] = profileLoginIdsJSONArray.getString(i);
            }
            setProfileLoginIDs(profLoginIDs);
        }

    }


}
