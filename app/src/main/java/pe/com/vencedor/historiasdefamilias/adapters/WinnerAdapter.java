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

import pe.com.vencedor.historiasdefamilias.R;
import pe.com.vencedor.historiasdefamilias.entities.Winner;

import java.util.List;

/**
 * Created by cesar on 18/08/2015.
 */
public class WinnerAdapter extends RecyclerView.Adapter<WinnerAdapter.ViewHolder>{
    private List<Winner> list;
    private Activity activity;
    private OnItemClickListener mItemClickListener;

    public WinnerAdapter(List<Winner> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    public List<Winner> getList() {
        return list;
    }

    public void setList(List<Winner> list) {
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
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_winners,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Winner object = list.get(position);
        if(position %2 == 0){
            holder.llMainBody.setBackgroundResource(R.color.vencedor_premio_par);
        }else{
            holder.llMainBody.setBackgroundResource(R.color.vencedor_premio_impar);
        }
        //holder.ivIcPrize.setImageResource(getPrizeImage(position));
        holder.tvWinnerName.setText(object.getName());
        holder.tvWinnerPoints.setText(""+object.getPoints()+" puntos");
        holder.tvWinnerPrizeType.setText("Premio "+object.getPriceType());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivIcWinner;
        public TextView tvWinnerName,tvWinnerPoints,tvWinnerPrizeType;
        public LinearLayout llMainBody;
        public ViewHolder (View v){
            super(v);
            ivIcWinner = (ImageView)v.findViewById(R.id.ivIcWinner);
            tvWinnerPoints = (TextView)v.findViewById(R.id.tvWinnerPoints);
            tvWinnerPrizeType = (TextView)v.findViewById(R.id.tvWinnerPrizeType);
            tvWinnerName = (TextView)v.findViewById(R.id.tvWinnerName);
            llMainBody = (LinearLayout)v.findViewById(R.id.llMainBody);
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
        TypedArray imgs = activity.getResources().obtainTypedArray(R.array.prize_image);
        int id = imgs.getResourceId(prize,-1);
        imgs.recycle();
        return id;
    }
}
