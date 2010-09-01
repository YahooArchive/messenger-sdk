/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.data.json;

import org.json.me.JSONException;
import org.json.me.JSONObject;


public class ContactCollection {

    private ContactList contactList;
    private Integer start;
    private Integer count;
    private Integer total;

    public ContactCollection() {
        contactList = new ContactList();
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
    public void setContactList(ContactList contacts) {
        this.contactList = contacts;
    }

    /**
     * @return the start
     */
    public Integer getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Integer start) {
        this.start = start;
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
     * @return the total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }


    //   JSON Serialization methods

    public void unserializeJSON(JSONObject o)
        throws JSONException
    {

        //  Mandatory fields

        getContactList().unserializeJSON(o.getJSONArray("contacts"));
        setStart(new Integer(o.getInt("start")));
        setCount(new Integer(o.getInt("count")));
        setTotal(new Integer(o.getInt("total")));


    }

    public JSONObject serializeJSON()
        throws JSONException
    {

        JSONObject o = new JSONObject();

        //  Mandatory fields

        o.put("contacts", getContactList().serializeJSON());
        o.put("start", getStart());
        o.put("count", getCount());
        o.put("total", getTotal());

        return o;
    }
}
