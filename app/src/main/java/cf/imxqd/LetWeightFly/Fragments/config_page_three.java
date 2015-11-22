package cf.imxqd.LetWeightFly.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.lang.reflect.Field;

import cf.imxqd.LetWeightFly.Activities.ConfigUserInfo;
import cf.imxqd.LetWeightFly.R;
import cf.imxqd.LetWeightFly.Utils.Tool;


public class config_page_three extends Fragment {

    public config_page_three() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config_page_three, container, false);
        Tool.initStatusBar(view, container.getContext());
        ConfigUserInfo.lastBtn.setText(R.string.last);
        ConfigUserInfo.nextBtn.setText(R.string.done);

        Log.i("config_page_three", "onCreateView");
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ConfigUserInfo.crPage = 3;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
