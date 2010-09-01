/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.data.json;

import org.json.me.JSONException;
import org.json.me.JSONObject;


public class Error {

    private Integer code;
    private String detail;
    private String description;

    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }


    //   JSON Serialization methods

    public void unserializeJSON(JSONObject o)
        throws JSONException
    {

        //  Mandatory fields

        setCode(new Integer(o.getInt("code")));
        setDetail(o.getString("detail"));
        setDescription(o.getString("description"));


    }

    public JSONObject serializeJSON()
        throws JSONException
    {

        JSONObject o = new JSONObject();

        //  Mandatory items

        o.put("code", getCode());
        o.put("detail", getDetail());
        o.put("description", getDescription());

        return o;
    }
}
