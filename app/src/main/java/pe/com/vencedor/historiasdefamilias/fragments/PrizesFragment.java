package pe.com.vencedor.historiasdefamilias.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import pe.com.vencedor.historiasdefamilias.R;
import pe.com.vencedor.historiasdefamilias.activities.TutorialActivity;
import pe.com.vencedor.historiasdefamilias.utils.Utils;
import com.google.android.gms.analytics.GoogleAnalytics;

/**
 * Created by cesar on 18/08/2015.
 */
public class PrizesFragment extends Fragment {
    private View view;
/*    private RecyclerView lvPrizes;*/

    private LinearLayoutManager mLayoutManager;
/*    private PrizeAdapter adapter;*/
    private String[] prizeTitle;
    private String[] prizeDescription;
    private Toolbar custom_toolbar;
    private WebView wvPrizes;

    public static Fragment newInstance(){
        return new PrizesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_prizes,container,false);

        Utils.windowEvent(getActivity(), "Premios", "Pantalla", "Viendo");
        /*Tracker tracker = ((VencedorApplication) getActivity().getApplication()).getTracker(VencedorApplication.TrackerName.GLOBAL_TRACKER);
        tracker.setScreenName("Premios");
        tracker.send(new HitBuilders.EventBuilder().setCategory("Pantalla").setAction("Viendo").build());*/

        wvPrizes = (WebView) view.findViewById(R.id.wvPrizes);
        wvPrizes.loadUrl("http://historiasdefamilias.pe/services/app-premios");

        wvPrizes.setWebViewClient(new WebViewClient() {

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.v("onPageStarted", "onPageStarted");
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.v("shouldOverrideUrlLoadi", "shouldOverrideUrlLoading");


                Utils.windowEvent(getActivity(), "Premios-Termino", "Pantalla", "Viendo");
                /*Tracker tracker = VencedorApplication.getInstance().getTracker(VencedorApplication.TrackerName.GLOBAL_TRACKER);
                tracker.setScreenName("Premios-Termino");
                tracker.send(new HitBuilders.EventBuilder().setCategory("Pantalla").setAction("Viendo").build());*/
                return false;
            }

            public void onPageFinished(WebView view, String url) {
                Log.v("onPageFinished", "onPageFinished");
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.v("onReceivedError", "onReceivedError");
            }
        });

/*        lvPrizes = (RecyclerView)view.findViewById(R.id.lvPrizes);*/
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
/*        lvPrizes.setLayoutManager(mLayoutManager);*/
/*        adapter = new PrizeAdapter(new ArrayList<Prize>(),getActivity());*/
        prizeTitle = getResources().getStringArray(R.array.prize_name);
        prizeDescription = getResources().getStringArray(R.array.prize_description);

/*        if(Constants.ARRAY_LIST_PRIZES.size() == 0){
            Constants.ARRAY_LIST_PRIZES.add(new Prize(Constants.ARRAY_LIST_PRIZES.size(),prizeTitle[Constants.ARRAY_LIST_PRIZES.size()],prizeDescription[Constants.ARRAY_LIST_PRIZES.size()]));
            Constants.ARRAY_LIST_PRIZES.add(new Prize(Constants.ARRAY_LIST_PRIZES.size(),prizeTitle[Constants.ARRAY_LIST_PRIZES.size()],prizeDescription[Constants.ARRAY_LIST_PRIZES.size()]));
            Constants.ARRAY_LIST_PRIZES.add(new Prize(Constants.ARRAY_LIST_PRIZES.size(),prizeTitle[Constants.ARRAY_LIST_PRIZES.size()],prizeDescription[Constants.ARRAY_LIST_PRIZES.size()]));
        }*/
/*        adapter.setList(Constants.ARRAY_LIST_PRIZES);
        lvPrizes.setAdapter(adapter);*/

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

        tvTitle.setText("Premios");
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
