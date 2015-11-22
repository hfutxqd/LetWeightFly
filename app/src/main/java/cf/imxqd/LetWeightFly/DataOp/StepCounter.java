package cf.imxqd.LetWeightFly.DataOp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cf.imxqd.LetWeightFly.Services.StepDetector;

/**
 * Created by Henry on 2015/7/15.
 */
public class StepCounter {
    StepDataBaseHelper helper;
    public StepCounter(Context context)
    {
        helper = new StepDataBaseHelper(context);
    }
    final String TABELE_NAME = "StepCounter";
    public void addStepCount(int count)
    {
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor =
        database.rawQuery("select todayCount from " +
                "StepCounter where Date=date('now');",null);
        if(cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put("todayCount", count);
            database.insert(TABELE_NAME, null, values);
        }else
        {
            cursor.moveToNext();
            int todayCount = cursor.getInt(
                    cursor.getColumnIndex("todayCount"));
            ContentValues values = new ContentValues();
            values.put("todayCount", todayCount+ count);
            database.update(TABELE_NAME,values,"Date=date('now')",null);
        }
        cursor.close();
        database.close();
    }

    public int getCurrTodayCount()
    {
        return StepDetector.CURRENT_SETP + getTodayStepCount();
    }

    public int getTodayStepCount()
    {
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor =
                database.rawQuery("select todayCount from " +
                        "StepCounter where Date=date('now');",null);
        if(cursor.getCount() == 0) {
            cursor.close();
            database.close();
            return 0;
        }else
        {
            cursor.moveToNext();
            int todayCount = cursor.getInt(
                    cursor.getColumnIndex("todayCount"));
            cursor.close();
            database.close();
            return todayCount;
        }
    }

    public int getTotalCount()
    {
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor =
                database.rawQuery("select sum(todayCount) from StepCounter;",null);
        cursor.moveToNext();
        int cout = cursor.getInt(0);
        cursor.close();
        database.close();
        return cout;
    }

    public void recycle()
    {
        helper.close();
    }
}
