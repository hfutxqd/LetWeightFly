package cf.imxqd.LetWeightFly.DataOp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Henry on 2015/7/27.
 *
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "data.db"; //数据库名称
    public static final int version = 1; //数据库版本
    public DBHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE [main].[food_lib] (\n" +
                "  [name] TEXT(20) NOT NULL, \n" +
                "  [energy] FLOAT NOT NULL, \n" +
                "  [id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT);");

        db.execSQL("CREATE TABLE [main].[food_rec] (\n" +
                "  [id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
                "  [title] TEXT(30), \n" +
                "  [time] INT64 NOT NULL DEFAULT (strftime('%s','now','localtime')), \n" +
                "  [energy] FLOAT NOT NULL);");

        db.execSQL("CREATE TABLE [main].[sport_lib] (\n" +
                "  [name] TEXT(20) NOT NULL, \n" +
                "  [energy] FLOAT NOT NULL, \n" +
                "  [id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT);");

        db.execSQL("CREATE TABLE [main].[sport_rec] (\n" +
                "  [id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
                "  [title] TEXT(30), \n" +
                "  [time] INT64 NOT NULL DEFAULT (strftime('%s','now','localtime')), \n" +
                "  [energy] FLOAT NOT NULL);");

        db.execSQL("CREATE TABLE [main].[task] (\n" +
                "  [id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
                "  [time] INT64 NOT NULL, \n" +
                "  [sport_id] INTEGER NOT NULL CONSTRAINT [sport_id] REFERENCES [sport_lib]([id]) ON DELETE SET NULL ON UPDATE RESTRICT DEFERRABLE INITIALLY IMMEDIATE, \n" +
                "  [done] BOOLEAN NOT NULL DEFAULT false, \n" +
                "  [alarm] BOOLEAN NOT NULL DEFAULT true);");

        db.execSQL("CREATE TABLE [main].[weight_rec] (\n" +
                "  [id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
                "  [weight] FLOAT NOT NULL, \n" +
                "  [time] INT64 NOT NULL DEFAULT (strftime('%s','now','localtime')), \n" +
                "  [title] TEXT(30));");

        System.out.println("DataBase is created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
