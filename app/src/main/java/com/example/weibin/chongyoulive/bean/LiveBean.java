package com.example.weibin.chongyoulive.bean;

import java.util.List;

public class LiveBean {


    /**
     * ActionStatus : OK
     * ErrorCode : 0
     * GroupIdList : [{"GroupId":"@TGS#2OD2S7LFA"},{"GroupId":"@TGS#27G2S7LFK"},{"GroupId":"@TGS#2DJ2S7LF2"}]
     * TotalCount : 3
     */

    private String ActionStatus;
    private int ErrorCode;
    private int TotalCount;
    private List<GroupIdListBean> GroupIdList;

    public String getActionStatus() {
        return ActionStatus;
    }

    public void setActionStatus(String ActionStatus) {
        this.ActionStatus = ActionStatus;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public List<GroupIdListBean> getGroupIdList() {
        return GroupIdList;
    }

    public void setGroupIdList(List<GroupIdListBean> GroupIdList) {
        this.GroupIdList = GroupIdList;
    }

    public static class GroupIdListBean {
        /**
         * GroupId : @TGS#2OD2S7LFA
         */

        private String GroupId;

        public String getGroupId() {
            return GroupId;
        }

        public void setGroupId(String GroupId) {
            this.GroupId = GroupId;
        }
    }
}
