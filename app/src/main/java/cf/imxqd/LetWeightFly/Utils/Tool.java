package cf.imxqd.LetWeightFly.Utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

/**
 * Created by Henry on 2015/6/19.
 */
public class Tool {


    public static int dp2px(Context context,float dp)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    public static int getNavBarHeight(Context c) {
        int result = 0;
        boolean hasMenuKey = ViewConfiguration.get(c).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if(!hasMenuKey && !hasBackKey) {
            //The device has a navigation bar
            Resources resources = c.getResources();

            int orientation = c.getResources().getConfiguration().orientation;
            int resourceId;
            if (isTablet(c)){
                resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", "android");
            }  else {
                resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_width", "dimen", "android");
            }

            if (resourceId > 0) {
                return c.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    private static boolean isTablet(Context c) {
        return (c.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            Log.e("Tool", "get status bar height failed!");
            e1.printStackTrace();
        }
        return sbar;
    }

    public static void setNavBarColor(Activity activity,int color)
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(color);
        }
    }

    public static void initButton(Button last, Button next)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            last.setPadding(
                    last.getPaddingLeft(),
                    last.getPaddingTop(),
                    last.getPaddingRight(),
                    last.getPaddingBottom() + Tool.getNavBarHeight(last.getContext())
            );

            next.setPadding(
                    next.getPaddingLeft(),
                    next.getPaddingTop(),
                    next.getPaddingRight(),
                    next.getPaddingBottom() + Tool.getNavBarHeight(next.getContext())
            );
        }
    }

    /**
     *
     * @param root  must be the root view of activity.
     * @param context   application context or activity context
     */
    public static void initStatusBar(View root, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int sh = Tool.getStatusBarHeight(context);
            int nh = Tool.getNavBarHeight(context);
            System.out.println("StatusBarHeight = "+sh);
            System.out.println("NavigationBarHeight = " + nh);
            root.setPadding(root.getPaddingLeft()
                    , root.getPaddingTop() + sh
                    , root.getPaddingRight()
                    , root.getPaddingBottom() + nh
            );
        }
    }

    /**
     *
     * @param bitmap    The bitmap you want to save.
     * @param filename  The filename you want to save to.
     * @throws IOException  Maybe throws IOException.
     */
    public static void save2jpg(Bitmap bitmap, File filename) throws IOException {
        if(!filename.exists())
            filename.createNewFile();
        OutputStream out = new FileOutputStream(filename);
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,out);
        out.close();
    }

    public static Bitmap getResizedBMP(Uri uri,Context context) throws FileNotFoundException {
        ContentResolver cr = context.getContentResolver();
        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        bitmap.recycle();
        bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri),null,options);
        if(options.outWidth >= 500 && options.outWidth <= 1000)
        {
            options.inSampleSize = 2;
        }else if(options.outWidth >= 1000 && options.outWidth <= 2000){
            options.inSampleSize = 4;
        }else if(options.outWidth > 2000 && options.outWidth <= 4000){
            options.inSampleSize = 8;
        }
        else if(options.outWidth > 4000){
            options.inSampleSize = 16;
        }
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri),null,options);
        return  bitmap;
    }



}

