package cf.imxqd.LetWeightFly.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.lang.reflect.Field;
import cf.imxqd.LetWeightFly.Activities.MainActivity;
import cf.imxqd.LetWeightFly.Adapter.WebTabsAdapter;
import cf.imxqd.LetWeightFly.R;

public class TipsFragment extends Fragment {

    View rootView;
    ViewPager viewPager;
    TabLayout tabLayout;
    WebTabsAdapter adapter;
    public static TipsFragment newInstance() {
        TipsFragment fragment = new TipsFragment();
        return fragment;
    }

    public TipsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tips, container, false);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        adapter = new WebTabsAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
        tabLayout.setTabTextColors(
                getResources().getColor(R.color.white_hint),
                getResources().getColor(R.color.white)
        );

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
    @Override
    public void onDetach() {
        adapter.recycle();
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

    @Override
    public void onResume()
    {
        super.onResume();
        ((MainActivity)getActivity()).setShadow(View.INVISIBLE);
    }
    @Override
    public void onPause()
    {
        super.onPause();
        //((MainActivity)getActivity()).setShadow(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        adapter.recycle();
        rootView = null;
        viewPager.removeAllViews();
        viewPager = null;
        tabLayout.removeAllViews();
        tabLayout = null;
        super.onStop();
    }
}
