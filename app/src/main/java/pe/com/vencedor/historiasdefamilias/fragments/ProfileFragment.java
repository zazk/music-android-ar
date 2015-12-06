package pe.com.vencedor.historiasdefamilias.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import pe.com.vencedor.historiasdefamilias.activities.TutorialActivity;
import pe.com.vencedor.historiasdefamilias.entities.User;
import pe.com.vencedor.historiasdefamilias.utils.Constants;

/**
 * Created by cesar on 8/17/15.
 */

public class ProfileFragment extends Fragment {
    private View view;
    private Toolbar custom_toolbar;
    private EditText txtPuntos,txtPuntaje,txtEmail,txtPassword,txtUsername;
    private TextView lblNombres;

    public static Fragment newInstance(){
        return new ProfileFragment();
    }

    public static Fragment newInstance(User user){
        Fragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        System.out.println("ENTRO AQUI XDDD");
        bundle.putString(Constants.KEY_PARAMS_NOMBRES, user.getNombres());
        bundle.putInt(Constants.KEY_PARAMS_PUNTOS, user.getPuntos());
        bundle.putInt(Constants.KEY_PARAMS_PUNTAJE, 0);
        bundle.putString(Constants.KEY_PARAMS_USERNAME, user.getNombres());
        bundle.putString(Constants.KEY_PARAMS_EMAIL, user.getEmail());
        bundle.putString(Constants.KEY_PARAMS_PASSWORD, user.getPassword());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(pe.com.vencedor.historiasdefamilias.R.layout.fragment_profile,container,false);
        lblNombres = (TextView) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.lblNombres);
        txtPuntos = (EditText) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.txtPuntos);
        txtPuntaje = (EditText) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.txtPuntaje);
        txtUsername = (EditText) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.txtUsername);
        txtEmail = (EditText) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.txtEmail3);
        txtPassword = (EditText) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.txtPassword3);

        Bundle bundle = getArguments();
        if (bundle != null) {
            System.out.println("LOS PUNTOS " + bundle.getInt(Constants.KEY_PARAMS_PUNTOS));
            lblNombres.setText(bundle.getString(Constants.KEY_PARAMS_NOMBRES));
            txtPuntos.setText(bundle.getInt(Constants.KEY_PARAMS_PUNTOS)+"");
            txtPuntaje.setText(bundle.getInt(Constants.KEY_PARAMS_PUNTAJE)+ "");
            txtUsername.setText(bundle.getString(Constants.KEY_PARAMS_USERNAME));
            txtEmail.setText(bundle.getString(Constants.KEY_PARAMS_EMAIL));
            txtPassword.setText(bundle.getString(Constants.KEY_PARAMS_PASSWORD));
        }
        initToolbar(view);
        return view;
    }

    private void initToolbar(View view) {
        custom_toolbar = (Toolbar)view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.custom_toolbar);
        final ImageView ivIcLeft = (ImageView)view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.ivIcLeft);
        TextView tvTitle = (TextView)view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.tvTitle);
        final ImageView ivIcRight = (ImageView)view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.ivIcRight);

        ivIcLeft.setImageResource(pe.com.vencedor.historiasdefamilias.R.drawable.backarrow_blue);
        ivIcRight.setImageResource(pe.com.vencedor.historiasdefamilias.R.drawable.ico_ayuda_header);

        ivIcLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        ivIcRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TutorialActivity.class);
                startActivity(intent);
            }
        });

        tvTitle.setText("Mi Perfil");
    }
}
