package pe.com.vencedor.historiasdefamilias.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cesar on 6/12/15.
 */
public class AppPreferences {

    public static final String PREF_NAME = "Vencedor";
    public static final String PREF_IS_USER_LOGGED = "IS_USER_LOGGED";
    public static final String PREF_USER_ID = "USER_ID";
    public static final String PREF_USERNAME = "USERNAME";
    public static final String PREF_USER_LASTNAME = "USER_LASTNAME";
    public static final String PREF_USER_NAME = "USER_NAME";
    public static final String PREF_USER_EMAIL = "USER_EMAIL";
    public static final String PREF_USER_PHONENUMBER = "USER_PHONENUMBER";
    public static final String PREF_USER_BIRTHDAY = "USER_BIRTHDAY";
    public static final String PREF_USER_GENRE = "USER_GENRE";
    public static final String PREF_USER_FACEBOOKID = "USER_FACEBOOKID";
    public static final String PREF_NIVEL_USER = "NIVEL_USER";
    public static final String PREF_PUNTAJE_USER = "PUNTAJE_USER";
    public static final String PREF_NIVEL_COMPLETE = "NIVEL_COMPLETE";
    public static final String PREF_SHARE_APP = "SHARE_APP";

    /*------------*/
    public static AppPreferences appPreferences;
    public SharedPreferences sharedPreferences;

    public static AppPreferences getInstance(final Context context){
        if(appPreferences == null){
            appPreferences = new AppPreferences(context.getApplicationContext());
        }
        return appPreferences;
    }

    public void clearSharedPreference(){
        sharedPreferences.edit().clear().apply();
    }

    public AppPreferences(final Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    }

    public String getString(final String prefKey){
        return sharedPreferences.getString(prefKey, "");
    }

    public int getInt(final String prefKey){
        return sharedPreferences.getInt(prefKey, 0);
    }

    public boolean getBoolean(final String prefKey){
        return sharedPreferences.getBoolean(prefKey, false);
    }

    public boolean isUserLogged(){
        return sharedPreferences.getBoolean(PREF_IS_USER_LOGGED, false);
    }

    public void putInt(final String strPrefKey, final int objPrefValue){
        sharedPreferences.edit().putInt(strPrefKey, objPrefValue).apply();
    }

    public void savePreference(final String strPrefKey, final Object objPrefValue){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (objPrefValue instanceof Integer) {
            editor.putInt(strPrefKey, (Integer) objPrefValue);
        } else if (objPrefValue instanceof String) {
            editor.putString(strPrefKey, (String) objPrefValue);
        } else if (objPrefValue instanceof Boolean) {
            editor.putBoolean(strPrefKey, (Boolean) objPrefValue);
        } else if (objPrefValue instanceof Long) {
            editor.putLong(strPrefKey, (Long) objPrefValue);
        } else if (objPrefValue instanceof Float) {
            editor.putFloat(strPrefKey, (Float) objPrefValue);
        }
        editor.apply();
    }

}
