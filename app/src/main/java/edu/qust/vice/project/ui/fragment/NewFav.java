package edu.qust.vice.project.ui.fragment;

import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;

import edu.qust.vice.project.MyApplication;
import edu.qust.vice.project.model.User;
import edu.qust.vice.project.utils.NetUtil;
import tsing.zhong.fu.frameworkforproject_skilltree_.R;

/**
 * Created by fuzho on 2015/8/30.
 */
public class NewFav extends Fragment{

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_new_course, container, false);
        Button button = (Button)view.findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = ((MyApplication) getActivity().getApplication()).u;
                TextView t = (TextView) view.findViewById(R.id.name);
                int tot = Integer.parseInt(u.getV1()) +
                        Integer.parseInt(u.getV2()) +
                        Integer.parseInt(u.getV3());
                int v1 = (int) (Integer.parseInt(u.getV1()) * 1.0 / tot * 100);
                int v2 = (int) (Integer.parseInt(u.getV2()) * 1.0 / tot * 100);
                int v3 = (int) (Integer.parseInt(u.getV3()) * 1.0 / tot * 100);
                Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
                NetUtil.get("?c=api&_table=course&_interface=insert" +
                                "&course_name=" + t.getText()
                                + "&course_type_id=" + spinner.getSelectedItemPosition()
                                + "&publisher=" + u.getId()
                                + "&effort=" + v1
                                + "&talent=" + v2
                                + "&excited=" + v3, null, new JsonHttpResponseHandler() {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_LONG).show();
                                super.onFailure(statusCode, headers, responseString, throwable);
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                super.onSuccess(statusCode, headers, responseString);
                            }
                        }
                );
            }
        });
        return view;
    }
}
