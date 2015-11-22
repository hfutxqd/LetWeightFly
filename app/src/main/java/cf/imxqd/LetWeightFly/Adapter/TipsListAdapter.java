package cf.imxqd.LetWeightFly.Adapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cf.imxqd.LetWeightFly.Internet.PullArticleList;
import cf.imxqd.LetWeightFly.Model.Web.Article;
import cf.imxqd.LetWeightFly.R;
import cf.imxqd.LetWeightFly.Views.LoadingView;
import cf.imxqd.LetWeightFly.Views.RefreshLayout;

/**
 * Created by Henry on 2015/6/25.
 * Adapter for ListView in TipsFragment
 */
public class TipsListAdapter extends BaseAdapter {
    List<Article> list;
    RefreshLayout refreshLayout;
    RelativeLayout network_error;
    LoadingView loadingView;
    int crrPage = 1;
    int maxPage = 0;
    PullArticleList articleList;
    PullWeb pullWebTask;
    private MyHandler mHandler;

    /**
     *
     * @param MODE     One Tab title must cast to one Mode
     * @param refreshLayout     When refresh complete,TipsListAdapter must stop it
     * @param loadingView       When loading complete,TipsListAdapter must hide it
     * @param network_error     When network error,TipsListAdapter must show it
     */
    public TipsListAdapter(String MODE, RefreshLayout refreshLayout, LoadingView loadingView
    ,RelativeLayout network_error)
    {
        list = new ArrayList<>();
        articleList = new PullArticleList(MODE);
        this.refreshLayout = refreshLayout;
        this.loadingView = loadingView;
        this.network_error = network_error;
        mHandler = new MyHandler(this);
        pullWebTask = new PullWeb(this);
        System.out.println("A new TipsListAdapter object created...");
    }

    public void startPull()
    {
        new Thread(pullWebTask).start();
    }

    public void recycle()
    {
        list.clear();
        refreshLayout = null;
        network_error = null;
        loadingView = null;
        mHandler = null;

    }

    @Override
    protected void finalize() throws Throwable {
        recycle();
        super.finalize();
    }

    public boolean firstPage()
    {
        crrPage = 1;
        new Thread(pullWebTask).start();
        return true;
    }

    public boolean nextPage()
    {
        if(crrPage < maxPage)
        {
            crrPage++;
            new Thread(pullWebTask).start();
            return true;
        }
        refreshLayout.setLoading(false);
        return false;
    }


    public void setList(List<Article> list)
    {
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    public void appendList(List<Article> list)
    {

        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    public String getUrl(int postion)
    {
        return list.get(postion).getUrl();
    }
    public String getTitle(int postion)
    {
        return list.get(postion).getTitle();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TipsItemViewHolder item;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tips_list_item, null);
            convertView.setTag(new TipsItemViewHolder(convertView));
        }
        item = (TipsItemViewHolder) convertView.getTag();
        item.title.setText(list.get(position).getTitle());
        item.time.setText(list.get(position).getTime());
        return convertView;
    }

    static class TipsItemViewHolder
    {
        public TextView title;
        public TextView time;
        public TipsItemViewHolder(View root)
        {
            title = (TextView) root.findViewById(R.id.title);
            time = (TextView) root.findViewById(R.id.time);
        }
    }

    public static class MyHandler extends Handler
    {
        TipsListAdapter adp;
        public MyHandler(TipsListAdapter adapter)
        {
            adp = adapter;
        }

        @Override
        public void handleMessage(Message msg)
        {
            System.out.println("MessageTime"+msg.getWhen());
            if(adp.loadingView != null) {
                if (msg.what == 404) {
                    adp.loadingView.setVisibility(View.INVISIBLE);
                    adp.network_error.setVisibility(View.VISIBLE);
                    adp.refreshLayout.setRefreshing(false);
                    adp.list.clear();
                    adp.notifyDataSetChanged();
                } else {
                    Bundle data = msg.getData();
                    ArrayList tmp = data.getParcelableArrayList("list");
                    if (adp.crrPage > 1) {
                        adp.appendList(tmp);
                        adp.refreshLayout.setLoading(false);
                        adp.network_error.setVisibility(View.GONE);
                    } else {
                        adp.setList(tmp);
                        adp.refreshLayout.setRefreshing(false);
                        adp.network_error.setVisibility(View.GONE);
                        adp.loadingView.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }


    }

    static class PullWeb implements Runnable
    {
        WeakReference<TipsListAdapter> adp;
        public PullWeb(TipsListAdapter adapter)
        {
            adp = new WeakReference<TipsListAdapter>(adapter);
        }
        @Override
        public void run() {
            try {
                if(adp.get() != null) {
                    ArrayList<Article> tmp =
                            adp.get().articleList.getArticleListFrom39jianFei(adp.get().crrPage);
                    adp.get().maxPage = adp.get().articleList.getMaxPage();
                    Message msg = Message.obtain(adp.get().mHandler);
                    Bundle data = new Bundle();
                    data.putParcelableArrayList("list", tmp);
                    msg.setData(data);
                    if (adp.get().loadingView != null)
                        adp.get().mHandler.sendMessage(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
                if(adp.get().loadingView != null)
                    adp.get().mHandler.sendEmptyMessage(404);
            }finally {
                adp.clear();
                System.out.println("Thread completed at:" + System.currentTimeMillis());
            }
        }
    }
}
