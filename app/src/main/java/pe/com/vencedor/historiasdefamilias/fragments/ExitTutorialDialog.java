package pe.com.vencedor.historiasdefamilias.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pe.com.vencedor.historiasdefamilias.activities.MenuActivity;
import pe.com.vencedor.historiasdefamilias.utils.Utils;
import com.google.android.gms.analytics.GoogleAnalytics;

/**
 * Created by cesar on 8/19/15.
 */
public class ExitTutorialDialog extends Fragment {
    private View view;
    private Button btnNo,btnSi;

    public static Fragment newInstance(){
        return new ExitTutorialDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(pe.com.vencedor.historiasdefamilias.R.layout.dialog_exit_tutorial,container,false);

        Utils.windowEvent(getActivity(), "Ayuda-Cerrar", "Pantalla", "Viendo");
        /*Tracker tracker = ((VencedorApplication) getActivity().getApplication()).getTracker(VencedorApplication.TrackerName.GLOBAL_TRACKER);
        tracker.setScreenName("Ayuda-Cerrar");
        tracker.send(new HitBuilders.EventBuilder().setCategory("Pantalla").setAction("Viendo").build());*/

        btnNo = (Button)view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.btnNo);
        btnSi = (Button)view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.btnSi);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(getActivity()).reportActivityStart(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(getActivity()).reportActivityStop(getActivity());
    }
}
