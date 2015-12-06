package pe.com.vencedor.historiasdefamilias.utils;

import android.content.Context;
import android.util.Log;

import com.moodstocks.android.MoodstocksError;
import com.moodstocks.android.Scanner;

/**
 * Created by Asahel on 29/08/2015.
 */
public final class ScannerSingleton implements Scanner.SyncListener{

    public static ScannerSingleton scannerSingleton;
    private static Scanner scanner;
    private static boolean compatible;
    private static boolean initialize;



    public static ScannerSingleton getInstance(){
        if(scannerSingleton==null)
            scannerSingleton = new ScannerSingleton();

        return scannerSingleton;
    }

    public void initScanner(Context context){
        initialize = true;
        compatible = Scanner.isCompatible();
        if (compatible) {
            try {
                scanner = Scanner.get();
                String path = Scanner.pathFromFilesDir(context, "scanner.db");
                Log.d("EL PACKAGE", path);
                scanner.open(path, Constants.API_KEY, Constants.API_SECRET);
                scanner.setSyncListener(this);
                scanner.sync();

            } catch (MoodstocksError e) {
                e.printStackTrace();
            }
        }
    }

    public void destroyScanner(){
        initialize = false;
        if (compatible) {
            try {
                scanner.close();
                scanner.destroy();
                scanner = null;
            } catch (MoodstocksError e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isInitialize(){
        return initialize;
    }

    @Override
    public void onSyncStart() {
        Log.d("Moodstocks SDK", "Sync will start.");
    }

    @Override
    public void onSyncComplete() {
        try {
            Log.d("Moodstocks SDK", "Sync succeeded (" + scanner.count() + " images)");
        } catch (MoodstocksError e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSyncFailed(MoodstocksError e) {
        Log.d("Moodstocks SDK", "Sync error #" + e.getErrorCode() + ": " + e.getMessage());
    }

    @Override
    public void onSyncProgress(int total, int current) {
        int percent = (int) ((float) current / (float) total * 100);
        Log.d("Moodstocks SDK", "Sync progressing: " + percent + "%");
    }
}
