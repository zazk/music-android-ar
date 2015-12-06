package pe.com.vencedor.historiasdefamilias.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pe.com.vencedor.historiasdefamilias.activities.TutorialActivity;
import pe.com.vencedor.historiasdefamilias.utils.Constants;

public class HurtadoGameFragment extends Fragment {

    private View view;
    private Toolbar custom_toolbar;

    public static Fragment newInstance(){
        return new HurtadoGameFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(pe.com.vencedor.historiasdefamilias.R.layout.fragment_game_hurtado,container,false);
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

        tvTitle.setText(Constants.FAMILIA_SELECCIONADA.getName());
    }
}
