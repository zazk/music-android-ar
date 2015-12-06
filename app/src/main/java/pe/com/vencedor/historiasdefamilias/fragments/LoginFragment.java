package pe.com.vencedor.historiasdefamilias.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import pe.com.vencedor.historiasdefamilias.R;
import pe.com.vencedor.historiasdefamilias.VencedorApplication;
import pe.com.vencedor.historiasdefamilias.activities.MenuActivity;
import pe.com.vencedor.historiasdefamilias.utils.Constants;
import pe.com.vencedor.historiasdefamilias.utils.RequestPost;
import com.facebook.CallbackManager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by cesar on 16/08/2015.
 */
public class LoginFragment extends Fragment {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.txtName)
    TextView txtName;
    @InjectView(R.id.txtEmail)
    TextView txtEmail;
    @InjectView(R.id.txtPassword)
    TextView txtPassword;
    @InjectView(R.id.txtPhoneNumber)
    TextView txtPhoneNumber;

    private CallbackManager callbackmanager;

    public static Fragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        final ActionBar actionbar = activity.getSupportActionBar();
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                toolbar.setTitle("INICIAR SESIÒN");
                actionbar.setDisplayHomeAsUpEnabled(true);
            }
        });
    }

    @OnClick(R.id.btnLogin)
    public void btnLoginOnClick(){
        String nombres = txtName.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String telefono = txtPhoneNumber.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        System.out.println("EL EMAIL " + email);
        System.out.println("EL PASSWORD " + password);

        if(!email.isEmpty())
            if(!password.isEmpty()){
                Map<String, Object> sendData = new HashMap<>();
                sendData.put("nombres", nombres);
                sendData.put("email",email);
                sendData.put("telefono",telefono);
                sendData.put("password", password);


                Map<String, String> params = new HashMap<String, String>();
                Gson gson = new Gson();
                String raw = gson.toJson(sendData);
                Log.v("requestfilter", raw);
                params.put("raw", raw);

                Log.d("LA DATA", email + " - " + password);

                RequestPost request = new RequestPost(RequestPost.TYPE_OBJECT, "http://historiasdefamilias.pe/services/registrar-usuario",params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.v("json",response.toString());
                                try {
                                    /*JSONArray jsonArray = response.getJSONArray("");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    }*/
                                    System.out.println("RESPONSE LOGUIN XD: " + String.valueOf(response));

                                    if(response.getBoolean("success")){

                                        Intent intent = new Intent(getActivity(), MenuActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();

                                    }

                                } catch (JSONException e) {
                                    System.out.println("ERROR!! XD1 " +e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                System.out.println("ERROR!! XD1 " +volleyError.getMessage());
                                volleyError.getStackTrace();
                            }
                        }
                );
                request.setRetryPolicy(new DefaultRetryPolicy(15 * 1000, 0, 0));
                RequestQueue queue = VencedorApplication.getInstance().getRequestQueue();
                queue.add(request);



            }
            else
                Toast.makeText(getActivity().getApplicationContext(), Constants.MSG_PASSWORD_VALIDATION, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getActivity().getApplicationContext(), Constants.MSG_EMAIL_VALIDATION, Toast.LENGTH_LONG).show();
    }

    /*private void onFbLogin() {
        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(LoginFragment.this, Arrays.asList("email", "public_profile", "user_friends"));

        LoginManager.getInstance().registerCallback(callbackmanager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        System.out.println("Success");
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject json, GraphResponse response) {
                                        if (response.getError() != null) {
                                            // handle error
                                            System.out.println("ERROR");
                                        } else {
                                            System.out.println("Success");
                                            try {

                                                String jsonresult = String.valueOf(json);
                                                System.out.println("JSON Result" + jsonresult + response);

                                                String str_fbid = json.getString("id");
                                                String str_name = json.getString("name");
                                                String str_email = json.getString("email");

                                                User user = new User();
                                                user.setEmail(str_email);
                                                user.setFacebookId(str_fbid);
                                                user.setNombres(str_name);

                                                Constants.CURRENT_USER = user;

                                                Intent intent = new Intent(getActivity(), MenuActivity.class);
                                                intent.putExtra(Constants.KEY_PARAMS_USER, user);
                                                startActivity(intent);
                                                getActivity().finish();

                                                AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_IS_USER_LOGGED, true);
//                                                AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_USER_ID,str_id);
                                                AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_USER_FACEBOOKID,str_fbid);
                                                AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_USER_NAME,str_name);
                                                AppPreferences.getInstance(getActivity()).savePreference(AppPreferences.PREF_USER_EMAIL,str_email);

                                                Log.d("MIPERFIL", str_fbid + " - " + str_name + " - " + str_email);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Log.d("CANCELAR", "On cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("ERROR", error.toString());
                    }
                });
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackmanager.onActivityResult(requestCode, resultCode, data);
    }

}

