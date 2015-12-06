package pe.com.vencedor.historiasdefamilias.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import pe.com.vencedor.historiasdefamilias.activities.TutorialActivity;
import pe.com.vencedor.historiasdefamilias.entities.User;
import pe.com.vencedor.historiasdefamilias.utils.AppPreferences;
import pe.com.vencedor.historiasdefamilias.utils.Constants;
import pe.com.vencedor.historiasdefamilias.utils.MyStringUtils;
import pe.com.vencedor.historiasdefamilias.utils.RequestPost;
import pe.com.vencedor.historiasdefamilias.utils.RoundImage;
import pe.com.vencedor.historiasdefamilias.utils.StringRequestPost;
import pe.com.vencedor.historiasdefamilias.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SignUpFragment extends Fragment {

    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.toolbar)
    Toolbar toolbar;
    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.txtName)
    TextView txtName;
    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.txtEmail)
    TextView txtEmail;
    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.txtPassword)
    TextView txtPassword;
    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.txtPhoneNumber)
    TextView txtPhoneNumber;
    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.cbTerms)
    AppCompatCheckBox cbTerms;
    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.imgFotoPreview)
    ImageView imgFotoPreview;
    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.btnSignUp)
    AppCompatButton btnSignUp;

    String fbid;

    public static Fragment newInstance(String fbid, String fullName, String email) {
        Bundle bundle = new Bundle();
        bundle.putString("fbid", fbid);
        bundle.putString("fullName", fullName);
        bundle.putString("email", email);
        SignUpFragment signUpFragment = new SignUpFragment();
        signUpFragment.setArguments(bundle);
        return signUpFragment;
    }

    public static Fragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(pe.com.vencedor.historiasdefamilias.R.layout.fragment_signup, container, false);
        ButterKnife.inject(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (toolbar != null) {
            activity.setSupportActionBar(toolbar);
            assert activity.getSupportActionBar() != null;
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setTitle("Registro");
        }

        btnSignUp.setSupportBackgroundTintList(getResources().getColorStateList(pe.com.vencedor.historiasdefamilias.R.color.vencedor_light_blue));

        Bundle bundle = getArguments();
        if(bundle != null){
            fbid = bundle.getString("fbid");
            String fullName = bundle.getString("fullName");
            String email = bundle.getString("email");
            txtName.setText(fullName);
            txtName.setEnabled(false);
            txtEmail.setText(email);
            txtPassword.setVisibility(View.GONE);
        }

        if (fbid != null) {
            RoundImage roundImage = new RoundImage(Utils.getFacebookProfilePicture(fbid));
            imgFotoPreview.setImageDrawable(roundImage);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(pe.com.vencedor.historiasdefamilias.R.id.btnTerms)
    public void btnTermsOnClick(){
        Fragment fragment = TermsFragment.newInstance();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.replace(android.R.id.content, fragment).commit();
    }

    @OnClick(pe.com.vencedor.historiasdefamilias.R.id.btnSignUp)
    public void btnSignUpOnClick(){

        if(cbTerms.isChecked()){

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Cargando...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            final String name = txtName.getText().toString().trim();
            final String email = txtEmail.getText().toString().trim();
            final String password = txtPassword.getText().toString().trim();
            final String phoneNumber = txtPhoneNumber.getText().toString().trim();

            if (!name.isEmpty() && !email.isEmpty() && !phoneNumber.isEmpty()) {
                if(MyStringUtils.validateEmail(email)){
                    if (!password.isEmpty() || txtPassword.getVisibility() == View.GONE) {

                        Map<String, String> sendUser = new HashMap<>();
                        sendUser.put("nombres", name);
                        sendUser.put("email", email);
                        if(getArguments() != null){
                            sendUser.put("fbid", fbid);
                        } else {
                            sendUser.put("password", password);
                        }
                        sendUser.put("telefono", phoneNumber);

                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        StringRequestPost stringRequestPost = new StringRequestPost(true, RequestPost.TYPE_OBJECT, "http://historiasdefamilias.pe/services/registrar-usuario" + (getArguments() != null ? "-fb":""), sendUser,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("RESPONSE XD: ", String.valueOf(response));
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);

                                            if(jsonObject.has("success")){
                                                String str_success = jsonObject.getString("success");
                                                if (str_success.equals("true")) {

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

                                                    User user = new User();
                                                    user.setUserId(str_id);
                                                    user.setEmail(email);
                                                    user.setFacebookId(fbid);
                                                    user.setNombres(name);
                                                    if(!str_puntos.equals("null")){
                                                        user.setPuntos(Integer.parseInt(str_puntos));
                                                    }

                                                    if(fbid != null){
                                                        Log.i("EL FBID NO ES NULL",fbid);
                                                        user.setFoto(Utils.getFacebookProfilePicture(user.getFacebookId()));
                                                        Utils.windowEvent(getActivity(), "Login", "Usuario", fbid);
                                                        Utils.saveImageInStorage(user.getFoto());
                                                    }

                                                    //preferible poner usuario en variable a asar datos bundle de constructor.
                                                    Constants.CURRENT_USER = user;

                                                    AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_IS_USER_LOGGED, true);
                                                    AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_USER_ID, str_id);
                                                    AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_USER_FACEBOOKID, fbid);
                                                    AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_USER_NAME, str_nombres);
                                                    AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_USER_EMAIL, str_email);
                                                    if(!str_puntos.equals("null")) {
                                                        AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_PUNTAJE_USER, str_puntos);
                                                    }

                                                    progressDialog.dismiss();

                                                    Intent intent = new Intent(getActivity(), TutorialActivity.class);
                                                    startActivity(intent);
                                                    getActivity().finish();

                                                }
                                            }

                                            if(jsonObject.has("error")){
                                                String str_error = jsonObject.getString("error");
                                                if(str_error.equals("true")){
                                                    String str_message = jsonObject.getString("message");
                                                    progressDialog.dismiss();
                                                    new AlertDialog.Builder(getActivity()).
                                                            setCancelable(false).
                                                            setTitle("Aviso").
                                                            setMessage(str_message).
                                                            setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                }
                                                            }).show();
                                                }
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
                                        progressDialog.dismiss();
                                    }
                                }
                        );
                        queue.add(stringRequestPost);
                    } else {
                        progressDialog.dismiss();
                        new AlertDialog.Builder(getActivity()).
                                setCancelable(false).
                                setTitle("Aviso").
                                setMessage(Constants.MSG_PASSWORD_VALIDATION).
                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                } else {
                    progressDialog.dismiss();
                    new AlertDialog.Builder(getActivity()).
                            setCancelable(false).
                            setTitle("Aviso").
                            setMessage(Constants.MSG_EMAIL_INVALID).
                            setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            } else {
                progressDialog.dismiss();
                new AlertDialog.Builder(getActivity()).
                        setCancelable(false).
                        setTitle("Datos de Contacto").
                        setMessage(Constants.MSG_CONTACT_DATA).
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }

        } else {
            new AlertDialog.Builder(getActivity()).
                    setCancelable(false).
                    setTitle("Aviso").
                    setMessage("Debes aceptar los términos y condiciones").
                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }

    }

}