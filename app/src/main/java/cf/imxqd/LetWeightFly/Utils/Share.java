package cf.imxqd.LetWeightFly.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Henry on 2015/6/13.
 */
public class Share {
    Context context;
    public Share(Context context)
    {
        this.context = context;
    }

    public  void shareUrl(String title,String URL)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TITLE,title);
        intent.putExtra(Intent.EXTRA_TEXT, title+"\n"+URL);
        intent.setType("text/plain");
        context.startActivity(Intent.createChooser(intent, title));
    }

    public static void shareUrl(Context context,String title,String URL)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TITLE,title);
        intent.putExtra(Intent.EXTRA_TEXT, title+"\n"+URL);
        intent.setType("text/plain");
        context.startActivity(Intent.createChooser(intent, title));
    }

}
