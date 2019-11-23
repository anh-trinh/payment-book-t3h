package com.tnta.t3h.paymentbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MonthDetailActivity extends AppCompatActivity {

    TextView thu, chi, chitiet;
    TextView anuong, sinhhoat, dilai, giaitri, khac;
    TextView luong, tietkiem;
    ProgressBar anuongBar, sinhhoatBar, dilaiBar, giaitriBar, khacBar;
    ProgressBar luongBar, tietkiemBar;

    DBHelper db;

    String month;

    DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_detail);

        chitiet = (TextView) findViewById(R.id.textView24_month);
        thu = (TextView) findViewById(R.id.textView_thu_month);
        chi = (TextView) findViewById(R.id.textView_chi_month);
        anuong = (TextView) findViewById(R.id.textView_anuong_month);
        sinhhoat = (TextView) findViewById(R.id.textView_sinhhoat_month);
        dilai = (TextView) findViewById(R.id.textView_dilai_month);
        giaitri = (TextView) findViewById(R.id.textView_giaitri_month);
        khac = (TextView) findViewById(R.id.textView_khac_month);
        luong = (TextView) findViewById(R.id.textView_luong_month);
        tietkiem = (TextView) findViewById(R.id.textView_tietkiem_month);

        df = new DecimalFormat("#,###");

        anuongBar = (ProgressBar) findViewById(R.id.progressBar_anuong_month);
        anuongBar.setProgress(0);
        anuongBar.setMax(100);
        sinhhoatBar = (ProgressBar) findViewById(R.id.progressBar_sinhhoat_month);
        sinhhoatBar.setProgress(0);
        sinhhoatBar.setMax(100);
        dilaiBar = (ProgressBar) findViewById(R.id.progressBar_dilai_month);
        dilaiBar.setProgress(0);
        dilaiBar.setMax(100);
        giaitriBar = (ProgressBar) findViewById(R.id.progressBar_giaitri_month);
        giaitriBar.setProgress(0);
        giaitriBar.setMax(100);
        khacBar = (ProgressBar) findViewById(R.id.progressBar_khac_month);
        khacBar.setProgress(0);
        khacBar.setMax(100);
        luongBar = (ProgressBar) findViewById(R.id.progressBar_luong_month);
        luongBar.setProgress(0);
        luongBar.setMax(100);
        tietkiemBar = (ProgressBar) findViewById(R.id.progressBar_tietkiem_month);
        tietkiemBar.setProgress(0);
        tietkiemBar.setMax(100);

        DateFormat dateFormat = new SimpleDateFormat("MM", Locale.getDefault());
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        Date date = new Date();

        chitiet.setText("Chi tiết trong tháng "+dateFormat.format(date));

        month = dateFormat2.format(date);

        db = new DBHelper(MonthDetailActivity.this);

        int total_chi_a_month = 100;
        if (db.getSumByBookIDByMonth(0,month)>0) {
            total_chi_a_month = db.getSumByBookIDByMonth(0, month);
            chi.setText("Chi: "+df.format(total_chi_a_month)+" VND");
        }
        else {
            chi.setText("Chi: 0 VND");
        }
        int chi_anuong = db.getSumByKindIDByBookIDByMonth(1,0,month);
        anuong.setText(df.format(chi_anuong)+" VND");
        anuongBar.setProgress((int) (chi_anuong*100.0f/total_chi_a_month));
        int chi_sinhhoat = db.getSumByKindIDByBookIDByMonth(2,0,month);
        sinhhoat.setText(df.format(chi_sinhhoat)+" VND");
        sinhhoatBar.setProgress((int) (chi_sinhhoat*100.0f/total_chi_a_month));
        int chi_dilai = db.getSumByKindIDByBookIDByMonth(3,0,month);
        dilai.setText(df.format(chi_dilai)+" VND");
        dilaiBar.setProgress((int) (chi_dilai*100.0f/total_chi_a_month));
        int chi_giaitri = db.getSumByKindIDByBookIDByMonth(4,0,month);
        giaitri.setText(df.format(chi_giaitri)+" VND");
        giaitriBar.setProgress((int) (chi_giaitri*100.0f/total_chi_a_month));
        int chi_khac = db.getSumByKindIDByBookIDByMonth(5,0,month);
        khac.setText(df.format(chi_khac)+" VND");
        khacBar.setProgress((int) (chi_khac*100.0f/total_chi_a_month));


        int total_thu_a_month = 100;
        if (db.getSumByBookIDByMonth(1,month)>0){
            total_thu_a_month = db.getSumByBookIDByMonth(1,month);
            thu.setText("Thu: "+df.format(total_thu_a_month)+" VND");
        }
        else {
            thu.setText("Thu: 0 VND");
        }
        int thu_luong = db.getSumByKindIDByBookIDByMonth(1,1,month);
        luong.setText(df.format(thu_luong)+" VND");
        luongBar.setProgress((int) (thu_luong*100.0f/total_thu_a_month));
        int thu_tietkiem = db.getSumByKindIDByBookIDByMonth(2,1,month);
        tietkiem.setText(df.format(thu_tietkiem)+" VND");
        tietkiemBar.setProgress((int) (thu_tietkiem*100.0f/total_thu_a_month));
    }
}
