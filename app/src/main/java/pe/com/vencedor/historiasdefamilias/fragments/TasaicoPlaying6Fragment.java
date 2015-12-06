package pe.com.vencedor.historiasdefamilias.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pe.com.vencedor.historiasdefamilias.R;
import pe.com.vencedor.historiasdefamilias.activities.TutorialActivity;

/**
 * Created by cesar on 8/20/15.
 */
public class TasaicoPlaying6Fragment extends Fragment {
    private View view;
    private ImageView ivVisor;
    private Toolbar custom_toolbar;

    public static Fragment newInstance(){
        return new TasaicoPlaying6Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tasaico_playing6,container,false);
        ivVisor = (ImageView)view.findViewById(R.id.ivVisor);

        ivVisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = TasaicoPlaying7Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.add(android.R.id.content, fragment).commit();
            }
        });
        initToolbar(view);
        return view;
    }

    private void initToolbar(View view) {
        custom_toolbar = (Toolbar)view.findViewById(R.id.custom_toolbar);
        final ImageView ivIcLeft = (ImageView)view.findViewById(R.id.ivIcLeft);
        TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);
        final ImageView ivIcRight = (ImageView)view.findViewById(R.id.ivIcRight);

        ivIcLeft.setImageResource(R.drawable.backarrow_blue);
        ivIcRight.setImageResource(R.drawable.ico_ayuda_header);

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

        tvTitle.setText("Los Tasaico");
    }
}
