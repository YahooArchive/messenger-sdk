/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.manager;

import com.yahoo.messenger.beans.IndexedPropertyChangeSupport;
import com.yahoo.messenger.manager.data.SessionData;
import com.yahoo.messenger.data.json.Contact;
import com.yahoo.messenger.data.json.ContactCollection;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.reqresp.YahooMessengerGetContactInfoReqResp;
import com.yahoo.messenger.reqresp.YahooMessengerGetContactsReqResp;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.json.me.JSONException;


public class YahooMessengerContactListManager {


    public static final String CONTACT_LIST_CONTACT_ADDED = "ContactListContactAdded";
    public static final String CONTACT_LIST_CONTACT_REMOVED = "ContactListContactRemoved";
    public static final String CONTACT_LIST_CONTACT_CHANGED = "ContactListContactChanged";


    private static YahooMessengerContactListManager instance;

    //private Contact[] contacts;

    private Hashtable contactsHashtable;
    private IndexedPropertyChangeSupport propertyChangeSupport;

    public static YahooMessengerContactListManager getInstance() {

        if (instance == null) {
            instance = new YahooMessengerContactListManager();
        }

        return instance;
    }

    protected YahooMessengerContactListManager() {

        YahooMessengerNotificationManager.getInstance();

        contactsHashtable = new Hashtable();
        propertyChangeSupport = new IndexedPropertyChangeSupport();
        
    }

    public void setContact(int index, Contact newContact) {

        Contact oldContact = (Contact)contactsHashtable.get(new Integer(index));

        contactsHashtable.put(new Integer(index), newContact);

        if (oldContact == null)
            propertyChangeSupport.fireIndexedPropertyChange(CONTACT_LIST_CONTACT_ADDED, index, null, newContact);
        else
            propertyChangeSupport.fireIndexedPropertyChange(CONTACT_LIST_CONTACT_CHANGED, index, oldContact, newContact);

    }

    public void removeContactAtIndex(int index) {

        Contact c = (Contact)contactsHashtable.remove(new Integer(index));

        if (c == null)
            return;

        propertyChangeSupport.fireIndexedPropertyChange(CONTACT_LIST_CONTACT_REMOVED, index, c, null);

    }

    public Contact[] getContactList()
        throws IOException, MessengerException
    {

        try {

            SessionData sessionData = YahooMessengerLoginManager.getInstance().
                    getCurrentSessionData();

            YahooMessengerGetContactsReqResp getContactsRequest = new YahooMessengerGetContactsReqResp();

            getContactsRequest.setRequestServer(sessionData.getRequestServer());
            getContactsRequest.setAuthentication(sessionData.getAuthentication());
            getContactsRequest.setSessionID(sessionData.getSessionID());
            getContactsRequest.setCrumb(sessionData.getCrumb());

            getContactsRequest.executeRequest();

            ContactCollection contactCollection = getContactsRequest.getContactCollection();
            Contact[] contactList = contactCollection.getContactList().getContacts();

            try {
            for (int i = 0; i < contactList.length; i++)
                setContact(i, contactList[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return contactList;

        } catch (JSONException e) {
            throw new MessengerException(MessengerException.JSON_PARSER_EXCEPTION);
        }

    }

    public Contact getContact(String uri)
        throws IOException, MessengerException
    {

        try {

            SessionData sessionData = YahooMessengerLoginManager.getInstance().
                    getCurrentSessionData();

            YahooMessengerGetContactInfoReqResp getContactInfoRequest = new YahooMessengerGetContactInfoReqResp();

            getContactInfoRequest.setRequestServer(sessionData.getRequestServer());
            getContactInfoRequest.setAuthentication(sessionData.getAuthentication());
            getContactInfoRequest.setSessionID(sessionData.getSessionID());
            getContactInfoRequest.setCrumb(sessionData.getCrumb());
            getContactInfoRequest.setUri(uri);

            getContactInfoRequest.executeRequest();

            return getContactInfoRequest.getContact();

        } catch (JSONException e) {
            throw new MessengerException(MessengerException.JSON_PARSER_EXCEPTION);
        }

    }

}
