package com.example.weibin.chongyoulive.bean;

import java.util.List;

/**
 * Created by weibin on 2018/8/22
 */


public class DetailLiveBean {


    /**
     * ActionStatus : OK
     * ErrorCode : 0
     * GroupInfo : [{"AppDefinedData":[{"Key":"live_owner","Value":"黄松艺"},{"Key":"live_outline","Value":"好的"},{"Key":"live_intro","Value":"不"},{"Key":"live_own_intr","Value":"开讲啦"},{"Key":"live_time","Value":"2018年8月4日"}],"Appid":1400130189,"ApplyJoinOption":"FreeAccess","CreateTime":1535298059,"ErrorCode":0,"FaceUrl":"http://pdqpejgdg.bkt.clouddn.com/1535298059764","GroupId":"@TGS#2JMKKQMFR","Introduction":"","LastInfoTime":1535298059,"LastMsgTime":0,"MaxMemberNum":2000,"MemberList":[{"JoinTime":1535298059,"LastSendMsgTime":0,"Member_Account":"86-13224010650","MsgFlag":"AcceptAndNotify","MsgSeq":0,"Role":"Owner","ShutUpUntil":0}],"MemberNum":1,"Name":"随便","NextMsgSeq":1,"Notification":"","OnlineMemberNum":0,"Owner_Account":"86-13224010650","ShutUpAllMember":"Off","Type":"Public"}]
     */

    private String ActionStatus;
    private int ErrorCode;
    private List<GroupInfoBean> GroupInfo;

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

    public List<GroupInfoBean> getGroupInfo() {
        return GroupInfo;
    }

    public void setGroupInfo(List<GroupInfoBean> GroupInfo) {
        this.GroupInfo = GroupInfo;
    }

    public static class GroupInfoBean {
        /**
         * AppDefinedData : [{"Key":"live_owner","Value":"黄松艺"},{"Key":"live_outline","Value":"好的"},{"Key":"live_intro","Value":"不"},{"Key":"live_own_intr","Value":"开讲啦"},{"Key":"live_time","Value":"2018年8月4日"}]
         * Appid : 1400130189
         * ApplyJoinOption : FreeAccess
         * CreateTime : 1535298059
         * ErrorCode : 0
         * FaceUrl : http://pdqpejgdg.bkt.clouddn.com/1535298059764
         * GroupId : @TGS#2JMKKQMFR
         * Introduction :
         * LastInfoTime : 1535298059
         * LastMsgTime : 0
         * MaxMemberNum : 2000
         * MemberList : [{"JoinTime":1535298059,"LastSendMsgTime":0,"Member_Account":"86-13224010650","MsgFlag":"AcceptAndNotify","MsgSeq":0,"Role":"Owner","ShutUpUntil":0}]
         * MemberNum : 1
         * Name : 随便
         * NextMsgSeq : 1
         * Notification :
         * OnlineMemberNum : 0
         * Owner_Account : 86-13224010650
         * ShutUpAllMember : Off
         * Type : Public
         */

        private int Appid;
        private String ApplyJoinOption;
        private int CreateTime;
        private int ErrorCode;
        private String FaceUrl;
        private String GroupId;
        private String Introduction;
        private int LastInfoTime;
        private int LastMsgTime;
        private int MaxMemberNum;
        private int MemberNum;
        private String Name;
        private int NextMsgSeq;
        private String Notification;
        private int OnlineMemberNum;
        private String Owner_Account;
        private String ShutUpAllMember;
        private String Type;
        private List<AppDefinedDataBean> AppDefinedData;
        private List<MemberListBean> MemberList;

        public int getAppid() {
            return Appid;
        }

        public void setAppid(int Appid) {
            this.Appid = Appid;
        }

        public String getApplyJoinOption() {
            return ApplyJoinOption;
        }

        public void setApplyJoinOption(String ApplyJoinOption) {
            this.ApplyJoinOption = ApplyJoinOption;
        }

        public int getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(int CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getErrorCode() {
            return ErrorCode;
        }

        public void setErrorCode(int ErrorCode) {
            this.ErrorCode = ErrorCode;
        }

        public String getFaceUrl() {
            return FaceUrl;
        }

        public void setFaceUrl(String FaceUrl) {
            this.FaceUrl = FaceUrl;
        }

        public String getGroupId() {
            return GroupId;
        }

        public void setGroupId(String GroupId) {
            this.GroupId = GroupId;
        }

        public String getIntroduction() {
            return Introduction;
        }

        public void setIntroduction(String Introduction) {
            this.Introduction = Introduction;
        }

        public int getLastInfoTime() {
            return LastInfoTime;
        }

        public void setLastInfoTime(int LastInfoTime) {
            this.LastInfoTime = LastInfoTime;
        }

        public int getLastMsgTime() {
            return LastMsgTime;
        }

        public void setLastMsgTime(int LastMsgTime) {
            this.LastMsgTime = LastMsgTime;
        }

        public int getMaxMemberNum() {
            return MaxMemberNum;
        }

        public void setMaxMemberNum(int MaxMemberNum) {
            this.MaxMemberNum = MaxMemberNum;
        }

        public int getMemberNum() {
            return MemberNum;
        }

        public void setMemberNum(int MemberNum) {
            this.MemberNum = MemberNum;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getNextMsgSeq() {
            return NextMsgSeq;
        }

        public void setNextMsgSeq(int NextMsgSeq) {
            this.NextMsgSeq = NextMsgSeq;
        }

        public String getNotification() {
            return Notification;
        }

        public void setNotification(String Notification) {
            this.Notification = Notification;
        }

        public int getOnlineMemberNum() {
            return OnlineMemberNum;
        }

        public void setOnlineMemberNum(int OnlineMemberNum) {
            this.OnlineMemberNum = OnlineMemberNum;
        }

        public String getOwner_Account() {
            return Owner_Account;
        }

        public void setOwner_Account(String Owner_Account) {
            this.Owner_Account = Owner_Account;
        }

        public String getShutUpAllMember() {
            return ShutUpAllMember;
        }

        public void setShutUpAllMember(String ShutUpAllMember) {
            this.ShutUpAllMember = ShutUpAllMember;
        }

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public List<AppDefinedDataBean> getAppDefinedData() {
            return AppDefinedData;
        }

        public void setAppDefinedData(List<AppDefinedDataBean> AppDefinedData) {
            this.AppDefinedData = AppDefinedData;
        }

        public List<MemberListBean> getMemberList() {
            return MemberList;
        }

        public void setMemberList(List<MemberListBean> MemberList) {
            this.MemberList = MemberList;
        }

        public static class AppDefinedDataBean {
            /**
             * Key : live_owner
             * Value : 黄松艺
             */

            private String Key;
            private String Value;

            public String getKey() {
                return Key;
            }

            public void setKey(String Key) {
                this.Key = Key;
            }

            public String getValue() {
                return Value;
            }

            public void setValue(String Value) {
                this.Value = Value;
            }
        }

        public static class MemberListBean {
            /**
             * JoinTime : 1535298059
             * LastSendMsgTime : 0
             * Member_Account : 86-13224010650
             * MsgFlag : AcceptAndNotify
             * MsgSeq : 0
             * Role : Owner
             * ShutUpUntil : 0
             */

            private int JoinTime;
            private int LastSendMsgTime;
            private String Member_Account;
            private String MsgFlag;
            private int MsgSeq;
            private String Role;
            private int ShutUpUntil;

            public int getJoinTime() {
                return JoinTime;
            }

            public void setJoinTime(int JoinTime) {
                this.JoinTime = JoinTime;
            }

            public int getLastSendMsgTime() {
                return LastSendMsgTime;
            }

            public void setLastSendMsgTime(int LastSendMsgTime) {
                this.LastSendMsgTime = LastSendMsgTime;
            }

            public String getMember_Account() {
                return Member_Account;
            }

            public void setMember_Account(String Member_Account) {
                this.Member_Account = Member_Account;
            }

            public String getMsgFlag() {
                return MsgFlag;
            }

            public void setMsgFlag(String MsgFlag) {
                this.MsgFlag = MsgFlag;
            }

            public int getMsgSeq() {
                return MsgSeq;
            }

            public void setMsgSeq(int MsgSeq) {
                this.MsgSeq = MsgSeq;
            }

            public String getRole() {
                return Role;
            }

            public void setRole(String Role) {
                this.Role = Role;
            }

            public int getShutUpUntil() {
                return ShutUpUntil;
            }

            public void setShutUpUntil(int ShutUpUntil) {
                this.ShutUpUntil = ShutUpUntil;
            }
        }
    }
}
