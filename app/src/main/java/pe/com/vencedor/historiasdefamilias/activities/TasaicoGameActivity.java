package pe.com.vencedor.historiasdefamilias.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import pe.com.vencedor.historiasdefamilias.fragments.TasaicoPlayingFragment;
import pe.com.vencedor.historiasdefamilias.utils.Constants;
import pe.com.vencedor.historiasdefamilias.utils.ScannerSingleton;
import pe.com.vencedor.historiasdefamilias.utils.Utils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.moodstocks.android.Scanner;

public class TasaicoGameActivity extends AppCompatActivity {

    private boolean compatible = false;
    private Scanner scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pe.com.vencedor.historiasdefamilias.R.layout.activity_game_hurtado);

        Utils.windowEvent(this, "Familia " + Constants.FAMILIA_SELECCIONADA.getName().substring(0, Constants.FAMILIA_SELECCIONADA.getName().indexOf(" ")) + " Escanner",
                "Pantalla", "Jugando");

        Fragment fragment = TasaicoPlayingFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(android.R.id.content, fragment).commit();

/*        if(!ScannerSingleton.getInstance().isInitialize())
            ScannerSingleton.getInstance().initScanner(getApplicationContext());*/


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constants.SUB_NIVEL = 1;

        ScannerSingleton.getInstance().destroyScanner();

    }

    @Override
    protected void onResume() {
        Log.d("PASO POR ACTIVITY", "aqui " + ScannerSingleton.getInstance().isInitialize() + " -");
        super.onResume();

        if(!ScannerSingleton.getInstance().isInitialize())
            ScannerSingleton.getInstance().initScanner(getApplicationContext());
        Utils.windowEvent(this, "Familia " + Constants.FAMILIA_SELECCIONADA.getName().substring(0, Constants.FAMILIA_SELECCIONADA.getName().indexOf(" ")) + " Escanner",
                "Pantalla", "Jugando");

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


    /*---------------------*/

}
