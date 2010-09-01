/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.data.notification.json;

import org.json.me.JSONException;
import org.json.me.JSONObject;


public class Message implements Response {

    private Integer sequence;
    private String sender;
    private String network;
    private String receiver;
    private Integer timeStamp;
    private String msg;
    private String hash;
    private Integer errorCode;

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
     * @return the receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * @return the timeStamp
     */
    public Integer getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(Integer timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @return the errorCode
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }


    //   JSON Serialization methods

    public void unserializeJSON(JSONObject o)
        throws JSONException
    {

        //  Mandatory fields

        setSequence(new Integer(o.getInt("sequence")));
        setSender(o.getString("sender"));
        setReceiver(o.getString("receiver"));
        setMsg(o.getString("msg"));

        //  Optional fields

        if (o.has("network"))
            setNetwork(o.getString("network"));
        if (o.has("timeStamp"))
            setTimeStamp(new Integer(o.getInt("timeStamp")));
        if (o.has("hash"))
            setHash(o.getString("hash"));
        if (o.has("errorCode"))
            setErrorCode(new Integer(o.getInt("errorCode")));

    }


}
