/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.ui.task;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.yahoo.messenger.data.json.Contact;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.manager.YahooMessengerContactListManager;
import com.yahoo.messenger.ui.MessengerLWUITMidlet;
import java.io.IOException;
import org.netbeans.microedition.util.Executable;



public class GetContactCancellableTask implements Executable {


    private MessengerLWUITMidlet midlet;

    private String uri;
    private Contact contact;

    public GetContactCancellableTask(
            MessengerLWUITMidlet midlet, String uri)
    {
        this.midlet = midlet;
        this.uri = uri;
    }

    /**
     * @return the contact
     */
    public Contact getContact() {
        return contact;
    }


    public void execute() {

        try {

            YahooMessengerContactListManager manager = YahooMessengerContactListManager.getInstance();
            contact = manager.getContact(uri);

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
