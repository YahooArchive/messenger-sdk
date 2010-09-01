/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.util;


public class YahooMessengerAuthentication {

    private String cookie;
    private boolean usingOAuth;

    private String realm;
    private String oauthConsumerKey;
    private String oauthNonce;
    private String oauthSignatureMethod;
    private String oauthTimestamp;
    private String oauthToken;
    private String oauthVersion;
    private String oauthSignature;


    public YahooMessengerAuthentication(String authenticationCookie) {

        this.cookie = authenticationCookie;
        this.usingOAuth = false;

    }

    public YahooMessengerAuthentication(String realm, String consumerKey,
            String nonce, String signatureMethod, String timestamp, String token,
            String version, String signature)
    {
        this.realm = realm;
        this.oauthConsumerKey = consumerKey;
        this.oauthNonce = nonce;
        this.oauthSignatureMethod = signatureMethod;
        this.oauthTimestamp = timestamp;
        this.oauthToken = token;
        this.oauthVersion = version;
        this.oauthSignature = signature;

        this.usingOAuth = true;

    }

    /**
     * @return the usingOAuth
     */
    public boolean isUsingOAuth() {
        return usingOAuth;
    }

    /**
     * @return the authentication
     */
    public String getCookie() {
        return cookie;
    }

    /**
     * @param authentication the authentication to set
     */
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    /**
     * @return the realm
     */
    public String getRealm() {
        return realm;
    }

    /**
     * @param realm the realm to set
     */
    public void setRealm(String realm) {
        this.realm = realm;
    }

    /**
     * @return the oauthConsumerKey
     */
    public String getOauthConsumerKey() {
        return oauthConsumerKey;
    }

    /**
     * @param oauthConsumerKey the oauthConsumerKey to set
     */
    public void setOauthConsumerKey(String oauthConsumerKey) {
        this.oauthConsumerKey = oauthConsumerKey;
    }

    /**
     * @return the oauthNonce
     */
    public String getOauthNonce() {
        return oauthNonce;
    }

    /**
     * @param oauthNonce the oauthNonce to set
     */
    public void setOauthNonce(String oauthNonce) {
        this.oauthNonce = oauthNonce;
    }

    /**
     * @return the oauthSignatureMethod
     */
    public String getOauthSignatureMethod() {
        return oauthSignatureMethod;
    }

    /**
     * @param oauthSignatureMethod the oauthSignatureMethod to set
     */
    public void setOauthSignatureMethod(String oauthSignatureMethod) {
        this.oauthSignatureMethod = oauthSignatureMethod;
    }

    /**
     * @return the oauthTimestamp
     */
    public String getOauthTimestamp() {
        return oauthTimestamp;
    }

    /**
     * @param oauthTimestamp the oauthTimestamp to set
     */
    public void setOauthTimestamp(String oauthTimestamp) {
        this.oauthTimestamp = oauthTimestamp;
    }

    /**
     * @return the oauthToken
     */
    public String getOauthToken() {
        return oauthToken;
    }

    /**
     * @param oauthToken the oauthToken to set
     */
    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    /**
     * @return the oauthVersion
     */
    public String getOauthVersion() {
        return oauthVersion;
    }

    /**
     * @param oauthVersion the oauthVersion to set
     */
    public void setOauthVersion(String oauthVersion) {
        this.oauthVersion = oauthVersion;
    }

    /**
     * @return the oauthSignature
     */
    public String getOauthSignature() {
        return oauthSignature;
    }

    /**
     * @param oauthSignature the oauthSignature to set
     */
    public void setOauthSignature(String oauthSignature) {
        this.oauthSignature = oauthSignature;
    }


    public String getOAuthParameters() {

        if (!isUsingOAuth()) {
            return "";
        }

        long timeStamp = System.currentTimeMillis() / 1000;

        return
          "&realm="+getRealm()+
          "&oauth_consumer_key="+getOauthConsumerKey()+
          "&oauth_nonce="+getOauthNonce()+
          "&oauth_signature_method="+getOauthSignatureMethod()+
          "&oauth_timestamp="+timeStamp+
          "&oauth_token="+getOauthToken()+
          "&oauth_version="+getOauthVersion()+
          "&oauth_signature="+getOauthSignature();
    }


}
