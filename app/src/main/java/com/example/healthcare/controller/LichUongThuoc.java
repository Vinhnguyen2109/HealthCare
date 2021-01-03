package com.example.healthcare.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.R;
import com.example.healthcare.database.DataLichUongThuoc;
import com.example.healthcare.model.Thuoc;
import com.example.healthcare.model.UongThuoc;

import java.util.ArrayList;
import java.util.List;

public class LichUongThuoc extends AppCompatActivity {
    static int idThuoc;
    static int idUongThuoc;
    static Thuoc cusThuoc;
    DataLichUongThuoc dataManager;
    ListView list_thuoc;
    Button btn_them_nhac_nho;
    Button btn_sang, btn_trua, btn_toi;
    ImageButton btn_back;
    List<Thuoc> items, items_sang, items_trua, items_toi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_uong_thuoc);
        getView();
        dataManager = new DataLichUongThuoc(LichUongThuoc.this);
        //region Lấy danh sách thuốc
        items = new ArrayList<Thuoc>();
        items_sang = new ArrayList<Thuoc>();
        items_trua = new ArrayList<Thuoc>();
        items_toi = new ArrayList<Thuoc>();
        items.clear();
        items = dataManager.Thuoc();
        idThuoc = items.size();
        for (Thuoc t : items) {
            List<UongThuoc> temp=dataManager.checkUongThuoc(t.getId());
            for (UongThuoc ut : temp) {
                if (ut.getBuoi().equals(btn_sang.getText())) items_sang.add(t);
                if (ut.getBuoi().equals(btn_trua.getText())) items_trua.add(t);
                if (ut.getBuoi().equals(btn_toi.getText())) items_toi.add(t);
            }
        }
        list_thuoc.setAdapter(new cusListAdapter(this, items_sang));
        btn_sang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sang.setBackgroundColor(Color.parseColor("#018786"));
                items=items_sang;
                list_thuoc.setAdapter(new cusListAdapter(LichUongThuoc.this, items_sang));
                btn_trua.setBackgroundColor(Color.parseColor("#03DAC5"));
                btn_toi.setBackgroundColor(Color.parseColor("#03DAC5"));
            }
        });
        btn_trua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_trua.setBackgroundColor(Color.parseColor("#018786"));
                items=items_trua;
                list_thuoc.setAdapter(new cusListAdapter(LichUongThuoc.this, items_trua));
                btn_sang.setBackgroundColor(Color.parseColor("#03DAC5"));
                btn_toi.setBackgroundColor(Color.parseColor("#03DAC5"));
            }
        });
        btn_toi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_toi.setBackgroundColor(Color.parseColor("#018786"));
                items=items_toi;
                list_thuoc.setAdapter(new cusListAdapter(LichUongThuoc.this, items_toi));
                btn_sang.setBackgroundColor(Color.parseColor("#03DAC5"));
                btn_trua.setBackgroundColor(Color.parseColor("#03DAC5"));
            }
        });
        idUongThuoc = dataManager.getIDUongThuoc();
        //endregion
        //region Xử lý nút thêm
        btn_them_nhac_nho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LichUongThuoc.this, ThemNhacNho.class);
                finish();
                startActivity(intent);
            }
        });
        //endregion
        //region Sửa thông tin thuốc
        cusThuoc=null;
        list_thuoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cusThuoc = items.get(position);
                Intent intent = new Intent(LichUongThuoc.this, ThemNhacNho.class);
                finish();
                startActivity(intent);
            }
        });
        //endregion
        //region Xử lý nút back
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LichUongThuoc.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        //endregion
    }

    void getView() {
        btn_back = findViewById(R.id.btn_uong_thuoc_back);
        list_thuoc = findViewById(R.id.list_thuoc);
        btn_them_nhac_nho = findViewById(R.id.btn_them_nhac_nho);
        btn_sang = findViewById(R.id.btn_view_sang);
        btn_trua = findViewById(R.id.btn_view_trua);
        btn_toi = findViewById(R.id.btn_view_toi);
    }
}