/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.util;


public class YahooMessengerConstants {

    public static final int debugHttpRequestResponse = 1;


    public static final String loginServerURL = 
            "https://login.yahoo.com";
    public static final String apiLoginServerURL =
            "https://api.login.yahoo.com";
    //        "https://yosgamma.api.login.yahoo.com";

    public static final String pwTokenGetURL =
            YahooMessengerConstants.loginServerURL + "/config/pwtoken_get?";
    public static final String pwTokenLoginURL =
            YahooMessengerConstants.loginServerURL + "/config/pwtoken_login?";
    public static final String partTokenGetURL =
            YahooMessengerConstants.loginServerURL + "/WSLogin/V1/get_auth_token";
    public static final String exchangePARTGetURL =
            YahooMessengerConstants.apiLoginServerURL + "/oauth/v2/get_token";


    public static final String stagingServerURL =
            "stage.rest-core.msg.yahoo.com";
    public static final String mobileProductionServerURL =
            "mobile.rest-core.msg.yahoo.com";
    public static final String stagingServerOAuthURL =
            "stage-ydn.rest-core.msg.yahoo.com";

    public static final String messengerServerURL =
            stagingServerURL;
    
    public static final String authenticationConsumerKey =
            "dj0yJmk9YmVSZkFRQjJNdU9aJmQ9WVdrOVFtOWhjM1prTnpnbWNHbzlOekkzTmpBMU9UWXkmcz1jb25zdW1lcnNlY3JldCZ4PTJl";
   //         "dj0yJmk9NUlzalhGYlpjdHhOJmQ9WVdrOVdrdHVVWEoyTkdzbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1lNA--";
    public static final String authenticationConsumerSecret =
            "d7824ee7ff07b5ab423abf06bc8f24aea4bdb1a6";
    //        "999003873a576ec9a51d43fb952604b4d4928be1";

    public static final String deleteSuffix = "_method=delete";
    public static final String putSuffix = "_method=put";
    public static final String createSuffix = "_method=create";


    public static final String messengerAPIVersion = "/v1";

    public static final String sessionManagementURL =
            messengerAPIVersion + "/session";
    public static final String sessionManagementKeepaliveURL =
            messengerAPIVersion + "/session/keepalive";
    public static final String presenceManagementURL =
            messengerAPIVersion + "/presence";
    public static final String contactListManagementURL =
            messengerAPIVersion + "/contacts";
    public static final String contactManagementURL =
            messengerAPIVersion + "/contact";
    public static final String messageManagementURL =
            messengerAPIVersion + "/message";
    public static final String notificationManagementURL =
            messengerAPIVersion + "/notifications";


}
