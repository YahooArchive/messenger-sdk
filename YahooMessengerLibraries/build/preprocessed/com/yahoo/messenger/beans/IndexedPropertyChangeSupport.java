/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.beans;

import java.util.Enumeration;
import java.util.Vector;


public class IndexedPropertyChangeSupport {

    Vector listeners;

    public IndexedPropertyChangeSupport() {
        listeners = new Vector();
    }

    public void addPropertyChangeListener(IndexedPropertyChangeListener l) {
        synchronized(this) {
            listeners.addElement(l);
        }
    }

    public void removePropertyChangeListener(IndexedPropertyChangeListener l) {
        synchronized(this) {
            listeners.removeElement(l);
        }
    }

    public void fireIndexedPropertyChange(Object id, int index, Object oldValue, Object newValue) {

        synchronized(this) {

            Enumeration e = listeners.elements();
            while (e.hasMoreElements()) {
                IndexedPropertyChangeListener l = (IndexedPropertyChangeListener)e.nextElement();
                l.propertyChanged(id, index, oldValue, newValue);
            }

        }
    }
}
