package com.tnta.t3h.paymentbook;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    Button button_calendar_total;
    DBHelper db;
    CalendarView calendarView;
    TextView tai_khoan_month;

    DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date dateType = new Date();
        String date_today = dateFormat.format(dateType);
        df = new DecimalFormat("#,###");

        db = new DBHelper(CalendarActivity.this);
        button_calendar_total = (Button) findViewById(R.id.button_calendar_total);
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        tai_khoan_month = (TextView) findViewById(R.id.tai_khoan_month);

        int tong_chi = db.getSumChi();
        int tong_thu = db.getSumThu();
        int tong = tong_thu - tong_chi;
        String tai_khoan_content = "Tài khoản: "+df.format(tong)+" VND";
        tai_khoan_month.setText(tai_khoan_content);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                Intent intent = new Intent(CalendarActivity.this,MainActivity.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarActivity.this, R.anim.slide_right_in, R.anim.slide_right_out);
                intent.putExtra("date",year+"-"+(month+1)+"-"+dayOfMonth);
                startActivity(intent, options.toBundle());
            }
        });
        button_calendar_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CalendarActivity.this,MonthDetailActivity.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarActivity.this, R.anim.slide_left_in, R.anim.slide_left_out);
                startActivity(i,options.toBundle());
            }
        });

        button_calendar_total.setText("Chi tiết tháng này");

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }
}
