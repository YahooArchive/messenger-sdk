/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */  

package com.yahoo.messenger.exception;


public class MessengerException extends Exception {

    public static final int UNKNOWN_USERNAME = 101;
    public static final int INCORRECT_PASSWORD = 102;
    public static final int INVALID_TOKEN = 103;
    public static final int NO_USERNAME_GIVEN = 104;
    public static final int ALREADY_LOGGED_IN = 105;
    public static final int NO_PASSWORD_GIVEN = 106;
    public static final int NO_CONSUMER_KEY_GIVEN = 107;


    public static final int NO_CRUMB_GIVEN = 110;
    public static final int NO_SESSION_ID_GIVEN = 111;

    public static final int NO_NETWORK_GIVEN = 120;
    public static final int NO_TARGET_ID_GIVEN = 121;
    public static final int NO_CONTACT_ID_GIVEN = 122;

    public static final int NO_SEQUENCE_GIVEN = 130;


    public static final int JSON_PARSER_EXCEPTION = 990;

    public static final int UNKNOWN_SERVER_ERROR = 998;
    public static final int UNKNOWN_ERROR = 999;

    protected int code;

    public MessengerException(int code) {

        super();
        this.code = code;

    }

    public MessengerException(int code, String message) {

        super(message);
        this.code = code;

    }

    public MessengerException(String message) {

        super(message);
        this.code = UNKNOWN_ERROR;

    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }
}
