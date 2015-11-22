package cf.imxqd.LetWeightFly.Adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cf.imxqd.LetWeightFly.Model.Web.Task;
import cf.imxqd.LetWeightFly.R;

/**
 * Created by Henry on 2015/7/12.
 *
 */
public class TodayListAdapter extends BaseAdapter {
    List<Task> taskList;

    public TodayListAdapter()
    {
        this.taskList = new ArrayList<>();
    }

    public TodayListAdapter(List<Task> taskList)

    {
        this.taskList = taskList;
    }

    public void addTask(Task task)
    {

    }
    public void removeTask(int index)
    {

    }

    public void recycle()
    {
        taskList.clear();
        taskList = null;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        if(convertView == null)
        {
            System.out.println("TodayListAdapter:a new convertView created.");
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.today_list_item, parent, false);
            itemHolder = new ItemHolder(convertView);
            convertView.setTag(itemHolder);
        }else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        Task task = taskList.get(position);
        itemHolder.setType(task.getType());
        itemHolder.today_list_item_text.setText(task.getContent());
        itemHolder.today_list_item_energy.setText(task.getEnergy() + "大卡");
        itemHolder.today_list_item_time.setText(task.getTime());

        return convertView;
    }
    public static class ItemHolder{
        public Drawable ic_sport;
        public Drawable ic_food;
        public Drawable ic_weight;
        public ImageView today_list_item_icon;
        public TextView today_list_item_text;
        public TextView today_list_item_energy;
        public TextView today_list_item_time;
        public ItemHolder(View itemView) {
            today_list_item_icon = (ImageView) itemView.findViewById(R.id.today_task_item_icon);
            today_list_item_text = (TextView) itemView.findViewById(R.id.today_task_item_text);
            today_list_item_time = (TextView) itemView.findViewById(R.id.today_task_item_time);
            today_list_item_energy = (TextView) itemView.findViewById(R.id.today_task_item_energy);
            ic_sport = ContextCompat.getDrawable(itemView.getContext(),R.drawable.ic_sport);
            ic_food  = ContextCompat.getDrawable(itemView.getContext(),R.drawable.ic_food);
            ic_weight= ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_weight);
        }

        public void setType(Task.Type type)
        {
            switch (type)
            {
                case TYPE_FOOD:
                    today_list_item_icon.setImageDrawable(ic_food);
                    today_list_item_icon.setBackgroundResource(R.drawable.circle_eating);
                    break;
                case TYPE_WEIGHT:
                    today_list_item_icon.setImageDrawable(ic_weight);
                    today_list_item_icon.setBackgroundResource(R.drawable.circle_weight);
                    break;
                case TYPE_SPORT:
                    today_list_item_icon.setImageDrawable(ic_sport);
                    today_list_item_icon.setBackgroundResource(R.drawable.circle_sport);
                    break;
            }
        }
    }
}
