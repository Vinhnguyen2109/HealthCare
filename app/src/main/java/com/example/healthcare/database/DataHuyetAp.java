package com.example.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.healthcare.model.HuyetAp;

import java.util.ArrayList;
import java.util.List;

public class DataHuyetAp extends DataManager {
    public DataHuyetAp(Context context) {
        super(context);
    }

    public List<HuyetAp> getHuyetAp(int ngay) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM HuyetAp WHERE ngay=" + String.valueOf(ngay);
        List<HuyetAp> items = new ArrayList<HuyetAp>();
        try {
            Cursor cur = db.rawQuery(sql, null);
            if (cur.getCount() != 0)
                if (cur.moveToFirst()) {
                    do {
                        HuyetAp temp = new HuyetAp();
                        temp.setId(cur.getInt(cur.getColumnIndex("id")));
                        temp.setNgay(cur.getInt(cur.getColumnIndex("ngay")));
                        temp.setMin(cur.getInt(cur.getColumnIndex("min")));
                        temp.setMax(cur.getInt(cur.getColumnIndex("max")));
                        items.add(temp);
                    } while (cur.moveToNext());
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
    public int highestID(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT MAX(id) res FROM HuyetAp";
        int res=0;
        try {
            Cursor cur = db.rawQuery(sql, null);
            if (cur.getCount() != 0)
                if (cur.moveToFirst()) {
                    do {
                        res=cur.getInt(cur.getColumnIndex("res"));
                    } while (cur.moveToNext());
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public void addHuyetAp(HuyetAp t) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id",t.getId());
        cv.put("ngay", t.getNgay());
        cv.put("min", t.getMin());
        cv.put("max", t.getMax());
        db.insert("HuyetAp", null, cv);
    }
}
