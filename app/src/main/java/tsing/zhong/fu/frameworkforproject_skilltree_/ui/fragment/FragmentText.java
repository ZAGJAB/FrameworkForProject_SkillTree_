package tsing.zhong.fu.frameworkforproject_skilltree_.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by neokree on 16/12/14.
 */
public class FragmentText extends Fragment{
    int num;
    public FragmentText(int num) {
        this.num = num;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView text = new TextView(container.getContext());
        String[] s = new String[3];
        s[0] = "好友动态";
        s[1] = "我的好友";
        s[2] = "消息";

        text.setText("关于 "+ s[num] + " 的内容");
        text.setGravity(Gravity.CENTER);

        return text;
    }
}
