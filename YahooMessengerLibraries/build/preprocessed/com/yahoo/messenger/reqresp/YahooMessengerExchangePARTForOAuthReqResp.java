/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.reqresp;

import com.yahoo.messenger.exception.HttpException;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.util.HttpUtils;
import com.yahoo.messenger.util.StringUtils;
import com.yahoo.messenger.util.YahooMessengerAuthentication;
import com.yahoo.messenger.util.YahooMessengerConstants;
import java.io.IOException;
import org.json.me.JSONException;


public class YahooMessengerExchangePARTForOAuthReqResp {

    private String consumerKey;
    private String signatureMethod;
    private String nonce;
    private String timestamp;
    private String signature;
    private String verifier;
    private String version;
    private String token;

    private YahooMessengerAuthentication authenticationToken;


    public void executeRequest()
        throws IOException, JSONException, HttpException, MessengerException
    {

        //  Verify mandatory parameters

        if (getConsumerKey() == null)
            throw new MessengerException(MessengerException.NO_CONSUMER_KEY_GIVEN,
                    "No consumer key given");

        //  Create the HTTP URL

        String url = YahooMessengerConstants.exchangePARTGetURL + "?" +
                "&oauth_consumer_key="+getConsumerKey()+
                "&oauth_signature_method="+getSignatureMethod()+
                "&oauth_nonce="+getNonce()+
                "&oauth_timestamp="+getTimestamp()+
                "&oauth_signature="+getSignature()+
               // "&oauth_verifier="+getVerifier()+
                "&oauth_version="+getVersion()+
                "&oauth_token="+getToken();

        //  Perform the actual call to the server

        System.out.println("ABOUT TO SEND REQUEST");
        
        String resultString =
                HttpUtils.performHttpGet(url);

        System.out.println("RESULT STRING IS: " + resultString);

        //  Take everything after "&oauth_token=" up to "&oauth_token_secret"
        //  and make it the oauth_token. Again, there are cleaner ways to
        //  parse this, but this is done for speed.

        int index = resultString.indexOf("&oauth_token_secret");
        String oauth_token = resultString.substring(12, index);

        System.out.println("OAUTH_TOKEN:" + oauth_token);

        setAuthenticationToken(
            new YahooMessengerAuthentication(
                StringUtils.URLEncoder("yahooapis.com"),
                YahooMessengerConstants.authenticationConsumerKey,
                "12345",
                "PLAINTEXT",
                "0",
                oauth_token,
                "1.0",
                YahooMessengerConstants.authenticationConsumerSecret+"%26"));
    }

    /**
     * @return the consumerKey
     */
    public String getConsumerKey() {
        return consumerKey;
    }

    /**
     * @param consumerKey the consumerKey to set
     */
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    /**
     * @return the signatureMethod
     */
    public String getSignatureMethod() {
        return signatureMethod;
    }

    /**
     * @param signatureMethod the signatureMethod to set
     */
    public void setSignatureMethod(String signatureMethod) {
        this.signatureMethod = signatureMethod;
    }

    /**
     * @return the nonce
     */
    public String getNonce() {
        return nonce;
    }

    /**
     * @param nonce the nonce to set
     */
    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * @return the verifier
     */
    public String getVerifier() {
        return verifier;
    }

    /**
     * @param verifier the verifier to set
     */
    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the authenticationToken
     */
    public YahooMessengerAuthentication getAuthenticationToken() {
        return authenticationToken;
    }

    /**
     * @param authenticationToken the authenticationToken to set
     */
    public void setAuthenticationToken(YahooMessengerAuthentication authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

}
