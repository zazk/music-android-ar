package pe.com.vencedor.historiasdefamilias.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;

import pe.com.vencedor.historiasdefamilias.entities.Ranking;
import pe.com.vencedor.historiasdefamilias.fragments.RankingFragment;

import java.util.ArrayList;

/**
 * Created by cesar on 8/19/15.
 */
public class MainRankingPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private ArrayList<Ranking> rankings;

    public MainRankingPagerAdapter(FragmentActivity activity, ArrayList<Ranking> categories) {
        super(activity.getSupportFragmentManager());
        context = activity;
        this.rankings = categories;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Ranking> getRankings() {
        return rankings;
    }

    public void setRankings(ArrayList<Ranking> rankings) {
        this.rankings = rankings;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new RankingFragment().newInstance(getRankings(),position);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Global";
        }else{
            return "Amigos";
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
