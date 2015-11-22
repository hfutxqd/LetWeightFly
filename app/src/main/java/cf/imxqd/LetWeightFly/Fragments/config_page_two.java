package cf.imxqd.LetWeightFly.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.lang.reflect.Field;

import cf.imxqd.LetWeightFly.Activities.ConfigUserInfo;
import cf.imxqd.LetWeightFly.R;
import cf.imxqd.LetWeightFly.Utils.Tool;


public class config_page_two extends Fragment {

    public static DatePicker datePicker;
    public static long date;
    public config_page_two() {
        // Required empty public constructor
    }

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config_page_two, container, false);
        Tool.initStatusBar(view, container.getContext());
        ConfigUserInfo.lastBtn.setText(R.string.last);
        ConfigUserInfo.nextBtn.setText(R.string.next);
        final Time time = new Time("local");
        time.setToNow();
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        datePicker.setMinDate(time.toMillis(false));
        datePicker.init(time.year, time.month, time.monthDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.i("Year", String.valueOf(year));
                Log.i("monthOfYear", String.valueOf(monthOfYear));
                Log.i("dayOfMonth", String.valueOf(dayOfMonth));
                Time time = new Time("local");
                time.set(dayOfMonth, monthOfYear, year);
                date = time.toMillis(false)/1000;
            }
        });
        Log.i("config_page_two", "onCreateView");
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ConfigUserInfo.crPage = 2;
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
            throw new RuntimeException(e);
        }

    }


}
