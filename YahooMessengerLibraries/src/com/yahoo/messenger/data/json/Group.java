/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.data.json;

import org.json.me.JSONException;
import org.json.me.JSONObject;


public class Group {

    private String name;
    private ContactList contactList;
    private String uri;

    public Group() {
        contactList = new ContactList();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }


    //   JSON Serialization methods

    public void unserializeJSON(JSONObject o)
        throws JSONException
    {

        //  Mandatory fields

        setName(o.getString("name"));
        setUri(o.getString("uri"));

        //  Optional fields

        if (o.has("contacts"))
            getContactList().unserializeJSON(o.getJSONArray("contacts"));

    }

    public JSONObject serializeJSON()
        throws JSONException
    {

        JSONObject o = new JSONObject();

        //  Mandatory items

        o.put("name", getName());
        o.put("uri", getUri());

        //  Optional items

        if (getContactList() != null)
            o.put("contacts", getContactList().serializeJSON());

        return o;
    }


}
