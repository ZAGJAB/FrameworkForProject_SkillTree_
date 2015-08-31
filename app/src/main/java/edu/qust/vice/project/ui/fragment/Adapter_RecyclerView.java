package edu.qust.vice.project.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tsing.zhong.fu.frameworkforproject_skilltree_.R;


public class Adapter_RecyclerView extends RecyclerView.Adapter<Adapter_RecyclerView.ViewHolder> {
    public ArrayList<FragmentFav.Data> datas = null;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnRecyclerViewMenuClickListener monMenuClickListener = null;

    public Adapter_RecyclerView(ArrayList<FragmentFav.Data> datas) {
        this.datas = datas;
    }

    /**
     * ������View����LayoutManager������
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weight_demo_recyclerview_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        //��������Viewע�����¼�
        vh.mTextView.setOnClickListener(new itemClick());
        vh.del.setOnClickListener(new menuClick());
        vh.add.setOnClickListener(new menuClick());
        vh.chenge.setOnClickListener(new menuClick());

        return vh;
    }

    /**
     * �������������а󶨵Ĳ���
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(datas.get(position).getName());
        //�����ݱ�����itemView��Tag�У��Ա���ʱ���л�ȡ
        holder.itemView.setTag(datas.get(position).getId());

        holder.add.setTag(position);
        holder.del.setTag(position);
        holder.chenge.setTag(position);
    }

    /**
     * ��ȡ���ݵ�����
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * ������Ŀ����
     *
     * @param listener
     */
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     * ���ò˵�����
     *
     * @param listener
     */
    public void setOnMenuClickListener(OnRecyclerViewMenuClickListener listener) {
        this.monMenuClickListener = listener;
    }


    /**
     * ��Ŀ����¼��ӿ�
     */
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    /**
     * �˵�����¼��ӿ�
     */
    public interface OnRecyclerViewMenuClickListener {
        void onMenuClick(View view, int position);
    }

    /**
     * �Զ����ViewHolder������ÿ��Item�ĵ����н���Ԫ��
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView del;
        private ImageView add;
        private ImageView chenge;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text);
            del = (ImageView) view.findViewById(R.id.del);
            add = (ImageView) view.findViewById(R.id.add);
            chenge = (ImageView) view.findViewById(R.id.chenge);
        }
    }

    /**
     * ��Ŀ����
     */
    class itemClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                //ע������ʹ��getTag������ȡ����
                mOnItemClickListener.onItemClick(v, ((TextView) v).getText().toString());
            }
        }
    }

    /**
     * �˵�����
     */
    class menuClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (monMenuClickListener != null) {
                //ע������ʹ��getTag������ȡ����
                monMenuClickListener.onMenuClick(v, (Integer) v.getTag());
            }
        }
    }

}