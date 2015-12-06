package pe.com.vencedor.historiasdefamilias.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import pe.com.vencedor.historiasdefamilias.R;
import pe.com.vencedor.historiasdefamilias.activities.TutorialActivity;
import pe.com.vencedor.historiasdefamilias.entities.Winner;
import pe.com.vencedor.historiasdefamilias.utils.Constants;
import pe.com.vencedor.historiasdefamilias.utils.Utils;
import com.google.android.gms.analytics.GoogleAnalytics;

/**
 * Created by cesar on 8/18/15.
 */
public class WinnersFragment extends Fragment {
    private View view;
/*    private RecyclerView rvWinners;*/
    private LinearLayoutManager mLayoutManager;
/*    private WinnerAdapter adapter;*/
    private Toolbar custom_toolbar;
    private WebView wvWinners;

    public static Fragment newInstance(){
        return new WinnersFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_winners,container,false);

        Utils.windowEvent(getActivity(), "Ganadores", "Pantalla", "Viendo");
        /*Tracker tracker = ((VencedorApplication) getActivity().getApplication()).getTracker(VencedorApplication.TrackerName.GLOBAL_TRACKER);
        tracker.setScreenName("Ganadores");
        tracker.send(new HitBuilders.EventBuilder().setCategory("Pantalla").setAction("Viendo").build());*/

        wvWinners = (WebView) view.findViewById(R.id.wvWinners);
        wvWinners.loadUrl("http://historiasdefamilias.pe/services/app-ganadores");
/*        rvWinners = (RecyclerView)view.findViewById(R.id.rvWinners);*/
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
/*        rvWinners.setLayoutManager(mLayoutManager);
        adapter = new WinnerAdapter(new ArrayList<Winner>(),getActivity());*/
        if(Constants.ARRAY_LIST_WINNERS.size() == 0){
            Constants.ARRAY_LIST_WINNERS.add(new Winner(Constants.ARRAY_LIST_WINNERS.size(),"Juliana Perez",3711,"A"));
            Constants.ARRAY_LIST_WINNERS.add(new Winner(Constants.ARRAY_LIST_WINNERS.size(),"Ricardo Franco",3711,"B"));
            Constants.ARRAY_LIST_WINNERS.add(new Winner(Constants.ARRAY_LIST_WINNERS.size(),"Joaquin Flores",3711,"A"));
        }
/*        adapter.setList(Constants.ARRAY_LIST_WINNERS);
        rvWinners.setAdapter(adapter);*/

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

        tvTitle.setText("Ganadores");
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
