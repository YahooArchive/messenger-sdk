/**
 * Copyright (c) 2009-2010, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 * http://searchmarketing.yahoo.com/developer/docs/license.txt
 */

package com.yahoo.messenger.manager.data;

import com.yahoo.messenger.data.notification.json.ResponseList;


public class NotificationData {

    private Integer pendingMsg;
    private Integer syncStatus;
    private ResponseList responseList;

    /**
     * @return the pendingMsg
     */
    public Integer getPendingMsg() {
        return pendingMsg;
    }

    /**
     * @param pendingMsg the pendingMsg to set
     */
    public void setPendingMsg(Integer pendingMsg) {
        this.pendingMsg = pendingMsg;
    }

    /**
     * @return the syncStatus
     */
    public Integer getSyncStatus() {
        return syncStatus;
    }

    /**
     * @param syncStatus the syncStatus to set
     */
    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    /**
     * @return the responseList
     */
    public ResponseList getResponseList() {
        return responseList;
    }

    /**
     * @param responseList the responseList to set
     */
    public void setResponseList(ResponseList responseList) {
        this.responseList = responseList;
    }



}
