package pe.com.vencedor.historiasdefamilias.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import pe.com.vencedor.historiasdefamilias.R;
import pe.com.vencedor.historiasdefamilias.activities.MenuActivity;
import pe.com.vencedor.historiasdefamilias.activities.TutorialActivity;
import pe.com.vencedor.historiasdefamilias.entities.User;
import pe.com.vencedor.historiasdefamilias.utils.AppPreferences;
import pe.com.vencedor.historiasdefamilias.utils.Constants;
import pe.com.vencedor.historiasdefamilias.utils.RequestPost;
import pe.com.vencedor.historiasdefamilias.utils.StringRequestPost;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cesar on 8/20/15.
 */
public class TasaicoUnlockFragment extends Fragment {
    private View view;
    private ImageView ivUnlock;
    private Toolbar custom_toolbar;
    private boolean nextLvl;
    private String title;

    public static Fragment newInstance() {
        return new TasaicoUnlockFragment();
    }

/*    public static Fragment newInstance(String title){
        Fragment fragment =  new TasaicoUnlockFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_PARAMS_TITLE_GAME, title);
        fragment.setArguments(bundle);
        return  fragment;
    }*/

    public static Fragment newInstance(boolean pasoNivel) {
        Fragment fragment = new TasaicoUnlockFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.KEY_NEXT_LVL, pasoNivel);
        /*bundle.putString(Constants.KEY_PARAMS_TITLE_GAME, title);*/
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tasaico_unlock, container, false);
        ivUnlock = (ImageView) view.findViewById(R.id.ivUnlock);

        Bundle bundle = getArguments();
        if (bundle != null) {
            nextLvl = bundle.getBoolean(Constants.KEY_NEXT_LVL);
            if (nextLvl) {




            }

        }
        ivUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextLvl) {
                    Log.d("PASO LVL", "PASO LVL");
                    Intent intent = new Intent(getActivity(), MenuActivity.class);
/*                    if (Constants.CURRENT_USER != null)
                        intent.putExtra(Constants.KEY_PARAMS_USER, Constants.CURRENT_USER);*/
                    startActivity(intent);
                    getActivity().finish();
                } else {
//                    TasaicoGameFragment.retorno = true;
                    Fragment fragment = TasaicoPlayingFragment.newInstance();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(android.R.id.content, fragment).commit();
                    /*if (ScannerSingleton.getInstance().isInitialize())
                        ScannerSingleton.getInstance().destroyScanner();
                    Fragment fragment = TasaicoPlayingFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.add(android.R.id.content, fragment).commit();*/
                    /*Log.d("ME VOY AL SCANNER", "EN EL SCANNER, DE HACER TAP");
                    Intent intent = new Intent(getActivity(), TasaicoGameActivity.class);
                    if (ScannerSingleton.getInstance().isInitialize())
                        ScannerSingleton.getInstance().destroyScanner();
                    startActivity(intent);*/
//                    getActivity().finish();
/*

                    String nivelActual = AppPreferences.getInstance(getActivity()).getString(AppPreferences.PREF_NIVEL_USER);
                    int nivel = Character.getNumericValue(nivelActual.charAt(0));
                    System.out.println("NIVEL ACTUAL!!!!!!!!!!    " + nivelActual +  " -  " + nivel);*/

/*                    Bundle bundle = getArguments();
                    if (bundle != null){

                        Log.d("Titulo", title);
                        intent.putExtra(Constants.KEY_PARAMS_NIVEL,nivel);
//                        intent.putExtra(Constants.KEY_PARAMS_TITLE_GAME, title);
                    }*/

                }

                /*Fragment fragment = ExitTasaicoGameDialog.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, fragment).commit();*/
            }
        });
        initToolbar(view);
        return view;
    }

    private void initToolbar(View view) {
        custom_toolbar = (Toolbar) view.findViewById(R.id.custom_toolbar);
        final ImageView ivIcLeft = (ImageView) view.findViewById(R.id.ivIcLeft);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        final ImageView ivIcRight = (ImageView) view.findViewById(R.id.ivIcRight);

        ivIcLeft.setImageResource(R.drawable.backarrow_blue);
        ivIcRight.setImageResource(R.drawable.ico_ayuda_header);

        ivIcLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        ivIcRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TutorialActivity.class);
                startActivity(intent);
            }
        });

        tvTitle.setText(Constants.FAMILIA_SELECCIONADA.getName());

        /*Bundle bundle = getArguments();

        if(bundle!=null){
            title = bundle.getString(Constants.KEY_PARAMS_TITLE_GAME);
            tvTitle.setText(title);
        }else
            tvTitle.setText("Title");*/
    }

    private void updateLevel(){
        Map<String, String> sendUser = new HashMap<String, String>();
        sendUser.put("id", Constants.CURRENT_USER.getUserId());
        sendUser.put("nivel", Constants.NIVEL_SELECCIONADO+"");

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequestPost stringRequestPost = new StringRequestPost(true,
                RequestPost.TYPE_OBJECT, "http://historiasdefamilias.pe/services/actualizar-nivel", sendUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE XD: ",String.valueOf(response));
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

                            if(str_success.equals("true")){
                                User user = new User();
                                user.setUserId(str_id);
                                user.setPuntos(Integer.parseInt(str_puntos));
                                int nivel =str_nivel.equals("null")?0:Integer.parseInt(str_nivel);
                                user.setNivel(nivel);

                                Constants.CURRENT_USER.setPuntos(user.getPuntos());
                                AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_PUNTAJE_USER, str_puntos);
                                AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_NIVEL_COMPLETE, user.getNivel());

                                Constants.CURRENT_USER.setNivel(Constants.NIVEL_SELECCIONADO);
                                Constants.ARRAY_LIST_FAMILIES.get(Constants.NIVEL_SELECCIONADO - 1).setComplete(true);

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
