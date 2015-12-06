package pe.com.vencedor.historiasdefamilias.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.com.vencedor.historiasdefamilias.R;
import pe.com.vencedor.historiasdefamilias.utils.OnTutorialArrowListener;
import pe.com.vencedor.historiasdefamilias.utils.Utils;
import com.google.android.gms.analytics.GoogleAnalytics;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TutorialFragmentBeta extends Fragment implements OnTutorialArrowListener{

    @InjectView(R.id.viewpager)
    ViewPager viewPager;

    public static Fragment newInstance() {
        return new TutorialFragmentBeta();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial_beta,container,false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] text = getResources().getStringArray(R.array.tutorial_text);
        SimplePagerAdapter pagerAdapter = new SimplePagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(TutorialItemFragment.newInstance(0, R.drawable.ico_ayuda1, text[0], this));
        pagerAdapter.addFragment(TutorialItemFragment.newInstance(1, R.drawable.ico_ayuda2, text[1], this));
        pagerAdapter.addFragment(TutorialItemFragment.newInstance(2, R.drawable.ico_ayuda3, text[2], this));
        pagerAdapter.addFragment(TutorialItemFragment.newInstance(3, R.drawable.ico_ayuda4, text[3], this));
        pagerAdapter.addFragment(TutorialItemFragment.newInstance(4, R.drawable.ico_ayuda5, text[4], this));
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Utils.windowEvent(getActivity(), "Ayuda-" + (position + 1), "Pantalla", "Viendo");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onNext() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    @Override
    public void onPreview() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(viewPager != null){
            Utils.windowEvent(getActivity(), "Ayuda-" + (viewPager.getCurrentItem() + 1), "Pantalla", "Viendo");
        }
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

    class SimplePagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        public SimplePagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentList = new ArrayList<>();
        }

        public void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

    }

}
