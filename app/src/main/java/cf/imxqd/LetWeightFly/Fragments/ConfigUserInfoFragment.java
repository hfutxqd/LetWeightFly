package cf.imxqd.LetWeightFly.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

import cf.imxqd.LetWeightFly.Activities.ConfigUserInfo;
import cf.imxqd.LetWeightFly.R;
import cf.imxqd.LetWeightFly.Utils.Tool;


public class ConfigUserInfoFragment extends Fragment {

    public ConfigUserInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config_user_info, container, false);
        Tool.initStatusBar(view, container.getContext());
        ConfigUserInfo.lastBtn.setText(R.string.cancel);
        ConfigUserInfo.nextBtn.setText(R.string.next);
        Log.i("ConfigUserInfoFragment", "onCreateView");

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ConfigUserInfo.crPage = 0;
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
