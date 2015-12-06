package pe.com.vencedor.historiasdefamilias.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import pe.com.vencedor.historiasdefamilias.activities.TutorialActivity;
import pe.com.vencedor.historiasdefamilias.utils.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SimpleWebViewFragment extends Fragment {

    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.toolbar)
    Toolbar toolbar;
    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.webview)
    WebView webview;

    private DrawerLayout drawerLayout;

    public static Fragment newInstance(String title, String url){
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        SimpleWebViewFragment fragment = new SimpleWebViewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(pe.com.vencedor.historiasdefamilias.R.layout.fragment_simple_webview,container,false);
        drawerLayout = (DrawerLayout) getActivity().findViewById(pe.com.vencedor.historiasdefamilias.R.id.drawer_layout);
        ButterKnife.inject(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (toolbar != null) {
            toolbar.setNavigationIcon(pe.com.vencedor.historiasdefamilias.R.drawable.ic_menu_white);
            activity.setSupportActionBar(toolbar);
            assert activity.getSupportActionBar() != null;
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setTitle(getArguments().getString("title"));
        }
        Utils.windowEvent(getActivity(), getArguments().getString("title"), "Pantalla", "Viendo");
        webview.loadUrl("http://historiasdefamilias.pe/services/" + getArguments().getString("url"));
        webview.setWebViewClient(new WebViewClient() {

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
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(pe.com.vencedor.historiasdefamilias.R.menu.menu_info, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case pe.com.vencedor.historiasdefamilias.R.id.action_help:
                Intent intent = new Intent(getActivity(), TutorialActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
