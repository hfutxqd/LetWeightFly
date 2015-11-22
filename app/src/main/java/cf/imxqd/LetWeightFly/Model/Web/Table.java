package cf.imxqd.LetWeightFly.Model.Web;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Henry on 2015/7/10.
 */
public class Table  implements Parcelable {
    public ArrayList<String> titles = new ArrayList<String>();
    public ArrayList<String> values = new ArrayList<String>();
    public String[] getRow(int index)
    {
        if(index < titles.size())
            return new String[]{titles.get(index),values.get(index)};
        return null;
    }
    public Table()
    {

    }
    public Table(Parcel source)
    {
        source.readList(titles, null);
        source.readList(values, null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(titles);
        dest.writeList(values);
    }

    //实例化静态内部对象CREATOR实现接口Parcelable.Creator
    public static final Parcelable.Creator<Table> CREATOR = new Creator<Table>() {
        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
        //将Parcel对象反序列化为ParcelableDate
        @Override
        public Table createFromParcel(Parcel source) {
            return new Table(source);
        }
    };
}
