package pe.com.vencedor.historiasdefamilias.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TermsFragment extends Fragment {

    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.toolbar)
    Toolbar toolbar;
    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.webview)
    WebView webview;

    public static Fragment newInstance(){
        return new TermsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(pe.com.vencedor.historiasdefamilias.R.layout.fragment_simple_webview,container,false);
        ButterKnife.inject(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (toolbar != null) {
            activity.setSupportActionBar(toolbar);
            assert activity.getSupportActionBar() != null;
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setTitle("Términos y Condiciones");
        }
        webview.loadUrl("http://historiasdefamilias.pe/services/app-terminos");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
