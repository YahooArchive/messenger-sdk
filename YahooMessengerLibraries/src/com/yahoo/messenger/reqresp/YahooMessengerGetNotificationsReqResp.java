/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.reqresp;

import com.yahoo.messenger.data.notification.json.ResponseList;
import com.yahoo.messenger.exception.HttpException;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.util.YahooMessengerConstants;
import com.yahoo.messenger.util.HttpUtils;
import java.io.IOException;
import org.json.me.JSONException;
import org.json.me.JSONObject;


public class YahooMessengerGetNotificationsReqResp extends YahooMessengerBaseReqResp
{

    //  Request URI parameters

    private Integer sequence;
    private Integer count;

    //  Response parameters

    private Integer pendingMsg;
    private Integer syncStatus;
    private ResponseList responseList;


    public YahooMessengerGetNotificationsReqResp() {
        responseList = new ResponseList();
    }


    /**
     * @return the sequence
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * @param sequence the sequence to set
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return the pendingMsg
     */
    public Integer getPendingMsg() {
        return pendingMsg;
    }

    /**
     * @param pendingMsg the pendingMsg to set
     */
    public void setPendingMsg(Integer pendingMsg) {
        this.pendingMsg = pendingMsg;
    }

    /**
     * @return the syncStatus
     */
    public Integer getSyncStatus() {
        return syncStatus;
    }

    /**
     * @param syncStatus the syncStatus to set
     */
    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    /**
     * @return the responseList
     */
    public ResponseList getResponseList() {
        return responseList;
    }

    /**
     * @param responseList the responseList to set
     */
    public void setResponseList(ResponseList responseList) {
        this.responseList = responseList;
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

        if (getSequence() == null)
            throw new MessengerException(MessengerException.NO_SEQUENCE_GIVEN,
                    "No sequence given");

        //  Create the HTTP URL

        String url = "http://" + getRequestServer() + YahooMessengerConstants.notificationManagementURL +
            "?c=" + getCrumb() + "&sid=" + getSessionID() +
            "&seq=" + getSequence();

        
        //  Add optional URI parameters

        if (getCount() != null) {
            url += "&count="+getCount();
        }

        //  Add authentication parameters

        url += getAuthentication().getOAuthParameters();

        //  Perform the actual call to the server

        String responseString =
            HttpUtils.performHttpGet(
                url, getAuthentication());

        System.out.println("RESPONSE STRING FROM NOTIFICATIONS IS: " + responseString);

        JSONObject response = new JSONObject(responseString);

        if (response != null) {
            unserializeJSONResponseParameters(response);
        } else {
            throw new MessengerException(MessengerException.UNKNOWN_SERVER_ERROR,
                    "Unknown error");
        }

    }


    //  JSON Serialization methods

    public void unserializeJSONResponseParameters(JSONObject o)
        throws JSONException
    {

        //  Mandatory fields

        getResponseList().unserializeJSON(o.getJSONArray("responses"));

        //  Optional fields

        if (o.has("@pendingMsg"))
            setPendingMsg(new Integer(o.getInt("@pendingMsg")));
        if (o.has("@syncStatus"))
            setSyncStatus(new Integer(o.getInt("@syncStatus")));

    }

}
