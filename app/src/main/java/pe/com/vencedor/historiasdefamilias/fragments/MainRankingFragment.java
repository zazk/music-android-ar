package pe.com.vencedor.historiasdefamilias.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import pe.com.vencedor.historiasdefamilias.R;
import pe.com.vencedor.historiasdefamilias.activities.TutorialActivity;

public class MainRankingFragment extends Fragment {
    private View view;
/*    private ViewPager pager;
    private PagerSlidingTabStrip tabs;
    MainRankingPagerAdapter adapter;*/
    private Toolbar custom_toolbar;

    public static Fragment newInstance(){
        return new MainRankingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_ranking,container,false);
        WebView myWebView = (WebView) view.findViewById(R.id.wvRanking);
        myWebView.loadUrl("http://historiasdefamilias.pe/services/app-ranking");
/*        pager = (ViewPager)view.findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip)view.findViewById(R.id.tabs);
        adapter = new MainRankingPagerAdapter(getActivity(),new ArrayList<Ranking>());

        if(Constants.ARRAY_LIST_RANKING.size() == 0){
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_AMIGOS,"Persona1",1200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_GLOBAL,"Persona2",2200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_AMIGOS,"Persona3",3200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_GLOBAL,"Persona4",4200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_AMIGOS,"Persona5",5200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_GLOBAL,"Persona6",6200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_AMIGOS,"Persona7",1200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_GLOBAL,"Persona8",2200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_AMIGOS,"Persona9",3200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_GLOBAL,"Persona10",4200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_AMIGOS,"Persona11",5200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_GLOBAL,"Persona12",6200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_AMIGOS,"Persona13",1200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_GLOBAL,"Persona14",2200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_AMIGOS,"Persona15",3200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_GLOBAL,"Persona16",4200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_AMIGOS,"Persona17",5200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_GLOBAL,"Persona18",6200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_AMIGOS,"Persona19",1200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_GLOBAL,"Persona20",2200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_AMIGOS,"Persona21",3200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_GLOBAL,"Persona22",4200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_AMIGOS,"Persona23",5200));
            Constants.ARRAY_LIST_RANKING.add(new Ranking(Constants.ARRAY_LIST_RANKING.size(),Constants.TYPE_GLOBAL,"Persona24",6200));
        }
        adapter.setRankings(Constants.ARRAY_LIST_RANKING);
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);*/
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

        tvTitle.setText("Ranking");
    }
}
