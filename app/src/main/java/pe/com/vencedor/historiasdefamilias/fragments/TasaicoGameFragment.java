package pe.com.vencedor.historiasdefamilias.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pe.com.vencedor.historiasdefamilias.activities.TasaicoGameActivity;
import pe.com.vencedor.historiasdefamilias.activities.TutorialActivity;
import pe.com.vencedor.historiasdefamilias.utils.Constants;
import pe.com.vencedor.historiasdefamilias.utils.Utils;

public class TasaicoGameFragment extends Fragment {

    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.toolbar)
    Toolbar toolbar;
    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.lblNivel)
    TextView lblNivel;
    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.lblInfo)
    TextView lblInfo;

    public static boolean retorno = false;

    public static Fragment newInstance() {
        return new TasaicoGameFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(pe.com.vencedor.historiasdefamilias.R.layout.fragment_game_tasaico, container, false);
        Utils.windowEvent(getActivity(), "Familia " + Constants.FAMILIA_SELECCIONADA.getName().substring(0, Constants.FAMILIA_SELECCIONADA.getName().indexOf(" ")) + " Intro", "Pantalla", "Viendo");
        System.out.println("EL NIVEL XD " + Constants.NIVEL_SELECCIONADO);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (toolbar != null) {
            toolbar.setNavigationIcon(pe.com.vencedor.historiasdefamilias.R.drawable.ic_arrow_left_white_24dp);
            toolbar.setTitle("Familia " + Constants.FAMILIA_SELECCIONADA.getName().substring(0, Constants.FAMILIA_SELECCIONADA.getName().indexOf(" ")));
            toolbar.inflateMenu(pe.com.vencedor.historiasdefamilias.R.menu.menu_info);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case pe.com.vencedor.historiasdefamilias.R.id.action_help:
                            Intent intent = new Intent(getActivity(), TutorialActivity.class);
                            startActivity(intent);
                            break;
                    }
                    return true;
                }
            });
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        }
        String[] info = getResources().getStringArray(pe.com.vencedor.historiasdefamilias.R.array.label_info);
        lblInfo.setText(Html.fromHtml(info[Constants.NIVEL_SELECCIONADO - 1]));
        lblNivel.setText(Html.fromHtml(Constants.STR_WELCOME_TO_LVL + Constants.NIVEL_SELECCIONADO + "!</b>"));
    }

    @OnClick(pe.com.vencedor.historiasdefamilias.R.id.btnPlay)
    public void btnPlayOnClick(){
        Intent intent = new Intent(getActivity(), TasaicoGameActivity.class);
        startActivity(intent);
    }

    @OnClick(pe.com.vencedor.historiasdefamilias.R.id.btnPlaySong)
    public void btnPlaySongOnClick(){
        Fragment fragment = MusicPlayerFragment.newInstance();
        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(pe.com.vencedor.historiasdefamilias.R.id.content_frame, fragment).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (retorno) {
            Intent intent = new Intent(getActivity(), TasaicoGameActivity.class);
            startActivity(intent);
            retorno = false;
        }
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
