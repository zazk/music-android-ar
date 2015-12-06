package pe.com.vencedor.historiasdefamilias.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pe.com.vencedor.historiasdefamilias.R;
import pe.com.vencedor.historiasdefamilias.entities.Family;
import pe.com.vencedor.historiasdefamilias.fragments.TasaicoGameFragment;
import pe.com.vencedor.historiasdefamilias.utils.Constants;

import java.util.List;

/**
 * Created by cesar on 16/08/2015.
 */
public class FamilyAdapter extends BaseAdapter {
    private FragmentActivity context;
    private List<Family> list;
    private Context context2;

    public FamilyAdapter(FragmentActivity context, List<Family> list) {
        this.context = context;
        this.list = list;
    }

    public FragmentActivity getContext() {
        return context;
    }

    public void setContext(AppCompatActivity context) {
        this.context = context;
    }

    public List<Family> getList() {
        return list;
    }

    public void setList(List<Family> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Family getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {

        final Family family = getItem(i);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_stories_list, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvFamilyName = (TextView) convertView.findViewById(R.id.tvFamilyName);
            viewHolder.ivIcoLeftStory = (ImageView) convertView.findViewById(R.id.ivIcoLeftStory);
            viewHolder.llMainBody = (LinearLayout) convertView.findViewById(R.id.llMainBody);
            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        convertView.setBackgroundResource(getFamilyColor(i));
        holder.tvFamilyName.setText(Constants.ARTICULO + family.getName());
        holder.ivIcoLeftStory.setImageResource(getFamilyImage(i));
        holder.llMainBody.setBackgroundResource(getFamilyColor(i));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LA POSITION", i + "");
                Constants.NIVEL_SELECCIONADO = i + 1;
                Constants.FAMILIA_SELECCIONADA = family;
                Fragment fragment = TasaicoGameFragment.newInstance();
                FragmentTransaction transaction = getContext().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(R.id.content_frame, fragment).commit();
            }
        });

        /*View v = LayoutInflater.from(context).inflate(R.layout.item_stories_list, null);
        SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(i));
        final Family object = list.get(i);
        v.findViewById(R.id.flPlayGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LA POSITION", i + "");
                Constants.NIVEL_SELECCIONADO = i + 1;
                Constants.FAMILIA_SELECCIONADA = object.getName();
*//*                if (object.isStatus()) {*//*
//                    Fragment fragment = TasaicoGameFragment.newInstance(i + 1, object.getName());
                Fragment fragment = TasaicoGameFragment.newInstance();
                FragmentTransaction transaction = getContext().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(R.id.content_frame, fragment).commit();
                *//*} else {
                    Fragment fragment = HurtadoGameFragment.newInstance();
                    FragmentTransaction transaction = getContext().getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.add(R.id.content_frame, fragment).commit();*//*
//                }

            }
        });
        v.findViewById(R.id.flPlaySong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.NIVEL_SELECCIONADO = i + 1;
                Constants.FAMILIA_SELECCIONADA = object.getName();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                if (object.isStatus()) {
                    if (Constants.CURRENT_USER.getNivel() == Constants.NIVEL_SELECCIONADO) {
                        Fragment fragment = MusicPlayerFragment.newInstance();
                        FragmentTransaction transaction = getContext().getSupportFragmentManager().beginTransaction();
                        transaction.addToBackStack(null);
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.add(R.id.content_frame, fragment).commit();
                    } else {
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle("Aviso");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.setMessage(Constants.MSG_SONG_VALIDATION);
                        alertDialog.show();
                    }
                    //hice esta maldad porque tengo sueño
                } else {
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle("Aviso");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.setMessage(Constants.MSG_SONG_VALIDATION);
                    alertDialog.show();
                }
            }
        });*/
        return convertView;
    }

    public static class ViewHolder {
        TextView tvFamilyName;
        LinearLayout llMainBody;
        ImageView ivIcoLeftStory;
    }

/*    @Override
    public void fillValues(int i, View view) {
        Family object = list.get(i);
        ImageView ivIcoLeftStory = (ImageView) view.findViewById(R.id.ivIcoLeftStory);
        TextView tvFamilyName = (TextView) view.findViewById(R.id.tvFamilyName);
        LinearLayout llMainBody = (LinearLayout) view.findViewById(R.id.llMainBody);
        LinearLayout expanded_menu = (LinearLayout) view.findViewById(R.id.expanded_menu);
        view.setBackgroundResource(getFamilyColor(i));
        ivIcoLeftStory.setImageResource(getFamilyImage(i));
        tvFamilyName.setText(Constants.ARTICULO + object.getName());
        llMainBody.setBackgroundResource(getFamilyColor(i));
        expanded_menu.setBackgroundResource(getFamilyColor(i));
    }*/

    public int getFamilyImage(int family) {
        TypedArray imgs = getContext().getResources().obtainTypedArray(R.array.family_image);
        int id = imgs.getResourceId(family, -1);
        imgs.recycle();
        return id;
    }

    public int getFamilyColor(int family) {
        TypedArray colors = null;
//        if (list.get(family).isStatus())
        colors = getContext().getResources().obtainTypedArray(R.array.family_colors_active);
/*        else
            colors = getContext().getResources().obtainTypedArray(R.array.family_colors);*/

        int id = colors.getResourceId(family, -1);
        colors.recycle();
        return id;
    }
}
