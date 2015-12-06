package pe.com.vencedor.historiasdefamilias.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import pe.com.vencedor.historiasdefamilias.R;
import pe.com.vencedor.historiasdefamilias.VencedorApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Asahel on 24/08/2015.
 */
public class Utils {
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getFacebookProfilePicture(String userID) {
        Bitmap bitmap = null;
        try{
            URL imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
            bitmap= BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        }catch(IOException e){
            e.printStackTrace();
        }

        return bitmap;
    }

    public static List<String> getStrofa(Context context, int position) {
        final Resources resources = context.getResources();
        InputStream inputStream = null;
        switch (position) {
            case 1:
                inputStream = resources.openRawResource(R.raw.estrofa_1);
                break;
            case 2:
                inputStream = resources.openRawResource(R.raw.estrofa_2);
                break;
            case 3:
                inputStream = resources.openRawResource(R.raw.estrofa_3);
                break;
            case 4:
                inputStream = resources.openRawResource(R.raw.estrofa_4);
                break;
            case 5:
                inputStream = resources.openRawResource(R.raw.estrofa_5);
                break;
            case 6:
                inputStream = resources.openRawResource(R.raw.estrofa_6);
                break;
            case 7:
                inputStream = resources.openRawResource(R.raw.estrofa_7);
                break;
            default:
                inputStream = resources.openRawResource(R.raw.estrofa_8);
                break;

        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> lista = new ArrayList<>();
        try {

            StringBuilder sb = new StringBuilder(512);
            String strLine;
            int c = 0;
            int cont = 0;
            while ((strLine = reader.readLine()) != null) {
                sb.append(strLine.trim() + " ").append("\n\n");
/*                if(line.charAt(0)=='-'|| Character.isDigit(line.charAt(0))){
                    continue;
                    //falta q adicione
                }
                String[] strings = line.split("\\.",2);
                ItemDic itemDic = new ItemDic(cont,strings[0],strings[1]);
                if (strings.length < 2) continue;
                lista.add(itemDic);
                *//*long id = addWord(strings[0].trim(), strings[1].trim());
                cont++;
                if (id < 0) {
                    Log.e("PROBLEMA", "NO SE PUDO AGREGAR: " + strings[0].trim());
                }*/

                cont++;
            }

            String[] splitted = sb.toString().split("(?<!\\S)\\d(?!\\S)");

            lista = Arrays.asList(splitted);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("FIN", "Termino de cargar");
        return lista;
    }

    public static boolean saveImageInStorage(Bitmap bitmap) {
        boolean success = false;
        try {
            File newFolder = new File(Environment.getExternalStorageDirectory(), Constants.FOLDER_IMG_NAME);
            if (!newFolder.exists()) {
                newFolder.mkdir();
            }

            File image = new File(newFolder, Constants.IMG_NAME);

            // Encode the file as a PNG image.
            FileOutputStream outStream;
            try {
                outStream = new FileOutputStream(image);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                /* 100 to keep full quality of the image */

                outStream.flush();
                outStream.close();
                success = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }

    public static void windowEvent(Activity activity,final String screenName,final String category, final String action){
        Tracker tracker = ((VencedorApplication) activity.getApplication()).getTracker(VencedorApplication.TrackerName.GLOBAL_TRACKER);
        tracker.setScreenName(screenName);
        tracker.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).build());
    }

}
