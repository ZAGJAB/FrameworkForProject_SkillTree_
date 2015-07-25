package tsing.zhong.fu.frameworkforproject_skilltree_;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import tsing.zhong.fu.frameworkforproject_skilltree_.ui.MainActivity;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.Loger;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> data;

    public MyAdapter(List<String> data) {
        this.data = data;
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


        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("fzq", "clicked!!");
                Toast.makeText(MyApplication.mainActivity,"clicked!!",Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.text.setText(data.get(i));
        int rd = new Random().nextInt(6);
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