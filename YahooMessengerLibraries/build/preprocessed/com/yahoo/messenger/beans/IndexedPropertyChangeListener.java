/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.beans;


public interface IndexedPropertyChangeListener {

    public void propertyChanged(Object id, int index, Object oldValue, Object newValue);
}
