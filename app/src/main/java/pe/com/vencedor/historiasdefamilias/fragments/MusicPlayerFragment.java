package pe.com.vencedor.historiasdefamilias.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
public class MusicPlayerFragment extends Fragment {
    private View view;
    private Toolbar toolbar;
    private LinearLayout btnShareSong;
    private ImageView ivAvatarSong, ivPlayPlay;
    private TextView lblTitleSong, lblLyricsComplete;
    private MediaPlayer mediaPlayer;

    TextView lblPuntos;

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    public static Fragment newInstance() {
        return new MusicPlayerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(pe.com.vencedor.historiasdefamilias.R.layout.fragment_music_player, container, false);
        Utils.windowEvent(getActivity(), "Familia " + Constants.FAMILIA_SELECCIONADA.getName().substring(0, Constants.FAMILIA_SELECCIONADA.getName().indexOf(" ")) + " Reproducir Todo",
                "Pantalla", "Escuchando");

        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

//        btnHome = (LinearLayout) view.findViewById(R.id.btnHome);
        /*btnPlayNext = (LinearLayout) view.findViewById(R.id.btnPlayNext);*/
        btnShareSong = (LinearLayout) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.btnShareSong);
        /*txtNextLvl = (TextView) view.findViewById(R.id.txtNextLvl);*/
/*        lblFamilyNameSong = (TextView) view.findViewById(R.id.lblFamilyNameSong);*/
        lblTitleSong = (TextView) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.lblTitleSong);
        lblLyricsComplete = (TextView) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.lblLyricsComplete);
        ivAvatarSong = (ImageView) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.ivAvatarSong);
        ivPlayPlay = (ImageView) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.ivPlayPlay);
        toolbar = (Toolbar) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.toolbar);

        if(getActivity() instanceof MenuActivity){
            lblPuntos = (TextView) getActivity().findViewById(R.id.lblPuntos);
        }

        ivAvatarSong.setImageResource(getFamilyImage(Constants.NIVEL_SELECCIONADO - 1));
        lblTitleSong.setText("Familia " + Constants.FAMILIA_SELECCIONADA.getName().substring(0, Constants.FAMILIA_SELECCIONADA.getName().indexOf(" ")) + "\n\"" + Constants.FAMILIA_SELECCIONADA.getTitleSong() + "\"");
        /*lblFamilyNameSong.setText("Familia " + Constants.FAMILIA_SELECCIONADA.getName().substring(0, Constants.FAMILIA_SELECCIONADA.getName().indexOf(" ")));*/
        String song = "";

        for (int i = 0; i < Utils.getStrofa(getActivity(), Constants.NIVEL_SELECCIONADO).size(); i++) {
            if (i == 0)
                continue;
            song += Utils.getStrofa(getActivity(), Constants.NIVEL_SELECCIONADO).get(i);
        }

        lblLyricsComplete.setText(song);

        String name = "song" + Constants.NIVEL_SELECCIONADO;
        mediaPlayer = MediaPlayer.create(getActivity(), getResources().getIdentifier(name, "raw", getActivity().getPackageName()));
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        ivPlayPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK EN PLAY", "PLAYING!!!");
                if (!mediaPlayer.isPlaying()) {
                    ivPlayPlay.setImageResource(pe.com.vencedor.historiasdefamilias.R.drawable.pause_player);
                    mediaPlayer.start();
                } else {
                    ivPlayPlay.setImageResource(pe.com.vencedor.historiasdefamilias.R.drawable.play_player);
                    mediaPlayer.pause();
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                ivPlayPlay.setImageResource(pe.com.vencedor.historiasdefamilias.R.drawable.play_player);
            }
        });

        btnShareSong.setOnClickListener(new View.OnClickListener() {
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
//                          Utils.windowEvent(MenuActivity.this, "Compartir", "APP", "Viendo");
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

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (toolbar != null) {
            toolbar.setNavigationIcon(pe.com.vencedor.historiasdefamilias.R.drawable.ic_arrow_left_white_24dp);
            toolbar.setTitle("Player");
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
        mediaPlayer.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mediaPlayer != null){
            if (!mediaPlayer.isPlaying()) {
                ivPlayPlay.setImageResource(pe.com.vencedor.historiasdefamilias.R.drawable.play_player);
            }
        }
    }

    private int getFamilyImage(int family) {
        TypedArray imgs = getActivity().getResources().obtainTypedArray(pe.com.vencedor.historiasdefamilias.R.array.family_image);
        int id = imgs.getResourceId(family, -1);
        imgs.recycle();
        return id;
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
