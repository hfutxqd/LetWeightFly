package cf.imxqd.LetWeightFly.Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cf.imxqd.LetWeightFly.Fragments.TipsListFragment;
import cf.imxqd.LetWeightFly.R;

/**
 * Created by Henry on 2015/6/25.
 */
public class WebTabsAdapter extends FragmentPagerAdapter
{
    FragmentManager fm;
    Fragment curr;
    List<Fragment> fragList = new ArrayList<>();

    public WebTabsAdapter(Fragment fragment)
    {
        super(fragment.getChildFragmentManager());
        fm = fragment.getChildFragmentManager();
        curr = fragment;
        fragList.add(TipsListFragment.newInstance(getMODE(0)));
        fragList.add(TipsListFragment.newInstance(getMODE(1)));
        fragList.add(TipsListFragment.newInstance(getMODE(2)));
        fragList.add(TipsListFragment.newInstance(getMODE(3)));
        fragList.add(TipsListFragment.newInstance(getMODE(4)));
        fragList.add(TipsListFragment.newInstance(getMODE(5)));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }


    @Override
    public int getCount() {
        return fragList.size();
    }

    public void recycle()
    {
        fragList.clear();
    }





    private static final String MODE_39JianFei_YinShiJianFei
            = "http://fitness.39.net/jfff/ysjf/index_";

    private static final String MODE_39JianFei_YingYangChangShi
            = "http://fitness.39.net/jfsp/yacs/zf/index_";

    private static final String MODE_39JianFei_YunDongJianFei
            = "http://fitness.39.net/jfff/ydjf/index_";

    private static final String MODE_39JianFei_JianFeiQiaoMen
            = "http://fitness.39.net/jfff/shqm/index_";

    private static final String MODE_39JianFei_AnMoDianXue
            = "http://fitness.39.net/jfff/zyjf/am/index_";

    private static final String MODE_39JianFei_ZhuanJiaJianFei
            = "http://fitness.39.net/jfzj/zjtjf/index_";

    public static String getMODE(int position)
    {
        String tmp = null;
        switch (position) {
            case 0:
                tmp = MODE_39JianFei_YinShiJianFei;
                break;
            case 1:
                tmp = MODE_39JianFei_YingYangChangShi;
                break;
            case 2:
                tmp = MODE_39JianFei_YunDongJianFei;
                break;
            case 3:
                tmp = MODE_39JianFei_JianFeiQiaoMen;
                break;
            case 4:
                tmp = MODE_39JianFei_AnMoDianXue;
                break;
            case 5:
                tmp = MODE_39JianFei_ZhuanJiaJianFei;
                break;
        }
        return tmp;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String tmp = null;
        switch (position) {
            case 0:
                tmp = curr.getString(R.string.ysjf);
                break;
            case 1:
                tmp = curr.getString(R.string.yycs);
                break;
            case 2:
                tmp = curr.getString(R.string.ydjf);
                break;
            case 3:
                tmp = curr.getString(R.string.jfqm);
                break;
            case 4:
                tmp = curr.getString(R.string.amdx);
                break;
            case 5:
                tmp = curr.getString(R.string.zjjf);
                break;
        }
        return tmp;
    }

    @Override
    protected void finalize() throws Throwable {
        recycle();
        fm = null;
        super.finalize();
    }
}