package cf.imxqd.LetWeightFly.Services;

/**
 * Created by Henry on 2015/7/14.
 */
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class StepService extends Service {
    public static Boolean flag = false;
    private SensorManager sensorManager;
    private StepDetector stepDetector;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startStepDetector();
        System.out.println("Service created.");
    }

    private void startStepDetector() {
        flag = true;
        stepDetector = new StepDetector(this);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);//获取传感器管理器的实例
        Sensor sensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//获得传感器的类型，这里获得的类型是加速度传感器
        //此方法用来注册，只有注册过才会生效，参数：SensorEventListener的实例，Sensor的实例，更新速率
        sensorManager.registerListener(stepDetector, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Service onStartCommand.");
        stepDetector.addCurrStep();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service onDestroy.");
        flag = false;
        if (stepDetector != null) {
            sensorManager.unregisterListener(stepDetector);
            stepDetector.recycle();
        }
    }
}
