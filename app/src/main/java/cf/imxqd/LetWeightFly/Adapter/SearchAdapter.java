package cf.imxqd.LetWeightFly.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import cf.imxqd.LetWeightFly.Internet.PullFromWeb;
import cf.imxqd.LetWeightFly.Model.Web.SearchRes;
import cf.imxqd.LetWeightFly.R;

/**
 * Created by Henry on 2015/7/10.
 */
public class SearchAdapter extends BaseAdapter {
    ArrayList<SearchRes> list;
    MyHandler handler = new MyHandler(this);
    Context context;
    String lastKey = "";
    public SearchAdapter(Context context)
    {
        this.context = context;
        list = new ArrayList<>();
    }

    public void search(final String key)
    {
        if(lastKey.equals(key))
        {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(handler.adapter != null) {
                        System.out.println("Search for Key: " + key);
                        ArrayList<SearchRes> list
                                = PullFromWeb.search(key);
                        Bundle data = new Bundle();
                        data.putParcelableArrayList("list", list);
                        Message msg = Message.obtain(handler);
                        msg.setData(data);
                        handler.sendMessage(msg);
                    }

                } catch (IOException e) {
                    handler.sendEmptyMessage(404);
                }
            }
        }).start();
        lastKey = key;
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
        SearchItemViewHolder item;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.search_item, null);
            convertView.setTag(new SearchItemViewHolder(convertView));
        }
        item = (SearchItemViewHolder) convertView.getTag();
        item.title.setText(list.get(position).title);
        item.describe.setText(list.get(position).describe);
        return convertView;
    }

    static class SearchItemViewHolder
    {
        public TextView title;
        public TextView describe;
        public SearchItemViewHolder(View root)
        {
            title = (TextView) root.findViewById(R.id.title);
            describe = (TextView) root.findViewById(R.id.describe);
        }
    }

    static class MyHandler extends android.os.Handler
    {
        SearchAdapter adapter;
        public MyHandler(SearchAdapter adapter)
        {
            this.adapter = adapter;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 404)
            {
                System.out.println("Search: network error!");
            }else {
                Bundle data = msg.getData();
                if (data != null) {
                    ArrayList tmp
                            = data.getParcelableArrayList("list");
                    adapter.list.clear();
                    System.gc();
                    adapter.list.addAll(tmp);
                    adapter.notifyDataSetChanged();
                }
            }
        }
        public void recycle()
        {
            adapter = null;
        }
    }

    public void recycle()
    {
        handler.recycle();
        list.clear();
        list = null;
        context = null;
    }

    @Override
    protected void finalize() throws Throwable {
        context = null;
        super.finalize();
    }
}
