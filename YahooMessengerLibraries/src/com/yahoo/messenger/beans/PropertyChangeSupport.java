/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.beans;

import java.util.Enumeration;
import java.util.Vector;


public class PropertyChangeSupport {

    Vector listeners;

    public PropertyChangeSupport() {
        listeners = new Vector();
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        synchronized(this) {
            listeners.addElement(l);
        }
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        synchronized(this) {
            listeners.removeElement(l);
        }
    }

    public void firePropertyChange(Object id, Object oldValue, Object newValue) {

        synchronized(this) {

            Enumeration e = listeners.elements();
            while (e.hasMoreElements()) {
                PropertyChangeListener l = (PropertyChangeListener)e.nextElement();
                l.propertyChanged(id, oldValue, newValue);
            }

        }
    }

}
