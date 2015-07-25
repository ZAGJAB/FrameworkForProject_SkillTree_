package tsing.zhong.fu.frameworkforproject_skilltree_.model;

import java.util.List;

/**
 * Created by fuzho on 2015/7/23.
 *
 * 用户的model
 */
public class User {

    private  boolean Online = false;
    private  Detail mDetail = null;
    private  List<String> courseSet;


    public boolean isOnline () {
        return Online;
    }

    public LogStatus Login(String u,String p) {
        if (Online) return LogStatus.Alreadyonline;
        if (true) {  //TODO
            return LogStatus.Ok;
        } else {
            return LogStatus.Failed_PwdWorng;
        }
    };



    public enum LogStatus
    {
        Ok,
        Alreadyonline,
        Failed_NotSuchUser,
        Failed_PwdWorng,
        Failed_NetWorkError
    };

    /**
     * name  姓名
     * signs 个性签名
     * courses_id 课程id
     */
    public class Detail {
        public String name;
        public String signs;
        public List<String> courses_id;
    }
}
