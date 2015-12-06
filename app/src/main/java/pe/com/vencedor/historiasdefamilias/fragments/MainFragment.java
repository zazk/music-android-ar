package pe.com.vencedor.historiasdefamilias.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainFragment extends Fragment {

    private AppCompatButton btnFacebook;

    private Button btnSignUp;

    private CallbackManager callbackmanager;

    public static Fragment newInstance(){
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(pe.com.vencedor.historiasdefamilias.R.layout.fragment_main,container,false);
        btnFacebook = (AppCompatButton) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.btnLoginFacebook);
        btnSignUp = (Button) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.btnSignUp);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        callbackmanager = CallbackManager.Factory.create();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnFacebook.setSupportBackgroundTintList(getResources().getColorStateList(pe.com.vencedor.historiasdefamilias.R.color.blue_facebook));
        btnFacebook.setOnClickListener(OnClickFacebookListener);
        btnSignUp.setOnClickListener(OnClickSignUpListener);
    }

    public View.OnClickListener OnClickFacebookListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onFbSignUp();
        }
    };

    public View.OnClickListener OnClickSignUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment fragment = SignUpFragment.newInstance();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(null);
            transaction.replace(android.R.id.content, fragment).commit();
        }
    };

    private void onFbSignUp() {
        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(MainFragment.this, Arrays.asList("email", "public_profile", "user_friends"));

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

                                                String fbid = json.getString("id");
                                                String str_name = json.getString("name");
                                                String str_email = null;
                                                if (json.has("email"))
                                                    str_email = json.getString("email");

                                                Fragment fragment = SignUpFragment.newInstance(fbid, str_name, str_email);
                                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                                transaction.addToBackStack(null);
                                                transaction.replace(android.R.id.content, fragment).commit();

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
                        Log.e("CANCELAR", "On cancel");
                    }

                    @Override
                    public void onError(FacebookException e) {
                        Log.e("ERROR", e.toString());
                        if (e instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                                onFbSignUp();
                            }
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackmanager.onActivityResult(requestCode, resultCode, data);
    }
}