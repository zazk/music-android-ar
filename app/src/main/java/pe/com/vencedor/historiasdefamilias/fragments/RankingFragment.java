package pe.com.vencedor.historiasdefamilias.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.com.vencedor.historiasdefamilias.R;
import pe.com.vencedor.historiasdefamilias.adapters.RankingAdapter;
import pe.com.vencedor.historiasdefamilias.entities.Ranking;
import pe.com.vencedor.historiasdefamilias.utils.Constants;
import pe.com.vencedor.historiasdefamilias.utils.Utils;
import com.google.android.gms.analytics.GoogleAnalytics;

import java.util.ArrayList;

/**
 * Created by cesar on 8/19/15.
 */
public class RankingFragment extends Fragment{
    private View view;
    private RecyclerView rvRanking;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Ranking> list;
    private Bundle bundle;
    RankingAdapter adapter;

    public static Fragment newInstance(ArrayList<Ranking> rankings, int pos){
        Fragment fragment = new RankingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.KEY_PARAMS_ARRAY, rankings);
        bundle.putInt("position",pos);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Fragment newInstance(){
        return new RankingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ranking,container,false);
        Utils.windowEvent(getActivity(), "Raking", "Pantalla", "Viendo");
        /*Tracker tracker = ((VencedorApplication) getActivity().getApplication()).getTracker(VencedorApplication.TrackerName.GLOBAL_TRACKER);
        tracker.setScreenName("Raking");
        tracker.send(new HitBuilders.EventBuilder().setCategory("Pantalla").setAction("Viendo").build());*/

        rvRanking = (RecyclerView)view.findViewById(R.id.rvRanking);
        adapter = new RankingAdapter(getActivity(), new ArrayList<Ranking>());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvRanking.setLayoutManager(mLayoutManager);
        int pos = -1;
        bundle = this.getArguments();
        if(bundle != null){
            list = bundle.getParcelableArrayList(Constants.KEY_PARAMS_ARRAY);
            pos = bundle.getInt("position");
        }
        ArrayList<Ranking> filterList = new ArrayList<>();
        for(Ranking content : list){
            if(pos == 0){
                if(content.getType() == Constants.TYPE_GLOBAL){
                    filterList.add(content);
                }
            }else{
                if(content.getType() == Constants.TYPE_AMIGOS){
                    filterList.add(content);
                }
            }

        }
        adapter.setList(filterList);
        rvRanking.setAdapter(adapter);
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
