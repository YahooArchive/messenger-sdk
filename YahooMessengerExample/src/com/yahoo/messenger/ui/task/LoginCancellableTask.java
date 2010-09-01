/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.yahoo.messenger.ui.task;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.manager.YahooMessengerLoginManager;
import com.yahoo.messenger.ui.MessengerLWUITMidlet;
import com.yahoo.messenger.util.YahooMessengerConstants;
import java.io.IOException;
import org.netbeans.microedition.util.Executable;


/**
 *
 * @author roberteckstein
 */
public class LoginCancellableTask implements Executable {


    private MessengerLWUITMidlet midlet;
    private String username;
    private String password;

    public LoginCancellableTask(
            MessengerLWUITMidlet midlet,
            String username, String password)
    {
        this.midlet = midlet;
        this.username = username;
        this.password = password;
    }

    public void execute() {

        username = "messengertestuser";   //  TEMPORARY - FOR TESTING
        password = "0246abcdef";          //  TEMPORARY - FOR TESTING

        if (username.equals("") || password.equals("")) {

            Dialog.show("Error", "Please enter a username and password",
                midlet.getYahooMessengerOkayCommand(),
                new Command[] {midlet.getYahooMessengerOkayCommand()},
                Dialog.TYPE_ERROR, null, 0);

            return;

        }

        try {

            YahooMessengerLoginManager manager = YahooMessengerLoginManager.getInstance();
            //manager.performLoginOAuth(username, password,
            //        YahooMessengerConstants.authenticationConsumerKey,
            //        YahooMessengerConstants.authenticationConsumerSecret);
            manager.performLoginYTCookie(username, password);

            midlet.changeApplicationState(MessengerLWUITMidlet.MIDLET_STATE_LOGIN_SUCCESSFUL);


        } catch (IOException e) {

            Dialog.show("Network Error", "Unable to contact Yahoo! Messenger server.",
                midlet.getYahooMessengerOkayCommand(), new Command[] {midlet.getYahooMessengerOkayCommand()},
                Dialog.TYPE_ERROR, null, 0);

            midlet.changeApplicationState(MessengerLWUITMidlet.MIDLET_STATE_LOGIN_SCREEN);

        } catch (MessengerException e) {

            if (e.getCode() == MessengerException.NO_USERNAME_GIVEN) {

                Dialog.show("Sign-in Problem", "Invalid user ID or password. Please try again.",
                    midlet.getYahooMessengerOkayCommand(), new Command[] {midlet.getYahooMessengerOkayCommand()},
                    Dialog.TYPE_ERROR, null, 0);

                midlet.changeApplicationState(MessengerLWUITMidlet.MIDLET_STATE_LOGIN_SCREEN);

            } else if (e.getCode() == MessengerException.UNKNOWN_USERNAME) {

                Dialog.show("Sign-in Problem", "Invalid user ID or password. Please try again.",
                    midlet.getYahooMessengerOkayCommand(), new Command[] {midlet.getYahooMessengerOkayCommand()},
                    Dialog.TYPE_ERROR, null, 0);

                midlet.changeApplicationState(MessengerLWUITMidlet.MIDLET_STATE_LOGIN_SCREEN);

            } else if (e.getCode() == MessengerException.INCORRECT_PASSWORD) {

                Dialog.show("Sign-in Problem", "Invalid user ID or password. Please try again.",
                    midlet.getYahooMessengerOkayCommand(), new Command[] {midlet.getYahooMessengerOkayCommand()},
                    Dialog.TYPE_ERROR, null, 0);

                midlet.changeApplicationState(MessengerLWUITMidlet.MIDLET_STATE_LOGIN_SCREEN);

            } else if (e.getCode() == MessengerException.INVALID_TOKEN) {

                Dialog.show("Invalid Token", "The token returned by the Yahoo! Messenger server is invalid.",
                    midlet.getYahooMessengerOkayCommand(), new Command[] {midlet.getYahooMessengerOkayCommand()},
                    Dialog.TYPE_ERROR, null, 0);

                midlet.changeApplicationState(MessengerLWUITMidlet.MIDLET_STATE_LOGIN_SCREEN);

            } else if (e.getCode() == MessengerException.ALREADY_LOGGED_IN) {

                Dialog.show("Already Logged In", "This user is already logged in with another session.",
                    midlet.getYahooMessengerOkayCommand(), new Command[] {midlet.getYahooMessengerOkayCommand()},
                    Dialog.TYPE_ERROR, null, 0);

                midlet.changeApplicationState(MessengerLWUITMidlet.MIDLET_STATE_LOGIN_SCREEN);

            } else {

                Dialog.show("Unknown Error", "Yahoo! Messenger encountered an unknown login error",
                    midlet.getYahooMessengerOkayCommand(), new Command[] {midlet.getYahooMessengerOkayCommand()},
                    Dialog.TYPE_ERROR, null, 0);

                midlet.changeApplicationState(MessengerLWUITMidlet.MIDLET_STATE_LOGIN_SCREEN);

            }
        } 
    }


}
