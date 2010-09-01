/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.ui;

import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;



public class MessengerSplashScreen implements ActionListener {

    private static final int SPLASH_SCREEN_DISPLAY_TIME_MILLISECONDS = 4000;

    private MessengerLWUITMidlet midlet;

    private Form ymSplashScreenForm;
    private Image ymSplashScreenImage;

    private Timer splashScreenTimer;


    public MessengerSplashScreen(MessengerLWUITMidlet midlet) {
        this.midlet = midlet;
    }


    public void displayYahooMessengerSplashScreen() {

        getYahooMessengerSplashForm().show();

        splashScreenTimer = new Timer();
        splashScreenTimer.schedule(new SplashScreenTimerClass(), SPLASH_SCREEN_DISPLAY_TIME_MILLISECONDS);

    }

    class SplashScreenTimerClass extends TimerTask {

        //  Run this when the timer expires

        public void run() {
            splashScreenTimer.cancel();
            midlet.changeApplicationState(MessengerLWUITMidlet.MIDLET_STATE_LOGIN_SCREEN);
        }

    }

    public Form getYahooMessengerSplashForm() {

        if (ymSplashScreenForm == null) {

            try {
                ymSplashScreenForm = new Form();
                ymSplashScreenForm.getStyle().setBgImage(getYahooMessengerSplashFormImage());
                ymSplashScreenForm.addCommand(midlet.getYahooMessengerExitCommand());
                ymSplashScreenForm.addCommand(midlet.getYahooMessengerDismissCommand());
                ymSplashScreenForm.setCommandListener(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ymSplashScreenForm;

    }

    public Image getYahooMessengerSplashFormImage() throws IOException {

        if (ymSplashScreenImage == null) {
            ymSplashScreenImage = midlet.getResources().getImage("splashScreen");
        }

        return ymSplashScreenImage;

    }


    public void actionPerformed(ActionEvent event) {

        int commandID = event.getCommand().getId();

        if (commandID == MessengerLWUITMidlet.DISMISS_COMMAND) {
            splashScreenTimer.cancel();
            midlet.changeApplicationState(MessengerLWUITMidlet.MIDLET_STATE_LOGIN_SCREEN);
        } else if (commandID == MessengerLWUITMidlet.EXIT_COMMAND) {
            midlet.exitApplication();
        }

    }

}
