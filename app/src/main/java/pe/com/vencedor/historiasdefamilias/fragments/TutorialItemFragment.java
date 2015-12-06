package pe.com.vencedor.historiasdefamilias.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pe.com.vencedor.historiasdefamilias.utils.Constants;
import pe.com.vencedor.historiasdefamilias.utils.OnTutorialArrowListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

/**
 * @author Kevin Salazar
 * @link kevicsalazar.com
 */
public class TutorialItemFragment extends Fragment{

    int position;
    int imageResId;
    String message;
    private OnTutorialArrowListener listener;

    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.tvWelcome)
    TextView tvWelcome;
    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.icoAyuda)
    ImageView icoAyuda;
    @InjectView(pe.com.vencedor.historiasdefamilias.R.id.tvTextAyuda)
    TextView tvTextAyuda;
    @InjectViews({
            pe.com.vencedor.historiasdefamilias.R.id.dot1,
            pe.com.vencedor.historiasdefamilias.R.id.dot2,
            pe.com.vencedor.historiasdefamilias.R.id.dot3,
            pe.com.vencedor.historiasdefamilias.R.id.dot4,
            pe.com.vencedor.historiasdefamilias.R.id.dot5
    })
    List<ImageView> dots;

    public static TutorialItemFragment newInstance(int position, int imageResId, String message, OnTutorialArrowListener listener) {
        TutorialItemFragment fragment = new TutorialItemFragment();
        fragment.position = position;
        fragment.imageResId = imageResId;
        fragment.message = message;
        fragment.listener = listener;
        return fragment;
    }

    public TutorialItemFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(pe.com.vencedor.historiasdefamilias.R.layout.fragment_tutorial_item, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvWelcome.setText(Html.fromHtml("¡Bienvenido,<br><b>" + Constants.CURRENT_USER.getNombres() + "!</b>"));
        icoAyuda.setImageResource(imageResId);
        tvTextAyuda.setText(Html.fromHtml(message));
        dots.get(position).setAlpha(1f);
    }

    @OnClick(pe.com.vencedor.historiasdefamilias.R.id.ivPrev)
    public void ivPrevOnClick(){
        listener.onPreview();
    }

    @OnClick(pe.com.vencedor.historiasdefamilias.R.id.ivNext)
    public void ivNextOnClick(){
        listener.onNext();
    }

    @OnClick(pe.com.vencedor.historiasdefamilias.R.id.btnSaltar)
    public void btnSaltarOnClick(){
        Fragment fragment = ExitTutorialDialog.newInstance();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.add(pe.com.vencedor.historiasdefamilias.R.id.content_frame, fragment).commit();
    }

}
