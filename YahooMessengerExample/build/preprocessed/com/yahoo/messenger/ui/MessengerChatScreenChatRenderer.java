/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.ui;

import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.list.ListCellRenderer;
import com.yahoo.messenger.manager.YahooMessengerLoginManager;


public class MessengerChatScreenChatRenderer extends Container implements ListCellRenderer {

    private MessengerChatScreen chatScreen;

    private Label messageLabel = new Label("");
    private Label myAvatarImageLabel = new Label("");
    private Label contactAvatarImageLabel = new Label("");

    private Label focus = new Label("");

    private String myLoginID;

    public MessengerChatScreenChatRenderer(MessengerChatScreen chatScreen) {

        this.chatScreen = chatScreen;

        myLoginID = YahooMessengerLoginManager.getInstance().
                                getCurrentUsername();;

        setLayout(new BorderLayout());
        addComponent(BorderLayout.WEST, myAvatarImageLabel);
        addComponent(BorderLayout.CENTER, messageLabel);
        addComponent(BorderLayout.EAST, contactAvatarImageLabel);

        focus.getStyle().setBgTransparency(100);

    }

    public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {

        MessengerChatScreen.ChatScreenMessage m = (MessengerChatScreen.ChatScreenMessage)value;

        messageLabel.setText(m.getMessage());

        if (!m.getSender().equals(myLoginID)) {
            messageLabel.setEnabled(false);
            messageLabel.setShiftText(20);
        } else {
            messageLabel.setEnabled(true);
            messageLabel.setShiftText(0);
        }

        if (isSelected) {
          setFocus(true);
          getStyle().setBgTransparency(100);
        } else {
          setFocus(false);
          getStyle().setBgTransparency(0);
        }

        return this;

    }

    public Component getListFocusComponent(List list) {
        return focus;
    }


}
