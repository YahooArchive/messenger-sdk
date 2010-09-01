/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.util;

import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.manager.data.SessionData;
import java.io.IOException;


public class YahooMessengerYTLoginUtilities {


    public static String performLoginGetPwToken(String username, String password)
        throws MessengerException, IOException
    {

        String cs = YahooMessengerConstants.pwTokenGetURL + "src=ymsgr&" +
                           "login=" + username + "&" +
                           "passwd=" + password;

        String resultString = HttpUtils.performHttpGet(cs);
        String[] nameValuePairs = StringUtils.tokenize(resultString, '\n', true);
        
        if (nameValuePairs.length == 1) {

            try {

                if (Integer.parseInt(nameValuePairs[0]) == 100)        //  NO USERNAME GIVEN
                    throw new MessengerException(MessengerException.NO_USERNAME_GIVEN);
                else if (Integer.parseInt(nameValuePairs[0]) == 1235)  //  UNKNOWN USERNAME
                    throw new MessengerException(MessengerException.UNKNOWN_USERNAME, "Unknown username");
                else if (Integer.parseInt(nameValuePairs[0]) == 1212)  //  INCORRECT PASSWORD
                    throw new MessengerException(MessengerException.INCORRECT_PASSWORD, "Incorrect password");
                else                                                   //  UNKNOWN ERROR
                    throw new MessengerException(MessengerException.UNKNOWN_ERROR, "Unknown error");

            } catch (NumberFormatException e) {
                throw new MessengerException(MessengerException.UNKNOWN_ERROR, "Unknown error");
            }

        } else if (nameValuePairs.length == 3) {

            String token = StringUtils.getValue(nameValuePairs[1], "ymsg");
            return token;

        } else {

            throw new MessengerException(MessengerException.UNKNOWN_ERROR, "Unknown error");

        }

    }

    public static void performLoginGetYTCookie(SessionData loginData, String token)
        throws MessengerException, IOException
    {

        String cs = YahooMessengerConstants.pwTokenLoginURL + "src=ymsgr&" + "token=" + token;

        String resultString = HttpUtils.performHttpGet(cs);
        String[] nameValuePairs = StringUtils.tokenize(resultString, '\n', true);

        if (nameValuePairs.length == 1) {

            try {

                if (Integer.parseInt(nameValuePairs[0]) == 100)        //  INVALID TOKEN
                    throw new MessengerException(MessengerException.INVALID_TOKEN, "Invalid token");
                else                                                   //  UNKNOWN ERROR
                    throw new MessengerException(MessengerException.UNKNOWN_ERROR, "Unknown error");

            } catch (NumberFormatException e) {
                throw new MessengerException(MessengerException.UNKNOWN_ERROR, "Unknown error");
            }

        } else if (nameValuePairs.length == 6) {

            //  Assemble the Y and T name value pairs into the information needed
            //  for the cookie.

            String cookie = nameValuePairs[2]+" "+nameValuePairs[3];
            loginData.setCookie(cookie);

        } else {

            throw new MessengerException(MessengerException.UNKNOWN_ERROR, "Unknown error");

        }
    }

 
}