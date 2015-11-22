package cf.imxqd.LetWeightFly.Fragments;

import android.app.Activity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import cf.imxqd.LetWeightFly.Activities.MainActivity;
import cf.imxqd.LetWeightFly.R;

public class RecordFragment extends Fragment {
    View rootView;
    ViewPager viewPager;
    TabLayout tabLayout;
    TabsAdapter adapter;

    public RecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        adapter = new TabsAdapter(getChildFragmentManager(), this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_records, container, false);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        tabLayout.setTabTextColors(
                getResources().getColor(R.color.white_hint),
                getResources().getColor(R.color.white)
        );
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        return rootView;
    }

    @Override
    public void onDestroy()
    {
        rootView = null;
        viewPager.removeAllViews();
        tabLayout.removeAllViews();
        adapter.recycle();
        viewPager = null;
        tabLayout = null;
        adapter = null;
        super.onDestroy();
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
//        ((MainActivity)getActivity()).setShadow(View.VISIBLE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public static class TabsAdapter extends FragmentPagerAdapter
    {
        Fragment fragment1;
        Fragment fragment2;
        Fragment fragment3;
        Fragment fragment;
        public TabsAdapter(FragmentManager fm,Fragment fragment)
        {

            super(fm);
            this.fragment = fragment;
            fragment1 = new WeekFragment();
            fragment2 = new MonthFragment();
            fragment3 = new HistoryFragment();
        }

        public void recycle()
        {
            fragment1 = null;
            fragment2 = null;
            fragment3 = null;
            fragment = null;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragment1;
            switch (position)
            {
                case 0:
                    fragment = fragment1;
                    break;
                case 1:
                    fragment = fragment2;
                    break;
                case 2:
                    fragment = fragment3;
                    break;
                default:
            }
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return 3;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            System.out.println("getPageTitle: position "+position);
            switch (position) {
                case 0:
                    return fragment.getString(R.string.title_week);
                case 1:
                    return fragment.getString(R.string.title_month);
                case 2:
                    return fragment.getString(R.string.title_more);
            }
            return "";
        }
    }

}
