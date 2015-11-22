package cf.imxqd.LetWeightFly.Model.Web;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Henry on 2015/6/26.
 */
public class Article implements Parcelable {
    public String title;
    public String url;
    public String time;
    public Article()
    {

    }
    public Article(Parcel source)
    {
        title = source.readString();
        time = source.readString();
        url = source.readString();
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(time);
        dest.writeString(url);
    }
    //实例化静态内部对象CREATOR实现接口Parcelable.Creator
    public static final Parcelable.Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
        //将Parcel对象反序列化为ParcelableDate
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }
    };
}