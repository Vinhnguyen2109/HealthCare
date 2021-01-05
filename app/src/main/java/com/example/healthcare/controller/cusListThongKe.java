package com.example.healthcare.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.healthcare.R;
import com.example.healthcare.database.DataHuyetAp;
import com.example.healthcare.database.DataTrieuChung;
import com.example.healthcare.model.HuyetAp;
import com.example.healthcare.model.TrieuChung;

import java.util.List;

public class cusListThongKe extends BaseAdapter {
    private List<Integer> listNgay;
    private LayoutInflater layoutInflater;
    private Context context;

    public cusListThongKe(Context aContext, List<Integer> listNgay) {
        this.context = aContext;
        this.listNgay = listNgay;
        this.layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listNgay.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_list_thong_ke, null);
            holder = new ViewHolder();
            holder.ngay = convertView.findViewById(R.id.ngay_tk);
            holder.huyetap = convertView.findViewById(R.id.huyet_ap_tk);
            holder.trieuchung = convertView.findViewById(R.id.trieu_chung_tk);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int ngay = listNgay.get(position);
        holder.ngay.setText(convertNgay(ngay));
        HuyetAp huyetAp = new DataHuyetAp(context).getHuyetAp(ngay);
        if (huyetAp != null)
            holder.huyetap.setText("Huyết áp : " + String.valueOf(huyetAp.getMax()) + "/" + String.valueOf(huyetAp.getMin()) + " mmHg");
        TrieuChung trieuChung = new DataTrieuChung(context).getTrieuChung(ngay);
        if (trieuChung != null)
            holder.trieuchung.setText("Triệu chứng: " + trieuChung.getMota().toString());
        return convertView;
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

    static class ViewHolder {
        TextView ngay;
        TextView huyetap;
        TextView trieuchung;

    }
}
