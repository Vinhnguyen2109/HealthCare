package com.example.healthcare.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.R;
import com.example.healthcare.database.DataHuyetAp;
import com.example.healthcare.model.HuyetAp;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ResultHuyetAp extends AppCompatActivity {
    DataHuyetAp dataManager;
    Button btn_huy, btn_luu;
    TextView chiso, ketqua, loikhuyen;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_huyet_ap);
        getView();
        dataManager = new DataHuyetAp(ResultHuyetAp.this);
        //region text view chi so
        String s = "Huyết áp : " + String.valueOf(TheoDoiHuyetAp.BLOOD_MAX) + "/" + String.valueOf(TheoDoiHuyetAp.BLOOD_MIN) + " mmHg";
        chiso.setText(s);
        //endregion
        //region text view ket qua va loi khuyen
        if ((TheoDoiHuyetAp.BLOOD_MAX < 90)) {
            ketqua.setText("Huyết áp thấp");
            ketqua.setTextColor(Color.RED);
            loikhuyen.setText("- Không nên thức khuya, giữ ấm cơ thể khi ngủ\n" +
                    "- Khi ngủ cần gối thấp\n" +
                    "- Không ra ngoài trời nắng gắt\n" +
                    "- Duy trì vận động nhẹ nhàng vừa phải như đi bộ\n");
        } else if ((TheoDoiHuyetAp.BLOOD_MAX >= 90) && (TheoDoiHuyetAp.BLOOD_MAX < 120) && (TheoDoiHuyetAp.BLOOD_MIN < 80)) {
            ketqua.setText("Huyết áp bình thường");
            ketqua.setTextColor(Color.BLACK);
            loikhuyen.setText("Giữ vững thói quen ăn uống hoạt động như hiện nay.\n");
        } else if (((TheoDoiHuyetAp.BLOOD_MAX >= 120) && (TheoDoiHuyetAp.BLOOD_MAX < 140))
                        || ((TheoDoiHuyetAp.BLOOD_MIN >= 80) && (TheoDoiHuyetAp.BLOOD_MIN < 90))) {
            ketqua.setText("Tiền cao huyết áp");
            ketqua.setTextColor(Color.YELLOW);
            loikhuyen.setText("- Chọn trái cây, rau, ngũ cốc, thịt gia cầm, cá và các thực phẩm từ sữa ít chất béo.\n" +
                    "- Sử dụng ít muối, hạn chế thực phẩm chế biến sẵn và đóng hộp\n" +
                    "- Hạn chế rượu\n" +
                    "- Không hút thuốc\n");
        } else if ((TheoDoiHuyetAp.BLOOD_MAX >= 140) || (TheoDoiHuyetAp.BLOOD_MIN >= 90)) {
            ketqua.setText("Huyết áp cao");
            ketqua.setTextColor(Color.RED);
            loikhuyen.setText("- Chọn trái cây, rau, ngũ cốc, thịt gia cầm, cá và các thực phẩm từ sữa ít chất béo.\n" +
                    "- Sử dụng ít muối, hạn chế thực phẩm chế biến sẵn và đóng hộp\n" +
                    "- Hạn chế rượu\n" +
                    "- Không hút thuốc\n");
        }
        if ((TheoDoiHuyetAp.BLOOD_MAX >= 160) && (TheoDoiHuyetAp.BLOOD_MIN >= 100)) {
            ketqua.setText("Nguy hiểm!");
            ketqua.setTextColor(Color.RED);
            loikhuyen.setText("Cần đến cơ sở y tế gần nhất trong thời gian sớm nhất\n");
        }
        //endregion
        //region luu ket qua
        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int y = localDate.getYear();
                int m = localDate.getMonthValue();
                int d = localDate.getDayOfMonth();
                String s = String.format("%02d", d) + String.format("%02d", m) + String.valueOf(y);
                HuyetAp temp = new HuyetAp();
                temp.setMax(TheoDoiHuyetAp.BLOOD_MAX);
                temp.setMin(TheoDoiHuyetAp.BLOOD_MIN);
                temp.setNgay(Integer.parseInt(s));
                dataManager.deleteHuyetAp(Integer.parseInt(s));
                dataManager.addHuyetAp(temp);
                Intent intent = new Intent(ResultHuyetAp.this, TheoDoiHuyetAp.class);
                finish();
                startActivity(intent);
            }
        });
        //endregion
        //region xoa ket qua
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultHuyetAp.this, TheoDoiHuyetAp.class);
                finish();
                startActivity(intent);
            }
        });
        //endregion
    }

    public void getView() {
        btn_luu = findViewById(R.id.btn_luu_huyet_ap);
        btn_huy = findViewById(R.id.btn_huy_huyet_ap);
        chiso = findViewById(R.id.txt_huyet_ap);
        ketqua = findViewById(R.id.huyet_ap_result);
        loikhuyen = findViewById(R.id.loi_khuyen_huyet_ap);
    }
}