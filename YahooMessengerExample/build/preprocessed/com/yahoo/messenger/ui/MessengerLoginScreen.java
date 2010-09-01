/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.ui;

import com.sun.lwuit.CheckBox;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.yahoo.messenger.ui.task.LoginCancellableTask;
import java.io.IOException;
import org.netbeans.microedition.util.SimpleCancellableTask;



public class MessengerLoginScreen implements ActionListener {

    private MessengerLWUITMidlet midlet;

    private Form ymLoginScreenForm;
    private Image ymLoginScreenTopLogoImage;
    private Label ymLoginScreenUsernameLabel;
    private TextArea ymLoginScreenUsernameTextArea;
    private Label ymLoginScreenPasswordLabel;
    private TextArea ymLoginScreenPasswordTextArea;
    private CheckBox ymLoginScreenRememberMeCheckBox;
    private CheckBox ymLoginScreenSignInAsInvisibleCheckBox;

    private Form ymLoginScreenWaitForm;
    private Label ymLoginScreenWaitLabel;

    private SimpleCancellableTask loginTask;


    public MessengerLoginScreen(MessengerLWUITMidlet midlet) {
        this.midlet = midlet;
    }

    public void performLoginTask() {

        displayYahooMessengerLoginWaitScreen();

        loginTask = new SimpleCancellableTask(
                   new LoginCancellableTask(
                           midlet,
                           ymLoginScreenUsernameTextArea.getText(),
                           ymLoginScreenPasswordTextArea.getText()));
        loginTask.run();

    }

    //  LOGIN SCREEN

    public void displayYahooMessengerLoginScreen() {

        getYahooMessengerLoginForm().show();

    }

    public Form getYahooMessengerLoginForm() {

        if (ymLoginScreenForm == null) {

            try {

                ymLoginScreenForm = new Form();

                ymLoginScreenForm.addComponent(new Label(getYahooMessengerTopLogoImage()));;

                ymLoginScreenForm.addComponent(getYahooMessengerLoginFormUsernameLabel());
                ymLoginScreenForm.addComponent(getYahooMessengerLoginFormUsernameTextArea());
                ymLoginScreenForm.addComponent(getYahooMessengerLoginFormPasswordLabel());
                ymLoginScreenForm.addComponent(getYahooMessengerLoginFormPasswordTextArea());

                ymLoginScreenForm.addComponent(getYahooMessengerLoginFormRememberMeCheckBox());
                ymLoginScreenForm.addComponent(getYahooMessengerLoginFormSignInAsInvisibleCheckBox());
                ymLoginScreenForm.addCommand(midlet.getYahooMessengerExitCommand());
                ymLoginScreenForm.addCommand(midlet.getYahooMessengerLoginCommand());

                ymLoginScreenForm.setCommandListener(this);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return ymLoginScreenForm;

    }

    public Image getYahooMessengerTopLogoImage() throws IOException {

        if (ymLoginScreenTopLogoImage == null) {
            ymLoginScreenTopLogoImage = midlet.getResources().getImage("logoTop");
        }

        return ymLoginScreenTopLogoImage;

    }

    public Label getYahooMessengerLoginFormUsernameLabel() {

        if (ymLoginScreenUsernameLabel == null) {
            ymLoginScreenUsernameLabel = new Label("Yahoo! ID:");
        }

        return ymLoginScreenUsernameLabel;

    }

    public Label getYahooMessengerLoginFormPasswordLabel() {

        if (ymLoginScreenPasswordLabel == null) {
            ymLoginScreenPasswordLabel = new Label("Password:");
        }

        return ymLoginScreenPasswordLabel;

    }

    public TextArea getYahooMessengerLoginFormUsernameTextArea() {

        if (ymLoginScreenUsernameTextArea == null) {
            ymLoginScreenUsernameTextArea = new TextArea(1, 20, TextArea.ANY);
            ymLoginScreenUsernameTextArea.setEditable(true);
        }

        return ymLoginScreenUsernameTextArea;
    }

    public TextArea getYahooMessengerLoginFormPasswordTextArea() {

        if (ymLoginScreenPasswordTextArea == null) {
            ymLoginScreenPasswordTextArea = new TextArea(1, 20, TextArea.PASSWORD);
            ymLoginScreenPasswordTextArea.setEditable(true);
        }

        return ymLoginScreenPasswordTextArea;
    }

    public CheckBox getYahooMessengerLoginFormRememberMeCheckBox() {

        if (ymLoginScreenRememberMeCheckBox == null) {
            ymLoginScreenRememberMeCheckBox = new CheckBox("Remember My ID");
            ymLoginScreenRememberMeCheckBox.setTickerEnabled(false);
        }

        return ymLoginScreenRememberMeCheckBox;
    }

    public CheckBox getYahooMessengerLoginFormSignInAsInvisibleCheckBox() {

        if (ymLoginScreenSignInAsInvisibleCheckBox == null) {
            ymLoginScreenSignInAsInvisibleCheckBox = new CheckBox("Sign In As Invisible");
            ymLoginScreenSignInAsInvisibleCheckBox.setTickerEnabled(false);
        }

        return ymLoginScreenSignInAsInvisibleCheckBox;
    }

    //  LOGIN WAIT SCREEN

    public void displayYahooMessengerLoginWaitScreen() {

        getYahooMessengerLoginWaitForm().show();

    }

    public Form getYahooMessengerLoginWaitForm() {

        if (ymLoginScreenWaitForm == null) {

            ymLoginScreenWaitForm = new Form("Logging Into Server");

            ymLoginScreenWaitForm.addComponent(getYahooMessengerLoginFormWaitLabel());
            ymLoginScreenWaitForm.addCommand(midlet.getYahooMessengerCancelCommand());

            ymLoginScreenWaitForm.setCommandListener(this);

        }

        return ymLoginScreenWaitForm;

    }

    public Label getYahooMessengerLoginFormWaitLabel() {

        if (ymLoginScreenWaitLabel == null) {
            ymLoginScreenWaitLabel = new Label("Logging into server...");
        }

        return ymLoginScreenWaitLabel;

    }


    public void actionPerformed(ActionEvent event) {

        int commandID = event.getCommand().getId();

        if (commandID == MessengerLWUITMidlet.LOGIN_COMMAND) {

            Thread t = new Thread() {
                public void run() {
                    midlet.changeApplicationState(MessengerLWUITMidlet.MIDLET_STATE_LOGGING_IN);
                }
            };
            t.start();

        } else if (commandID == MessengerLWUITMidlet.EXIT_COMMAND) {
            midlet.exitApplication();
        }

    }

}
