package pe.com.vencedor.historiasdefamilias.utils;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import pe.com.vencedor.historiasdefamilias.fragments.TasaicoPlayingFragment;

/**
 * Created by Asahel on 04/09/2015.
 */
public class PlayingText extends Thread {

    private boolean running;
    private TextView lblTexto;
    private Activity activity;

    public PlayingText(TextView lblTexto,Activity activity) {
        this.lblTexto = lblTexto;
        this.activity = activity;
    }

    @Override
    public void run() {
        super.run();

        playing();
    }

    private void playing() {
        int count = 0;
        while (!TasaicoPlayingFragment.resultado) {

            try {
                synchronized (this) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("ES FALSE", "FALSE");
                            lblTexto.setText("Coloca la casa\n dentro del círculo");
                        }
                    });
                }

                Thread.sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }


    public void pauseThread() throws InterruptedException {
        running = false;
    }

    public void resumeThread() {
        running = true;
    }

    public boolean isRunning() {
        return running;
    }
}
