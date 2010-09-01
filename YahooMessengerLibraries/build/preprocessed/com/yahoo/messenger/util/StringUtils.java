/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.util;

import java.util.Vector;


public class StringUtils {

    public static String[] tokenize(String s, char character, boolean trimTwoChars) {

        Vector strings = new Vector();
        int index = s.indexOf(character);

        while (index != -1) {
            
            String newString = s.substring(0, trimTwoChars? index-1 : index);
            strings.addElement(newString);
            s = s.substring(index+1, s.length());
            index = s.indexOf(character);

        }

        strings.addElement(s);

        String[] returnString = new String[strings.size()];

        for (int i = 0; i < strings.size(); i++) {
            returnString[i] = (String)strings.elementAt(i);
        }

        return returnString;

    }

    public static String getValue(String s, String name) {


        String[] o = tokenize(s,'=', false);

        if (!name.equals(o[0])) {
            System.err.println("Warning: name does not match name/value detected in string ("+name+","+o[0]+")");
        }

        if (o.length != 2) {
            System.err.println("Warning: string passed in does not have only one '=' character");
        }

        return o[1];
    }

    public static String URLEncoder(String str) {
        if (str == null) {
            return null;
        }

        StringBuffer resultStr = new StringBuffer(str.length());
        char tmpChar;

        for (int ix = 0; ix < str.length(); ix++) {
            tmpChar = str.charAt(ix);
            switch (tmpChar) {
                case ' ':
                    resultStr.append("%20");
                    break;
                case '-':
                    resultStr.append("%2D");
                    break;
                case '/':
                    resultStr.append("%2F");
                    break;
                case ':':
                    resultStr.append("%3A");
                    break;
                case '=':
                    resultStr.append("%3D");
                    break;
                case '?':
                    resultStr.append("%3F");
                    break;
                case '#':
                    resultStr.append("%23");
                    break;
                case '\r':
                    resultStr.append("%0D");
                    break;
                case '\n':
                    resultStr.append("%0A");
                    break;
                default:
                    resultStr.append(tmpChar);
                    break;
            }
        }
        return resultStr.toString();
    }
}
