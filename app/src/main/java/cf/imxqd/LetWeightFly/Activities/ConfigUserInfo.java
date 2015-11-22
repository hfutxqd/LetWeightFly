package cf.imxqd.LetWeightFly.Activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.Button;

import cf.imxqd.LetWeightFly.DataOp.UserInfoOp;
import cf.imxqd.LetWeightFly.Fragments.ConfigUserInfoFragment;
import cf.imxqd.LetWeightFly.Fragments.config_page_one;
import cf.imxqd.LetWeightFly.Fragments.config_page_three;
import cf.imxqd.LetWeightFly.Fragments.config_page_two;
import cf.imxqd.LetWeightFly.R;
import cf.imxqd.LetWeightFly.Utils.Tool;


public class ConfigUserInfo extends AppCompatActivity implements View.OnClickListener{

    public static Button nextBtn;
    public static Button lastBtn;
    public static int crPage = 0;
    public SharedPreferences preferences;
    static Fragment page1 = new config_page_one();
    static Fragment page2 = new config_page_two();
    static Fragment page3 = new config_page_three();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_config_user_info);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment, new ConfigUserInfoFragment())
                .commit();
        preferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        nextBtn = (Button) findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(this);
        lastBtn = (Button) findViewById(R.id.last_btn);
        lastBtn.setOnClickListener(this);
        Tool.initButton(lastBtn, nextBtn);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public int getNavBarHeight() {
        Context c = getBaseContext();
        int result = 0;
        boolean hasMenuKey = ViewConfiguration.get(c).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if(!hasMenuKey && !hasBackKey) {
            //The device has a navigation bar
            Resources resources = getBaseContext().getResources();

            int orientation = getResources().getConfiguration().orientation;
            int resourceId;
            if (isTablet(c)){
                resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", "android");
            }  else {
                resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_width", "dimen", "android");
            }

            if (resourceId > 0) {
                return getResources().getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }


    private boolean isTablet(Context c) {
        return (c.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    enum ErrorType{
        name,age,weight,target,none
    }

    public ErrorType checkInfo()
    {
        String name = config_page_one.name.getText().toString();
        String age = config_page_one.age.getText().toString();
        String weight = config_page_one.weight.getText().toString();
        String target = config_page_one.target.getText().toString();
        if (name.length() >=2)
        {
            if(age.equals(""))
                return ErrorType.age;
            if (Integer.valueOf(age) > 8 && Integer.valueOf(age) < 90)
            {
                if(weight.equals(""))
                    return ErrorType.weight;
                if(Float.valueOf(weight) > 20)
                {
                    if(target.equals(""))
                        return ErrorType.target;
                    if(Float.valueOf(target) > 20
                            && Float.valueOf(target) < Float.valueOf(weight))
                    {
                        return ErrorType.none;
                    }
                    return ErrorType.target;
                }
                return ErrorType.weight;
            }
            return ErrorType.age;
        }
        return ErrorType.name;
    }

    public void saveInfo()
    {
        UserInfoOp.User user = new UserInfoOp.User();
        UserInfoOp.Plan plan = new UserInfoOp.Plan();
        user.name = config_page_one.name.getText().toString();
        user.age = Integer.valueOf(config_page_one.age.getText().toString());
        user.weight = Float.valueOf(config_page_one.weight.getText().toString());
        plan.targetWeight =Float.valueOf(config_page_one.weight.getText().toString());
        plan.planDate = config_page_two.datePicker.getDrawingTime();
        UserInfoOp userop = new UserInfoOp(getApplicationContext());
        userop.setUser(user.name,user.age,user.weight);
        userop.setPlan(plan.targetWeight,plan.planDate);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (v.getId() == R.id.next_btn) {
            Log.i("ConfigUserInfo","Next Button pressed");
            switch (crPage) {
                case 0:
                    ft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left)
                            .replace(R.id.fragment, page1)
                            .commit();
                    break;
                case 1:
                    ErrorType type = checkInfo();
                    if (type == ErrorType.age.none) {
                        ft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left)
                                .replace(R.id.fragment, page2)
                                .commit();
                    }else{
                        switch (type)
                        {
                            case name:
                                config_page_one.name.setError(
                                        getString(R.string.name_error));
                                break;
                            case age:
                                config_page_one.age.setError(
                                        getString(R.string.age_error)
                                );
                                break;
                            case weight:
                                config_page_one.weight.setError(
                                        getString(R.string.weight_error)
                                );
                                break;
                            case target:
                                config_page_one.target.setError(
                                        getString(R.string.target_error)
                                );
                                break;
                        }
                    }
                    break;
                case 2:
                    ft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left)
                            .replace(R.id.fragment, page3)
                            .commit();
                    break;
                case 3:
                    saveInfo();
                    Intent intent = new Intent();
                    intent.setClass(this,MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.translate_in,R.anim.translate_out);
                    finish();
                    break;
            }
        }else if(v.getId() == R.id.last_btn)
        {
            Log.i("ConfigUserInfo","Last Button pressed");
            switch (crPage) {
                case 0:
                    finish();
                    break;
                case 1:
                    ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                            .replace(R.id.fragment, new ConfigUserInfoFragment())
                            .commit();
                    break;
                case 2:
                    ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                            .replace(R.id.fragment, page1)
                            .commit();
                    break;
                case 3:
                    ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                            .replace(R.id.fragment, page2)
                            .commit();
                    break;
            }
        }
    }

}
