package tsing.zhong.fu.frameworkforproject_skilltree_.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import tsing.zhong.fu.frameworkforproject_skilltree_.MyApplication;
import tsing.zhong.fu.frameworkforproject_skilltree_.R;
import tsing.zhong.fu.frameworkforproject_skilltree_.ui.ActionBarActivity;

/**
 * Created by fuzho on 2015/7/28.
 * Material 弹出框
 */
public class DialogHelper {
    static public MaterialDialog.Builder commit(Activity activity,
                                                MaterialDialog.InputCallback inputCallback,
                                                OnCancelListener onCancelListener) {
        return new MaterialDialog
                  .Builder(activity)
                  .title(R.string.input_commit)
                  .inputMaxLength(140, R.color.material_blue_500)
                  .input("请输入评论", null, inputCallback)
                  .negativeText("取消")
                  .positiveText("评论")
                  .cancelable(true)
                  .cancelListener(onCancelListener);
    }
    static public MaterialDialog.Builder login(Activity activity,
                                               MaterialDialog.ButtonCallback callback) {
        if (callback == null) callback = defalutCallBack;
        return new MaterialDialog
                  .Builder(activity)
                  .title(R.string.title_activity_login)
                  .customView(R.layout.mini_login, true)
                  .negativeText("取消")
                  .positiveText("登陆")
                  .cancelable(true)
                  .callback(callback);
    }

    /**
     * example area
     * 一些监听器的示范
     *
     */
    static MaterialDialog.ButtonCallback defalutCallBack = new MaterialDialog.ButtonCallback() {
        @Override
        public void onAny(MaterialDialog dialog) {
            super.onAny(dialog);
            Log.i("fzq","onAny");
        }

        @Override
        public void onPositive(MaterialDialog dialog) {
            super.onPositive(dialog);
            makeText("Positive");
        }

        @Override
        public void onNegative(MaterialDialog dialog) {
            super.onNegative(dialog);
            makeText("Negative");
        }

        @Override
        public void onNeutral(MaterialDialog dialog) {
            super.onNeutral(dialog);
            makeText("onNeutral");
        }
    };

    static void makeText(String msg) {
        Toast.makeText(MyApplication.mainActivity,msg,Toast.LENGTH_LONG).show();
    }

}
