package cf.imxqd.LetWeightFly.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import cf.imxqd.LetWeightFly.R;

/**
 * Created by Henry on 2015/7/29.
 *
 */
public class AddListAdapter extends BaseAdapter{

    List<String> itemList;
    public AddListAdapter(List<String> list)
    {
        itemList = list;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.add_list_item, parent, false);
            itemHolder = new ItemHolder(convertView);
            convertView.setTag(itemHolder);
        }
        itemHolder = (ItemHolder) convertView.getTag();
        itemHolder.itemText.setText(itemList.get(position));
        return convertView;
    }

    static class ItemHolder{
        public TextView itemText;
        public ItemHolder(View root)
        {
            itemText = (TextView) root.findViewById(R.id.text);
        }
    }
}
