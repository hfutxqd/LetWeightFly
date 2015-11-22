package cf.imxqd.LetWeightFly.Fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

import cf.imxqd.LetWeightFly.Activities.ConfigUserInfo;
import cf.imxqd.LetWeightFly.R;
import cf.imxqd.LetWeightFly.Utils.Tool;


public class config_page_one extends Fragment{

    public static EditText name;
    public static EditText age;
    public static EditText weight;
    public static EditText target;
    View view;

    public config_page_one() {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_config_page_one, container, false);
        Tool.initStatusBar(view, container.getContext());
        ConfigUserInfo.lastBtn.setText(R.string.last);
        ConfigUserInfo.nextBtn.setText(R.string.next);
        name = (EditText) view.findViewById(R.id.name);
        age = (EditText) view.findViewById(R.id.age);
        weight = (EditText) view.findViewById(R.id.weight);
        target = (EditText) view.findViewById(R.id.target);

        target.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    ConfigUserInfo.nextBtn.callOnClick();
                    ((InputMethodManager)getActivity().
                            getSystemService(getActivity().INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken()
                                    , InputMethodManager.HIDE_NOT_ALWAYS);

                }
                return true;
            }
        });
        Rect r = new Rect();
        View root = getActivity().getWindow().getDecorView();
        root.getWindowVisibleDisplayFrame(r);
        Log.i("config_page_one", "onCreateView");
        System.out.print("height---------------->"+r.height());
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ConfigUserInfo.crPage = 1;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager =
                    Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);


        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}

