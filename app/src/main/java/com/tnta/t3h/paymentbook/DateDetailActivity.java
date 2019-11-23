package com.tnta.t3h.paymentbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class DateDetailActivity extends AppCompatActivity {

    TextView thu, chi;
    TextView anuong, sinhhoat, dilai, giaitri, khac;
    TextView luong, tietkiem;
    ProgressBar anuongBar, sinhhoatBar, dilaiBar, giaitriBar, khacBar;
    ProgressBar luongBar, tietkiemBar;

    DBHelper db;

    String date;

    DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_detail);

        thu = (TextView) findViewById(R.id.textView_thu);
        chi = (TextView) findViewById(R.id.textView_chi);
        anuong = (TextView) findViewById(R.id.textView_anuong);
        sinhhoat = (TextView) findViewById(R.id.textView_sinhhoat);
        dilai = (TextView) findViewById(R.id.textView_dilai);
        giaitri = (TextView) findViewById(R.id.textView_giaitri);
        khac = (TextView) findViewById(R.id.textView_khac);
        luong = (TextView) findViewById(R.id.textView_luong);
        tietkiem = (TextView) findViewById(R.id.textView_tietkiem);

        df = new DecimalFormat("#,###");

        anuongBar = (ProgressBar) findViewById(R.id.progressBar_anuong);
        anuongBar.setProgress(0);
        anuongBar.setMax(100);
        sinhhoatBar = (ProgressBar) findViewById(R.id.progressBar_sinhhoat);
        sinhhoatBar.setProgress(0);
        sinhhoatBar.setMax(100);
        dilaiBar = (ProgressBar) findViewById(R.id.progressBar_dilai);
        dilaiBar.setProgress(0);
        dilaiBar.setMax(100);
        giaitriBar = (ProgressBar) findViewById(R.id.progressBar_giaitri);
        giaitriBar.setProgress(0);
        giaitriBar.setMax(100);
        khacBar = (ProgressBar) findViewById(R.id.progressBar_khac);
        khacBar.setProgress(0);
        khacBar.setMax(100);
        luongBar = (ProgressBar) findViewById(R.id.progressBar_luong);
        luongBar.setProgress(0);
        luongBar.setMax(100);
        tietkiemBar = (ProgressBar) findViewById(R.id.progressBar_tietkiem);
        tietkiemBar.setProgress(0);
        tietkiemBar.setMax(100);

        date = getIntent().getStringExtra("date");

        db = new DBHelper(DateDetailActivity.this);

        int total_chi_a_day = 100;
        if (db.getSumByBookIDByDate(0,date)>0) {
            total_chi_a_day = db.getSumByBookIDByDate(0, date);
            chi.setText("Chi: "+df.format(total_chi_a_day)+" VND");
        }
        else {
            chi.setText("Chi: 0 VND");
        }
        int chi_anuong = db.getSumByKindIDByBookIDByDate(1,0,date);
        anuong.setText(df.format(chi_anuong)+" VND");
        anuongBar.setProgress((int) (chi_anuong*100.0f/total_chi_a_day));
        int chi_sinhhoat = db.getSumByKindIDByBookIDByDate(2,0,date);
        sinhhoat.setText(df.format(chi_sinhhoat)+" VND");
        sinhhoatBar.setProgress((int) (chi_sinhhoat*100.0f/total_chi_a_day*100));
        int chi_dilai = db.getSumByKindIDByBookIDByDate(3,0,date);
        dilai.setText(df.format(chi_dilai)+" VND");
        dilaiBar.setProgress((int) (chi_dilai*100.0f/total_chi_a_day));
        int chi_giaitri = db.getSumByKindIDByBookIDByDate(4,0,date);
        giaitri.setText(df.format(chi_giaitri)+" VND");
        giaitriBar.setProgress((int) (chi_giaitri*100.0f/total_chi_a_day));
        int chi_khac = db.getSumByKindIDByBookIDByDate(5,0,date);
        khac.setText(df.format(chi_khac)+" VND");
        khacBar.setProgress((int) (chi_khac*100.0f/total_chi_a_day));


        int total_thu_a_day = 100;
        if (db.getSumByBookIDByDate(1,date)>0){
            total_thu_a_day = db.getSumByBookIDByDate(1,date);
            thu.setText("Thu: "+df.format(total_thu_a_day)+" VND");
        }
        else {
            thu.setText("Thu: 0 VND");
        }
        int thu_luong = db.getSumByKindIDByBookIDByDate(1,1,date);
        luong.setText(df.format(thu_luong)+" VND");
        luongBar.setProgress((int) (thu_luong*100.0f/total_thu_a_day));
        int thu_tietkiem = db.getSumByKindIDByBookIDByDate(2,1,date);
        tietkiem.setText(df.format(thu_tietkiem)+" VND");
        tietkiemBar.setProgress((int) (thu_tietkiem*100.0f/total_thu_a_day));
    }
}
