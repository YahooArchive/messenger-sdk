/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.data.json;

import org.json.me.JSONException;
import org.json.me.JSONObject;


public class Contact {

    private String id;
    private String network;
    private Integer authorized;
    private GroupList groups;
    private Presence presence;
    private AddressBook addressBook;
    private ClientCapabilityList clientCapabilities;
    private String uri;

    public Contact() {

        groups = new GroupList();
        presence = new Presence();
        addressBook = new AddressBook();
        clientCapabilities = new ClientCapabilityList();

    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the authorized
     */
    public Integer getAuthorized() {
        return authorized;
    }

    /**
     * @param authorized the authorized to set
     */
    public void setAuthorized(Integer authorized) {
        this.authorized = authorized;
    }

    /**
     * @return the groups
     */
    public GroupList getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(GroupList groups) {
        this.groups = groups;
    }

    /**
     * @return the presence
     */
    public Presence getPresence() {
        return presence;
    }

    /**
     * @param presence the presence to set
     */
    public void setPresence(Presence presence) {
        this.presence = presence;
    }

    /**
     * @return the addressBook
     */
    public AddressBook getAddressBook() {
        return addressBook;
    }

    /**
     * @param addressBook the addressBook to set
     */
    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
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

        setId(o.getString("id"));
        setUri(o.getString("uri"));

        //  Optional fields

        if (o.has("network"))
            setNetwork(o.getString("network"));
        if (o.has("authorized"))
            setAuthorized(new Integer(o.getInt("authorized")));
        if (o.has("groups"))
            getGroups().unserializeJSON(o.getJSONArray("groups"));
        if (o.has("presence"))
            getPresence().unserializeJSON(o.getJSONObject("presence"));
        if (o.has("addressbook"))
            getAddressBook().unserializeJSON(o.getJSONObject("addressbook"));
        if (o.has("clientCapabilities"))
            getClientCapabilities().unserializeJSON(o.getJSONArray("clientCapabilities"));

    }

    public JSONObject serializeJSON()
        throws JSONException
    {

        JSONObject o = new JSONObject();

        //  Mandatory items

        o.put("id", getId());
        o.put("uri", getUri());

        //  Optional items

        if (getNetwork() != null)
            o.put("network", getNetwork());
        if (getAuthorized() != null)
            o.put("authorized", getAuthorized());
        if (getGroups() != null)
            o.put("groups", getGroups().serializeJSON());
        if (getPresence() != null)
            o.put("presence", getPresence().serializeJSON());
        if (getAddressBook() != null)
            o.put("addressbook", getAddressBook().serializeJSON());
        if (getClientCapabilities() != null)
            o.put("clientCapabilities", getClientCapabilities().serializeJSON());

        return o;
    }


}
