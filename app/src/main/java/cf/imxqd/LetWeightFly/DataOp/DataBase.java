package cf.imxqd.LetWeightFly.DataOp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cf.imxqd.LetWeightFly.Model.DataBase.Food_Lib;
import cf.imxqd.LetWeightFly.Model.DataBase.Food_Rec;
import cf.imxqd.LetWeightFly.Model.DataBase.Sport_Lib;
import cf.imxqd.LetWeightFly.Model.DataBase.Sport_Rec;
import cf.imxqd.LetWeightFly.Model.DataBase.Task;
import cf.imxqd.LetWeightFly.Model.DataBase.Weight_Rec;

/**
 * Created by Henry on 2015/7/27.
 *
 */
public class DataBase {
    private DBHelper dbHelper;
    public DataBase(Context context)
    {
        dbHelper = new DBHelper(context);
        Log.i("Database Version", String.valueOf(dbHelper.getWritableDatabase().getVersion()));
    }
    public void close()
    {
        dbHelper.close();
        dbHelper = null;
    }

    public boolean addWeight(@NonNull String title, float weight,@Nullable Timestamp timestamp)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("weight",weight);
        if(timestamp != null)
        {
            values.put("timestamp",timestamp.getTime());
        }
        db.insert("weight_rec",null,values);
        db.close();
        return true;
    }

    public boolean addSport(@NonNull String title, float energy,@Nullable Timestamp timestamp)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("energy",energy);
        if(timestamp != null)
        {
            values.put("timestamp",timestamp.getTime());
        }
        db.insert("sport_rec",null,values);
        db.close();
        return true;
    }

    public boolean addFood(@NonNull String title, float energy,@Nullable Timestamp timestamp)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("energy",energy);
        if(timestamp != null)
        {
            values.put("timestamp",timestamp.getTime());
        }
        db.insert("food_rec",null,values);
        db.close();
        return true;
    }

    public boolean addToFoodLib(@NonNull String name, float energy)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("energy",energy);
        db.insert("food_lib",null,values);
        db.close();
        return true;
    }

    public boolean addToSportLib(@NonNull String name, float energy)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("energy",energy);
        db.insert("sport_lib",null,values);
        db.close();
        return true;
    }

    public int deleteFromFoodLib(@NonNull String name)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int res = db.delete("food_lib","name = ?",new String[]{name});
        db.close();
        return res;
    }

    public int deleteFromSportLib(@NonNull String name)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int res = db.delete("sport_lib","name = ?",new String[]{name});
        db.close();
        return res;
    }

    public boolean canAddToLib(@NonNull String name)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        Cursor cursor = db.rawQuery("select * from food_lib where name = ?", new String[]{name});
        if(cursor.getCount() > 0)
        {
            cursor.close();
            db.close();
            return false;
        }
        cursor = db.rawQuery("select * from sport_lib where name = ?", new String[]{name});
        if(cursor.getCount() > 0)
        {
            cursor.close();
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public boolean addTask(int soprt_id,@NonNull Timestamp timestamp, boolean alarm)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soprt_id",soprt_id);
        values.put("timestamp",timestamp.getTime());
        values.put("alarm",alarm);
        db.insert("sport_lib",null,values);
        db.close();
        return true;
    }

    public int deleteTask(int task_id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int res = db.delete("task","id = ?",new String[]{""+task_id});
        db.close();
        return res;
    }

    public float getCurrentWeight()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cr = db.rawQuery("select weight from weight_rec order by time desc;", null);
        float weight = 0;
        while (cr.moveToNext())
        {
            weight = cr.getFloat(cr.getColumnIndex("weight"));
        }
        cr.close();
        db.close();
        return weight;
    }

    public List<Task> getAllTask()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from task order by time desc;", null);
        List<Task> list = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Task task = new Task();
            task.sport_id = cursor.getInt(cursor.getColumnIndex("sport_id"));
            task.alarm = (cursor.getInt(cursor.getColumnIndex("alarm")) != 0);
            task.done = (cursor.getInt(cursor.getColumnIndex("done")) != 0);
            task.id = cursor.getInt(cursor.getColumnIndex("id"));
            task.time = new Time(cursor.getLong(cursor.getColumnIndex("time")));
            list.add(task);
        }
        cursor.close();
        return list;
    }

    public List<Task> getTodayTask()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from task time >= strftime('%s','now'" +
                ",'localtime','start of day')" +
                " and time < strftime('%s','now','localtime','+1 day','start of day');", null);
        List<Task> list = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Task task = new Task();
            task.sport_id = cursor.getInt(cursor.getColumnIndex("sport_id"));
            task.alarm = (cursor.getInt(cursor.getColumnIndex("alarm")) != 0);
            task.done = (cursor.getInt(cursor.getColumnIndex("done")) != 0);
            task.id = cursor.getInt(cursor.getColumnIndex("id"));
            task.time = new Time(cursor.getLong(cursor.getColumnIndex("time")));
            list.add(task);
        }
        cursor.close();
        return list;
    }

    public List<Task> getUndoTask()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from task done = false;", null);
        List<Task> list = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Task task = new Task();
            task.sport_id = cursor.getInt(cursor.getColumnIndex("sport_id"));
            task.alarm = (cursor.getInt(cursor.getColumnIndex("alarm")) != 0);
            task.done = (cursor.getInt(cursor.getColumnIndex("done")) != 0);
            task.id = cursor.getInt(cursor.getColumnIndex("id"));
            task.time = new Time(cursor.getLong(cursor.getColumnIndex("time")));
            list.add(task);
        }
        cursor.close();
        return list;
    }

    public List<Sport_Lib> getAllSportsFromLib()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from sport_lib;", null);
        List<Sport_Lib> list = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Sport_Lib sport_lib = new Sport_Lib();
            sport_lib.name = cursor.getString(cursor.getColumnIndex("name"));
            sport_lib.energy = cursor.getFloat(cursor.getColumnIndex("energy"));
            sport_lib.id = cursor.getInt(cursor.getColumnIndex("id"));
            list.add(sport_lib);
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Food_Lib> getAllFoodsFromLib()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from food_lib;", null);
        List<Food_Lib> list = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Food_Lib food_lib = new Food_Lib();
            food_lib.name = cursor.getString(cursor.getColumnIndex("name"));
            food_lib.energy = cursor.getFloat(cursor.getColumnIndex("energy"));
            food_lib.id = cursor.getInt(cursor.getColumnIndex("id"));
            list.add(food_lib);
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Food_Rec> getFoodRecToday()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Food_Rec> list = new ArrayList<>();
        Cursor cr = db.rawQuery("select * from food_rec where time between strftime('%s','now'" +
                ",'localtime','start of day') " +
                "and strftime('%s','now','localtime','+1 day','start of day');",null);
        while (cr.moveToNext())
        {
            Food_Rec food_rec = new Food_Rec();
            food_rec.id = cr.getInt(cr.getColumnIndex("id"));
            food_rec.energy = cr.getFloat(cr.getColumnIndex("energy"));
            food_rec.time = new Time(cr.getLong(cr.getColumnIndex("time")));
            food_rec.title = cr.getString(cr.getColumnIndex("title"));
            list.add(food_rec);
        }
        cr.close();
        db.close();
        return list;
    }

    public List<Food_Rec> getFoodRecWeekly()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Food_Rec> list = new ArrayList<>();
        Cursor cr = db.rawQuery("select * from food_rec where time >= strftime('%s','now','localtime'," +
                "'-7 day','weekday 1') and time < strftime('%s','now','localtime','weekday 1');",null);
        while (cr.moveToNext())
        {
            Food_Rec food_rec = new Food_Rec();
            food_rec.id = cr.getInt(cr.getColumnIndex("id"));
            food_rec.energy = cr.getFloat(cr.getColumnIndex("energy"));
            food_rec.time = new Time(cr.getLong(cr.getColumnIndex("time")));
            food_rec.title = cr.getString(cr.getColumnIndex("title"));
            list.add(food_rec);
        }
        cr.close();
        db.close();
        return list;
    }

    public List<Food_Rec> getFoodRecMonthy()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Food_Rec> list = new ArrayList<>();
        Cursor cr = db.rawQuery("select * from food_rec where time >= " +
                "strftime('%s','now','localtime','start of month') and time" +
                " < strftime('%s','now','localtime','+1 month'" +
                ",'start of month');",null);
        while (cr.moveToNext())
        {
            Food_Rec food_rec = new Food_Rec();
            food_rec.id = cr.getInt(cr.getColumnIndex("id"));
            food_rec.energy = cr.getFloat(cr.getColumnIndex("energy"));
            food_rec.time = new Time(cr.getLong(cr.getColumnIndex("time")));
            food_rec.title = cr.getString(cr.getColumnIndex("title"));
            list.add(food_rec);
        }
        cr.close();
        db.close();
        return list;
    }

    public List<Food_Rec> getAllFoodRec()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Food_Rec> list = new ArrayList<>();
        Cursor cr = db.rawQuery("select * from food_rec order by time desc;",null);
        while (cr.moveToNext())
        {
            Food_Rec food_rec = new Food_Rec();
            food_rec.id = cr.getInt(cr.getColumnIndex("id"));
            food_rec.energy = cr.getFloat(cr.getColumnIndex("energy"));
            food_rec.time = new Time(cr.getLong(cr.getColumnIndex("time")));
            food_rec.title = cr.getString(cr.getColumnIndex("title"));
            list.add(food_rec);
        }
        cr.close();
        db.close();
        return list;
    }

    public List<Sport_Rec> getSportRecToday()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Sport_Rec> list = new ArrayList<>();
        Cursor cr = db.rawQuery("select * from sport_rec where time >= " +
                "strftime('%s','now','localtime','start of day') and time < " +
                "strftime('%s','now','localtime','+1 day','start of day');",null);
        while (cr.moveToNext())
        {
            Sport_Rec sport_rec = new Sport_Rec();
            sport_rec.id = cr.getInt(cr.getColumnIndex("id"));
            sport_rec.energy = cr.getFloat(cr.getColumnIndex("energy"));
            sport_rec.time = new Time(cr.getLong(cr.getColumnIndex("time")));
            sport_rec.title = cr.getString(cr.getColumnIndex("title"));
            list.add(sport_rec);
        }
        cr.close();
        db.close();
        return list;
    }

    public List<Sport_Rec> getSportRecWeekly()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Sport_Rec> list = new ArrayList<>();
        Cursor cr = db.rawQuery("select * from sport_rec where time >= " +
                "strftime('%s','now','localtime','-7 day','weekday 1') and time < " +
                "strftime('%s','now','localtime','weekday 1');",null);
        while (cr.moveToNext())
        {
            Sport_Rec sport_rec = new Sport_Rec();
            sport_rec.id = cr.getInt(cr.getColumnIndex("id"));
            sport_rec.energy = cr.getFloat(cr.getColumnIndex("energy"));
            sport_rec.time = new Time(cr.getLong(cr.getColumnIndex("time")));
            sport_rec.title = cr.getString(cr.getColumnIndex("title"));
            list.add(sport_rec);
        }
        cr.close();
        db.close();
        return list;
    }

    public List<Sport_Rec> getSportRecMonthy()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Sport_Rec> list = new ArrayList<>();
        Cursor cr = db.rawQuery("select * from sport_rec where time >= " +
                "strftime('%s','now','localtime','start of month') and time < " +
                "strftime('%s','now', 'localtime','+1 month','start of month');",null);
        while (cr.moveToNext())
        {
            Sport_Rec sport_rec = new Sport_Rec();
            sport_rec.id = cr.getInt(cr.getColumnIndex("id"));
            sport_rec.energy = cr.getFloat(cr.getColumnIndex("energy"));
            sport_rec.time = new Time(cr.getLong(cr.getColumnIndex("time")));
            sport_rec.title = cr.getString(cr.getColumnIndex("title"));
            list.add(sport_rec);
        }
        cr.close();
        db.close();
        return list;
    }

    public List<Sport_Rec> getAllSportRec()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Sport_Rec> list = new ArrayList<>();
        Cursor cr = db.rawQuery("select * from sport_rec order by time desc;",null);
        while (cr.moveToNext())
        {
            Sport_Rec sport_rec = new Sport_Rec();
            sport_rec.id = cr.getInt(cr.getColumnIndex("id"));
            sport_rec.energy = cr.getFloat(cr.getColumnIndex("energy"));
            sport_rec.time = new Time(cr.getLong(cr.getColumnIndex("time")));
            sport_rec.title = cr.getString(cr.getColumnIndex("title"));
            list.add(sport_rec);
        }
        cr.close();
        db.close();
        return list;
    }

    public List<Weight_Rec> getWeightRecToday()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Weight_Rec> list = new ArrayList<>();
        Cursor cr = db.rawQuery("select * from weight_rec where time >= " +
                "strftime('%s','now','localtime','start of day') and time " +
                "< strftime('%s','now','localtime','+1 day','start of day') " +
                "order by time desc;",null);
        while (cr.moveToNext())
        {
            Weight_Rec weight_rec = new Weight_Rec();
            weight_rec.id = cr.getInt(cr.getColumnIndex("id"));
            weight_rec.weight = cr.getFloat(cr.getColumnIndex("weight"));
            weight_rec.time = new Time(cr.getLong(cr.getColumnIndex("time")));
            weight_rec.title = cr.getString(cr.getColumnIndex("title"));
            list.add(weight_rec);
        }
        cr.close();
        db.close();
        return list;
    }

    public List<Weight_Rec> getWeightRecWeekly()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Weight_Rec> list = new ArrayList<>();
        Cursor cr = db.rawQuery("select * from weight_rec where time >= " +
                "strftime('%s','now','localtime','-7 day','weekday 1') and time " +
                "< strftime('%s','now','localtime','weekday 1') " +
                "order by time desc;",null);
        while (cr.moveToNext())
        {
            Weight_Rec weight_rec = new Weight_Rec();
            weight_rec.id = cr.getInt(cr.getColumnIndex("id"));
            weight_rec.weight = cr.getFloat(cr.getColumnIndex("weight"));
            weight_rec.time = new Time(cr.getLong(cr.getColumnIndex("time")));
            weight_rec.title = cr.getString(cr.getColumnIndex("title"));
            list.add(weight_rec);
        }
        cr.close();
        db.close();
        return list;
    }

    public List<Weight_Rec> getWeightRecMonthy()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Weight_Rec> list = new ArrayList<>();
        Cursor cr = db.rawQuery("select * from weight_rec where time >= " +
                "strftime('%s','now','localtime','start of month') and time " +
                "< strftime('%s','now','localtime','+1 month','start of month') " +
                "order by time desc;",null);
        while (cr.moveToNext())
        {
            Weight_Rec weight_rec = new Weight_Rec();
            weight_rec.id = cr.getInt(cr.getColumnIndex("id"));
            weight_rec.weight = cr.getFloat(cr.getColumnIndex("weight"));
            weight_rec.time = new Time(cr.getLong(cr.getColumnIndex("time")));
            weight_rec.title = cr.getString(cr.getColumnIndex("title"));
            list.add(weight_rec);
        }
        cr.close();
        db.close();
        return list;
    }

    public List<Weight_Rec> getAllWeightRec()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Weight_Rec> list = new ArrayList<>();
        Cursor cr = db.rawQuery("select * from weight_rec order by time desc;",null);
        while (cr.moveToNext())
        {
            Weight_Rec weight_rec = new Weight_Rec();
            weight_rec.id = cr.getInt(cr.getColumnIndex("id"));
            weight_rec.weight = cr.getFloat(cr.getColumnIndex("weight"));
            weight_rec.time = new Time(cr.getLong(cr.getColumnIndex("time")));
            weight_rec.title = cr.getString(cr.getColumnIndex("title"));
            list.add(weight_rec);
        }
        cr.close();
        db.close();
        return list;
    }

}
