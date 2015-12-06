package pe.com.vencedor.historiasdefamilias.adapters;

import android.app.Activity;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import pe.com.vencedor.historiasdefamilias.R;
import pe.com.vencedor.historiasdefamilias.entities.MenuItem;

import java.util.ArrayList;

/**
 * Created by cesar on 8/17/15.
 */
public class NavigationAdapter extends BaseAdapter {
    private Activity activity;
    ArrayList<MenuItem> list;

    public NavigationAdapter(Activity activity, ArrayList<MenuItem> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        MenuItem object = list.get(position);
        LayoutInflater inflator = activity.getLayoutInflater();
        if(convertView == null){
            convertView = inflator.inflate(R.layout.item_menu,null);
            holder.ivIcGo = (ImageView)convertView.findViewById(R.id.ivIcGo);
            holder.ivSectionImage = (ImageView)convertView.findViewById(R.id.ivSectionImage);
            holder.tvSectionName = (TextView)convertView.findViewById(R.id.tvSectionName);
            convertView.setTag(holder);
        }
        holder = (ViewHolder)convertView.getTag();
        holder.ivSectionImage.setImageResource(getMenuImage(position));
        holder.tvSectionName.setText(object.getName());
        return convertView;
    }

    public static class ViewHolder{
        TextView tvSectionName;
        ImageView ivSectionImage,ivIcGo;
    }

    public ArrayList<MenuItem> getList() {
        return list;
    }

    public void setList(ArrayList<MenuItem> list) {
        this.list = list;
    }

    public int getMenuImage (int prize){
        TypedArray imgs = activity.getResources().obtainTypedArray(R.array.menu_images);
        int id = imgs.getResourceId(prize,-1);
        imgs.recycle();
        return id;
    }

}
