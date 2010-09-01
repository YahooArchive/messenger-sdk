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
import com.yahoo.messenger.data.json.Presence;
import java.io.IOException;


public class MessengerContactsScreenPresenceRenderer extends Container implements ListCellRenderer {

    private MessengerContactsScreen contactsScreen;

    private Label presenceDescription = new Label("");
    private Label presenceIcon = new Label("");

    private Label focus = new Label("");

    public MessengerContactsScreenPresenceRenderer(MessengerContactsScreen contactsScreen) {

      this.contactsScreen = contactsScreen;

      setLayout(new BorderLayout());
      addComponent(BorderLayout.WEST, presenceIcon);
      presenceDescription.getStyle().setBgTransparency(0);
      addComponent(BorderLayout.CENTER, presenceDescription);

      focus.getStyle().setBgTransparency(100);
    }

    public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {

      try {

          Integer elementInList = (Integer) value;
          presenceIcon.setIcon(contactsScreen.getYahooMessengerPresenceImage(elementInList.intValue()));

          if (elementInList.intValue() == Presence.PRESENCE_STATE_OFFLINE) {
              presenceDescription.setText("Offline");
          } else if (elementInList.intValue() == Presence.PRESENCE_STATE_ONLINE) {
              presenceDescription.setText("Online");
          } else if (elementInList.intValue() == Presence.PRESENCE_STATE_IDLE) {
              presenceDescription.setText("Idle");
          } else if (elementInList.intValue() == Presence.PRESENCE_STATE_BUSY) {
              presenceDescription.setText("Busy");
          }

          if (isSelected) {
              setFocus(true);
              getStyle().setBgTransparency(100);
          } else {
              setFocus(false);
              getStyle().setBgTransparency(0);
          }

          return this;

      } catch (IOException e) {
          System.out.println("Internal error: Unable to load image resources");
          return this;
      }
    }

    public Component getListFocusComponent(List list) {
      return focus;
    }


}
