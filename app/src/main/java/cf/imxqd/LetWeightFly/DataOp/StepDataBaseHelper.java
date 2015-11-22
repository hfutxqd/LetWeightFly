package cf.imxqd.LetWeightFly.DataOp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Henry on 2015/7/15.
 */
public class StepDataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "StepCounter.db"; //数据库名称
    public static final int version = 1; //数据库版本

    public StepDataBaseHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL = "create table StepCounter (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "todayCount INTEGER, Date date default (date('now','localtime')));";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
