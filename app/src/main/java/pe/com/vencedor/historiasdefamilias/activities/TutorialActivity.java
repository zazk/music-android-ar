package pe.com.vencedor.historiasdefamilias.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pe.com.vencedor.historiasdefamilias.adapters.NavigationAdapter;
import pe.com.vencedor.historiasdefamilias.entities.MenuItem;
import pe.com.vencedor.historiasdefamilias.entities.User;
import pe.com.vencedor.historiasdefamilias.fragments.SimpleWebViewFragment;
import pe.com.vencedor.historiasdefamilias.fragments.SongRecordFragment;
import pe.com.vencedor.historiasdefamilias.fragments.TutorialFragmentBeta;
import pe.com.vencedor.historiasdefamilias.utils.AppPreferences;
import pe.com.vencedor.historiasdefamilias.utils.Constants;
import pe.com.vencedor.historiasdefamilias.utils.RequestPost;
import pe.com.vencedor.historiasdefamilias.utils.RoundImage;
import pe.com.vencedor.historiasdefamilias.utils.StringRequestPost;
import pe.com.vencedor.historiasdefamilias.utils.Utils;

/**
 * Created by cesar on 16/08/2015.
 */
public class TutorialActivity extends AppCompatActivity {
    private String[] titulos;
    private DrawerLayout NavDrawerLayout;
    private ListView NavList;
    private ArrayList<MenuItem> navItem;
    private User user;
    private TextView lblNombres, lblPuntos;
    private ImageView imgFoto, shareApp;
    NavigationAdapter adapter;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pe.com.vencedor.historiasdefamilias.R.layout.actitivy_menu);

        Utils.windowEvent(this, "Tutorial", "Pantalla", "Viendo");
        /*Tracker tracker = ((VencedorApplication) getApplication()).getTracker(VencedorApplication.TrackerName.GLOBAL_TRACKER);
        tracker.setScreenName("Tutorial");
        tracker.send(new HitBuilders.EventBuilder().setCategory("Pantalla").setAction("Viendo").build());*/

        lblNombres = (TextView) findViewById(pe.com.vencedor.historiasdefamilias.R.id.lblNombres2);
        lblPuntos = (TextView) findViewById(pe.com.vencedor.historiasdefamilias.R.id.lblPuntos);
        imgFoto = (ImageView) findViewById(pe.com.vencedor.historiasdefamilias.R.id.imgFoto);
        shareApp = (ImageView) findViewById(pe.com.vencedor.historiasdefamilias.R.id.shareApp);
        NavDrawerLayout = (DrawerLayout) findViewById(pe.com.vencedor.historiasdefamilias.R.id.drawer_layout);
        NavList = (ListView) findViewById(pe.com.vencedor.historiasdefamilias.R.id.left_drawer);
        navItem = Constants.ARRAY_MENU_ITEM;
        titulos = getResources().getStringArray(pe.com.vencedor.historiasdefamilias.R.array.navigation_options);
        adapter = new NavigationAdapter(TutorialActivity.this, new ArrayList<MenuItem>());
        View header = getLayoutInflater().inflate(pe.com.vencedor.historiasdefamilias.R.layout.menu_header, null);
        //NavList.addHeaderView(header);
        //NavList.addFooterView(footer);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("http://historiasdefamilias.pe/?compartir=app"))
                            .build();

                    shareDialog.show(linkContent);
                    shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

                        @Override
                        public void onSuccess(Sharer.Result result) {
                            Log.d("SUCCESS SHARE", "OK");
                            boolean appShared = AppPreferences.getInstance(TutorialActivity.this).getBoolean(AppPreferences.PREF_SHARE_APP);
                            Log.d("COMPARTIDO", appShared + "");
                            shareApp();
                        }

                        @Override
                        public void onCancel() {
                            Log.d("CANCEL SHARE", "MAL");
                        }

                        @Override
                        public void onError(FacebookException e) {
                            Log.d("ERROR SHARE", "ERROR!!");
                            e.printStackTrace();
                        }
                    });
                }
            }
        });

        if (Constants.CURRENT_USER != null) {
//            user = intent.getParcelableExtra(Constants.KEY_PARAMS_USER);
            user = Constants.CURRENT_USER;
            lblNombres.setText(user.getNombres());
            lblPuntos.setText(user.getPuntos() + "");

            System.out.println("FOTO!!!!!!!!!!!!! " + user.getFacebookId());
            if (user.getFoto() != null) {
                File f = new File(Constants.PATH_IMG);
                Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
                RoundImage roundImage = new RoundImage(bmp);
                imgFoto.setImageDrawable(roundImage);
            }

            Log.d("USUARIO XD", user.getNombres());
        }

        if (navItem.size() == 0) {
            navItem.add(new MenuItem(titulos[navItem.size()]));
            navItem.add(new MenuItem(titulos[navItem.size()]));
            navItem.add(new MenuItem(titulos[navItem.size()]));
            navItem.add(new MenuItem(titulos[navItem.size()]));
            navItem.add(new MenuItem(titulos[navItem.size()]));
/*            navItem.add(new MenuItem(titulos[navItem.size()]));
            navItem.add(new MenuItem(titulos[navItem.size()]));*/
        }
        adapter.setList(navItem);
        NavList.setAdapter(adapter);
        NavList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  adapter.setSelectedIndex(position);
                FragmentManager fm = TutorialActivity.this.getSupportFragmentManager();
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentTransaction ft = fm.beginTransaction();

                Fragment fragment = null;

                switch (position) {
                    case 0:
                        fragment = SongRecordFragment.newInstance();
                        break;
                    /*case 1:
                        fragment = ProfileFragment.newInstance(user);
                        break;
                    case 2:
                        fragment = FamilyStoriesFragment.newInstance();
                        break;*/
                    case 1:
                        fragment = SimpleWebViewFragment.newInstance("Ranking", "app-ranking");
                        break;
                    case 2:
                        fragment = SimpleWebViewFragment.newInstance("Premios", "app-premios");
                        break;
                    case 3:
                        fragment = SimpleWebViewFragment.newInstance("Ganadores", "app-ganadores");
                        break;
                    case 4:
                        AppPreferences.getInstance(TutorialActivity.this).savePreference(AppPreferences.PREF_IS_USER_LOGGED, false);
                        Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    /*case 4:
                        AppPreferences.getInstance(MenuActivity.this).savePreference(AppPreferences.PREF_IS_USER_LOGGED, false);
                        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;*/
                }
                if (position != 4) {
                    ft.replace(pe.com.vencedor.historiasdefamilias.R.id.content_frame, fragment).commit();
                    NavDrawerLayout.closeDrawer(Gravity.LEFT);
                }
            }
        });
        Fragment fragment = TutorialFragmentBeta.newInstance();
        FragmentTransaction transaction = TutorialActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(pe.com.vencedor.historiasdefamilias.R.id.content_frame, fragment).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void shareApp() {
        Map<String, String> sendUser = new HashMap<String, String>();
        sendUser.put("id", Constants.CURRENT_USER.getUserId());
        sendUser.put("nivel", "app");

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
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
                                AppPreferences.getInstance(getApplicationContext()).savePreference(AppPreferences.PREF_PUNTAJE_USER, str_puntos);
                                AppPreferences.getInstance(getApplicationContext()).savePreference(AppPreferences.PREF_SHARE_APP, true);

                                lblPuntos.setText(user.getPuntos() + "");
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
    public void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

}
