/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.exception;


public class HttpException extends MessengerException {

    public static final int HTTP_OK_NOT_RECEIVED = 100;
    public static final int NO_AUTHENTICATION_GIVEN = 101;

    private int httpCode;

    public HttpException(int code) {

        super(code);

    }

    public HttpException(int code, int httpCode) {

        super(code);

        this.httpCode = httpCode;

    }

    public HttpException(int code, int httpCode, String message) {

        super(code, message);

        this.httpCode = httpCode;

    }

}
