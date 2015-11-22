package cf.imxqd.LetWeightFly.Fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import cf.imxqd.LetWeightFly.R;
import cf.imxqd.LetWeightFly.Utils.Tool;

/**
 * Created by Henry on 2015/7/5.
 */
public class FoodFragment extends Fragment {
    View rootView;
    TextView advantages;
    TextView disadvantages;
    TextView food_to_sport_title;
    TextView walk;
    TextView running;
    TextView skipping;
    TextView aerobics;
    TableLayout composition;
    public FoodFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.res_food, container, false);
        advantages = (TextView) rootView.findViewById(R.id.advantages);
        disadvantages = (TextView) rootView.findViewById(R.id.disadvantages);
        food_to_sport_title = (TextView) rootView.findViewById(R.id.food_to_sport_title);
        walk = (TextView) rootView.findViewById(R.id.walk);
        running = (TextView) rootView.findViewById(R.id.running);
        skipping = (TextView) rootView.findViewById(R.id.skipping);
        aerobics = (TextView) rootView.findViewById(R.id.aerobics);
        composition = (TableLayout) rootView.findViewById(R.id.composition);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addRow("13","3123");
        addRow("13","3123");
        addRow("13","3123");
        addRow("13","3123");
        addRow("13","3123");
    }

    int count = 0;
    private void addRow(String key, String value)
    {
        count = ++count % 2;
        int color = getResources().getColor(R.color.teal);
        if(count == 0)
        {
            color = getResources().getColor(R.color.bluegrey);
        }
        TableRow tmp = new TableRow(getActivity().getBaseContext());
        TextView txt1 = new TextView(getActivity().getBaseContext());
        TextView txt2 = new TextView(getActivity().getBaseContext());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                Tool.dp2px(getActivity().getBaseContext(),
                        45f),1.0f);
        txt1.setText(key);
        txt1.setGravity(Gravity.CENTER);
        txt1.setTextColor(getResources().getColor(R.color.white));
        txt2.setText(value);
        txt2.setGravity(Gravity.CENTER);
        txt2.setTextColor(getResources().getColor(R.color.white));
        tmp.addView(txt1, 0, lp);
        tmp.addView(txt2, 1, lp);
        tmp.setBackgroundColor(color);

        composition.addView(tmp, lp);
    }
}
