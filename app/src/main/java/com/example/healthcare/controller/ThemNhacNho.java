package com.example.healthcare.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.R;
import com.example.healthcare.database.DataLichUongThuoc;
import com.example.healthcare.model.Thuoc;
import com.example.healthcare.model.UongThuoc;

import java.util.List;

public class ThemNhacNho extends AppCompatActivity {
    DataLichUongThuoc dataManager;
    EditText ten, donvi, lieuluong;
    CheckBox sang, trua, toi;
    RadioButton truocan, sauan;
    Button huy, luu;
    Thuoc thuoc;
    UongThuoc sangUT, truaUT, toiUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhac_nho);
        getView();
        MainActivity.setupUI(this, findViewById(R.id.them_nhac_nho_parents));// ẩn keyboard khi không dùng
        dataManager = new DataLichUongThuoc(ThemNhacNho.this);
        /*------------Lưu-------------*/
        if (LichUongThuoc.cusThuoc != null) {
            fillInfo(LichUongThuoc.cusThuoc);
            List<UongThuoc> temp = dataManager.checkUongThuoc(LichUongThuoc.cusThuoc.getId());
            for (UongThuoc i : temp) {
                if (i.getBuoi().equals(sang.getText())) sang.setChecked(true);
                if (i.getBuoi().equals(trua.getText())) trua.setChecked(true);
                if (i.getBuoi().equals(toi.getText())) toi.setChecked(true);
            }
        }
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmpty()) return;
                luuThuoc();
                if (LichUongThuoc.cusThuoc != null) {
                    dataManager.cusThuoc(LichUongThuoc.cusThuoc.getId(), thuoc);
                } else {
                    dataManager.addThuoc(thuoc);
                }
                luuUongThuoc();
                dataManager.deleteUongThuoc(thuoc.getId());
                dataManager.addUongThuoc(sangUT);
                dataManager.addUongThuoc(truaUT);
                dataManager.addUongThuoc(toiUT);
                Intent intent = new Intent(ThemNhacNho.this, LichUongThuoc.class);
                startActivity(intent);
            }
        });
        /*-----------Hủy--------------*/
        if (LichUongThuoc.cusThuoc != null) {
            huy.setText("Xóa");
        } else {
            huy.setText("Hủy");
        }
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LichUongThuoc.cusThuoc != null)
                    dataManager.deleteThuoc(LichUongThuoc.cusThuoc.getId());
                Intent intent = new Intent(ThemNhacNho.this, LichUongThuoc.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkEmpty() {
        if (TextUtils.isEmpty(ten.getText())) {
            ten.setError("Bạn chưa điền tên thuốc");
            return true;
        }
        if (TextUtils.isEmpty(lieuluong.getText())) {
            lieuluong.setError("Bạn chưa điền liều lượng");
            return true;
        }
        if (TextUtils.isEmpty(donvi.getText())) {
            donvi.setError("Bạn chưa điền đơn vị");
            return true;
        }
        return false;
    }

    private void fillInfo(Thuoc t) {
        ten.setText(t.getTen());
        lieuluong.setText(String.valueOf(t.getLieuluong()));
        donvi.setText(t.getDonvi());
        if (t.getTruocsau().equals("Trước ăn")) truocan.setChecked(true);
        else sauan.setChecked(true);
    }

    void getView() {
        ten = findViewById(R.id.txt_ten_thuoc);
        lieuluong = findViewById(R.id.txt_lieu_luong);
        donvi = findViewById(R.id.txt_don_vi);
        sang = findViewById(R.id.check_sang);
        trua = findViewById(R.id.check_trua);
        toi = findViewById(R.id.check_toi);
        truocan = findViewById(R.id.check_truoc_an);
        sauan = findViewById(R.id.check_sau_an);
        huy = findViewById(R.id.btn_huy_nhacnho);
        luu = findViewById(R.id.btn_luu_nhacnho);
    }

    void luuThuoc() {
        thuoc = new Thuoc();
        if (LichUongThuoc.cusThuoc != null) thuoc.setId(LichUongThuoc.cusThuoc.getId());
        else
            thuoc.setId(LichUongThuoc.idThuoc + 1);
        thuoc.setTen(ten.getText().toString());
        thuoc.setLieuluong(Integer.parseInt(lieuluong.getText().toString()));
        thuoc.setDonvi(donvi.getText().toString());
        if (truocan.isChecked()) thuoc.setTruocsau(truocan.getText().toString());
        else thuoc.setTruocsau(sauan.getText().toString());
    }

    void luuUongThuoc() {
        if (sang.isChecked()) {
            sangUT = new UongThuoc();
            LichUongThuoc.idUongThuoc++;
            sangUT.setId(LichUongThuoc.idUongThuoc);
            sangUT.setIdthuoc(thuoc.getId());
            sangUT.setBuoi(sang.getText().toString());
        }
        if (trua.isChecked()) {
            truaUT = new UongThuoc();
            LichUongThuoc.idUongThuoc++;
            truaUT.setId(LichUongThuoc.idUongThuoc);
            truaUT.setIdthuoc(thuoc.getId());
            truaUT.setBuoi(trua.getText().toString());
        }
        if (toi.isChecked()) {
            toiUT = new UongThuoc();
            LichUongThuoc.idUongThuoc++;
            toiUT.setId(LichUongThuoc.idUongThuoc);
            toiUT.setIdthuoc(thuoc.getId());
            toiUT.setBuoi(toi.getText().toString());
        }
    }
}