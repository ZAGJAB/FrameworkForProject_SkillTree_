package tsing.zhong.fu.frameworkforproject_skilltree_.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import tsing.zhong.fu.frameworkforproject_skilltree_.MyApplication;
import tsing.zhong.fu.frameworkforproject_skilltree_.R;
import tsing.zhong.fu.frameworkforproject_skilltree_.ui.ActionBarActivity;

/**
 * Created by fuzho on 2015/7/28.
 * Material 弹出框
 */
public class DialogHelper {
    static public MaterialDialog.Builder commit(Context context,
                                                MaterialDialog.InputCallback inputCallback,
                                                OnCancelListener onCancelListener) {
        if (inputCallback==null) inputCallback = defalutInputCallback;
        return new MaterialDialog
                  .Builder(context)
                  .title(R.string.input_commit)
                  .inputMaxLength(140, R.color.material_blue_500)
                  .input(R.string.input_commit_hint, R.string.empty, inputCallback)
                  .negativeText("取消")
                  .positiveText("评论")
                  .cancelable(true)
                  .cancelListener(onCancelListener);
    }
    static public MaterialDialog.Builder login(Context context,
                                               MaterialDialog.ButtonCallback callback) {
        if (callback == null) callback = defalutCallBack;
        return new MaterialDialog
                  .Builder(context)
                  .title(R.string.title_activity_login)
                  .customView(R.layout.mini_login, true)
                  .negativeText("取消")
                  .positiveText("登陆")
                  .cancelable(true)
                  .callback(callback);
    }
    static public MaterialDialog.Builder process(Context context) {
        return new MaterialDialog
                .Builder(context)
                .title("登陆中,请等待...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .autoDismiss(false);
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
    static MaterialDialog.InputCallback defalutInputCallback = new MaterialDialog.InputCallback() {
        @Override
        public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
            makeText("OnInput Callback");
        }
    };

    static void makeText(String msg) {
        Toast.makeText(MyApplication.mainActivity,msg,Toast.LENGTH_LONG).show();
    }

}