/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.ui;

import com.sun.lwuit.Command;
import com.sun.lwuit.Display;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;
import com.yahoo.messenger.data.json.Contact;
import com.yahoo.messenger.data.json.Presence;
import java.io.IOException;
import java.util.Hashtable;
import javax.microedition.midlet.MIDlet;


public class MessengerLWUITMidlet extends MIDlet {

    public static final int MIDLET_STATE_SPLASH_SCREEN = 10;
    public static final int MIDLET_STATE_LOGIN_SCREEN = 20;
    public static final int MIDLET_STATE_LOGGING_IN = 21;
    public static final int MIDLET_STATE_LOGIN_SUCCESSFUL = 22;
    public static final int MIDLET_STATE_UPDATING_CONTACTS_AND_PRESENCE = 30;
    public static final int MIDLET_STATE_CONTACT_SCREEN = 31;
    public static final int MIDLET_STATE_CHAT_WITH_CONTACT_SCREEN = 40;

    public static final int EXIT_COMMAND = 1;
    public static final int LOGIN_COMMAND = 2;
    public static final int DISMISS_COMMAND = 3;
    public static final int CANCEL_COMMAND = 4;
    public static final int OKAY_COMMAND = 5;
    public static final int BACK_COMMAND = 6;

    private Resources resources;

    private int state;

    private Command ymExitCommand;
    private Command ymLoginCommand;
    private Command ymDismissCommand;
    private Command ymCancelCommand;
    private Command ymOkayCommand;
    private Command ymBackCommand;

    private MessengerLoginScreen loginScreen;
    private MessengerSplashScreen splashScreen;
    private MessengerContactsScreen contactsScreen;
    private MessengerChatScreen chatScreen;

    private Hashtable chatScreensHashtable;


    public void startApp() {

        Display.init(this);

        //  Load the resources

        try {
            resources = Resources.open("/LWUITtheme.res");
            UIManager.getInstance().setThemeProps(resources.getTheme(resources.getThemeResourceNames()[0]));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        //  Initialize the individual screens in the application

        splashScreen = new MessengerSplashScreen(this);
        contactsScreen = new MessengerContactsScreen(this);
        loginScreen = new MessengerLoginScreen(this);

        //  Initialize the hastable used to store each chat screen, keyed
        //  by the user ID that you are having a chat with.

        chatScreensHashtable = new Hashtable();

        //  Start off by displaying the splash screen

        changeApplicationState(MessengerLWUITMidlet.MIDLET_STATE_SPLASH_SCREEN);

    }

    public void pauseApp() {

    }

    public void destroyApp(boolean unconditional) {

    }


    public Resources getResources() {
        return resources;
    }


    public void changeApplicationState(int newState) {

        state = newState;

        if (state == MIDLET_STATE_SPLASH_SCREEN) {

            splashScreen.displayYahooMessengerSplashScreen();

        } else if (state == MIDLET_STATE_LOGIN_SCREEN) {

            loginScreen.displayYahooMessengerLoginScreen();

        } else if (state == MIDLET_STATE_LOGGING_IN) {

            loginScreen.displayYahooMessengerLoginWaitScreen();
            performLogin();

        } else if (state == MIDLET_STATE_LOGIN_SUCCESSFUL) {

            changeApplicationState(MessengerLWUITMidlet.MIDLET_STATE_UPDATING_CONTACTS_AND_PRESENCE);

        } else if (state == MIDLET_STATE_UPDATING_CONTACTS_AND_PRESENCE) {

            contactsScreen.displayYahooMessengerGetContactsWaitScreen();
            performGetContacts();
            changeApplicationState(MessengerLWUITMidlet.MIDLET_STATE_CONTACT_SCREEN);

        } else if (state == MIDLET_STATE_CONTACT_SCREEN) {

            contactsScreen.displayYahooMessengerContactListScreen();
            performGetMyPresence();

        }  else if (state == MIDLET_STATE_CHAT_WITH_CONTACT_SCREEN) {

            if (chatScreen != null)
                chatScreen.displayYahooMessengerChatScreen();

        }
    }

    //   These are commands that appear with the left and right soft buttons.
    //   Note that Java ME will often group several of them under the "Menu"
    //   soft button on the right side, even if this is not desirable.


    public Command getYahooMessengerLoginCommand() {

        if (ymLoginCommand == null) {
            ymLoginCommand = new Command("Sign In", LOGIN_COMMAND);
        }

        return ymLoginCommand;
    }

    public Command getYahooMessengerExitCommand() {

        if (ymExitCommand == null) {
            ymExitCommand = new Command("Exit", EXIT_COMMAND);
        }

        return ymExitCommand;
    }

    public Command getYahooMessengerDismissCommand() {

        if (ymDismissCommand == null) {
            ymDismissCommand = new Command("Dismiss", DISMISS_COMMAND);
        }

        return ymDismissCommand;
    }

    public Command getYahooMessengerCancelCommand() {

        if (ymCancelCommand == null) {
            ymCancelCommand = new Command("Cancel", CANCEL_COMMAND);
        }

        return ymCancelCommand;
    }

    public Command getYahooMessengerBackCommand() {

        if (ymBackCommand == null) {
            ymBackCommand = new Command("Back", BACK_COMMAND);
        }

        return ymBackCommand;
    }

    public Command getYahooMessengerOkayCommand() {

        if (ymOkayCommand == null) {
            ymOkayCommand = new Command("OK", OKAY_COMMAND);
        }

        return ymOkayCommand;
    }


    //  These are various tasks that are done in response to application
    //  state changes

    public void performLogin() {
        loginScreen.performLoginTask();
    }

    public void performGetContacts() {
        contactsScreen.performGetContactsTask();
    }

    public void performGetMyPresence() {
        contactsScreen.performGetPresenceTask();
    }

    public void performSetMyPresence(Presence presence) {
        contactsScreen.performSetPresenceTask(presence);
    }

    public void setCurrentChatScreen(Contact contact) {

        //  Attempt to retrieve an existing chat screen from the hashtable,
        //  if it exists already. Otherwise, create a new one.

        if (chatScreensHashtable.containsKey(contact.getId())) {
            this.chatScreen = (MessengerChatScreen)chatScreensHashtable.get(contact.getId());
        } else {
            MessengerChatScreen screen = new MessengerChatScreen(this, contact);
            chatScreensHashtable.put(contact.getId(), chatScreen);
            this.chatScreen = screen;
        }
    }

    public void exitApplication() {
        notifyDestroyed();
    }


}
