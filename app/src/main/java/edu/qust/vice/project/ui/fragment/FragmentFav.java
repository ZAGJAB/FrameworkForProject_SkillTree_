package edu.qust.vice.project.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
import org.json.JSONException;
import org.json.JSONObject;

import edu.qust.vice.project.MyApplication;
import edu.qust.vice.project.utils.NetUtil;
import tsing.zhong.fu.frameworkforproject_skilltree_.R;
import edu.qust.vice.project.utils.DialogHelper;

/**
 * Created by neokree on 16/12/14.
 *
 */
@SuppressLint("ValidFragment")
public class FragmentFav extends Fragment{
    String uid;
    String nick,sig;
    int v1,v2,v3;
    TextView nickView,sigView;
    BarChart barChart;
    public FragmentFav(String uid) {
        this.uid = uid;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fregment_index, container, false);
        nickView = (TextView) view.findViewById(R.id.nickname_nikename);
        sigView  = (TextView) view.findViewById(R.id.sig_index);
        barChart = (BarChart) view.findViewById(R.id.barchart_index);
        NetUtil.get("?c=api&_table=user_data&_interface=list&user_id=" + uid, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data").getJSONArray("items").getJSONObject(0);

                    nick = data.getString("nickname");
                    sig = data.getString("sig");
                    v1 = data.getInt("effort");
                    v2 = data.getInt("talent");
                    v3 = data.getInt("excited");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Looper looper = Looper.getMainLooper();
                MainHandler handler = new MainHandler(looper);
                Message msg = handler.obtainMessage(1, 1, 1, "");
                msg.getData().putString("cmd", "draw");
                handler.sendMessage(msg);
                super.onSuccess(statusCode, headers, response);
            }
        });
        return view;
    }
    class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ("draw".equals(msg.getData().getString("cmd"))) {
                setView();
            }
        }
    }
    public void setView() {
        nickView.setText(nick);
        sigView.setText(sig);
        barChart.addBar(new BarModel("v1", v1, 0xFFF44336));
        barChart.addBar(new BarModel("v2",v2,0xFF2196F3));
        barChart.addBar(new BarModel("v3",v3,0xFF009688));
        barChart.startAnimation();
        nickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.edit(getActivity(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                        NetUtil.post("?c=api&_table=user_data&_interface=chg_nickname&uid=" + uid + "&nnm=" + charSequence, null, new JsonHttpResponseHandler());
                        ((MyApplication) getActivity().getApplication()).u.setUname(charSequence.toString());
                    }
                }, nick).show();
            }
        });
        sigView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.edit(getActivity(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                        NetUtil.post("?c=api&_table=user_data&_interface=chg_sig&uid="+uid+"&nsg="+charSequence,null,new JsonHttpResponseHandler());
                        ((MyApplication)getActivity().getApplication()).u.setSig(charSequence.toString());
                    }
                }, sig).show();
            }
        });
    }
}
