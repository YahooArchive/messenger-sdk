/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.data.notification.json;

import org.json.me.JSONException;
import org.json.me.JSONObject;


public class BuddyInfoContact implements Response {

    private String sender;
    private Integer presenceState;
    private String presenceMessage;
    private String clientType;
    private Integer customDNDStatus;
    private Integer clientCapabilities;
    private String avatarPreference;
    private String avatarHash;
    private String checksum;
    private String network;

   /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
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
     * @return the clientType
     */
    public String getClientType() {
        return clientType;
    }

    /**
     * @param clientType the clientType to set
     */
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    /**
     * @return the customDNDStatus
     */
    public Integer getCustomDNDStatus() {
        return customDNDStatus;
    }

    /**
     * @param customDNDStatus the customDNDStatus to set
     */
    public void setCustomDNDStatus(Integer customDNDStatus) {
        this.customDNDStatus = customDNDStatus;
    }

    /**
     * @return the clientCapabilities
     */
    public Integer getClientCapabilities() {
        return clientCapabilities;
    }

    /**
     * @param clientCapabilities the clientCapabilities to set
     */
    public void setClientCapabilities(Integer clientCapabilities) {
        this.clientCapabilities = clientCapabilities;
    }

    /**
     * @return the avatarPreference
     */
    public String getAvatarPreference() {
        return avatarPreference;
    }

    /**
     * @param avatarPreference the avatarPreference to set
     */
    public void setAvatarPreference(String avatarPreference) {
        this.avatarPreference = avatarPreference;
    }

    /**
     * @return the avatarHash
     */
    public String getAvatarHash() {
        return avatarHash;
    }

    /**
     * @param avatarHash the avatarHash to set
     */
    public void setAvatarHash(String avatarHash) {
        this.avatarHash = avatarHash;
    }

    /**
     * @return the checksum
     */
    public String getChecksum() {
        return checksum;
    }

    /**
     * @param checksum the checksum to set
     */
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    /**
     * @return the network
     */
    public String getNetwork() {
        return network;
    }

    /**
     * @param network the network to set
     */
    public void setNetwork(String network) {
        this.network = network;
    }

    //   JSON Serialization methods

    public void unserializeJSON(JSONObject o)
        throws JSONException
    {

        //  Mandatory fields

        setSender(o.getString("sender"));
        setPresenceState(new Integer(o.getInt("presenceState")));
        setClientCapabilities(new Integer(o.getInt("clientCapabilities")));

        //  Optional fields

        if (o.has("presenceMessage"))
            setPresenceMessage(o.getString("presenceMessage"));
        if (o.has("clientType"))
            setClientType(o.getString("clientType"));
        if (o.has("customDNDStatus"))
            setCustomDNDStatus(new Integer(o.getInt("customDNDStatus")));
        if (o.has("avatarPreference"))
            setAvatarPreference(o.getString("avatarPreference"));
        if (o.has("avatarHash"))
            setAvatarHash(o.getString("avatarHash"));
        if (o.has("checksum"))
            setChecksum(o.getString("checksum"));
        if (o.has("network"))
            setNetwork(o.getString("network"));

    }

 


}
