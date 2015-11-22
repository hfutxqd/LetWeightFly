package cf.imxqd.LetWeightFly.Model.Web;

import android.annotation.SuppressLint;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Henry on 2015/7/13.
 */
public class Task {
    public enum Type
    {
        TYPE_SPORT,TYPE_FOOD,TYPE_WEIGHT
    }
    String content;
    Type type;
    long timestamp;
    float energy;
    String time;
    boolean completed;

    public Task(String title,Type type, float energy, long timestamp)
    {
        this.content = title;
        this.type = type;
        this.timestamp = timestamp;
        this.energy = energy;
        Timestamp ts = new Timestamp(timestamp*1000);
        @SuppressLint("SimpleDateFormat")
        DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        time = sdf.format(ts);
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getContent() {
        return content;
    }

    public Type getType() {
        return type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public float getEnergy() {
        return energy;
    }

    public String getTime() {
        return time;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
