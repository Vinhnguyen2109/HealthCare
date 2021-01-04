package com.example.healthcare.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.R;

public class TheoDoiHuyetAp extends AppCompatActivity {
    public static int BLOOD_MIN = 0;
    public static int BLOOD_MAX = 0;
    ImageButton btn_back;
    EditText min, max;
    Button btn_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theo_doi_huyet_ap);
        MainActivity.setupUI(this, findViewById(R.id.huyet_ap_parents));// ẩn keyboard khi không dùng
        getView();
        //region button check
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmpty()) return;
                BLOOD_MIN = Integer.parseInt(min.getText().toString());
                BLOOD_MAX = Integer.parseInt(max.getText().toString());
                if (BLOOD_MAX < BLOOD_MIN) {
                    max.setError("Phải cao hơn chỉ số DIA");
                    return;
                }
                Intent intent = new Intent(TheoDoiHuyetAp.this, ResultHuyetAp.class);
                finish();
                startActivity(intent);
            }
        });
        //endregion
        //region button back
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TheoDoiHuyetAp.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        //endregion
    }

    private boolean checkEmpty() {
        if (TextUtils.isEmpty(min.getText())) {
            min.setError("Chưa nhập ô này");
            return true;
        }
        if (TextUtils.isEmpty(max.getText())) {
            max.setError("Chưa nhập ô này");
            return true;
        }
        return false;
    }

    public void getView() {
        btn_back = findViewById(R.id.btn_huyet_ap_back);
        min = findViewById(R.id.index_DIA);
        max = findViewById(R.id.index_SYS);
        btn_check = findViewById(R.id.btn_check_huyet_ap);
    }
}