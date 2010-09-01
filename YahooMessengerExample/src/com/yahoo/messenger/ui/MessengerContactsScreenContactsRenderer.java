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
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.ListCellRenderer;
import com.yahoo.messenger.data.json.Contact;
import com.yahoo.messenger.data.json.Presence;
import java.io.IOException;


public class MessengerContactsScreenContactsRenderer extends Container implements ListCellRenderer {

    private MessengerContactsScreen contactsScreen;

    private Label contactId = new Label("");
    private Label presenceIcon = new Label("");

    private Label focus = new Label("");

    public MessengerContactsScreenContactsRenderer(MessengerContactsScreen contactsScreen) {

      this.contactsScreen = contactsScreen;

      setLayout(new BorderLayout());
      addComponent(BorderLayout.WEST, presenceIcon);
      Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
      contactId.getStyle().setBgTransparency(0);
      container.addComponent(contactId);
      addComponent(BorderLayout.CENTER, container);

      focus.getStyle().setBgTransparency(100);
    }

    public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {

      try {

          Contact contactInList = (Contact) value;

          contactId.setText(contactInList.getId());
          presenceIcon.setIcon(contactsScreen.getYahooMessengerPresenceImage(Presence.PRESENCE_STATE_OFFLINE));

          Integer presence = contactInList.getPresence().getPresenceState();

          if (presence != null)
              presenceIcon.setIcon(contactsScreen.getYahooMessengerPresenceImage(presence.intValue()));

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
