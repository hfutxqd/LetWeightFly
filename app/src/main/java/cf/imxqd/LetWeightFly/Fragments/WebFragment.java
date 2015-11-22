package cf.imxqd.LetWeightFly.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.io.IOException;

import cf.imxqd.LetWeightFly.Activities.WebViewActivity;
import cf.imxqd.LetWeightFly.Internet.PullFromWeb;
import cf.imxqd.LetWeightFly.R;

public class WebFragment extends Fragment {

    private static final String ARG_PARAM1 = "URL";
    private static final String ARG_PARAM2 = "MODE";

    public static final int MODE_TIPS = 0;
    public static final int MODE_SEARCH = 1;

    View rootView;
    LinearLayout webviewLayout;
    WebView webView;
    String Url;
    int Mode;
    public static WebFragment newInstance(String Url,int MODE) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, Url);
        args.putInt(ARG_PARAM2,MODE);
        fragment.setArguments(args);
        return fragment;
    }

    public WebFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Url = getArguments().getString(ARG_PARAM1);
            Mode = getArguments().getInt(ARG_PARAM2);
        }
    }
    Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_web, container, false);
        webviewLayout = (LinearLayout) rootView.findViewById(R.id.webview);
        //屏蔽长按事件
        webView = new WebView(getActivity().getApplicationContext());
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (getActivity() != null) {
                    if (msg.what == 404) {
                        ((WebViewActivity) getActivity()).loadingView.setVisibility(View.INVISIBLE);
                        ((FrameLayout) rootView).addView(
                                View.inflate(getActivity().getBaseContext(),
                                        R.layout.network_error, null)
                        );
                    } else {
                        ((WebViewActivity) getActivity()).loadingView.setVisibility(View.INVISIBLE);
                        Bundle data = msg.getData();
                        String content = data.getString("content");
                        WebSettings settings = webView.getSettings();
                        settings.setLoadWithOverviewMode(true);
                        webView.loadData(content, "text/html; charset=UTF-8", null);
                    }
                }
                return true;
            }
        });
        System.out.println("Url in WebView is :" + Url);
        if(Mode == MODE_TIPS) {
            new Thread(new PullContent()).start();
        }else if(Mode == MODE_SEARCH)
        {
            new Thread(new PullSearchContent()).start();
        }
        webviewLayout.addView(webView);
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDestroy() {
        webView.destroy();
        webView.destroyDrawingCache();
        webviewLayout.removeAllViews();
        webviewLayout.destroyDrawingCache();
        rootView.destroyDrawingCache();
        webView = null;
        webviewLayout = null;
        rootView = null;
        super.onDestroy();
    }

    class PullSearchContent implements Runnable
    {

        @Override
        public void run() {
            try {
                String content = PullFromWeb.getSearchContentHtml(Url);
                Bundle data = new Bundle();
                data.putString("content", content);
                Message msg = Message.obtain(handler);
                msg.setData(data);
                handler.sendMessage(msg);

            } catch (IOException e) {
                handler.sendEmptyMessage(404);
            }
        }
    }


    class PullContent implements Runnable
    {

        @Override
        public void run() {
            try {
                String content = PullFromWeb.getContentHtmlFrom39jianFei(Url);
                Bundle data = new Bundle();
                data.putString("content",content);
                Message msg = new Message();
                msg.setData(data);
                handler.sendMessage(msg);

            } catch (IOException e) {
                handler.sendEmptyMessage(404);
            }
        }
    }

}
