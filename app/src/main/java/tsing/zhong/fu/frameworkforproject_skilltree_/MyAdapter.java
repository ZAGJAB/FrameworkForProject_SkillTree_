package tsing.zhong.fu.frameworkforproject_skilltree_;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.loopj.android.http.HttpGet;

import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tsing.zhong.fu.frameworkforproject_skilltree_.model.Courses;
import tsing.zhong.fu.frameworkforproject_skilltree_.ui.MainActivity;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.DialogHelper;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.Loger;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> data;
    private List<Courses> detail;
    private Context mContext;
    public MyAdapter(List<String> data,Context context) {
        this.data = data;
        this.mContext = context;
        detail = new ArrayList<Courses>();
    }

    private View.OnClickListener onClickListener = null;
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 加载Item的布局.布局中用到的真正的CardView.
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        // ViewHolder参数一定要是Item的Root节点.
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        if (i >= detail.size()) {
            detail.add(i,new Courses(data.get(i),this,i));
        }
        Courses c = detail.get(i);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.commit(mContext,null,null).show();
            }
        });


        viewHolder.text.setText(c.getTitle());
        viewHolder.kind.setText(c.getType());
        viewHolder.text2.setText(c.getTitle2());
        int rd = Integer.parseInt(c.getPicName());
        viewHolder.main.setOnClickListener(this.onClickListener);
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
        }
    }
}