package tsing.zhong.fu.frameworkforproject_skilltree_.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class NetUtil {
    private static final String BASE_URL = "https://apiapiapi.sinaapp.com/";
    private static AsyncHttpClient client = new AsyncHttpClient();
    public static void get(String url,RequestParams parmas, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), parmas,responseHandler);
    }
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}