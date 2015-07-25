package tsing.zhong.fu.frameworkforproject_skilltree_.model;

import java.util.List;

import tsing.zhong.fu.frameworkforproject_skilltree_.ui.ListActivity;

/**
 * Created by fuzho on 2015/7/25.
 *
 * ͨ�������ȡ�û���Դ
 *
 */
abstract class UserResourceImpl {


    /**
     * ����û��Ƿ����
     * @param uName �û���
     * @return ����ֵ �Ƿ����
     */
    public abstract boolean isExistUser(String uName);

    /**
     * �������״̬
     * @return 10 ����
     * @return 11 �쳣
     */
    public abstract int     netStatus();

    /**
     * ��½
     * @param uName
     * @param pwd
     * @return ״̬��/�û�id
     *
     */
    public abstract int     Login(String uName, String pwd);

    /**
     * Detail �ṹ�� {@link User}
     * @param Uid
     * @return {@link tsing.zhong.fu.frameworkforproject_skilltree_.model.User.Detail}
     */
    public abstract User.Detail getDetail(String Uid);

    /**
     * ��ȡ�ղ�
     * @param Uid
     * @return
     */
    public abstract List<String> getBookmarks(String Uid);

    /**
     * �����û�����ȡ�γ�id�ļ���
     * @param Uid
     * @return �γ�id����
     */
    public abstract List<String> getCoursesId(String Uid);

    /**
     * ��ȡ���ſγ�
     * @return �γ�id����
     */
    public abstract List<String> getHotCoursesId();

    /**
     * ͨ�������ȡ��������
     * @param Kind todo: discuz
     * @return ����id����
     */
    public abstract List<String> getHotCoursesIdByDepart(String Kind);

    /**
     * ͨ���ؼ�������
     * @param keyWord �ؼ���
     * @return ����id����
     */
    public abstract List<String> searchByKeyWord(String keyWord);

    /**
     * Uid ���Course�γ�,������ڿγ̷��ؽ���(�ڼ���)
     * @param Uid
     * @param Cid
     * @return ״ֵ̬ ���� �ɹ�/ʧ��{ʧ��ԭ��}
     */
    public abstract int          addCourse(String Uid,String Cid);

    /**
     * ɾ���γ�
     * @param Uid
     * @param Cid
     * @return �Ƿ�ɾ���ɹ�
     */
    public abstract int          delCourse(String Uid,String Cid);

    /**
     * ����ccid �����ȡ10������
     * @param Ccid
     * @return ����id��
     */
    public abstract List<String> getQuestionsId(String Ccid);

    /**
     * ����Cid��ȡ��ϸ�Ŀγ�����
     * @param Cid
     * @return
     */
    public abstract Courses      getCourse(String Cid);

    /**
     * ����Ccid��ȡ�γ���ϸ����
     * @param Ccid
     * @return
     */
    public abstract Class        getClass(String Ccid);

    /**
     * ����Qid��ȡ������ϸ����
     * @param Qid
     * @return
     */
    public abstract Question     getQuestion(String Qid);

    /**
     * ����Qid��ȡ������ϸ����
     * @param Cid
     * @return
     */
    public abstract List<String> getCommitsId(String Cid);


    /**
     * ����ComId��ȡCommit
     * @param ComId
     * @return
     */
    public abstract Commit       getCommit(String ComId);

    /**
     * ����ĳ���γ�
     * @param Cid
     * @return
     */
    public abstract int          commitToCourse(String Cid);

    /**
     * �ղ�
     * @param Cid
     * @return
     */
    public abstract int          bookmarkToCourse(String Uid,String Cid);

    /**
     * ����
     * @param Cid
     * @return
     */
    public abstract int          likeToCourse(String Cid);

    /**
     * ȡ������
     * @param Cid
     * @return
     */
    public abstract int          dislikeToCourse(String Cid);


}
