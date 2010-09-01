/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.reqresp;

import com.yahoo.messenger.data.json.ClientCapabilityList;
import com.yahoo.messenger.data.json.ContactList;
import com.yahoo.messenger.exception.HttpException;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.util.YahooMessengerConstants;
import com.yahoo.messenger.util.HttpUtils;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;


public class YahooMessengerCreateSessionReqResp extends YahooMessengerBaseReqResp {


    //  Request URI parameters

    private String fieldsBuddyList;
    private String notifyServerToken;

    //  Request body parameters

    private Integer presenceState;
    private String presenceMessage;
    private ClientCapabilityList clientCapabilities;

    //  Response parameters
    
    private ContactList contactList;
    private String primaryLoginID;
    private String[] profileLoginIDList;
    private String server;
    private String notifyServer;
    private Hashtable displayInfo;


    public YahooMessengerCreateSessionReqResp() {

        //  Instantiate only the response parameters that are
        //  objects other than String.

        contactList = new ContactList();
        displayInfo = new Hashtable();

    }



    /**
     * @return the fieldsBuddyList
     */
    public String getFieldsBuddyList() {
        return fieldsBuddyList;
    }

    /**
     * @param fieldsBuddyList the fieldsBuddyList to set
     */
    public void setFieldsBuddyList(String fieldsBuddyList) {
        this.fieldsBuddyList = fieldsBuddyList;
    }

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

    /**
     * @return the presenceState
     */
    public Integer getPresenceState() {
        return presenceState;
    }

    /**
     * @param presenceState the presenceState to set
     */
    public void setPresenceState(Integer presenceState) {
        this.presenceState = presenceState;
    }

    /**
     * @return the presenceMessage
     */
    public String getPresenceMessage() {
        return presenceMessage;
    }

    /**
     * @param presenceMessage the presenceMessage to set
     */
    public void setPresenceMessage(String presenceMessage) {
        this.presenceMessage = presenceMessage;
    }

    /**
     * @return the clientCapabilities
     */
    public ClientCapabilityList getClientCapabilities() {
        return clientCapabilities;
    }

    /**
     * @param clientCapabilities the clientCapabilities to set
     */
    public void setClientCapabilities(ClientCapabilityList clientCapabilities) {
        this.clientCapabilities = clientCapabilities;
    }

    /**
     * @return the contactList
     */
    public ContactList getContactList() {
        return contactList;
    }

    /**
     * @param contactList the contactList to set
     */
    public void setContactList(ContactList contactList) {
        this.contactList = contactList;
    }

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
     * @return the profileLoginIDList
     */
    public String[] getProfileLoginIDList() {
        return profileLoginIDList;
    }

    /**
     * @param profileLoginIDList the profileLoginIDList to set
     */
    public void setProfileLoginIDList(String[] profileLoginIDList) {
        this.profileLoginIDList = profileLoginIDList;
    }

    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @return the notifyServer
     */
    public String getNotifyServer() {
        return notifyServer;
    }

    /**
     * @param notifyServer the notifyServer to set
     */
    public void setNotifyServer(String notifyServer) {
        this.notifyServer = notifyServer;
    }

    /**
     * @return the displayInfo
     */
    public Hashtable getDisplayInfo() {
        return displayInfo;
    }

    /**
     * @param displayInfo the displayInfo to set
     */
    public void setDisplayInfo(Hashtable displayInfo) {
        this.displayInfo = displayInfo;
    }


    public void executeRequest()
        throws IOException, JSONException, HttpException, MessengerException
    {

        //  Verify mandatory parameters

        if (getCrumb() == null)
            throw new MessengerException(MessengerException.NO_CRUMB_GIVEN,
                    "No crumb given");

        //  Create the HTTP URI

        String url = "http://" + getRequestServer() +
                YahooMessengerConstants.sessionManagementURL +
                "?c="+getCrumb();

        //  Add optional URI parameters

        if (getFieldsBuddyList() != null) {
            url += "&fieldBuddyList="+getFieldsBuddyList();
        }
        if (getNotifyServerToken() != null) {
            url += "&notifyServerToken="+getNotifyServerToken();
        }

        //  Add authentication parameters

        url += getAuthentication().getOAuthParameters();

        //  Perform the actual call to the server

        String responseString =
            HttpUtils.performHttpPost(
                url, getAuthentication(), serializeJSONRequestParameters().toString());
        
        //  Parse the response

        JSONObject response = new JSONObject(responseString);

        if (response != null) {
            unserializeJSONResponseParameters(response);
        } else {
            throw new MessengerException(MessengerException.UNKNOWN_SERVER_ERROR,
                    "Unknown server error");
        }

    }


    //  JSON serialization methods

    public JSONObject serializeJSONRequestParameters()
        throws JSONException
    {
        JSONObject o = new JSONObject();

        //  Optional fields

        if (getPresenceState() != null)
            o.put("presenceState", getPresenceState());
        if (getPresenceMessage() != null)
            o.put("presenceMessage", getPresenceMessage());
        if (getClientCapabilities() != null)
            o.put("clientCapabilities", getClientCapabilities().serializeJSON());

        return o;

    }

    public void unserializeJSONResponseParameters(JSONObject o)
        throws JSONException
    {
        //  Mandatory fields

        setSessionID(o.getString("sessionId"));
        setPrimaryLoginID(o.getString("primaryLoginId"));
        setServer(o.getString("server"));
        setNotifyServer(o.getString("notifyServer"));

        JSONObject displayInfoJSONObject = o.getJSONObject("displayInfo");
        Enumeration e = displayInfoJSONObject.keys();
        while (e.hasMoreElements()) {
            String key = (String)e.nextElement();
            String value = displayInfoJSONObject.getString(key);
            getDisplayInfo().put(key, value);
        }


        //  Optional fields

        if (o.has("profileLoginIds"))
        {
            JSONArray profileLoginIdsJSONObject = o.getJSONArray("profileLoginIds");
            String[] profileLoginIDs = new String[profileLoginIdsJSONObject.length()];
            for (int i = 0; i < profileLoginIdsJSONObject.length(); i++) {
                profileLoginIDs[i] = profileLoginIdsJSONObject.getString(i);
            }
            setProfileLoginIDList(profileLoginIDs);
        }

        if (o.has("contacts"))
        {
            getContactList().unserializeJSON(o.getJSONArray("contacts"));
        }

    }


}
