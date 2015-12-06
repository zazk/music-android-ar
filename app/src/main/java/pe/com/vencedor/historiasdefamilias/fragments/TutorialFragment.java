package pe.com.vencedor.historiasdefamilias.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import pe.com.vencedor.historiasdefamilias.utils.Constants;
import pe.com.vencedor.historiasdefamilias.utils.Utils;
import com.google.android.gms.analytics.GoogleAnalytics;

import java.util.ArrayList;
import java.util.List;

public class TutorialFragment extends Fragment {
    private Toolbar custom_toolbar;
    private View view;
    private SliderLayout imgSlider;
    private PagerIndicator custom_indicator;
    private TextView tvWelcome,tvTextAyuda;
    private Button btnSaltar;
    private ImageView ivPrev,ivNext;
    private DrawerLayout drawerLayout;
    private String[] tutorial_text;

    public static Fragment newInstance() {
        return new TutorialFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(pe.com.vencedor.historiasdefamilias.R.layout.fragment_tutorial,container,false);

        drawerLayout = (DrawerLayout)getActivity().findViewById(pe.com.vencedor.historiasdefamilias.R.id.drawer_layout);
        imgSlider = (SliderLayout)view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.imgSlider);
        custom_indicator = (PagerIndicator)view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.custom_indicator);
        tvWelcome = (TextView)view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.tvWelcome);
        tvTextAyuda = (TextView)view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.tvTextAyuda);
        btnSaltar = (Button)view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.btnSaltar);
        ivPrev = (ImageView)view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.ivPrev);
        ivNext = (ImageView)view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.ivNext);
        tutorial_text = getResources().getStringArray(pe.com.vencedor.historiasdefamilias.R.array.tutorial_text);
        tvTextAyuda.setText(Html.fromHtml(tutorial_text[0]));
        tvWelcome.setText(Html.fromHtml("¡Bienvenido,<br><b>" + Constants.CURRENT_USER.getNombres()+"</b>"));

       /* HashMap<String,Integer> images = new HashMap<String, Integer>();
        images.put("tuto1",R.drawable.ico_ayuda1);
        images.put("tuto2", R.drawable.ico_ayuda2);
        images.put("tuto3",R.drawable.ico_ayuda3);
        images.put("tuto4", R.drawable.ico_ayuda4);
        images.put("tuto5", R.drawable.ico_ayuda5);*/

        List<Integer> images = new ArrayList<>();
        images.add(pe.com.vencedor.historiasdefamilias.R.drawable.ico_ayuda1);
        images.add(pe.com.vencedor.historiasdefamilias.R.drawable.ico_ayuda2);
        images.add(pe.com.vencedor.historiasdefamilias.R.drawable.ico_ayuda3);
        images.add(pe.com.vencedor.historiasdefamilias.R.drawable.ico_ayuda4);
        images.add(pe.com.vencedor.historiasdefamilias.R.drawable.ico_ayuda5);

        imgSlider.setCustomIndicator(custom_indicator);
        for(int i= 0; i<images.size();i++){
            DefaultSliderView slider = new DefaultSliderView(getActivity());
            slider.image(images.get(i)).setScaleType(BaseSliderView.ScaleType.Fit);
            slider.bundle(new Bundle());
            slider.getBundle().putString("extra", i+"");
            imgSlider.addSlider(slider);
        }
        imgSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                //tvTextAyuda.setText(tutorial_text[i]);
            }

            @Override
            public void onPageSelected(int i) {
                tvTextAyuda.setText(Html.fromHtml(tutorial_text[i]));
                Utils.windowEvent(getActivity(), "Ayuda-" + (i + 1), "Pantalla", "Viendo");
                /*Tracker tracker = ((VencedorApplication) getActivity().getApplication()).getTracker(VencedorApplication.TrackerName.GLOBAL_TRACKER);
                tracker.setScreenName("Ayuda-" + (i+ 1));
                tracker.send(new HitBuilders.EventBuilder().setCategory("Pantalla").setAction("Viendo").build());*/
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSlider.moveNextPosition();
            }
        });

        ivPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSlider.movePrevPosition();
            }
        });

        btnSaltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = ExitTutorialDialog.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.add(pe.com.vencedor.historiasdefamilias.R.id.content_frame, fragment).commit();
            }
        });
//        initToolbar(view);
        Utils.windowEvent(getActivity(), "Ayuda-" + (imgSlider.getCurrentPosition() + 1), "Pantalla", "Viendo");
        imgSlider.stopAutoCycle();
        return view;
    }

    private void initToolbar(View view) {
        custom_toolbar = (Toolbar) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.custom_toolbar);
        final ImageView ic_menu = (ImageView) view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.ivIcLeft);
        final ImageView ivIcRight = (ImageView)view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.ivIcRight);
        TextView tvTitle = (TextView)view.findViewById(pe.com.vencedor.historiasdefamilias.R.id.tvTitle);
        ic_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        tvTitle.setText("Ayuda");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("INICIALIZANDO", "INIT");

        Utils.windowEvent(getActivity(), "Ayuda-" + (imgSlider.getCurrentPosition() + 1), "Pantalla", "Viendo");
/*        Tracker tracker = ((VencedorApplication) getActivity().getApplication()).getTracker(VencedorApplication.TrackerName.GLOBAL_TRACKER);
        tracker.setScreenName("Ayuda-" + (imgSlider.getCurrentPosition() + 1));
        tracker.send(new HitBuilders.EventBuilder().setCategory("Pantalla").setAction("Viendo").build());*/
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
