/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.ui.task;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.yahoo.messenger.data.json.Presence;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.manager.YahooMessengerPresenceManager;
import com.yahoo.messenger.ui.MessengerLWUITMidlet;
import java.io.IOException;
import org.netbeans.microedition.util.Executable;


public class SetPresenceCancellableTask implements Executable {


    private MessengerLWUITMidlet midlet;
    private Presence presence;

    public SetPresenceCancellableTask(
            MessengerLWUITMidlet midlet,
            Presence presence)
    {
        this.midlet = midlet;
        this.presence = presence;
    }


    public void execute() {

        try {

            YahooMessengerPresenceManager manager = YahooMessengerPresenceManager.getInstance();
            manager.setPresenceInformation(presence);

        } catch (IOException e) {

            Dialog.show("Network Error", "Unable to contact Yahoo! Messenger server.",
                midlet.getYahooMessengerOkayCommand(), new Command[] {midlet.getYahooMessengerOkayCommand()},
                Dialog.TYPE_ERROR, null, 0);

        } catch (MessengerException e) {

            if (e.getCode() == MessengerException.NO_CRUMB_GIVEN) {

                Dialog.show("Authentication Problem", "Invalid crumb authentication credentials.",
                    midlet.getYahooMessengerOkayCommand(), new Command[] {midlet.getYahooMessengerOkayCommand()},
                    Dialog.TYPE_ERROR, null, 0);

            } else if (e.getCode() == MessengerException.NO_SESSION_ID_GIVEN) {

                Dialog.show("Authentication Problem", "Invalid session ID authentication credentials.",
                    midlet.getYahooMessengerOkayCommand(), new Command[] {midlet.getYahooMessengerOkayCommand()},
                    Dialog.TYPE_ERROR, null, 0);

            } else if (e.getCode() == MessengerException.JSON_PARSER_EXCEPTION) {

                Dialog.show("Communication Problem", "Unrecognized data while communication with server.",
                    midlet.getYahooMessengerOkayCommand(), new Command[] {midlet.getYahooMessengerOkayCommand()},
                    Dialog.TYPE_ERROR, null, 0);

            } else {

                Dialog.show("Unknown Error", "Yahoo! Messenger encountered an unknown login error",
                    midlet.getYahooMessengerOkayCommand(), new Command[] {midlet.getYahooMessengerOkayCommand()},
                    Dialog.TYPE_ERROR, null, 0);

            }
        } 
    }


}
