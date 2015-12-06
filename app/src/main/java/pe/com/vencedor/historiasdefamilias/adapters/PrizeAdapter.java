package pe.com.vencedor.historiasdefamilias.adapters;

import android.app.Activity;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pe.com.vencedor.historiasdefamilias.entities.Prize;

import java.util.List;

/**
 * Created by cesar on 18/08/2015.
 */
public class PrizeAdapter extends RecyclerView.Adapter<PrizeAdapter.ViewHolder>{
    private List<Prize> list;
    private Activity activity;
    private OnItemClickListener mItemClickListener;

    public PrizeAdapter(List<Prize> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    public List<Prize> getList() {
        return list;
    }

    public void setList(List<Prize> list) {
        this.list = list;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(pe.com.vencedor.historiasdefamilias.R.layout.item_prize,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Prize object = list.get(position);
        if(position %2 == 0){
            holder.llMainBody.setBackgroundResource(pe.com.vencedor.historiasdefamilias.R.color.vencedor_premio_par);
        }else{
            holder.llMainBody.setBackgroundResource(pe.com.vencedor.historiasdefamilias.R.color.vencedor_premio_impar);
        }
        holder.ivIcPrize.setImageResource(getPrizeImage(position));
        holder.tvPrizeTitle.setText(object.getName());
        holder.tvPrizeDescription.setText(object.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivIcPrize;
        public TextView tvPrizeTitle,tvPrizeDescription;
        public LinearLayout llMainBody;
        public ViewHolder (View v){
            super(v);
            ivIcPrize = (ImageView)v.findViewById(pe.com.vencedor.historiasdefamilias.R.id.ivIcPrize);
            tvPrizeTitle = (TextView)v.findViewById(pe.com.vencedor.historiasdefamilias.R.id.tvPrizeTitle);
            tvPrizeDescription = (TextView)v.findViewById(pe.com.vencedor.historiasdefamilias.R.id.tvPrizeDescription);
            llMainBody = (LinearLayout)v.findViewById(pe.com.vencedor.historiasdefamilias.R.id.llMainBody);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if(mItemClickListener != null){
                mItemClickListener.onItemClick(v,getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    public int getPrizeImage (int prize){
        TypedArray imgs = activity.getResources().obtainTypedArray(pe.com.vencedor.historiasdefamilias.R.array.prize_image);
        int id = imgs.getResourceId(prize,-1);
        imgs.recycle();
        return id;
    }
}
