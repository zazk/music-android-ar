package pe.com.vencedor.historiasdefamilias.utils;

import pe.com.vencedor.historiasdefamilias.entities.Family;
import pe.com.vencedor.historiasdefamilias.entities.MenuItem;
import pe.com.vencedor.historiasdefamilias.entities.Prize;
import pe.com.vencedor.historiasdefamilias.entities.Ranking;
import pe.com.vencedor.historiasdefamilias.entities.User;
import pe.com.vencedor.historiasdefamilias.entities.Winner;

import java.util.ArrayList;

/**
 * Created by cesar on 16/08/2015.
 */
public class Constants {

    public static final String API_KEY = "utzvxjeappmcrmxz8gad";
    public static final String API_SECRET = "odOBSMW30yr1ygxu";

    public static ArrayList<Family> ARRAY_LIST_FAMILIES = new ArrayList<>() ;
    public static ArrayList<MenuItem> ARRAY_MENU_ITEM = new ArrayList<>() ;
    public static ArrayList<Prize> ARRAY_LIST_PRIZES = new ArrayList<>() ;
    public static ArrayList<Winner> ARRAY_LIST_WINNERS = new ArrayList<>() ;
    public static ArrayList<Ranking> ARRAY_LIST_RANKING = new ArrayList<>();

    public static String KEY_PARAMS_ARRAY = "array";
    public static String MSG_FULLNAME_VALIDATION = "Por favor, ingrese su Nombre completo";
    public static String MSG_TERMS = "Debes aceptar los términos y condiciones";
    public static String MSG_EMAIL_VALIDATION = "Por favor, ingrese su E-mail";
    public static String MSG_PASSWORD_VALIDATION = "Por favor, ingrese su Contraseña";
    public static String MSG_EMAIL_INVALID = "Correo no válido";
    public static String MSG_CONFIRM_PASSWORD_VALIDATION = "Por favor, ingrese su Contraseña nuevamente";
    public static String MSG_DISTINCT_PASSWORD = "Contraseña no coincide";
    public static String MSG_NUMBER_PHONE_VALIDATION = "Por favor, ingrese su numero de telefono";
    public static String MSG_CONTACT_DATA = "Necesitamos tu correo y celular válidos para participar del sorteo.";
    public static String MSG_SONG_VALIDATION = "Para escuchar la canción completa, deberás escanear las 9 casitas.";
    public static String MSG_LVL_VALIDATION = "Debe completar el nivel anterior para poder jugar.";

    public static final String KEY_PARAMS_USER = "User";
    public static final String KEY_PARAMS_NOMBRES = "Nombres";
    public static final String KEY_PARAMS_PUNTOS = "Puntos";
    public static final String KEY_PARAMS_PUNTAJE = "Puntaje";
    public static final String KEY_PARAMS_USERNAME = "Username";
    public static final String KEY_PARAMS_EMAIL = "Email";
    public static final String KEY_PARAMS_PASSWORD = "Password";

    public static final String KEY_PARAMS_PLAYER = "Player";
    public static final String KEY_PARAMS_PRIZES = "Prizes";
    public static final String KEY_PARAMS_NIVEL = "Nivel";
    public static final String STR_WELCOME_TO_LVL = "BIENVENIDO AL <b>NIVEL ";
    public static final String KEY_PARAMS_TITLE_GAME = "Titulo";
    public static final String KEY_NEXT_LVL = "Siguiente Nivel";

    public static User CURRENT_USER = null;

    public static int NIVEL_SELECCIONADO = 0;
    public static int SUB_NIVEL = 1;
    public static Family FAMILIA_SELECCIONADA = null;

    public static int TYPE_GLOBAL = 1;
    public static int TYPE_AMIGOS = 2;



    public static String ARTICULO = "Familia ";


    public static String PATH_IMG = "/mnt/sdcard/VencedorApp/profileVencedor.png";
    public static String FOLDER_IMG_NAME = AppPreferences.PREF_NAME + "App";
    public static String IMG_NAME = "profileVencedor.png";
}
