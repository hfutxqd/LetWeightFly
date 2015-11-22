package cf.imxqd.LetWeightFly.Utils;

import android.app.Activity;
import android.os.Build;

import java.lang.reflect.Method;

/**
 * Created by Henry on 2015/6/23.
 */
public class Utils
{

    public static boolean isGingerbreadOrHigher()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean isHoneycombOrHigher()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean isHoneycombMR1OrHigher()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean isKitkatOrHigher()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isLollipopOrHigher()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

}
