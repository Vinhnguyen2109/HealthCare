package com.example.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.healthcare.model.Thuoc;
import com.example.healthcare.model.UongThuoc;

import java.util.ArrayList;
import java.util.List;

public class DataLichUongThuoc extends DataManager {
    public DataLichUongThuoc(Context context) {
        super(context);
    }
    public void addThuoc(Thuoc thuoc) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", thuoc.getId());
        cv.put("ten", thuoc.getTen());
        cv.put("lieuluong", thuoc.getLieuluong());
        cv.put("donvi", thuoc.getDonvi());
        cv.put("truocsau", thuoc.getTruocsau());
        db.insert("Thuoc", null, cv);
    }
    public void deleteThuoc(int id){
        SQLiteDatabase db=getWritableDatabase();
        try{
            String sql="DELETE FROM Thuoc WHERE id="+id;
            db.execSQL(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<UongThuoc> checkUongThuoc(int idthuoc){
        SQLiteDatabase db=getReadableDatabase();
        String sql="SELECT * FROM UongThuoc WHERE idthuoc="+idthuoc;
        List<UongThuoc> item_data = null;
        try{
            Cursor cur = db.rawQuery(sql, null);
            item_data = new ArrayList<UongThuoc>();
            if (cur.getCount() != 0)
                if (cur.moveToFirst()) {
                    do {
                        UongThuoc obj = new UongThuoc();
                        obj.setId(cur.getInt(cur.getColumnIndex("id")));
                        obj.setIdthuoc(cur.getInt(cur.getColumnIndex("idthuoc")));
                        obj.setBuoi(cur.getString(cur.getColumnIndex("buoi")));
                        item_data.add(obj);
                    } while (cur.moveToNext());
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return item_data;
    }
    public void addUongThuoc(UongThuoc uongThuoc){
        if(uongThuoc==null) return;
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("id",uongThuoc.getId());
        cv.put("idthuoc",uongThuoc.getIdthuoc());
        cv.put("buoi",uongThuoc.getBuoi());
        db.insert("UongThuoc",null,cv);
    }
    public void deleteUongThuoc(int idthuoc){
        SQLiteDatabase db=getWritableDatabase();
        try{
            String sql="DELETE FROM UongThuoc WHERE idthuoc="+idthuoc;
            db.execSQL(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void cusThuoc(int id, Thuoc thuoc) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("ten", thuoc.getTen());
        cv.put("lieuluong", thuoc.getLieuluong());
        cv.put("donvi", thuoc.getDonvi());
        cv.put("truocsau", thuoc.getTruocsau());
        db.update("Thuoc", cv, "id=" + id, null);
    }
    public int getIDUongThuoc(){
        SQLiteDatabase db = getReadableDatabase();
        String sql="SELECT id FROM UongThuoc ORDER BY id  DESC";
        Cursor cur= db.rawQuery(sql,null);
        if(cur.getCount()!=0) cur.moveToFirst();
        else return 0;
        return cur.getInt(cur.getColumnIndex("id"));
    }
    public List<Thuoc> Thuoc() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Thuoc";
        Cursor cur = db.rawQuery(sql, null);
        List<Thuoc> item_data = new ArrayList<Thuoc>();
        if (cur.getCount() != 0)
            if (cur.moveToFirst()) {
                do {
                    Thuoc obj = new Thuoc();
                    obj.setId(cur.getInt(cur.getColumnIndex("id")));
                    obj.setTen(cur.getString(cur.getColumnIndex("ten")));
                    obj.setLieuluong(cur.getInt(cur.getColumnIndex("lieuluong")));
                    obj.setDonvi(cur.getString(cur.getColumnIndex("donvi")));
                    obj.setTruocsau(cur.getString(cur.getColumnIndex("truocsau")));
                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        return item_data;
    }
}
