package com.example.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.healthcare.model.TrieuChung;

import java.util.ArrayList;
import java.util.List;

public class DataTrieuChung extends DataManager {

    public DataTrieuChung(Context context) {
        super(context);
    }

    public TrieuChung getTrieuChung(int ngay) {
        SQLiteDatabase db=getReadableDatabase();
        String sql="SELECT * FROM TrieuChung WHERE ngay="+ngay;
        TrieuChung temp=null;
        try{
            Cursor cur = db.rawQuery(sql, null);
            if (cur.getCount() != 0)
                if (cur.moveToFirst()) {
                    do {
                        temp=new TrieuChung();
                        temp.setNgay(cur.getInt(cur.getColumnIndex("ngay")));
                        temp.setMota(cur.getString(cur.getColumnIndex("mota")));
                        temp.setTaikham(cur.getInt(cur.getColumnIndex("taikham")));
                    } while (cur.moveToNext());
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return temp;
    }

    public void addTrieuChung(TrieuChung t) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ngay", t.getNgay());
        cv.put("mota", t.getMota());
        cv.put("taikham", t.getTaikham());
        db.insert("TrieuChung", null, cv);
    }

    public void deleteTrieuChung(int ngay) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String sql = "DELETE FROM TrieuChung WHERE ngay=" + ngay;
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<TrieuChung> getListTrieuChung() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM TrieuChung";
        List<TrieuChung> items = new ArrayList<TrieuChung>();
        try {
            Cursor cur = db.rawQuery(sql, null);
            if (cur.getCount() != 0)
                if (cur.moveToFirst()) {
                    do {
                        TrieuChung temp = new TrieuChung();
                        temp.setNgay(cur.getInt(cur.getColumnIndex("ngay")));
                        temp.setTaikham(cur.getInt(cur.getColumnIndex("taikham")));
                        temp.setMota(cur.getString(cur.getColumnIndex("mota")));
                        items.add(temp);
                    } while (cur.moveToNext());
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
}
