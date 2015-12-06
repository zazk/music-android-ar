package pe.com.vencedor.historiasdefamilias.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.com.vencedor.historiasdefamilias.entities.Ranking;

import java.util.List;

/**
 * Created by cesar on 18/08/2015.
 */
public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder>{
    private List<Ranking> list;
    private Activity activity;
    private OnItemClickListener mItemClickListener;

    public RankingAdapter(Activity activity, List<Ranking> list) {
        this.activity = activity;
        this.list = list;
    }

    public List<Ranking> getList() {
        return list;
    }

    public void setList(List<Ranking> list) {
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
        final View view = LayoutInflater.from(parent.getContext()).inflate(pe.com.vencedor.historiasdefamilias.R.layout.item_ranking,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ranking object = list.get(position);
        if(position %2 == 0){
            holder.tvPoints.setBackgroundResource(pe.com.vencedor.historiasdefamilias.R.color.vencedor_light_sky_blue);
            holder.tvUsername.setBackgroundResource(pe.com.vencedor.historiasdefamilias.R.color.vencedor_light_sky_blue);
        }else{
            holder.tvPoints.setBackgroundResource(pe.com.vencedor.historiasdefamilias.R.color.white);
            holder.tvUsername.setBackgroundResource(pe.com.vencedor.historiasdefamilias.R.color.white);
        }
        holder.tvPoints.setText("" + object.getPoints() + " Puntos");
        holder.tvUsername.setText(object.getUsername());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvUsername,tvPoints;
        public ViewHolder (View v){
            super(v);
            tvUsername = (TextView)v.findViewById(pe.com.vencedor.historiasdefamilias.R.id.tvUsername);
            tvPoints = (TextView)v.findViewById(pe.com.vencedor.historiasdefamilias.R.id.tvPoints);
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
}
