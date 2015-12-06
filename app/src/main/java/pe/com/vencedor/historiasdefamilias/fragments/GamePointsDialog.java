package pe.com.vencedor.historiasdefamilias.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pe.com.vencedor.historiasdefamilias.R;
import pe.com.vencedor.historiasdefamilias.activities.MenuActivity;
import pe.com.vencedor.historiasdefamilias.entities.User;
import pe.com.vencedor.historiasdefamilias.utils.AppPreferences;
import pe.com.vencedor.historiasdefamilias.utils.Constants;
import pe.com.vencedor.historiasdefamilias.utils.RequestPost;
import pe.com.vencedor.historiasdefamilias.utils.StringRequestPost;
import pe.com.vencedor.historiasdefamilias.utils.Utils;

/**
 * Created by cesar on 8/20/15.
 */
public class GamePointsDialog extends Fragment {
    private View view;
    private LinearLayout btnVerPremios, btnShare, btnHome;
    //    private ImageView ivCancel;
    private TextView lblPuntajeFinal;
    private String title;
    private int status;
    private TextView lblCongratulationsText2;

    TextView lblPuntos;

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    public static Fragment newInstance() {
        return new GamePointsDialog();
    }

    public static Fragment newInstance(int status) {
        GamePointsDialog gamePointsDialog = new GamePointsDialog();
        gamePointsDialog.status = status;
        return gamePointsDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(pe.com.vencedor.historiasdefamilias.R.layout.dialog_game_points, container, false);

        Utils.windowEvent(getActivity(), "Familia " + Constants.FAMILIA_SELECCIONADA.getName().substring(0, Constants.FAMILIA_SELECCIONADA.getName().indexOf(" ")) + " Final",
                "Pantalla", "Final");

//        ivCancel = (ImageView)view.findViewById(R.id.ivCancel);
        btnVerPremios = (LinearLayout) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.btnVerPremios);
        btnShare = (LinearLayout) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.btnShare);
        btnHome = (LinearLayout) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.btnHome);
        lblPuntajeFinal = (TextView) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.lblPuntajeFinal);
        lblCongratulationsText2 = (TextView) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.lblCongratulationsText2);

        if(getActivity() instanceof MenuActivity){
            lblPuntos = (TextView) getActivity().findViewById(R.id.lblPuntos);
        }

        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        switch (status){
            case 1:
                lblCongratulationsText2.setText("Vuelve al menú y continúa\ncon otra familia para tener\nmás opciones de ganar.");
                break;
            case 2:
                lblCongratulationsText2.setText(Html.fromHtml("<b>Has repetido este nivel.</b><br>Vuelve al menú y continúa<br>con otra familia para tener<br> más opciones de ganar."));
                break;
            case 3:
                lblCongratulationsText2.setText(Html.fromHtml("<b>Terminaste el juego</b><br>El 17/10 sabrás si ganaste un<br>viaje para ti y tu familia."));
                break;
        }

        lblPuntajeFinal.setText("Tu puntaje es de " + Constants.CURRENT_USER.getPuntos());
        btnShare.setOnClickListener(new View.OnClickListener() {
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
                            Log.d("EL NIVEL DEL USUARIO", "NIVEL " + Constants.CURRENT_USER.getNivel());
                            progressDialog.dismiss();
                            if (status == 1 || status == 3) {
                                Log.v("COMPARTO", "GANO PUNTOS");
                                shareLvl();

                            } else {
                                Log.v("SOLO COMPARTO", "NO GANO PUNTOS");
                                Intent intent = new Intent(getActivity(), MenuActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                getActivity().finish();
                            }


                        }

                        @Override
                        public void onCancel() {
                            Log.d("CANCEL SHARE", "MAL");
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onError(FacebookException e) {
                            progressDialog.dismiss();
                            Log.d("ERROR SHARE", "ERROR!!");
                            e.printStackTrace();
                        }
                    });
                }
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnVerPremios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PASO LVL", "PASO LVL");
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra(Constants.KEY_PARAMS_PRIZES, true);
                startActivity(intent);
                getActivity().finish();
            }
        });

/*        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });*/

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void shareLvl() {
        Map<String, String> sendUser = new HashMap<String, String>();
        sendUser.put("id", Constants.CURRENT_USER.getUserId());
        sendUser.put("nivel", "cancion-" + Constants.NIVEL_SELECCIONADO + "");

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequestPost stringRequestPost = new StringRequestPost(true,
                RequestPost.TYPE_OBJECT, "http://historiasdefamilias.pe/services/actualizar-nivel", sendUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE XD: ", String.valueOf(response));
                        try {
                            JSONObject jsonObject = new JSONObject(response);
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

                                if(lblPuntos != null){
                                    lblPuntos.setText(str_puntos);
                                }

                                Intent intent = new Intent(getActivity(), MenuActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                getActivity().finish();
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
}
