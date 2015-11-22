package cf.imxqd.LetWeightFly.Receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import cf.imxqd.LetWeightFly.R;

/**
 * Created by Henry on 2015/7/21.
 * AlarmBroadcastReceiver
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("msg");
        System.out.println(msg);
    }
}
