/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.manager;

import com.yahoo.messenger.beans.PropertyChangeListener;
import com.yahoo.messenger.beans.PropertyChangeSupport;
import com.yahoo.messenger.manager.data.SessionData;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.manager.data.NotificationData;
import com.yahoo.messenger.reqresp.YahooMessengerGetNotificationsReqResp;
import java.io.IOException;
import org.json.me.JSONException;


public class YahooMessengerNotificationManager {

    public static final String NOTIFICATION = "Notification";

    private static YahooMessengerNotificationManager instance;
    private PropertyChangeSupport propertyChangeSupport;

    public static YahooMessengerNotificationManager getInstance() {

        if (instance == null) {
            instance = new YahooMessengerNotificationManager();
        }

        return instance;
    }

    protected YahooMessengerNotificationManager() {

        propertyChangeSupport = new PropertyChangeSupport();

        Thread t = new Thread() {

            public boolean alive = true;

            public void run() {
                while (alive) {
                    try {
                        Thread.sleep(5000);    //  Every five second, poll the server for new notifications
                        getNewNotifications();
                    } catch (InterruptedException e) {
                        //  Do nothing
                    } catch (IOException e) {

                    } catch (MessengerException e) {

                    }
                }
            }
        };

        t.start();
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public void getNewNotifications()
        throws IOException, MessengerException
    {

        try {

            SessionData currentSessionData = YahooMessengerLoginManager.getInstance().
                    getCurrentSessionData();

            YahooMessengerGetNotificationsReqResp getNotificationsRequest = new YahooMessengerGetNotificationsReqResp();

            getNotificationsRequest.setRequestServer(currentSessionData.getRequestServer());
            getNotificationsRequest.setAuthentication(currentSessionData.getAuthentication());
            getNotificationsRequest.setSessionID(currentSessionData.getSessionID());
            getNotificationsRequest.setCrumb(currentSessionData.getCrumb());
            getNotificationsRequest.setSequence(new Integer(0));
            getNotificationsRequest.setCount(new Integer(100));

            getNotificationsRequest.executeRequest();

            NotificationData n = new NotificationData();
            n.setPendingMsg(getNotificationsRequest.getPendingMsg());
            n.setSyncStatus(getNotificationsRequest.getSyncStatus());
            n.setResponseList(getNotificationsRequest.getResponseList());

            propertyChangeSupport.firePropertyChange(NOTIFICATION, null, n);


        } catch (JSONException e) {
            throw new MessengerException(MessengerException.JSON_PARSER_EXCEPTION);
        }
 
    }


}
