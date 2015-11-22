package cf.imxqd.LetWeightFly.Model.Web;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Henry on 2015/7/10.
 */
public class SearchRes implements Parcelable {
    public String Url;
    public String title;
    public String describe;
    public float calory;

    public SearchRes()
    {
    }
    public SearchRes(Parcel source)
    {
        Url = source.readString();
        title = source.readString();
        describe = source.readString();
        calory = source.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Url);
        dest.writeString(title);
        dest.writeString(describe);
        dest.writeFloat(calory);
    }
    //实例化静态内部对象CREATOR实现接口Parcelable.Creator
    public static final Parcelable.Creator<SearchRes> CREATOR = new Creator<SearchRes>() {
        @Override
        public SearchRes[] newArray(int size) {
            return new SearchRes[size];
        }
        //将Parcel对象反序列化为ParcelableDate
        @Override
        public SearchRes createFromParcel(Parcel source) {
            return new SearchRes(source);
        }
    };
}
