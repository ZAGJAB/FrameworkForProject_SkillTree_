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
 *
 */
public class DialogHelper {
    static public MaterialDialog.Builder commit(Context context,
                                                MaterialDialog.InputCallback inputCallback,
                                                OnCancelListener onCancelListener) {
        //if (inputCallback==null) inputCallback = defalutInputCallback;
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
    static public MaterialDialog.Builder edit(Context context,
                                                MaterialDialog.InputCallback inputCallback,
                                                String org) {
        //if (inputCallback==null) inputCallback = defalutInputCallback;
        return new MaterialDialog
                .Builder(context)
                .title("请输入修改内容")
                .inputMaxLength(140, R.color.material_blue_500)
                .input(org,"",false,inputCallback)
                .negativeText("取消")
                .positiveText("确定");
    }
    static public MaterialDialog.Builder search(Context context, MaterialDialog.InputCallback inputCallback) {
        return new MaterialDialog.Builder(context)
                .title("搜索")
                .inputMaxLength(50, R.color.material_blue_500)
                .input(R.string.input_commit_hint, R.string.empty, inputCallback)
                .negativeText("取消")
                .positiveText("搜索")
                .cancelable(true);
    }
    static public MaterialDialog.Builder login(Context context,
                                               MaterialDialog.ButtonCallback callback) {
        if (callback == null) callback = defalutCallBack;
        return new MaterialDialog
                  .Builder(context)
                  .title(R.string.title_activity_login)
                  .customView(R.layout.mini_login, true)
                  .negativeText("注册")
                  .positiveText("登陆")
                  .cancelable(true)
                  .callback(callback)
                .autoDismiss(true);
    }
    static public MaterialDialog.Builder process(Context context) {
        return new MaterialDialog
                .Builder(context)
                .title("登陆")
                .content("登陆中,请等待...")
                .progress(true,0)
                .autoDismiss(false)
                .cancelable(false);
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
