package com.example.healthcare.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.healthcare.R;
import com.example.healthcare.database.DataTrieuChung;
import com.example.healthcare.model.TrieuChung;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TheoDoiTrieuChung extends AppCompatActivity {
    DataTrieuChung dataManager;
    CalendarView calendar;
    CheckBox taikham;
    EditText mota;
    int date_ma_hoa;
    Button btn_save;
    ImageButton btn_back;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theo_doi_trieu_chung);
        getView();
        MainActivity.setupUI(this, findViewById(R.id.trieu_chung_parents));// ẩn keyboard khi không dùng
        dataManager = new DataTrieuChung(TheoDoiTrieuChung.this);
        //region init
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int y = localDate.getYear();
        int m = localDate.getMonthValue();
        int d = localDate.getDayOfMonth();
        String temp = String.format("%02d", d) + String.format("%02d", m) + String.valueOf(y);
        date_ma_hoa=Integer.parseInt(temp);
        getInfo();
        //endregion
        //region Change selected date
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                MainActivity.setupUI(TheoDoiTrieuChung.this, findViewById(R.id.calendar));// ẩn keyboard khi không dùng
                String temp = String.format("%02d", dayOfMonth) + String.format("%02d", month + 1) + String.valueOf(year);
                date_ma_hoa = Integer.parseInt(temp);
                getInfo();
            }
        });
        //endregion
        //region button save
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataManager.deleteTrieuChung(date_ma_hoa);
                TrieuChung temp = new TrieuChung();
                temp.setNgay(date_ma_hoa);
                temp.setMota(mota.getText().toString());
                if (taikham.isChecked()) temp.setTaikham(1);
                else temp.setTaikham(0);
                dataManager.addTrieuChung(temp);
                DialogFragment dialog = new MyDialogFragment();
                dialog.show(getSupportFragmentManager(), "MyDialogFragmentTag");

            }
        });
        //endregion
        //region button back
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TheoDoiTrieuChung.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        //endregion
    }

    private void getInfo() {
        taikham.setChecked(false);
        mota.setText(null);
        TrieuChung temp = dataManager.getTrieuChung(date_ma_hoa);
        if (temp != null) {
            if (temp.getTaikham() == 1) taikham.setChecked(true);
            else taikham.setChecked(false);
            mota.setText(temp.getMota());
        }
    }

    public void getView() {
        btn_back = findViewById(R.id.btn_trieu_chung_back);
        calendar = findViewById(R.id.calendar);
        taikham = findViewById(R.id.check_taikham);
        mota = findViewById(R.id.mo_ta_trieu_chung);
        btn_save = findViewById(R.id.btn_luu_trieu_chung);
    }

    public static class MyDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Đã lưu");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // You don't have to do anything here if you just
                    // want it dismissed when clicked
                }
            });

            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}