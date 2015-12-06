package pe.com.vencedor.historiasdefamilias.fragments;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.com.vencedor.historiasdefamilias.VencedorApplication;
import pe.com.vencedor.historiasdefamilias.activities.TutorialActivity;
import pe.com.vencedor.historiasdefamilias.utils.Constants;
import pe.com.vencedor.historiasdefamilias.utils.PlayingText;
import pe.com.vencedor.historiasdefamilias.utils.Utils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.moodstocks.android.AutoScannerSession;
import com.moodstocks.android.MoodstocksError;
import com.moodstocks.android.Result;
import com.moodstocks.android.Scanner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Asahel on 8/25/15.
 */
public class TasaicoPlayingFragment extends Fragment implements AutoScannerSession.Listener {
    private AutoScannerSession session = null;

    private static final int TYPES = Result.Type.IMAGE | Result.Type.QRCODE | Result.Type.EAN13;

    private MediaPlayer song;

    private View view;
    /*    private ImageView ivVisor, pantallaJuega;*/
    private Toolbar toolbar;
    private SurfaceView preview;
    private TextView tvBottomPart;
    public static boolean resultado = false;
    //    private int count = 0;
    private boolean nextlvl;
    private PlayingText playingText;
//    private String titulo;

    public static Fragment newInstance() {
        return new TasaicoPlayingFragment();
    }

/*    public static Fragment newInstance(int nivelSeleccionado, String familiaSeleccionada) {
        Fragment fragment = new TasaicoPlayingFragment();
        Bundle bundle = new Bundle();
        System.out.println("ENTRO A TasaicoPlayingFragment NEW INSTANCE 2");
        bundle.putInt(Constants.KEY_PARAMS_NIVEL, nivelSeleccionado);
        bundle.putString(Constants.KEY_PARAMS_TITLE_GAME, familiaSeleccionada);
        fragment.setArguments(bundle);
        return fragment;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(pe.com.vencedor.historiasdefamilias.R.layout.fragment_tasaico_playing, container, false);

        Utils.windowEvent(getActivity(), "Familia " + Constants.FAMILIA_SELECCIONADA.getName().substring(0, Constants.FAMILIA_SELECCIONADA.getName().indexOf(" ")) + " Escanner",
                "Pantalla", "Jugando");
/*        ivVisor = (ImageView) view.findViewById(R.id.ivVisor);
        pantallaJuega = (ImageView) view.findViewById(R.id.pantallaJuega);*/
        tvBottomPart = (TextView) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.tvBottomPart);
        preview = (SurfaceView) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.preview);
        toolbar = (Toolbar) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.toolbar);
        initScanner();

        tvBottomPart.setVisibility(View.VISIBLE);
        session.start();
//        count++;


//        tvBottomPart.setText("Escanee la casa N° " + Constants.SUB_NIVEL + " de la familia " + Constants.FAMILIA_SELECCIONADA.getName());

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
                    if (song != null) {
                        song.stop();
                    }
                }
            });
        }
    }

    private void initScanner() {
        try {
            session = new AutoScannerSession(getActivity(), Scanner.get(), this, preview);
            session.setResultTypes(TYPES);
            Log.d("TODO BONO", "====");
        } catch (MoodstocksError e) {
            Log.e("ERROR! : ", e.getMessage());
            e.printStackTrace();
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        session.stop();
    }

    @Override
    public void onCameraOpenFailed(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onResult(final Result result) {
        resultado = true;
        Tracker tracker = ((VencedorApplication) getActivity().getApplication()).getTracker(VencedorApplication.TrackerName.GLOBAL_TRACKER);
        tracker.setScreenName("Juego");
        tracker.send(new HitBuilders.EventBuilder()
                .setAction(Constants.FAMILIA_SELECCIONADA.getName().substring(0, Constants.FAMILIA_SELECCIONADA.getName().indexOf(" ")))
                .setLabel("Escaneo")
                .setValue((long) Constants.NIVEL_SELECCIONADO)
                .build());

        if (Constants.SUB_NIVEL == 1) {
            Tracker tracker2 = ((VencedorApplication) getActivity().getApplication()).getTracker(VencedorApplication.TrackerName.GLOBAL_TRACKER);
            tracker2.setScreenName("Juego");
            tracker2.send(new HitBuilders.EventBuilder()
                    .setAction(Constants.FAMILIA_SELECCIONADA.getName().substring(0, Constants.FAMILIA_SELECCIONADA.getName().indexOf(" ")))
                    .setLabel("Inicio")
                    .build());
        }

        int nivelCompletado = Constants.CURRENT_USER.getNivel();

        if (!(Constants.CURRENT_USER.getNivel() + 1 == Constants.NIVEL_SELECCIONADO)) {
//        if (Constants.ARRAY_LIST_FAMILIES.get(Constants.NIVEL_SELECCIONADO - 1).isComplete()) {
            nivelCompletado = Constants.NIVEL_SELECCIONADO - 1;
        }

        int nivelSiguiente = nivelCompletado + 1;

        int nivelResultado = Character.getNumericValue(result.getValue().charAt(0));
        int subNivelResultado = Character.getNumericValue(result.getValue().charAt(2));
        if (Constants.NIVEL_SELECCIONADO != nivelResultado) {
            tvBottomPart.setText("Familia Equivocada");
        } else if (subNivelResultado > Constants.SUB_NIVEL) {
            tvBottomPart.setText("!Debes seguir el orden!\nEscanea la casa " + Constants.SUB_NIVEL);
        } else if (subNivelResultado < Constants.SUB_NIVEL) {
            tvBottomPart.setText("!Ya escaneaste esa casa!\nEscanea la casa " + Constants.SUB_NIVEL);
        }

        String nivelActual = nivelSiguiente + "-" + Constants.SUB_NIVEL;

        Log.d("NIVEL ACTUAL", nivelActual);
        Log.i("RESULTADO1", result.getValue().charAt(0) + " " + result.getValue() + " - " + result.getValue().charAt(result.getValue().length() - 1));
        Log.i("RESULTADO2", nivelResultado + "");
        Log.i("NIVEL_SELECCIONADO", Constants.NIVEL_SELECCIONADO + "");

        if (nivelResultado == Constants.NIVEL_SELECCIONADO && nivelActual.equals(result.getValue())) {
            if (subNivelResultado == 9) {
                Log.i("ENTRO A SUBNIVEL", "DENTRO");

                tracker.setScreenName("Juego");
                tracker.send(new HitBuilders.EventBuilder()
                        .setAction(Constants.FAMILIA_SELECCIONADA.getName().substring(0, Constants.FAMILIA_SELECCIONADA.getName().indexOf(" ")))
                        .setLabel("Final")
                        .build());
                if (Constants.NIVEL_SELECCIONADO < 8) {
                    if (Constants.CURRENT_USER.getNivel() + 1 == Constants.NIVEL_SELECCIONADO)
                        Constants.ARRAY_LIST_FAMILIES.get(Constants.NIVEL_SELECCIONADO).setStatus(nextlvl);

                    nextlvl = true;
                } else if (Constants.NIVEL_SELECCIONADO == 8) {
                    nextlvl = true;
                }
                Constants.SUB_NIVEL = ++subNivelResultado;
            } else
                Constants.SUB_NIVEL = ++subNivelResultado;
//            String siguienteNivel = nivelActual.substring(0,nivelActual.length()-1)+ ++subNivel;
//            AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_NIVEL_USER, siguienteNivel); // SE SETEA EL NIVEL DEL USUARIO
//            Toast.makeText(view.getContext(), (result.getType() == Result.Type.IMAGE ? "Felicidades:" : "Barcode:") + "Has Encontrado un paso más del Nivel " + result.getValue() + ".", Toast.LENGTH_LONG).show();

            String name = "song" + result.getValue().replace("-", "i");
            Log.d("EL NAME", name);
            song = MediaPlayer.create(getActivity(), getResources().getIdentifier(name, "raw", getActivity().getPackageName()));

            Fragment fragment = TasaicoSongFragment.newInstance(song, nextlvl);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.add(android.R.id.content, fragment).commit();

            session.stop();
            Log.d("DURATION2!!", (new SimpleDateFormat("mm:ss")).format(new Date(song.getDuration())));

            song.start();
        } else
            session.resume();

        resultado = false;
    }

    @Override
    public void onWarning(String s) {
        Log.d("EN WARNING", "==========" + s);
    }

    @Override
    public void onResume() {
        super.onResume();
        //session.start();
        if (!session.isListening()) {
            Log.d("ENTRO A INITIALIZE", "AQUI");
            initScanner();
            session.start();
        }

        playingText = new PlayingText(tvBottomPart, getActivity());
        playingText.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        session.stop();
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
