/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.ui;

import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.List;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.DefaultListModel;
import com.sun.lwuit.list.ListModel;
import com.yahoo.messenger.beans.PropertyChangeListener;
import com.yahoo.messenger.data.json.Contact;
import com.yahoo.messenger.data.notification.json.Message;
import com.yahoo.messenger.data.notification.json.Response;
import com.yahoo.messenger.manager.YahooMessengerLoginManager;
import com.yahoo.messenger.manager.YahooMessengerNotificationManager;
import com.yahoo.messenger.manager.data.NotificationData;
import com.yahoo.messenger.ui.task.SendMessageCancellableTask;
import java.util.Hashtable;
import org.netbeans.microedition.util.SimpleCancellableTask;


public class MessengerChatScreen implements ActionListener, PropertyChangeListener {

    private MessengerLWUITMidlet midlet;
    private Contact contact;

    private Form ymChatForm;
    private TextArea ymChatTextArea;

    private List ymConversationList;
    private ListModel listModel;

    private Hashtable messagesReceived;


    public MessengerChatScreen(MessengerLWUITMidlet midlet,
            Contact contact)
    {

        this.midlet = midlet;
        this.contact = contact;

        this.messagesReceived = new Hashtable();

        YahooMessengerNotificationManager manager =
                YahooMessengerNotificationManager.getInstance();
        manager.addPropertyChangeListener(this);

    }

    public void displayYahooMessengerChatScreen() {

        getYahooMessengerChatForm().show();

    }

    public Form getYahooMessengerChatForm() {

        if (ymChatForm == null) {

            ymChatForm = new Form("Chat with " + contact.getId());

            ymChatForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            ymChatForm.addComponent(getYahooMessengerChatTextArea());
            ymChatForm.addComponent(getYahooMessengerConversationList());
            ymChatForm.addCommand(midlet.getYahooMessengerBackCommand());

            ymChatForm.setCommandListener(this);

        }

        return ymChatForm;
    }

    public TextArea getYahooMessengerChatTextArea() {

        if (ymChatTextArea == null) {
            ymChatTextArea = new TextArea();
            ymChatTextArea.addActionListener(this);
        }

        return ymChatTextArea;
    }

 

    public List getYahooMessengerConversationList() {

        if (ymConversationList == null) {
            listModel = new DefaultListModel();
            ymConversationList = new List(listModel);
            ymConversationList.addActionListener(this);
            ymConversationList.setListCellRenderer(new MessengerChatScreenChatRenderer(this));
        } else {
            ymConversationList.setModel(listModel);
        }
        
        return ymConversationList;

    }



    public void actionPerformed(ActionEvent event) {

 
        if (event.getSource() == ymChatTextArea)
        {

            Thread t = new Thread() {
                public void run() {

                    String myLoginID = YahooMessengerLoginManager.getInstance().
                                getCurrentUsername();

                    String message = ymChatTextArea.getText();
                    performSendMessageTask(contact, message);
                    ymConversationList.addItem(new ChatScreenMessage(myLoginID, message));
                    ymChatTextArea.setText("");
                    ymChatForm.repaint();
                    ymConversationList.setSelectedIndex(ymConversationList.getModel().getSize()-1);
                }
            };
            t.start();

        } else {

            Command c = event.getCommand();

            if (c != null) {

                int commandID = c.getId();

                if (commandID == MessengerLWUITMidlet.BACK_COMMAND) {
                    midlet.changeApplicationState(MessengerLWUITMidlet.MIDLET_STATE_CONTACT_SCREEN);
                } else if (commandID == MessengerLWUITMidlet.EXIT_COMMAND) {
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
                if (rlist[i] instanceof Message) {

                    Message m = (Message)rlist[i];

                    if (!messagesReceived.containsKey(m.getSequence())) {
                        messagesReceived.put(m.getSequence(), m.getHash());
                        ymConversationList.addItem(new ChatScreenMessage(m.getSender(), m.getMsg()));
                        ymChatForm.repaint();
                        ymConversationList.setSelectedIndex(ymConversationList.getModel().getSize()-1);
                    }

                }
            }


        }

    }

    public void performSendMessageTask(Contact contact, String message) {

        
        SendMessageCancellableTask getPresenceTask =
                new SendMessageCancellableTask(midlet, contact, message);

        SimpleCancellableTask cTask = new SimpleCancellableTask(getPresenceTask);
        cTask.run();
         
         
    }

    public class ChatScreenMessage {

        private String sender;
        private String message;

        public ChatScreenMessage(String sender, String message) {

            this.sender = sender;
            this.message = message;

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
         * @return the message
         */
        public String getMessage() {
            return message;
        }

        /**
         * @param message the message to set
         */
        public void setMessage(String message) {
            this.message = message;
        }

    }


}
