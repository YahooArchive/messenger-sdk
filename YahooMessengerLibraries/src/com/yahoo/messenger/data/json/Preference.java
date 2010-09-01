/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.data.json;

import org.json.me.JSONException;
import org.json.me.JSONObject;


public class Preference {

    private String key;
    private String cat;
    private String value;

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the cat
     */
    public String getCategory() {
        return cat;
    }

    /**
     * @param cat the cat to set
     */
    public void setCategory(String cat) {
        this.cat = cat;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }


    //   JSON Serialization methods

    public void unserializeJSON(JSONObject o)
        throws JSONException
    {

        //  Mandatory fields

        setKey(o.getString("key"));
        setCategory(o.getString("cat"));
        setValue(o.getString("value"));

    }

    public JSONObject serializeJSON()
        throws JSONException
    {

        JSONObject o = new JSONObject();

        //  Mandatory items

        o.put("key", getKey());
        o.put("cat", getCategory());
        o.put("value", getValue());

        return o;
    }

}
