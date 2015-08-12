package tsing.zhong.fu.frameworkforproject_skilltree_;

import android.app.AlertDialog;
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

import java.util.List;
import java.util.Random;

import tsing.zhong.fu.frameworkforproject_skilltree_.model.Courses;
import tsing.zhong.fu.frameworkforproject_skilltree_.ui.MainActivity;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.DialogHelper;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.Loger;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> data;

    public MyAdapter(List<String> data) {
        this.data = data;
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

        Courses c = new Courses(data.get(i));
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.commit(MyApplication.mainActivity,null,null).show();
            }
        });
        viewHolder.text.setText(c.getTitle());
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
        /*
        switch (rd){
            case 0:
                viewHolder.content.setBackgroundColor(Color.rgb(0xB7, 0x1C, 0x1C));break;
            case 1:
                viewHolder.content.setBackgroundColor(Color.rgb(0x88, 0x0E, 0x4F));break;
            case 2:
                viewHolder.content.setBackgroundColor(Color.rgb(0x4A, 0x14, 0x8C));break;
            case 3:
                viewHolder.content.setBackgroundColor(Color.rgb(0x31, 0x1B, 0x92));break;
            case 4:
                viewHolder.content.setBackgroundColor(Color.rgb(0x1A, 0x23, 0x7E));break;
            case 5:
                viewHolder.content.setBackgroundColor(Color.rgb(0x00, 0x4D, 0x40));break;
            case 6:
                viewHolder.content.setBackgroundColor(Color.rgb(0x42, 0x42, 0x42));break;
            default:
                viewHolder.content.setBackgroundColor(Color.rgb(0x37, 0x47, 0x4F));break;
        }
        */
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View main;
        TextView text;
        ImageView imageView;
        LinearLayout content;

        public ViewHolder(View itemView) {
            // super这个参数一定要注意,必须为Item的根节点.否则会出现莫名的FC.
            super(itemView);
            main = itemView;
            text = (TextView) itemView.findViewById(R.id.text);
            content = (LinearLayout) itemView.findViewById(R.id.contentPic);
            imageView = (ImageView) itemView.findViewById(R.id.card_icon_1);
        }
    }
}