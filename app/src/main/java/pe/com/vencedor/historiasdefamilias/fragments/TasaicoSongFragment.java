package pe.com.vencedor.historiasdefamilias.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.com.vencedor.historiasdefamilias.R;
import pe.com.vencedor.historiasdefamilias.VencedorApplication;
import pe.com.vencedor.historiasdefamilias.activities.MenuActivity;
import pe.com.vencedor.historiasdefamilias.activities.TutorialActivity;
import pe.com.vencedor.historiasdefamilias.entities.User;
import pe.com.vencedor.historiasdefamilias.utils.AppPreferences;
import pe.com.vencedor.historiasdefamilias.utils.Constants;
import pe.com.vencedor.historiasdefamilias.utils.RequestPost;
import pe.com.vencedor.historiasdefamilias.utils.StringRequestPost;
import pe.com.vencedor.historiasdefamilias.utils.Utils;

/**
 * Created by cesar on 8/20/15.
 */
public class TasaicoSongFragment extends Fragment {
    private View view;
    private ImageView/* starbox, ivPause,*/ ivPlay/*, ivProgress*/, familyImage;
    private Toolbar toolbar;
    private TextView /*lblTime,*/lblLyrics, lblTitleVerse;
    private MediaPlayer song;
    private LinearLayout llSong, btnShareVerse;
//    private LinearLayout.LayoutParams layoutParams;

    private ScrollView svLyrics;

    private Bitmap bitmap;
    private int seconds;
    private Thread thread;
    /*private MySong mySong;*/
    public static double ancho = 0;

    /**/
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();

    private int forwardTime = 5000;
    private int backwardTime = 5000;
    public static int oneTimeOnly = 0;
    private SeekBar seekBar;
    private boolean nextLvl;
    private Button btnContinue;

    TextView lblPuntos;

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    public static Fragment newInstance() {
        return new TasaicoSongFragment();
    }

    public static Fragment newInstance(MediaPlayer song, boolean nextLvl) {
        TasaicoSongFragment tasaicoSongFragment = new TasaicoSongFragment();
        tasaicoSongFragment.song = song;
        tasaicoSongFragment.nextLvl = nextLvl;
        return tasaicoSongFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(pe.com.vencedor.historiasdefamilias.R.layout.fragment_tasaico_song, container, false);

        Utils.windowEvent(getActivity(), "Familia " +
                        Constants.FAMILIA_SELECCIONADA.getName().substring(0, Constants.FAMILIA_SELECCIONADA.getName().indexOf(" ")) +
                        " Reproducir " + Constants.NIVEL_SELECCIONADO + "-" + Constants.SUB_NIVEL,
                "Pantalla", "Escuchando-Jugando");

        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        svLyrics = (ScrollView) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.svLyrics);
        btnContinue = (Button) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.btnContinue);
//        starbox = (ImageView) view.findViewById(R.id.starbox2);
//        ivPause = (ImageView) view.findViewById(R.id.ivPause);
        ivPlay = (ImageView) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.ivPlay);
        familyImage = (ImageView) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.familyImage);
//        ivProgress = (ImageView) view.findViewById(R.id.ivProgress);
//        lblTime = (TextView) view.findViewById(R.id.lblTime);
        lblLyrics = (TextView) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.lblLyrics);
        lblTitleVerse = (TextView) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.lblTitleVerse);
        seekBar = (SeekBar) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.seekBar);
        llSong = (LinearLayout) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.llSong);
        btnShareVerse = (LinearLayout) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.btnShareVerse);
        toolbar = (Toolbar) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.toolbar);
//        layoutParams = new LinearLayout.LayoutParams(0, 65);
//        ivProgress.setLayoutParams(layoutParams);

        if(getActivity() instanceof MenuActivity){
            lblPuntos = (TextView) getActivity().findViewById(R.id.lblPuntos);
        }

        if (nextLvl)
            btnContinue.setText("Finalizar");

        llSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK EN LAYOUT", "CLICK");
            }
        });

        familyImage.setImageResource(Constants.FAMILIA_SELECCIONADA.getImagen());

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song.stop();
                if (nextLvl) {
                    Constants.SUB_NIVEL = 1;
                    updateLevel();
                    /*if (Constants.CURRENT_USER.getNivel() + 1 == Constants.NIVEL_SELECCIONADO) {
                        updateLevel();

                       *//* Fragment fragment = GamePointsDialog.newInstance(true);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.addToBackStack(null);
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.add(android.R.id.content, fragment).commit();*//*
*//*                        int siguienteNivel = Constants.NIVEL_SELECCIONADO + 1;
                        if (siguienteNivel <= 8)
                            AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_NIVEL_USER, Constants.NIVEL_SELECCIONADO + "-1");*//*
                    } else {
                        Log.d("ESTA JUGANDO", "ESTA JUGANDO");

                        Fragment fragment = GamePointsDialog.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.addToBackStack(null);
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.add(android.R.id.content, fragment).commit();
                       *//* if (Constants.NIVEL_SELECCIONADO < 8) {
                            Fragment fragment = GamePointsDialog.newInstance(true);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.addToBackStack(null);
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            transaction.add(android.R.id.content, fragment).commit();
                        } else if (Constants.NIVEL_SELECCIONADO == 8) {
                            Fragment fragment = GamePointsDialog.newInstance();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.addToBackStack(null);
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            transaction.add(android.R.id.content, fragment).commit();
                        }*//*
                    }*/


//                    Fragment fragment = TasaicoUnlockFragment.newInstance(true, title);
/*                    Fragment fragment = TasaicoUnlockFragment.newInstance(true);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.addToBackStack(null);
                    transaction.add(android.R.id.content, fragment).commit();*/
                } else {
                    Fragment fragment = TasaicoPlayingFragment.newInstance();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(android.R.id.content, fragment).commit();

                    /*Fragment fragment = TasaicoUnlockFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.addToBackStack(null);
                    transaction.add(android.R.id.content, fragment).commit();*/
                }
            }
        });

        List<String> estrofas = Utils.getStrofa(getActivity(), Constants.NIVEL_SELECCIONADO);
        System.out.println("LA ESTROFA XDDD!!! : " + estrofas.get(Constants.SUB_NIVEL - 1));


        lblLyrics.setText(estrofas.get(Constants.SUB_NIVEL - 1).trim());
        lblTitleVerse.setText("Familia " + Constants.FAMILIA_SELECCIONADA.getName().substring(0, Constants.FAMILIA_SELECCIONADA.getName().indexOf(" ")) + "\n\"" + Constants.FAMILIA_SELECCIONADA.getTitleSong() + "\"");

        btnShareVerse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Cargando...");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("http://historiasdefamilias.pe/?compartir=app&cancion=" + Constants.NIVEL_SELECCIONADO))
                            .build();

                    shareDialog.show(linkContent);
                    shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

                        @Override
                        public void onSuccess(Sharer.Result result) {
                            Log.d("SUCCESS SHARE", "OK");
                            boolean appShared = AppPreferences.getInstance(getActivity()).getBoolean(AppPreferences.PREF_SHARE_APP);
                            Log.d("COMPARTIDO", appShared + "");
                            Tracker tracker = ((VencedorApplication) getActivity().getApplication()).getTracker(VencedorApplication.TrackerName.GLOBAL_TRACKER);
                            tracker.setScreenName("Compartir");
                            tracker.send(new HitBuilders.EventBuilder().setLabel("Facebook").setCategory("RedSocial").setAction("APP").build());

                                shareApp();
                                progressDialog.dismiss();

                        }

                        @Override
                        public void onCancel() {
                            Log.d("CANCEL SHARE", "MAL");
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onError(FacebookException e) {
                            Log.d("ERROR SHARE", "ERROR!!");
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    });
                }
            }
        });

/*        starbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextLvl) {
                    Fragment fragment = TasaicoUnlockFragment.newInstance(true, title);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.add(android.R.id.content, fragment).commit();
                } else {
                    Fragment fragment = TasaicoUnlockFragment.newInstance(title);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.add(android.R.id.content, fragment).commit();
                }

            }
        });*/

/*        ivPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                song.pause();
                try {
                    mySong.pauseThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });*/

        song.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                ivPlay.setImageResource(pe.com.vencedor.historiasdefamilias.R.drawable.play_player);
            }
        });

        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK EN PLAY", "PLAYING!!!");
                if (!song.isPlaying()) {
                    ivPlay.setImageResource(pe.com.vencedor.historiasdefamilias.R.drawable.pause_player);
                    song.start();
                } else {
                    ivPlay.setImageResource(pe.com.vencedor.historiasdefamilias.R.drawable.play_player);
                    song.pause();
                }


//                mySong = new MySong(song, ivProgress, getActivity(),lblTime);
//                mySong = new MySong(song,getActivity());
//                mySong.start();

/*                finalTime = song.getDuration();
                startTime = song.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekBar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }

                seekBar.setProgress((int) startTime);*/
//                myHandler.postDelayed(UpdateSongTime, 100);

            }
        });

        return view;
    }

    private void shareApp() {
        Map<String, String> sendUser = new HashMap<>();
        sendUser.put("id", Constants.CURRENT_USER.getUserId());
        sendUser.put("nivel", "cancion-" + Constants.NIVEL_SELECCIONADO);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequestPost stringRequestPost = new StringRequestPost(true,
                RequestPost.TYPE_OBJECT, "http://historiasdefamilias.pe/services/actualizar-nivel", sendUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE XD: ", String.valueOf(response));
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has("success")) {
                                String str_success = jsonObject.getString("success");

                                JSONObject jsonObject1 = jsonObject.getJSONObject("usuario");
                                String str_id = jsonObject1.getString("id");
                                String str_nombres = jsonObject1.getString("nombres");
                                String str_fbid = jsonObject1.getString("fbid");
                                String str_email = jsonObject1.getString("email");
                                String str_puntos = jsonObject1.getString("puntos");
                                String str_posicion = jsonObject1.getString("posicion");
                                String str_ganador = jsonObject1.getString("ganador");
                                String str_password = jsonObject1.getString("password");
                                String str_fecha_creacion = jsonObject1.getString("fecha_creacion");
                                String str_telefono = jsonObject1.getString("telefono");
                                String str_nivel = jsonObject1.getString("nivel");

                                if (str_success.equals("true")) {
                                    User user = new User();
                                    user.setUserId(str_id);
                                    user.setPuntos(Integer.parseInt(str_puntos));

                                    Constants.CURRENT_USER.setPuntos(user.getPuntos());
                                    AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_PUNTAJE_USER, str_puntos);
                                    AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_SHARE_APP, true);

                                    if(lblPuntos != null){
                                        lblPuntos.setText(str_puntos);
                                    }

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.getStackTrace();
                    }
                }
        );
        queue.add(stringRequestPost);

    }

    @Override
    public void onPause() {
        super.onPause();
        song.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(song != null){
            if (!song.isPlaying()) {
                ivPlay.setImageResource(pe.com.vencedor.historiasdefamilias.R.drawable.play_player);
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (toolbar != null) {
            toolbar.setNavigationIcon(pe.com.vencedor.historiasdefamilias.R.drawable.ic_arrow_left_white_24dp);
            toolbar.setTitle(Constants.FAMILIA_SELECCIONADA.getName());
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
                    if(nextLvl){
                        Toast.makeText(getActivity(), "Debe darle click en finalizar para obtener los puntos.", Toast.LENGTH_SHORT).show();
                    } else{
                        getActivity().onBackPressed();
                    }
                    if (song != null) {
                        song.stop();
                    }
                }
            });
        }
    }

    /*    public Bitmap splitBitmap(Bitmap picture) {
        Bitmap[] imgs = new Bitmap[4];
        imgs[0] = Bitmap.createBitmap(picture, 0, 0, picture.getWidth() / 16, picture.getHeight());
        imgs[1] = Bitmap.createBitmap(picture, picture.getWidth() / 2, 0, picture.getWidth() / 2, picture.getHeight() / 2);
        imgs[2] = Bitmap.createBitmap(picture, 0, picture.getHeight() / 2, picture.getWidth() / 2, picture.getHeight() / 2);
        imgs[3] = Bitmap.createBitmap(picture, picture.getWidth() / 2, picture.getHeight() / 2, picture.getWidth() / 2, picture.getHeight() / 2);

        return imgs[0];

    }*/

/*    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = song.getCurrentPosition();

            seekBar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };*/


    private synchronized void updateLevel() {
        Map<String, String> sendUser = new HashMap<String, String>();
        sendUser.put("id", Constants.CURRENT_USER.getUserId());
        sendUser.put("nivel", Constants.NIVEL_SELECCIONADO + "");

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequestPost stringRequestPost = new StringRequestPost(true,
                RequestPost.TYPE_OBJECT, "http://historiasdefamilias.pe/services/actualizar-nivel", sendUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPONSE XD: ", String.valueOf(response));
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.has("success")){
                                String str_success = jsonObject.getString("success");

                                JSONObject jsonObject1 = jsonObject.getJSONObject("usuario");
                                String str_id = jsonObject1.getString("id");
                                String str_nombres = jsonObject1.getString("nombres");
                                String str_fbid = jsonObject1.getString("fbid");
                                String str_email = jsonObject1.getString("email");
                                String str_puntos = jsonObject1.getString("puntos");
                                String str_posicion = jsonObject1.getString("posicion");
                                String str_ganador = jsonObject1.getString("ganador");
                                String str_password = jsonObject1.getString("password");
                                String str_fecha_creacion = jsonObject1.getString("fecha_creacion");
                                String str_telefono = jsonObject1.getString("telefono");
                                String str_nivel = jsonObject1.getString("nivel");

                                if (str_success.equals("true")) {
                                    User user = new User();
                                    user.setUserId(str_id);
                                    user.setPuntos(Integer.parseInt(str_puntos));
                                    int nivel = str_nivel.equals("null") ? 0 : Integer.parseInt(str_nivel);
                                    user.setNivel(nivel);

                                    Constants.CURRENT_USER.setPuntos(user.getPuntos());
                                    if(!str_puntos.equals("null")){
                                        AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_PUNTAJE_USER, str_puntos);
                                    }
                                    AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_NIVEL_COMPLETE, user.getNivel());


                                    Constants.CURRENT_USER.setNivel(Constants.NIVEL_SELECCIONADO);
                                    Constants.ARRAY_LIST_FAMILIES.get(Constants.NIVEL_SELECCIONADO - 1).setComplete(true);
                                    Log.d("INGRESO QUI ", Constants.CURRENT_USER.getNivel() + " nivel");

                                    Log.d("PASA DE NIVEL", "PASAR DE NIVEL " + Constants.CURRENT_USER.getNivel() + " - " + Constants.NIVEL_SELECCIONADO);

                                    Fragment fragment = GamePointsDialog.newInstance(Constants.CURRENT_USER.getNivel() != 8 ? 1 : 3);
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                    transaction.add(android.R.id.content, fragment).commit();

                                }
                            }

                            if(jsonObject.has("error")){
                                Fragment fragment = GamePointsDialog.newInstance(2);
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.addToBackStack(null);
                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                transaction.add(android.R.id.content, fragment).commit();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.getStackTrace();
                    }
                }
        );
        queue.add(stringRequestPost);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
