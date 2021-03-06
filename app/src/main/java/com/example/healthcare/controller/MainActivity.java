package com.example.healthcare.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.healthcare.R;

public class MainActivity extends AppCompatActivity {
    Button btn_uong_thuoc;
    Button btn_trieu_chung;
    Button btn_huyet_ap;
    Button btn_thong_ke;
    Button btn_lien_he;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getView();
        //region nhac lich uong thuoc
        btn_uong_thuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LichUongThuoc.class);
                startActivity(intent);
            }
        });
        //endregion
        //region theo doi suc khoe
        btn_trieu_chung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TheoDoiTrieuChung.class);
                startActivity(intent);
            }
        });
        //endregion
        //region theo doi huyet ap
        btn_huyet_ap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TheoDoiHuyetAp.class);
                startActivity(intent);
            }
        });
        //endregion
        //region thong ke
        btn_thong_ke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ThongKe.class);
                startActivity(intent);
            }
        });
        //endregion
        //region thong tin lien he
        btn_lien_he.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new TheoDoiTrieuChung.MyDialogFragment();
                dialog.show(getSupportFragmentManager(), "ThongTinLienHe");
            }
        });
        //endregion
    }

    private void getView() {
        btn_uong_thuoc = findViewById(R.id.btn_lich_uong_thuoc);
        btn_trieu_chung = findViewById(R.id.btn_trieu_chung);
        btn_huyet_ap = findViewById(R.id.btn_huyet_ap);
        btn_thong_ke = findViewById(R.id.btn_thong_ke);
        btn_lien_he = findViewById(R.id.btn_lien_he);
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void setupUI(Activity act, View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(act);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(act, innerView);
            }
        }
    }
}
