package tsing.zhong.fu.frameworkforproject_skilltree_;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.HttpGet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import tsing.zhong.fu.frameworkforproject_skilltree_.model.Courses;
import tsing.zhong.fu.frameworkforproject_skilltree_.model.User;
import tsing.zhong.fu.frameworkforproject_skilltree_.ui.ActionBarActivity;
import tsing.zhong.fu.frameworkforproject_skilltree_.ui.FlexibleSpaceWithImageWithViewPagerTabActivity;
import tsing.zhong.fu.frameworkforproject_skilltree_.ui.MainActivity;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.DialogHelper;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.Loger;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.NetUtil;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> data;
    private List<Courses> detail;
    private Activity mActivity;
    private User u;
    public MyAdapter(List<String> data,Activity mActivity) {
        this.data = data;
        detail = new ArrayList<Courses>();
        this.mActivity = mActivity;
        u = ((MyApplication)mActivity.getApplication()).u;
    }

    private View.OnClickListener onClickListener = null;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 加载Item的布局.布局中用到的真正的CardView.
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        // ViewHolder参数一定要是Item的Root节点.
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        final int pos = i;
        if (i >= detail.size()) {
            detail.add(i,new Courses(data.get(i),this,i));
        }
        final Courses c = detail.get(i);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.commit(mActivity, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                        NetUtil.post("?c=api&_table=commet&_interface=insert&course_id=" + c.getId() + "&author_id=" + u.getId() + "&commet="
                                + charSequence.toString() + "&time=" + new Date().getTime(), null, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                Toast.makeText(mActivity,"评论成功",Toast.LENGTH_LONG).show();
                            }
                            @Override
                            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                                Toast.makeText(mActivity,"评论失败",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }, null).show();
            }
        });
        viewHolder.text.setText(c.getTitle());
        viewHolder.kind.setText(c.getType());
        viewHolder.text2.setText(c.getTitle2());
        int rd = Integer.parseInt(c.getPicName());

        if (u.getCourseIdSet().indexOf(data.get(i))!=-1) {
            viewHolder.imageView2.setImageResource(R.drawable.ic_favorite_border2_black_48dp);
            c.isInList = true;
        } else {
            viewHolder.imageView2.setImageResource(R.drawable.ic_favorite_border_black_48dp);
            c.isInList = false;
        }
        viewHolder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, FlexibleSpaceWithImageWithViewPagerTabActivity.class);
                intent.putExtra("uid",u.getId());
                intent.putExtra("cid",c.getId());
                intent.putExtra("tit",c.getTitle());
                mActivity.startActivity(intent);
            }
        });
        switch (rd){
            case 0:
                viewHolder.content.setBackgroundResource(R.drawable.card00);break;
            case 1:
                viewHolder.content.setBackgroundResource(R.drawable.card01);break;
            case 2:
                viewHolder.content.setBackgroundResource(R.drawable.card02);break;
            case 3:
                viewHolder.content.setBackgroundResource(R.drawable.card03);break;
            case 4:
                viewHolder.content.setBackgroundResource(R.drawable.card04);break;
            case 5:
                viewHolder.content.setBackgroundResource(R.drawable.card05);break;
            case 6:
                viewHolder.content.setBackgroundResource(R.drawable.card06);break;
            default:
                viewHolder.content.setBackgroundResource(R.drawable.card00);break;
        }
        viewHolder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c.isInList) {
                  //remove
                    NetUtil.post("?c=api&_table=collect&_interface=remove&user_id=" +
                            u.getId() + "&course_id=" + c.getId(), null, new JsonHttpResponseHandler());
                    if (u.getCourseIdSet().indexOf(c.getId())!=-1) {
                        u.getCourseIdSet().remove(u.getCourseIdSet().indexOf(c.getId()));
                    }

                    notifyDataSetChanged();
                } else {
                  //add
                    NetUtil.post("?c=api&_table=collect&_interface=insert&user_id="+
                            u.getId()+"&course_id="+c.getId(),null,new JsonHttpResponseHandler());
                    u.getCourseIdSet().add(c.getId());


                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View main;
        TextView text;
        TextView kind;
        TextView text2;
        ImageView imageView;
        ImageView imageView2;
        ImageView imageView3;
        LinearLayout content;

        public ViewHolder(View itemView) {
            // super这个参数一定要注意,必须为Item的根节点.否则会出现莫名的FC.
            super(itemView);
            main = itemView;
            text = (TextView) itemView.findViewById(R.id.text);
            text2 = (TextView) itemView.findViewById(R.id.decription);
            kind = (TextView) itemView.findViewById(R.id.kind);
            content = (LinearLayout) itemView.findViewById(R.id.contentPic);
            imageView = (ImageView) itemView.findViewById(R.id.card_icon_1);
            imageView2 = (ImageView) itemView.findViewById(R.id.card_icon_2);
            imageView3 = (ImageView) itemView.findViewById(R.id.card_icon_3);
        }
    }
}