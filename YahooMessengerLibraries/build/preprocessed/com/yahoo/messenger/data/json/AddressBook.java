/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.data.json;

import org.json.me.JSONException;
import org.json.me.JSONObject;


public class AddressBook {

    private String id;
    private String nickname;
    private String firstname;
    private String lastname;
    private String mobileno;
    private String homeno;
    private String workno;
    private String email;
    private Integer lastModified;

    public AddressBook() {

        //  Perform any initialization

    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the mobileno
     */
    public String getMobileno() {
        return mobileno;
    }

    /**
     * @param mobileno the mobileno to set
     */
    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    /**
     * @return the homeno
     */
    public String getHomeno() {
        return homeno;
    }

    /**
     * @param homeno the homeno to set
     */
    public void setHomeno(String homeno) {
        this.homeno = homeno;
    }

    /**
     * @return the workno
     */
    public String getWorkno() {
        return workno;
    }

    /**
     * @param workno the workno to set
     */
    public void setWorkno(String workno) {
        this.workno = workno;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the lastModified
     */
    public Integer getLastModified() {
        return lastModified;
    }

    /**
     * @param lastModified the lastModified to set
     */
    public void setLastModified(Integer lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public void unserializeJSON(JSONObject o)
        throws JSONException
    {

        //  Optional items

        if (o.has("id"))
            setId(o.getString("id"));   
        if (o.has("nickname"))
            setNickname(o.getString("nickname"));
        if (o.has("firstname"))
            setFirstname(o.getString("firstname"));
        if (o.has("lastname"))
            setLastname(o.getString("lastname"));
        if (o.has("mobileno"))
            setMobileno(o.getString("mobileno"));
        if (o.has("homeno"))
            setHomeno(o.getString("homeno"));
        if (o.has("workno"))
            setWorkno(o.getString("workno"));
        if (o.has("email"))
            setEmail(o.getString("email"));
        if (o.has("lastModified"))
            setLastModified(new Integer(o.getInt("lastModified")));


    }

    public JSONObject serializeJSON()
            throws JSONException
    {
        JSONObject o = new JSONObject();

        //  Optional items

        if (getId() != null)
            o.put("id", getId());               
        if (getNickname() != null)
            o.put("nickname", getNickname());
        if (getFirstname() != null)
            o.put("firstname", getFirstname());
        if (getLastname() != null)
            o.put("lastname", getLastname());
        if (getMobileno() != null)
            o.put("mobileno", getMobileno());
        if (getHomeno() != null)
            o.put("homeno", getHomeno());
        if (getWorkno() != null)
            o.put("workno", getWorkno());
        if (getEmail() != null)
            o.put("email", getEmail());
        if (getLastModified() != null)
            o.put("lastModified", getLastModified().intValue());

        return o;
    }

}
