package com.example.healthcare.controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.healthcare.R;
import com.example.healthcare.database.DataHuyetAp;
import com.example.healthcare.database.DataTrieuChung;
import com.example.healthcare.model.HuyetAp;
import com.example.healthcare.model.TrieuChung;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ThongKe extends AppCompatActivity {
    static Button fromdate, todate;
    static String from = "08-01-2021", to = "08-01-2021";
    DataTrieuChung dataTrieuChung;
    DataHuyetAp dataHuyetAp;
    List<Integer> listNgay;
    ListView listThongKe;
    Button btn_thong_ke;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        getView();
        //region init
        dataTrieuChung = new DataTrieuChung(ThongKe.this);
        dataHuyetAp = new DataHuyetAp(ThongKe.this);
        //endregion
        //region từ ngày ...
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "fromdate");
            }
        });
        //endregion
        //region đến ngày ...
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "todate");
            }
        });
        //endregion
        //region Thống kê
        btn_thong_ke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listNgay = new ArrayList<Integer>();
                List<TrieuChung> tc = dataTrieuChung.getListTrieuChung();
                List<HuyetAp> ha = dataHuyetAp.getHuyetAp();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date from_i = formatNgay(from);
                Date to_i = formatNgay(to);
                for (TrieuChung i : tc) {
                    String ngay = convertNgay(i.getNgay());
                    Date i_ngay = formatNgay(ngay);
                    if (i_ngay.after(from_i) && i_ngay.before(to_i)) listNgay.add(i.getNgay());
                    if (i_ngay.equals(from_i) || i_ngay.equals(to_i)) listNgay.add(i.getNgay());
                }
                for (HuyetAp i : ha) {
                    String ngay = convertNgay(i.getNgay());
                    Date i_ngay = formatNgay(ngay);
                    if (i_ngay.after(from_i) && i_ngay.before(to_i))
                        if (!listNgay.contains(i.getNgay()))
                            listNgay.add(i.getNgay());
                    if (i_ngay.equals(from_i) || i_ngay.equals(to_i))
                        if (!listNgay.contains(i.getNgay()))
                            listNgay.add(i.getNgay());
                }
                sortListNgay();
                listThongKe.setAdapter(new cusListThongKe(ThongKe.this, listNgay));
            }
        });
        //endregion
        //region button back
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongKe.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        //endregion
    }

    private void sortListNgay() {
        List<Integer> list = listNgay;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                Date i_ngay = formatNgay(convertNgay(list.get(i)));
                Date j_ngay = formatNgay(convertNgay(list.get(j)));
                if (j_ngay.before(i_ngay)) {
                    int t = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, t);
                }
            }
        }
        listNgay = list;
    }

    public Date formatNgay(String t) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date res = null;
        try {
            res = sdf.parse(t);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    public String convertNgay(int ngay) {
        String res;
        int d, m, y;
        y = ngay % 10000;
        d = ngay / 10000;
        m = d % 100;
        d = d / 100;
        res = String.format("%02d", d) + "-" + String.format("%02d", m) + "-" + String.valueOf(y);
        return res;
    }

    public void getView() {
        fromdate = findViewById(R.id.btn_from_date);
        todate = findViewById(R.id.btn_to_date);
        listThongKe = findViewById(R.id.list_thong_ke);
        btn_thong_ke = findViewById(R.id.thong_ke);
        btn_back = findViewById(R.id.btn_thong_ke_back);
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            return dialog;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String s = String.format("%02d", dayOfMonth) + "-" + String.format("%02d", month + 1) + "-" + String.valueOf(year);
            if (getTag().equals("fromdate")) {
                from = s;
                fromdate.setText("Từ:\t" + s);
            }
            if (getTag().equals("todate")) {
                to = s;
                todate.setText("Đến:\t" + s);
            }
        }
    }
}