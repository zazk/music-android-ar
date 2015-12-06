package pe.com.vencedor.historiasdefamilias.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mopub.mobileads.MoPubConversionTracker;

import pe.com.vencedor.historiasdefamilias.entities.User;
import pe.com.vencedor.historiasdefamilias.fragments.MainFragment;
import pe.com.vencedor.historiasdefamilias.utils.AppPreferences;
import pe.com.vencedor.historiasdefamilias.utils.Constants;
import pe.com.vencedor.historiasdefamilias.utils.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pe.com.vencedor.historiasdefamilias.R.layout.activity_main);

        new MoPubConversionTracker().reportAppOpen(this);

        Log.d("EL ESTADO!!!!!!! ", "" + AppPreferences.getInstance(MainActivity.this).isUserLogged());

        if(AppPreferences.getInstance(MainActivity.this).isUserLogged()){
            User user = new User();
            user.setUserId(AppPreferences.getInstance(MainActivity.this).getString(AppPreferences.PREF_USER_ID));
            user.setEmail(AppPreferences.getInstance(MainActivity.this).getString(AppPreferences.PREF_USER_EMAIL));
            user.setFacebookId(AppPreferences.getInstance(MainActivity.this).getString(AppPreferences.PREF_USER_FACEBOOKID));
            user.setNombres(AppPreferences.getInstance(MainActivity.this).getString(AppPreferences.PREF_USER_NAME));
            int nivel = AppPreferences.getInstance(MainActivity.this).getInt(AppPreferences.PREF_NIVEL_COMPLETE);
            user.setNivel(nivel);

            try{
                Log.e(":)", AppPreferences.getInstance(MainActivity.this).getString(AppPreferences.PREF_PUNTAJE_USER) + ":Fdhh");
                String puntos = AppPreferences.getInstance(MainActivity.this).getString(AppPreferences.PREF_PUNTAJE_USER);
                user.setPuntos(Integer.parseInt(puntos.trim()));
            }catch (Exception e){
                e.printStackTrace();
            }

            Utils.windowEvent(this, "AbriendoAPP", "Usuario", user.getFacebookId());

            Constants.CURRENT_USER = user;

            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            intent.putExtra(Constants.KEY_PARAMS_USER, user);
            startActivity(intent);
            finish();
        } else {
            AppPreferences.getInstance(MainActivity.this).savePreference(AppPreferences.PREF_NIVEL_USER, "1-1");
            AppPreferences.getInstance(MainActivity.this).savePreference(AppPreferences.PREF_PUNTAJE_USER, "0");

            Fragment fragment = MainFragment.newInstance();
            FragmentTransaction transaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.add(android.R.id.content, fragment).commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("SE ABRE LA APP","EJECUTANDO...");
    }
}
