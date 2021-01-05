package com.example.healthcare.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.healthcare.R;
import com.example.healthcare.model.Thuoc;

import java.util.List;

public class cusListLichUongThuoc extends BaseAdapter {
    private List<Thuoc> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    public cusListLichUongThuoc(Context aContext, List<Thuoc> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
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
            convertView = layoutInflater.inflate(R.layout.item_lich_uong_thuoc, null);
            holder = new ViewHolder();
            holder.ten = convertView.findViewById(R.id.item_ten);
            holder.thoidiem = convertView.findViewById(R.id.item_thoigian);
            holder.lieuluong = convertView.findViewById(R.id.item_lieuluong);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Thuoc thuoc = this.listData.get(position);
        holder.ten.setText(thuoc.getTen());
        holder.thoidiem.setText(thuoc.getTruocsau());
        holder.lieuluong.setText(thuoc.getLieuluong()+" "+thuoc.getDonvi());

        return convertView;
    }

    static class ViewHolder {
        TextView ten;
        TextView thoidiem;
        TextView lieuluong;

    }
}
