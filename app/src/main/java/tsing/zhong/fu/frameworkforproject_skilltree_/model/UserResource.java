package tsing.zhong.fu.frameworkforproject_skilltree_.model;

import com.loopj.android.http.HttpGet;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by fuzho on 2015/7/28.
 */
public class UserResource extends UserResourceImpl {
    @Override
    public boolean isExistUser(String uName) {
        return false;
    }

    @Override
    public int netStatus() {
        return 0;
    }

    @Override
    public int Login(String uName, String pwd) {
        return 0;
    }
    @Override
    public List<String> getBookmarks(String Uid) {
        return null;
    }

    @Override
    public List<String> getCoursesId(String Uid) {
        return null;
    }

    @Override
    public List<String> getHotCoursesId() {
        return null;
    }

    @Override
    public List<String> getHotCoursesIdByDepart(String Kind) {
        return null;
    }

    @Override
    public List<String> searchByKeyWord(String keyWord) {
        return null;
    }

    @Override
    public int addCourse(String Uid, String Cid) {
        return 0;
    }

    @Override
    public int delCourse(String Uid, String Cid) {
        return 0;
    }

    @Override
    public List<String> getQuestionsId(String Ccid) {
        return null;
    }

    @Override
    public Courses getCourse(String Cid) {
        return null;
    }

    @Override
    public Class getClass(String Ccid) {
        return null;
    }

    @Override
    public Question getQuestion(String Qid) {
        return null;
    }

    @Override
    public List<String> getCommitsId(String Cid) {
        return null;
    }

    @Override
    public Commit getCommit(String ComId) {
        return null;
    }

    @Override
    public int commitToCourse(String Cid) {
        return 0;
    }

    @Override
    public int bookmarkToCourse(String Uid, String Cid) {
        return 0;
    }

    @Override
    public int likeToCourse(String Cid) {
        return 0;
    }

    @Override
    public int dislikeToCourse(String Cid) {
        return 0;
    }

}
