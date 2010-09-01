/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.data.notification.json;

import org.json.me.JSONException;
import org.json.me.JSONObject;


public class BuddyInfo implements Response {

    private Integer sequence;
    private String network;
    private BuddyInfoContactList buddyInfoContactList;

    public BuddyInfo() {
        buddyInfoContactList = new BuddyInfoContactList();
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

    /**
     * @return the buddyInfoContact
     */
    public BuddyInfoContactList getBuddyInfoContactList() {
        return buddyInfoContactList;
    }

    /**
     * @param buddyInfoContact the buddyInfoContact to set
     */
    public void setBuddyInfoContactList(BuddyInfoContactList buddyInfoContactList) {
        this.buddyInfoContactList = buddyInfoContactList;
    }


    //   JSON Serialization methods

    public void unserializeJSON(JSONObject o)
        throws JSONException
    {

        //  Mandatory fields

        setSequence(new Integer(o.getInt("sequence")));
        getBuddyInfoContactList().unserializeJSON(o.getJSONArray("contact"));

        //  Optional fields

        if (o.has("network"))
            setNetwork(o.getString("network"));

    }


}
