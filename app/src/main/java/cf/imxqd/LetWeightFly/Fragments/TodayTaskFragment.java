package cf.imxqd.LetWeightFly.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cf.imxqd.LetWeightFly.Activities.MainActivity;
import cf.imxqd.LetWeightFly.Activities.SearchActivity;
import cf.imxqd.LetWeightFly.Adapter.AddListAdapter;
import cf.imxqd.LetWeightFly.Adapter.TodayListAdapter;
import cf.imxqd.LetWeightFly.DataOp.DataBase;
import cf.imxqd.LetWeightFly.DataOp.StepCounter;
import cf.imxqd.LetWeightFly.Model.DataBase.Food_Lib;
import cf.imxqd.LetWeightFly.Model.DataBase.Sport_Lib;
import cf.imxqd.LetWeightFly.Model.DataBase.Weight_Rec;
import cf.imxqd.LetWeightFly.Model.Web.Task;
import cf.imxqd.LetWeightFly.R;


public class TodayTaskFragment extends Fragment implements AbsListView.OnScrollListener
,View.OnClickListener{

    FloatingActionButton sport;
    FloatingActionButton food;
    FloatingActionButton weight;
    FloatingActionMenu menu;
    ListView listView;
    TodayListAdapter adapter;
    View taskHeader;
    TextView stepcount;
    TextView sport_total;
    TextView food_total;
    TextView weight_total;

    public static TodayTaskFragment newInstance() {
        TodayTaskFragment fragment = new TodayTaskFragment();

        return fragment;
    }

    public TodayTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_task, container, false);
        listView = (ListView) view.findViewById(R.id.listview);
        taskHeader =LayoutInflater.from(getActivity())
                .inflate(R.layout.task_header,null);

        stepcount = (TextView) taskHeader.findViewById(R.id.today_step_count_title);
        sport_total = (TextView) taskHeader.findViewById(R.id.sport_total);
        food_total = (TextView) taskHeader.findViewById(R.id.food_total);
        weight_total = (TextView) taskHeader.findViewById(R.id.weight_total);

        Task task = new Task("2321", Task.Type.TYPE_FOOD,234f,132312323l);
        Task task2 = new Task("sdfasdasdd", Task.Type.TYPE_SPORT,-223f,132312323l);
        ArrayList<Task> taskArrayList = new ArrayList<>();
        taskArrayList.add(task);
        taskArrayList.add(task);
        taskArrayList.add(task);
        taskArrayList.add(task);
        taskArrayList.add(task);
        taskArrayList.add(task2);
        taskArrayList.add(task);
        taskArrayList.add(task2);
        taskArrayList.add(task);
        taskArrayList.add(task);
        taskArrayList.add(task);
        taskArrayList.add(task);
        taskArrayList.add(task2);
        taskArrayList.add(task);
        taskArrayList.add(task2);
        adapter = new TodayListAdapter(taskArrayList);
        listView.setAdapter(adapter);
        listView.addHeaderView(taskHeader);
        listView.setOnScrollListener(this);

        menu = (FloatingActionMenu) view.findViewById(R.id.menu);
        sport = (FloatingActionButton) view.findViewById(R.id.menu_sport);
        food = (FloatingActionButton) view.findViewById(R.id.menu_food);
        weight = (FloatingActionButton) view.findViewById(R.id.menu_weight);

        sport.setOnClickListener(this);
        food.setOnClickListener(this);
        weight.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StepCounter stepCounter = new StepCounter(getActivity().getApplicationContext());
        int count = stepCounter.getCurrTodayCount();
        stepcount.setText(getString(R.string.today_walk_step)+count
        +getString(R.string.today_walk_step2)+step2food(count));
    }

    private String step2food(int count)
    {
        DecimalFormat df = new DecimalFormat("0.0");
        float kll = count /100 * 3;
        if(kll <= 50)
        {
            return df.format(kll/16)+getString(R.string.today_walk_step3_scallion);
        }else if(kll <= 100)
        {
            return df.format(kll/76)+getString(R.string.today_walk_step3_egg);
        }else if (kll <= 150)
        {
            return df.format(kll/83)+getString(R.string.today_walk_step3_apple);
        }else if (kll <= 200) {
            return (kll/150)+getString(R.string.today_walk_step3_chicken);
        }else if (kll <= 250) {
            return df.format(kll/210)+getString(R.string.today_walk_step3_cola);
        }
        return "";
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.recycle();
        adapter = null;
        System.gc();
        System.out.println("TodayTaskFragment:onDestroy");
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }
    private int mPreviousVisibleItem;
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem > mPreviousVisibleItem) {
            menu.hideMenuButton(true);
        } else if (firstVisibleItem < mPreviousVisibleItem) {
            menu.showMenuButton(true);
        }
        mPreviousVisibleItem = firstVisibleItem;

        if (firstVisibleItem == 0 &&
                ((MainActivity)getActivity()).toolbar_shadow.getVisibility() == View.VISIBLE)
        {
            System.out.println("VISIBLE ---> INVISIBLE");
            ((MainActivity)getActivity()).toolbar_shadow.setVisibility(View.INVISIBLE);
            ((MainActivity)getActivity()).setShadow(View.INVISIBLE);
        }else if(firstVisibleItem >= 1 &&
                ((MainActivity)getActivity()).toolbar_shadow.getVisibility() == View.INVISIBLE)
        {
            System.out.println("INVISIBLE ---> VISIBLE");
            ((MainActivity)getActivity()).setShadow(View.VISIBLE);
        }
    }
    @Override
    public void onClick(View v) {

        View titleView = LayoutInflater.from(getActivity())
                .inflate(R.layout.add_title_view, null);
        View buttomView = LayoutInflater.from(getActivity())
                .inflate(R.layout.find_more, null);
        buttomView.findViewById(R.id.find_more_btn)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), SearchActivity.class);
                        startActivity(intent);
                    }
                });

        String title = null;
        DataBase dataBase = new DataBase(getActivity());
        List<String> list = new ArrayList<>();
        switch (v.getId())
        {
            case R.id.menu_sport:
                List<Sport_Lib> sport_list = dataBase.getAllSportsFromLib();
                for(Sport_Lib i:sport_list)
                {
                    list.add(i.name);
                }
                title = "请选择一种运动：";
                break;
            case R.id.menu_food:
                List<Food_Lib> food_list = dataBase.getAllFoodsFromLib();
                for(Food_Lib i:food_list)
                {
                    list.add(i.name);
                }
                title = "请选择一种食物：";
                break;
            case R.id.menu_weight:

                break;
        }
        if(v.getId() != R.id.menu_weight)
        {
            ((TextView)titleView.findViewById(R.id.title)).setText(title);
            new AlertDialog.Builder(getActivity())
                    .setCustomTitle(titleView)
                    .setView(buttomView)
                    .setAdapter(new AddListAdapter(list), null)
                    .show();
            dataBase.close();
        }

        menu.close(false);
    }

}






