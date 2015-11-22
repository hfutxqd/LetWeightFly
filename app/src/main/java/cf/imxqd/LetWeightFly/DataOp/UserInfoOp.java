package cf.imxqd.LetWeightFly.DataOp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Henry on 2015/7/29.
 *
 */
public class UserInfoOp {
    SharedPreferences preferences;
    public UserInfoOp(Context context)
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUser(String name, int age, float weight)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", name);
        editor.putString("age", ""+age);
        editor.putString("weight", ""+weight);
        editor.apply();
    }

    public void setName(String name)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", name);
        editor.apply();
    }

    public void setAge(int age)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("age", ""+age);
        editor.apply();
    }

    public void setWeight(float weight)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("weight", ""+weight);
        editor.apply();
    }

    public void setTargetWeight(float targetWeight)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("targetWeight", ""+targetWeight);
        editor.apply();
    }

    public void setPlanDate(long planDate)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("planDate", ""+planDate);
        editor.apply();
    }

    public void setPlan(float weight, long planDate)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("targetWeight", ""+weight);
        editor.putString("planDate", "" + planDate);
        editor.apply();
    }

    public User getUser()
    {

        User user = new User();
        user.age = Integer.valueOf(preferences.getString("age", "0"));
        user.name = preferences.getString("name", null);
        user.weight = Float.valueOf(preferences.getString("weight", "0"));
        return user;
    }

    public Plan getPlan()
    {
        Plan plan = new Plan();
        plan.planDate = Long.valueOf(preferences.getString("planDate","0"));
        plan.targetWeight = Float.valueOf(preferences.getString("targetWeight", "0"));
        return plan;
    }

    public boolean hasUser()
    {
        User user = getUser();
        return user.weight != 0;
    }

    public void setNull()
    {
        preferences = null;
    }

    public static class User
    {
        public String name;
        public int age;
        public float weight;
    }
    public static class Plan
    {
        public float targetWeight;
        public long planDate;
    }
}
