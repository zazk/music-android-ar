package pe.com.vencedor.historiasdefamilias.fragments;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.analytics.GoogleAnalytics;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pe.com.vencedor.historiasdefamilias.activities.TutorialActivity;
import pe.com.vencedor.historiasdefamilias.adapters.FamilyAdapter;
import pe.com.vencedor.historiasdefamilias.entities.Family;
import pe.com.vencedor.historiasdefamilias.utils.Constants;
import pe.com.vencedor.historiasdefamilias.utils.Utils;

public class SongRecordFragment extends Fragment {

    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.toolbar)
    Toolbar toolbar;
    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.lvStories)
    ListView lvStories;

    private DrawerLayout drawerLayout;
    private FamilyAdapter adapter;

    public static Fragment newInstance() {
        return new SongRecordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(pe.com.vencedor.historiasdefamilias.R.layout.fragment_song_record, container, false);
        drawerLayout = (DrawerLayout) getActivity().findViewById(pe.com.vencedor.historiasdefamilias.R.id.drawer_layout);
        Utils.windowEvent(getActivity(), "Juega", "Pantalla", "Viendo");
        ButterKnife.inject(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (toolbar != null) {
            toolbar.setNavigationIcon(pe.com.vencedor.historiasdefamilias.R.drawable.ic_menu_white);
            activity.setSupportActionBar(toolbar);
            assert activity.getSupportActionBar() != null;
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setTitle("Historias de Familias");
        }

        if (Constants.ARRAY_LIST_FAMILIES.size() == 0) {
            Constants.ARRAY_LIST_FAMILIES.add(new Family(Constants.ARRAY_LIST_FAMILIES.size(), "Ludeña Martinez", 1, true,false));
            Constants.ARRAY_LIST_FAMILIES.add(new Family(Constants.ARRAY_LIST_FAMILIES.size(), "Quispe Guerra", 2, false,false));
            Constants.ARRAY_LIST_FAMILIES.add(new Family(Constants.ARRAY_LIST_FAMILIES.size(), "León Sayán", 3, false,false));
            Constants.ARRAY_LIST_FAMILIES.add(new Family(Constants.ARRAY_LIST_FAMILIES.size(), "Cesar Peraldo", 4, false,false));
            Constants.ARRAY_LIST_FAMILIES.add(new Family(Constants.ARRAY_LIST_FAMILIES.size(), "Roque Tantalean", 5, false,false));
            Constants.ARRAY_LIST_FAMILIES.add(new Family(Constants.ARRAY_LIST_FAMILIES.size(), "Vásquez Loncharich", 6, false,false));
            Constants.ARRAY_LIST_FAMILIES.add(new Family(Constants.ARRAY_LIST_FAMILIES.size(), "Carrillo Ruiz", 7, false,false));
            Constants.ARRAY_LIST_FAMILIES.add(new Family(Constants.ARRAY_LIST_FAMILIES.size(), "Chuzón Proleón", 8, false,false));
        }
        String[] title = getResources().getStringArray(pe.com.vencedor.historiasdefamilias.R.array.label_title_song);
        for (int i = 0; i < Constants.ARRAY_LIST_FAMILIES.size(); i++) {
            if ((Constants.CURRENT_USER.getNivel() + 1) >= Constants.ARRAY_LIST_FAMILIES.get(i).getNivel())
                Constants.ARRAY_LIST_FAMILIES.get(i).setStatus(true);
            Constants.ARRAY_LIST_FAMILIES.get(i).setImagen(getFamilyImage(i));
            Constants.ARRAY_LIST_FAMILIES.get(i).setTitleSong(title[i]);
        }
        adapter = new FamilyAdapter(getActivity(),Constants.ARRAY_LIST_FAMILIES);
        lvStories.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(pe.com.vencedor.historiasdefamilias.R.menu.menu_info, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case pe.com.vencedor.historiasdefamilias.R.id.action_help:
                Intent intent = new Intent(getActivity(), TutorialActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public int getFamilyImage(int family) {
        TypedArray imgs = getResources().obtainTypedArray(pe.com.vencedor.historiasdefamilias.R.array.family_image);
        int id = imgs.getResourceId(family, -1);
        imgs.recycle();
        return id;
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(getActivity()).reportActivityStart(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(getActivity()).reportActivityStop(getActivity());
    }

}
