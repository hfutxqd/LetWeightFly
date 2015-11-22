package cf.imxqd.LetWeightFly.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cf.imxqd.LetWeightFly.Activities.MainActivity;
import cf.imxqd.LetWeightFly.Services.StepService;

/**
 * Created by Henry on 2015/7/15.
 * BootBroadcastReceiver
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent service = new Intent(context, StepService.class);
            context.startService(service);
        }
    }
}
