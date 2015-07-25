package tsing.zhong.fu.frameworkforproject_skilltree_.model;

import java.util.List;

import tsing.zhong.fu.frameworkforproject_skilltree_.ui.ListActivity;

/**
 * Created by fuzho on 2015/7/25.
 *
 * 通过网络获取用户资源
 *
 */
abstract class UserResourceImpl {


    /**
     * 检查用户是否存在
     * @param uName 用户名
     * @return 布尔值 是否存在
     */
    public abstract boolean isExistUser(String uName);

    /**
     * 检查网络状态
     * @return 10 正常
     * @return 11 异常
     */
    public abstract int     netStatus();

    /**
     * 登陆
     * @param uName
     * @param pwd
     * @return 状态码/用户id
     *
     */
    public abstract int     Login(String uName, String pwd);

    /**
     * Detail 结构见 {@link User}
     * @param Uid
     * @return {@link tsing.zhong.fu.frameworkforproject_skilltree_.model.User.Detail}
     */
    public abstract User.Detail getDetail(String Uid);

    /**
     * 获取收藏
     * @param Uid
     * @return
     */
    public abstract List<String> getBookmarks(String Uid);

    /**
     * 根据用户名获取课程id的集合
     * @param Uid
     * @return 课程id集合
     */
    public abstract List<String> getCoursesId(String Uid);

    /**
     * 获取热门课程
     * @return 课程id集合
     */
    public abstract List<String> getHotCoursesId();

    /**
     * 通过种类获取热门问题
     * @param Kind todo: discuz
     * @return 问题id集合
     */
    public abstract List<String> getHotCoursesIdByDepart(String Kind);

    /**
     * 通过关键字搜索
     * @param keyWord 关键字
     * @return 问题id集合
     */
    public abstract List<String> searchByKeyWord(String keyWord);

    /**
     * Uid 添加Course课程,如果存在课程返回进度(第几节)
     * @param Uid
     * @param Cid
     * @return 状态值 包括 成功/失败{失败原因}
     */
    public abstract int          addCourse(String Uid,String Cid);

    /**
     * 删除课程
     * @param Uid
     * @param Cid
     * @return 是否删除成功
     */
    public abstract int          delCourse(String Uid,String Cid);

    /**
     * 根据ccid 随机获取10个问题
     * @param Ccid
     * @return 问题id集
     */
    public abstract List<String> getQuestionsId(String Ccid);

    /**
     * 根据Cid获取详细的课程内容
     * @param Cid
     * @return
     */
    public abstract Courses      getCourse(String Cid);

    /**
     * 根据Ccid获取课程详细内容
     * @param Ccid
     * @return
     */
    public abstract Class        getClass(String Ccid);

    /**
     * 根据Qid获取问题详细内容
     * @param Qid
     * @return
     */
    public abstract Question     getQuestion(String Qid);

    /**
     * 根据Qid获取问题详细内容
     * @param Cid
     * @return
     */
    public abstract List<String> getCommitsId(String Cid);


    /**
     * 根据ComId获取Commit
     * @param ComId
     * @return
     */
    public abstract Commit       getCommit(String ComId);

    /**
     * 评论某个课程
     * @param Cid
     * @return
     */
    public abstract int          commitToCourse(String Cid);

    /**
     * 收藏
     * @param Cid
     * @return
     */
    public abstract int          bookmarkToCourse(String Uid,String Cid);

    /**
     * 点赞
     * @param Cid
     * @return
     */
    public abstract int          likeToCourse(String Cid);

    /**
     * 取消点赞
     * @param Cid
     * @return
     */
    public abstract int          dislikeToCourse(String Cid);


}
