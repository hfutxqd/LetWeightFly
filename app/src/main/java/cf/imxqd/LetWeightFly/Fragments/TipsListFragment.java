package cf.imxqd.LetWeightFly.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import cf.imxqd.LetWeightFly.Activities.WebViewActivity;
import cf.imxqd.LetWeightFly.Adapter.TipsListAdapter;
import cf.imxqd.LetWeightFly.R;
import cf.imxqd.LetWeightFly.Views.LoadingView;
import cf.imxqd.LetWeightFly.Views.RefreshLayout;


public class TipsListFragment extends Fragment implements
        AdapterView.OnItemClickListener{

    private static final String ARG_PARAM1 = "MODE";
    private String MODE;
    View rootView;
    ListView listView;
    TipsListAdapter adapter;
    RefreshLayout refreshLayout;
    LoadingView loadingView;
    RelativeLayout network_error;


    public static TipsListFragment newInstance(String MODE) {
        TipsListFragment fragment = new TipsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, MODE);
        fragment.setArguments(args);
        return fragment;
    }

    public TipsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            MODE = getArguments().getString(ARG_PARAM1);
        }
        System.out.println("A new TipsListFragment created..");
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(container != null) {
            rootView = inflater.inflate(R.layout.fragment_tips_list, container, false);
            listView = (ListView) rootView.findViewById(R.id.listview);
            refreshLayout = (RefreshLayout) rootView.findViewById(R.id.swipe_layout);
            loadingView = (LoadingView) rootView.findViewById(R.id.loadingView);
            network_error = (RelativeLayout) rootView.findViewById(R.id.network_error_layout);

            loadingView.setVisibility(View.VISIBLE);
            network_error.setVisibility(View.GONE);
            adapter = new TipsListAdapter(MODE,refreshLayout, loadingView,network_error);
            adapter.startPull();
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);

            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    adapter.firstPage();
                }
            });
            refreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
                @Override
                public void onLoad() {
                    adapter.nextPage();
                }
            });
        }
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("Url", adapter.getUrl(position));
        intent.putExtra("title", adapter.getTitle(position));
        intent.setClass(getActivity(), WebViewActivity.class);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit);
    }

    @Override
    public void onDestroy() {
        adapter.recycle();
        adapter = null;
        loadingView.destroyDrawingCache();
        loadingView = null;
        System.gc();
        super.onDestroy();
    }
}
