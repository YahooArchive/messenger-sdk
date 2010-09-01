/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.manager.data;

import com.yahoo.messenger.data.json.ClientCapabilityList;
import com.yahoo.messenger.util.YahooMessengerAuthentication;
import java.util.Hashtable;


public class SessionData {

    private String crumb;
    private String sessionID;
    private YahooMessengerAuthentication authentication;

    //  Request parameters

    private String notifyServerToken;
    private String fieldsBuddyList;
    private Integer loginPresenceState;
    private String loginPresenceMessage;

    //  Response parameters

    private String cookie;
    private Integer loggedIn;
    private String requestServer;
    private String notifyServer;
    private Hashtable displayInfo;
    private String primaryLoginID;
    private String[] profileLoginIDs;
    private ClientCapabilityList clientCapabilities;



    public SessionData() {

        displayInfo = new Hashtable();
        clientCapabilities = new ClientCapabilityList();
    }

    /**
     * @return the crumb
     */
    public String getCrumb() {
        return crumb;
    }

    /**
     * @param crumb the crumb to set
     */
    public void setCrumb(String crumb) {
        this.crumb = crumb;
    }

    /**
     * @return the sessionID
     */
    public String getSessionID() {
        return sessionID;
    }

    /**
     * @param sessionID the sessionID to set
     */
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    /**
     * @return the authentication
     */
    public YahooMessengerAuthentication getAuthentication() {
        return authentication;
    }

    /**
     * @param authentication the authentication to set
     */
    public void setAuthentication(YahooMessengerAuthentication authentication) {
        this.authentication = authentication;
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
     * @return the loginPresenceState
     */
    public Integer getLoginPresenceState() {
        return loginPresenceState;
    }

    /**
     * @param loginPresenceState the loginPresenceState to set
     */
    public void setLoginPresenceState(Integer loginPresenceState) {
        this.loginPresenceState = loginPresenceState;
    }

    /**
     * @return the loginPresenceMessage
     */
    public String getLoginPresenceMessage() {
        return loginPresenceMessage;
    }

    /**
     * @param loginPresenceMessage the loginPresenceMessage to set
     */
    public void setLoginPresenceMessage(String loginPresenceMessage) {
        this.loginPresenceMessage = loginPresenceMessage;
    }

    /**
     * @return the cookie
     */
    public String getCookie() {
        return cookie;
    }

    /**
     * @param cookie the cookie to set
     */
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    /**
     * @return the loggedIn
     */
    public Integer getLoggedIn() {
        return loggedIn;
    }

    /**
     * @param loggedIn the loggedIn to set
     */
    public void setLoggedIn(Integer loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * @return the requestServer
     */
    public String getRequestServer() {
        return requestServer;
    }

    /**
     * @param requestServer the requestServer to set
     */
    public void setRequestServer(String server) {
        this.requestServer = server;
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
     * @return the profileLoginIDs
     */
    public String[] getProfileLoginIDs() {
        return profileLoginIDs;
    }

    /**
     * @param profileLoginIDs the profileLoginIDs to set
     */
    public void setProfileLoginIDs(String[] profileLoginIDs) {
        this.profileLoginIDs = profileLoginIDs;
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


    
}
