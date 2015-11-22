package cf.imxqd.LetWeightFly.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import cf.imxqd.LetWeightFly.DataOp.UserInfoOp;
import cf.imxqd.LetWeightFly.R;

/**
 * Created by Henry on 2015/6/22.
 *
 */
public class SettingsFragment extends PreferenceFragment
implements SharedPreferences.OnSharedPreferenceChangeListener{
    UserInfoOp userInfoOp;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        userInfoOp = new UserInfoOp(getActivity());
        addPreferencesFromResource(R.xml.preferences);
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        UserInfoOp.User user = userInfoOp.getUser();
        findPreference("name").setSummary(user.name);
        findPreference("weight").setSummary(""+user.weight+" kg");
        findPreference("age").setSummary(""+user.age);
        findPreference("targetWeight").setSummary(""+userInfoOp.getPlan().targetWeight+" kg");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userInfoOp.setNull();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        UserInfoOp.User user = userInfoOp.getUser();
        findPreference("name").setSummary(user.name);
        findPreference("weight").setSummary(""+user.weight+" kg");
        findPreference("age").setSummary(""+user.age);
        findPreference("targetWeight").setSummary(""+userInfoOp.getPlan().targetWeight+" kg");
    }
}
