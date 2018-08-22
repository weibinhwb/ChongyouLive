package com.example.weibin.chongyoulive.base;

import java.util.List;

/**
 * Created by weibin on 2018/8/22
 */


public class DetailLiveBean {

    /**
     * ActionStatus : OK
     * ErrorCode : 0
     * GroupInfo : [{"Appid":1400130189,"ApplyJoinOption":"NeedPermission","CreateTime":1534815371,"ErrorCode":0,"FaceUrl":"http://pdqpejgdg.bkt.clouddn.com/1534815372879","GroupId":"@TGS#25NITAMFT","Introduction":"。。","LastInfoTime":1534815371,"LastMsgTime":0,"MaxMemberNum":2000,"MemberList":[{"JoinTime":1534815371,"LastSendMsgTime":0,"Member_Account":"86-13224010650","MsgFlag":"AcceptAndNotify","MsgSeq":0,"Role":"Owner","ShutUpUntil":0}],"MemberNum":1,"Name":"好的第四次","NextMsgSeq":1,"Notification":"","OnlineMemberNum":0,"Owner_Account":"86-13224010650","ShutUpAllMember":"Off","Type":"Public"},{"Appid":1400130189,"ApplyJoinOption":"NeedPermission","CreateTime":1534831663,"ErrorCode":0,"FaceUrl":"http://pdqpejgdg.bkt.clouddn.com/1534831664805","GroupId":"@TGS#27TJ6AMF5","Introduction":"jdjdjd","LastInfoTime":1534831663,"LastMsgTime":0,"MaxMemberNum":2000,"MemberList":[{"JoinTime":1534831663,"LastSendMsgTime":0,"Member_Account":"86-13224010650","MsgFlag":"AcceptAndNotify","MsgSeq":0,"Role":"Owner","ShutUpUntil":0}],"MemberNum":1,"Name":"jdjeke","NextMsgSeq":1,"Notification":"","OnlineMemberNum":0,"Owner_Account":"86-13224010650","ShutUpAllMember":"Off","Type":"Public"},{"Appid":1400130189,"ApplyJoinOption":"NeedPermission","CreateTime":1534827081,"ErrorCode":0,"FaceUrl":"http://pdqpejgdg.bkt.clouddn.com/1534827082441","GroupId":"@TGS#2FR52AMFR","Introduction":"。。。","LastInfoTime":1534827081,"LastMsgTime":0,"MaxMemberNum":2000,"MemberList":[{"JoinTime":1534827081,"LastSendMsgTime":0,"Member_Account":"86-13224010650","MsgFlag":"AcceptAndNotify","MsgSeq":0,"Role":"Owner","ShutUpUntil":0}],"MemberNum":1,"Name":"第五次","NextMsgSeq":1,"Notification":"","OnlineMemberNum":0,"Owner_Account":"86-13224010650","ShutUpAllMember":"Off","Type":"Public"},{"Appid":1400130189,"ApplyJoinOption":"NeedPermission","CreateTime":1534845113,"ErrorCode":0,"FaceUrl":"http://pdqpejgdg.bkt.clouddn.com/1534845114696","GroupId":"@TGS#2GCBIBMFJ","Introduction":"嗯好的","LastInfoTime":1534845113,"LastMsgTime":0,"MaxMemberNum":2000,"MemberList":[{"JoinTime":1534845113,"LastSendMsgTime":0,"Member_Account":"86-13224010650","MsgFlag":"AcceptAndNotify","MsgSeq":0,"Role":"Owner","ShutUpUntil":0}],"MemberNum":1,"Name":"就是这样XP","NextMsgSeq":1,"Notification":"","OnlineMemberNum":0,"Owner_Account":"86-13224010650","ShutUpAllMember":"Off","Type":"Public"},{"Appid":1400130189,"ApplyJoinOption":"NeedPermission","CreateTime":1534864503,"ErrorCode":0,"FaceUrl":"http://pdqpejgdg.bkt.clouddn.com/1534864505243","GroupId":"@TGS#2ILIDCMFX","Introduction":"？？？？","LastInfoTime":1534864503,"LastMsgTime":0,"MaxMemberNum":2000,"MemberList":[{"JoinTime":1534864503,"LastSendMsgTime":0,"Member_Account":"86-13123006240","MsgFlag":"AcceptAndNotify","MsgSeq":0,"Role":"Owner","ShutUpUntil":0}],"MemberNum":1,"Name":"怎么回事","NextMsgSeq":1,"Notification":"","OnlineMemberNum":0,"Owner_Account":"86-13123006240","ShutUpAllMember":"Off","Type":"Public"},{"Appid":1400130189,"ApplyJoinOption":"NeedPermission","CreateTime":1534814473,"ErrorCode":0,"FaceUrl":"http://pdqpejgdg.bkt.clouddn.com/1534814474644","GroupId":"@TGS#2KJYSAMFV","Introduction":"。。。","LastInfoTime":1534814473,"LastMsgTime":0,"MaxMemberNum":2000,"MemberList":[{"JoinTime":1534814473,"LastSendMsgTime":0,"Member_Account":"86-13224010650","MsgFlag":"AcceptAndNotify","MsgSeq":0,"Role":"Owner","ShutUpUntil":0}],"MemberNum":1,"Name":"上传","NextMsgSeq":1,"Notification":"","OnlineMemberNum":0,"Owner_Account":"86-13224010650","ShutUpAllMember":"Off","Type":"Public"},{"Appid":1400130189,"ApplyJoinOption":"NeedPermission","CreateTime":1534814826,"ErrorCode":0,"FaceUrl":"http://pdqpejgdg.bkt.clouddn.com/1534814827489","GroupId":"@TGS#2TN6SAMF6","Introduction":"的","LastInfoTime":1534814826,"LastMsgTime":0,"MaxMemberNum":2000,"MemberList":[{"JoinTime":1534814826,"LastSendMsgTime":0,"Member_Account":"86-13224010650","MsgFlag":"AcceptAndNotify","MsgSeq":0,"Role":"Owner","ShutUpUntil":0}],"MemberNum":1,"Name":"第二次压缩","NextMsgSeq":1,"Notification":"","OnlineMemberNum":0,"Owner_Account":"86-13224010650","ShutUpAllMember":"Off","Type":"Public"},{"Appid":1400130189,"ApplyJoinOption":"NeedPermission","CreateTime":1534814996,"ErrorCode":0,"FaceUrl":"http://pdqpejgdg.bkt.clouddn.com/1534814998144","GroupId":"@TGS#2WLBTAMFN","Introduction":"好的","LastInfoTime":1534814996,"LastMsgTime":0,"MaxMemberNum":2000,"MemberList":[{"JoinTime":1534814996,"LastSendMsgTime":0,"Member_Account":"86-13224010650","MsgFlag":"AcceptAndNotify","MsgSeq":0,"Role":"Owner","ShutUpUntil":0}],"MemberNum":1,"Name":"第三次压缩","NextMsgSeq":1,"Notification":"","OnlineMemberNum":0,"Owner_Account":"86-13224010650","ShutUpAllMember":"Off","Type":"Public"}]
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
         * Appid : 1400130189
         * ApplyJoinOption : NeedPermission
         * CreateTime : 1534815371
         * ErrorCode : 0
         * FaceUrl : http://pdqpejgdg.bkt.clouddn.com/1534815372879
         * GroupId : @TGS#25NITAMFT
         * Introduction : 。。
         * LastInfoTime : 1534815371
         * LastMsgTime : 0
         * MaxMemberNum : 2000
         * MemberList : [{"JoinTime":1534815371,"LastSendMsgTime":0,"Member_Account":"86-13224010650","MsgFlag":"AcceptAndNotify","MsgSeq":0,"Role":"Owner","ShutUpUntil":0}]
         * MemberNum : 1
         * Name : 好的第四次
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

        public List<MemberListBean> getMemberList() {
            return MemberList;
        }

        public void setMemberList(List<MemberListBean> MemberList) {
            this.MemberList = MemberList;
        }

        public static class MemberListBean {
            /**
             * JoinTime : 1534815371
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
