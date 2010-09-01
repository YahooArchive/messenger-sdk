/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.ui;

import com.sun.lwuit.ComboBox;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.DefaultListModel;
import com.sun.lwuit.list.ListModel;
import com.yahoo.messenger.beans.PropertyChangeListener;
import com.yahoo.messenger.data.json.Contact;
import com.yahoo.messenger.data.json.Presence;
import com.yahoo.messenger.data.notification.json.BuddyInfo;
import com.yahoo.messenger.data.notification.json.BuddyInfoContact;
import com.yahoo.messenger.data.notification.json.Response;
import com.yahoo.messenger.manager.YahooMessengerNotificationManager;
import com.yahoo.messenger.manager.data.NotificationData;
import com.yahoo.messenger.ui.task.GetContactCancellableTask;
import com.yahoo.messenger.ui.task.GetContactListCancellableTask;
import com.yahoo.messenger.ui.task.GetPresenceCancellableTask;
import com.yahoo.messenger.ui.task.SetPresenceCancellableTask;
import java.io.IOException;
import org.netbeans.microedition.util.SimpleCancellableTask;


public class MessengerContactsScreen implements ActionListener, PropertyChangeListener {

    private MessengerLWUITMidlet midlet;

    private Form ymGetContactsScreenWaitForm;
    private Label ymGetContactsScreenWaitLabel;

    private Form ymContactListForm;
    private Container ymContactPresenceContainer;
    private Container ymContactContactContainer;
    private ComboBox ymContactListPresenceComboBox;
    private Label ymContactListPresenceMessage;
    private TextArea ymContactListPresenceTextArea;
    private Label ymContactListPresenceFriendsLabel;

    private List ymContactList;


    private Contact[] contacts;
    private ListModel listModel;

    private int ymPresenceState = Presence.PRESENCE_STATE_OFFLINE;
    private String ymPresenceMessage;

    public MessengerContactsScreen(MessengerLWUITMidlet midlet) {

        this.midlet = midlet;

        YahooMessengerNotificationManager manager =
                YahooMessengerNotificationManager.getInstance();
        manager.addPropertyChangeListener(this);

    }



    public void displayYahooMessengerGetContactsWaitScreen() {

        getYahooMessengerGetContactsWaitForm().show();

    }

    public Form getYahooMessengerGetContactsWaitForm() {

        if (ymGetContactsScreenWaitForm == null) {

            ymGetContactsScreenWaitForm = new Form("Retrieving Contact List");

            ymGetContactsScreenWaitForm.addComponent(getYahooMessengerGetContactsFormWaitLabel());
            ymGetContactsScreenWaitForm.addCommand(midlet.getYahooMessengerCancelCommand());

            ymGetContactsScreenWaitForm.setCommandListener(this);

        }

        return ymGetContactsScreenWaitForm;

    }

    public Label getYahooMessengerGetContactsFormWaitLabel() {

        if (ymGetContactsScreenWaitLabel == null) {
            ymGetContactsScreenWaitLabel = new Label("Retrieving contact list...");
        }

        return ymGetContactsScreenWaitLabel;

    }



    public void displayYahooMessengerContactListScreen() {

        getYahooMessengerContactListForm().show();

    }

    public Form getYahooMessengerContactListForm() {

        if (ymContactListForm == null) {

            ymContactListForm = new Form("Yahoo! Messenger");

            ymContactListForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            ymContactListForm.addComponent(getYahooMessengerPresenceContainer());
            ymContactListForm.addComponent(getYahooMessengerContactContainer());
            ymContactListForm.addCommand(midlet.getYahooMessengerExitCommand());

            ymContactListForm.setCommandListener(this);

        }

        return ymContactListForm;
    }

    public Container getYahooMessengerPresenceContainer() {

        if (ymContactPresenceContainer == null) {

            ymContactPresenceContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            ymContactPresenceContainer.addComponent(getYahooMessengerPresenceComboBox());
            ymContactPresenceContainer.addComponent(getYahooMessengerPresenceMessageLabel());
            ymContactPresenceContainer.addComponent(getYahooMessengerPresenceTextArea());
        }

        return ymContactPresenceContainer;

    }

    public ComboBox getYahooMessengerPresenceComboBox() {

        if (ymContactListPresenceComboBox == null) {
            ymContactListPresenceComboBox =
                new ComboBox(new DefaultListModel(
                    new Integer[] {
                        new Integer(Presence.PRESENCE_STATE_OFFLINE),
                        new Integer(Presence.PRESENCE_STATE_ONLINE),
                        new Integer(Presence.PRESENCE_STATE_BUSY),
                        new Integer(Presence.PRESENCE_STATE_IDLE)
                    }
                ));
            ymContactListPresenceComboBox.setListCellRenderer(new MessengerContactsScreenPresenceRenderer(this));
            ymContactListPresenceComboBox.addActionListener(this);
        }

        return ymContactListPresenceComboBox;

    }

    public Label getYahooMessengerPresenceMessageLabel() {

        if (ymContactListPresenceMessage == null) {
            ymContactListPresenceMessage = new Label("   ");
        }

        return ymContactListPresenceMessage;
    }

    public void updatePresenceDisplayInfo() {

        if (ymContactListPresenceMessage != null) {
            ymContactListPresenceMessage.setText(ymPresenceMessage);
        }

        if (ymContactListPresenceComboBox != null) {
            
            int index = 0;
            if (ymPresenceState == Presence.PRESENCE_STATE_ONLINE)
                index = 1;
            else if (ymPresenceState == Presence.PRESENCE_STATE_BUSY)
                index = 2;
            else if (ymPresenceState == Presence.PRESENCE_STATE_IDLE)
                index = 3;

            ymContactListPresenceComboBox.setSelectedIndex(index);
        }
    }

    public TextArea getYahooMessengerPresenceTextArea() {

        if (ymContactListPresenceTextArea == null) {
            ymContactListPresenceTextArea = new TextArea();
            ymContactListPresenceTextArea.addActionListener(this);
        }

        return ymContactListPresenceTextArea;
    }

    public Container getYahooMessengerContactContainer() {

        if (ymContactContactContainer == null) {

            ymContactContactContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            ymContactContactContainer.addComponent(getYahooMessengerFriendsLabel());
            ymContactContactContainer.addComponent(getYahooMessengerContactList());
        }

        return ymContactContactContainer;

    }

    public Label getYahooMessengerFriendsLabel() {

        if (ymContactListPresenceFriendsLabel == null) {
            ymContactListPresenceFriendsLabel = new Label("Friends");
        }

        return ymContactListPresenceFriendsLabel;

    }

    public List getYahooMessengerContactList() {

        if (ymContactList == null) {
            listModel = new DefaultListModel(contacts);
            ymContactList = new List(listModel);
            ymContactList.addActionListener(this);
            ymContactList.setListCellRenderer(new MessengerContactsScreenContactsRenderer(this));
        } else {
            ymContactList.setModel(listModel);
        }
        
        return ymContactList;

    }

    public Image getYahooMessengerPresenceImage(int state) throws IOException {

        String resourceName = "";

        if (state == Presence.PRESENCE_STATE_BUSY) {
            resourceName = "Busy";
        } else if (state == Presence.PRESENCE_STATE_IDLE) {
            resourceName = "Idle";
        } else if (state == Presence.PRESENCE_STATE_OFFLINE) {
            resourceName = "Offline";
        } else if (state == Presence.PRESENCE_STATE_ONLINE) {
            resourceName = "Online";
        }

        resourceName += "16";

        Image image = midlet.getResources().getImage(resourceName);

        return image;

    }


    public void actionPerformed(ActionEvent event) {

        if ((event.getSource() == ymContactListPresenceTextArea) ||
            (event.getSource() == ymContactListPresenceComboBox))
        {

            Thread t = new Thread() {
                public void run() {

                    ymPresenceMessage = ymContactListPresenceTextArea.getText();
                    ymPresenceState = ((Integer)ymContactListPresenceComboBox.getSelectedItem()).intValue();

                    Presence newPresence = new Presence();
                    newPresence.setPresenceState(new Integer(ymPresenceState));
                    newPresence.setPresenceMessage(ymPresenceMessage);

                    performSetPresenceTask(newPresence);
                    performGetPresenceTask();
                    ymContactListPresenceTextArea.setText("");
                }
            };
            t.start();

        } else if (event.getSource() == ymContactList) {
            Thread t = new Thread() {
                public void run() {

                    Contact contact = (Contact)ymContactList.getSelectedItem();
                    midlet.setCurrentChatScreen(contact);
                    midlet.changeApplicationState(MessengerLWUITMidlet.MIDLET_STATE_CHAT_WITH_CONTACT_SCREEN);
                }
            };
            t.start();

        } else {

            Command c = event.getCommand();

            if (c != null) {

                int commandID = c.getId();

                if (commandID == MessengerLWUITMidlet.EXIT_COMMAND) {
                    midlet.exitApplication();
                }
            }

        }

    }

    public void propertyChanged(Object id, Object oldValue, Object newValue) {

        if (id.equals(YahooMessengerNotificationManager.NOTIFICATION)) {

            NotificationData n = (NotificationData)newValue;
            Response[] rlist = n.getResponseList().getResponses();

            for (int i = 0; i < rlist.length; i++) {
                if (rlist[i] instanceof BuddyInfo) {

                    BuddyInfoContact[] buddyInfoContacts =
                            ((BuddyInfo)rlist[i]).getBuddyInfoContactList().getBuddyInfoContacts();
                    
                    for (int j = 0; j < buddyInfoContacts.length; j++) {

                        BuddyInfoContact b = buddyInfoContacts[j];
                        System.out.println("BUDDY " + b.getSender() + " CHANGED!");
                    }

                }
            }


        }

    }

    public void performSetPresenceTask(Presence presence) {

        SetPresenceCancellableTask getPresenceTask =
                new SetPresenceCancellableTask(midlet, presence);

        SimpleCancellableTask cTask = new SimpleCancellableTask(getPresenceTask);
        cTask.run();
    }

    public void performGetContactTask(String uri, int index) {

        GetContactCancellableTask getContactTask =
                new GetContactCancellableTask(midlet, uri);

        SimpleCancellableTask cTask = new SimpleCancellableTask(getContactTask);
        cTask.run();

        Contact c = getContactTask.getContact();

        contacts[index] = c;

    }

    public void performGetContactsTask() {

        GetContactListCancellableTask getContactListTask =
                new GetContactListCancellableTask(midlet);

        SimpleCancellableTask cTask = new SimpleCancellableTask(getContactListTask);
        cTask.run();

        contacts = getContactListTask.getContacts();

    }

    public void performGetPresenceTask() {

        GetPresenceCancellableTask getPresenceTask =
            new GetPresenceCancellableTask(midlet);

        SimpleCancellableTask cTask = new SimpleCancellableTask(getPresenceTask);
        cTask.run();

        Presence returnedPresence = getPresenceTask.getPresence();

        ymPresenceState = returnedPresence.getPresenceState().intValue();
        String message = returnedPresence.getPresenceMessage();

        if (message != null)
            ymPresenceMessage = message;
        else
            ymPresenceMessage = "    ";

        updatePresenceDisplayInfo();

    }


}
