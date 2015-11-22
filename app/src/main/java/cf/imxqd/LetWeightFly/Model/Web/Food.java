package cf.imxqd.LetWeightFly.Model.Web;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Henry on 2015/7/10.
 */
public class Food  implements Parcelable {

    public Table chengfenTab;

    public String advantages;
    public String disadvantages;
    public String walk;
    public String running;
    public String skipping;
    public String aerobics;

    public Food()
    {
        chengfenTab = new Table();
    }
    public Food(Parcel source)
    {
        chengfenTab = source.readParcelable(null);
        advantages = source.readString();
        disadvantages = source.readString();
        walk = source.readString();
        running = source.readString();
        skipping = source.readString();
        aerobics = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(chengfenTab, 0);
        dest.writeString(advantages);
        dest.writeString(disadvantages);
        dest.writeString(walk);
        dest.writeString(running);
        dest.writeString(skipping);
        dest.writeString(aerobics);
    }

    //实例化静态内部对象CREATOR实现接口Parcelable.Creator
    public static final Parcelable.Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
        //将Parcel对象反序列化为ParcelableDate
        @Override
        public Food createFromParcel(Parcel source) {
            return new Food(source);
        }
    };
}
