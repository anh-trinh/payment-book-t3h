package com.tnta.t3h.paymentbook;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tnta.t3h.paymentbook.model.Book;
import com.tnta.t3h.paymentbook.model.ChiKind;
import com.tnta.t3h.paymentbook.model.ThuKind;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    DBHelper db;
    TextView tai_khoan;
    TextView ngay;
    ListView date_list_view;
    String fileSharePreferences = "USER";
    CustomMainListViewAdapter customMainListViewAdapter;
    ArrayList<Book> arrayBook;

    Button buttonThu;
    Button buttonChi;
    Button chitietngay;

    String date;
    String date_today;

    TextView truoc;
    TextView sau;

    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    DecimalFormat df;

    ArrayList<ThuKind> arrayList_thu;
    ArrayList<ChiKind> arrayList_chi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(fileSharePreferences,MODE_PRIVATE);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        sharedPreferences.getString("username","");
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("password",edtPassword.getText().toString());

        df = new DecimalFormat("#,###");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        final Date dateType = new Date();
        date_today = dateFormat.format(dateType);

        date = getIntent().getStringExtra("date");
        if (date==null){
            date = date_today;
        }

        db = new DBHelper(MainActivity.this);
        arrayBook = db.getAllBookByDate(date);

        arrayList_thu = db.getKindThu();
        arrayList_chi = db.getKindChi();

        ngay = (TextView) findViewById(R.id.textView_date);
        tai_khoan = (TextView) findViewById(R.id.tai_khoan);
        buttonThu = (Button) findViewById(R.id.btn_thu);
        buttonChi = (Button) findViewById(R.id.btn_chi);
        chitietngay = (Button) findViewById(R.id.chi_tiet);
        date_list_view = (ListView) findViewById(R.id.detail_listView);
        truoc = (TextView) findViewById(R.id.textView_previous);
        sau = (TextView) findViewById(R.id.textView_next);

        truoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date myDate = null;
                try {
                    myDate = dateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date oneDayBefore = new Date(myDate.getTime() - 2);
                String previous_date = dateFormat.format(oneDayBefore);

                Intent intent = getIntent();
                ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.slide_right_in, R.anim.slide_right_out);
                intent.putExtra("date",previous_date);
                finish();
                startActivity(intent, options.toBundle());
            }
        });

        if (date.equals(date_today)){
            sau.setTextColor(Color.parseColor("#ffffff"));
        }
        else {
            sau.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date myDate = null;
                    try {
                        myDate = dateFormat.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date oneDayAfter = new Date(myDate.getTime() + 1 * 24 * 60 * 60 * 1000);
                    String next_date = dateFormat.format(oneDayAfter);

                    Intent intent = getIntent();
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.slide_left_in, R.anim.slide_left_out);
                    intent.putExtra("date",next_date);
                    finish();
                    startActivity(intent, options.toBundle());
                }
            });
        }

        String show_date = date;
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat showFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            show_date = showFormat.format(dataFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ngay.setText(show_date);
        updateListView();

        buttonThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View contentView = inflater.inflate(R.layout.add_payment_list, null);

                final Spinner spinner = (Spinner) contentView.findViewById(R.id.spinner_add);
                ArrayList<ThuKind> arrayList = new ArrayList<ThuKind>();
                arrayList.clear();
                arrayList = arrayList_thu;
                if (arrayList.size()>0) {
                    final CustomThuSpinnerAdapter customThuSpinnerAdapter = new CustomThuSpinnerAdapter(MainActivity.this,
                            R.layout.add_spinner_layout, arrayList);
                    spinner.setAdapter(customThuSpinnerAdapter);

                    final EditText input_name = (EditText) contentView.findViewById(R.id.editText_ten);
                    final EditText input_money = (EditText) contentView.findViewById(R.id.editText_sotien);
                    final EditText input_note = (EditText) contentView.findViewById(R.id.editText_ghichu);

                    builder.setTitle("Tiền Thu");
                    builder.setView(contentView)
                            // Add action buttons
                            .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    int kind_id = (int) customThuSpinnerAdapter.getItemId(spinner.getSelectedItemPosition());
                                    db.insertBook(input_name.getText().toString(),kind_id,Integer.parseInt(input_money.getText().toString()),date,input_note.getText().toString(),1);

                                    customMainListViewAdapter.clear();
                                    arrayBook.clear();
                                    arrayBook.addAll(db.getAllBookByDate(date));
                                    updateListView();
                                }
                            })
                            .setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Đã xảy ra lỗi",Toast.LENGTH_LONG).show();
                }
            }
        });


        buttonChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int payment_limit = Integer.parseInt(prefs.getString("payment_limit","0"));
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View contentView = inflater.inflate(R.layout.add_payment_list, null);

                final Spinner spinner = (Spinner) contentView.findViewById(R.id.spinner_add);
                ArrayList<ChiKind> arrayList = arrayList_chi;
                if (arrayList.size()>0) {
                    final CustomChiSpinnerAdapter customChiSpinnerAdapter = new CustomChiSpinnerAdapter(MainActivity.this,
                            R.layout.add_spinner_layout, arrayList);
                    spinner.setAdapter(customChiSpinnerAdapter);

                    final EditText input_name = (EditText) contentView.findViewById(R.id.editText_ten);
                    final EditText input_money = (EditText) contentView.findViewById(R.id.editText_sotien);
                    final EditText input_note = (EditText) contentView.findViewById(R.id.editText_ghichu);

                    builder.setTitle("Tiền Chi");
                    builder.setView(contentView)
                            // Add action buttons
                            .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    int kind_id = (int) customChiSpinnerAdapter.getItemId(spinner.getSelectedItemPosition());

                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
                                    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

                                    String month = "";

                                    try {
                                        Date dateType = formatter2.parse(date);
                                        String current_date = formatter.format(dateType);
                                        Date dateType_today = formatter2.parse(date_today);
                                        String current_today_date = formatter.format(dateType_today);

                                        if (current_date.equals(current_today_date)){
                                            month = formatter.format(dateType_today);
                                            int total_sum_in_month = db.getSumByBookIDByMonth(0,month);
                                            Log.i("bbb",total_sum_in_month+"     "+payment_limit);
                                            if (total_sum_in_month>payment_limit&&payment_limit!=0){
                                                int notifyID = 1;
                                                mBuilder =
                                                        new NotificationCompat.Builder(MainActivity.this)
                                                                .setSmallIcon(R.mipmap.ic_launcher)
                                                                .setContentTitle("Giới hạn chi")
                                                                .setContentText("Tiền chi tháng này của bạn đã nhiều hơn giới hạn")
                                                                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

                                                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

                                                inboxStyle.setBigContentTitle("Tiền chi của bạn đã nhiều hơn giới hạn");
                                                inboxStyle.addLine("Giới hạn: "+payment_limit+" VND");
                                                inboxStyle.addLine("Tiền chi tháng này: "+total_sum_in_month+" VND");
//              /*  String[] events = new String[5];
//                events[0] = "Image Processing";
//                events[1] = "Mathematical Sciences";
//                events[2] = "Artificial Intelligence";
//                events[3] = "Quantum Computing";
//                events[4] = "Analytical Physics";
//
//                for (int i = 0; i< events.length; i++) {
//                inboxStyle.addLine(events[i]);

                                                mBuilder.setStyle(inboxStyle);



                                                Intent resultIntent = new Intent(MainActivity.this, MonthDetailActivity.class);


                                                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                                                        0,
                                                        resultIntent,
                                                        PendingIntent.FLAG_UPDATE_CURRENT );
                                                mBuilder.setContentIntent(pendingIntent);

                                                mNotificationManager =
                                                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                                                mBuilder.setAutoCancel(true);
                                                mNotificationManager.notify(notifyID,mBuilder.build());
                                            }
                                        }

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    db.insertBook(input_name.getText().toString(), kind_id, Integer.parseInt(input_money.getText().toString()), date, input_note.getText().toString(), 0);

                                    customMainListViewAdapter.clear();
                                    arrayBook.clear();
                                    arrayBook.addAll(db.getAllBookByDate(date));
                                    updateListView();
                                }
                            })
                            .setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Đã xảy ra lỗi",Toast.LENGTH_LONG).show();
                }
            }
        });

        chitietngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,DateDetailActivity.class);
                i.putExtra("date",date);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.slide_left_in, R.anim.slide_left_out);
                startActivity(i,options.toBundle());
            }
        });

        date_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setMessage(customMainListViewAdapter.getItem(i).getNote())
                        .setTitle("Ghi chú");

                builder.show();
            }
        });

    }

    public void updateListView(){
        String tai_khoan_content = "Thêm vào danh sách";
        int tong_chi = db.getSumChi();
        int tong_thu = db.getSumThu();
        int tong = tong_thu - tong_chi;
        if (arrayBook.size()>0){
            tai_khoan_content = "Tài khoản: "+ df.format(tong) +" VND";
        }

        tai_khoan.setText(tai_khoan_content);
        customMainListViewAdapter = new CustomMainListViewAdapter(MainActivity.this,R.layout.date_listview_item,arrayBook);
        date_list_view.setAdapter(customMainListViewAdapter);
        customMainListViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_calendar:
                Intent intent = new Intent(MainActivity.this,CalendarActivity.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.slide_left_in, R.anim.slide_left_out);
                startActivity(intent, options.toBundle());
                return true;
            case R.id.menu_setting:
                Intent intent1 = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public class CustomMainListViewAdapter extends ArrayAdapter<Book> {

        Context context;
        int layout;
        ArrayList<Book> arrayList;
        DBHelper db;

        public CustomMainListViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Book> objects) {
            super(context, resource, objects);
            this.context = context;
            this.layout = resource;
            this.arrayList = objects;
            db = new DBHelper(context);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Nullable
        @Override
        public Book getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return arrayList.get(position).getId();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Book book = arrayList.get(position);
            final int pos = position;
            TextView name_detail;
            TextView money_detail;
            final ImageView image_icon_detail;
            ImageView image_edit_detail;
            View v = convertView;

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            v = mInflater.inflate(layout, null);
            name_detail = (TextView) v.findViewById(R.id.name_detail);
            money_detail = (TextView) v.findViewById(R.id.money_detail);
            image_icon_detail = (ImageView) v.findViewById(R.id.image_icon_detail);
            image_edit_detail = (ImageView) v.findViewById(R.id.image_edit_detail);

            image_icon_detail.setImageResource(book.getImg());

            name_detail.setText(book.getName());
            money_detail.setText(df.format(book.getMoney())+" VND");

            try {
                image_edit_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.image_edit_detail:
                                PopupMenu popup = new PopupMenu(context, v);
                                popup.getMenuInflater().inflate(R.menu.menu_date_listview,
                                        popup.getMenu());
                                popup.show();
                                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {

                                        switch (item.getItemId()) {
                                            case R.id.edit:

                                                AlertDialog.Builder builder_edit = new AlertDialog.Builder(context);

                                                LayoutInflater inflater_edit = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                                View contentView_edit = inflater_edit.inflate(R.layout.add_payment_list, null);

                                                final Spinner spinner = (Spinner) contentView_edit.findViewById(R.id.spinner_add);

                                                if (book.getBook_type()==1) {
                                                    final ArrayList<ThuKind> arrayList_edit = arrayList_thu;
                                                    final CustomThuSpinnerAdapter customSpinnerAdapter = new CustomThuSpinnerAdapter(context,
                                                            R.layout.add_spinner_layout, arrayList_edit);
                                                    spinner.setAdapter(customSpinnerAdapter);
                                                    spinner.setSelection(book.getKind_id()-1);

                                                    final EditText input_name = (EditText) contentView_edit.findViewById(R.id.editText_ten);
                                                    final EditText input_money = (EditText) contentView_edit.findViewById(R.id.editText_sotien);
                                                    final EditText input_note = (EditText) contentView_edit.findViewById(R.id.editText_ghichu);

                                                    input_name.setText(book.getName());
                                                    input_money.setText(""+book.getMoney());
                                                    input_note.setText(book.getNote());

                                                    builder_edit.setTitle("Chỉnh sửa");
                                                    builder_edit.setView(contentView_edit)
                                                            // Add action buttons
                                                            .setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    int kind_id = (int) customSpinnerAdapter.getItemId(spinner.getSelectedItemPosition());
                                                                    db.updateBook(book.getId(),input_name.getText().toString(), kind_id, Integer.parseInt(input_money.getText().toString()), date, input_note.getText().toString(), book.getBook_type());
                                                                    clear();
                                                                    addAll(db.getAllBookByDate(date));
                                                                    updateListView();
                                                                }
                                                            })
                                                            .setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    dialog.cancel();
                                                                }
                                                            });
                                                    builder_edit.show();
                                                }
                                                else if (book.getBook_type()==0){
                                                    final ArrayList<ChiKind> arrayList_edit = arrayList_chi;
                                                    final CustomChiSpinnerAdapter customSpinnerAdapter = new CustomChiSpinnerAdapter(context,
                                                            R.layout.add_spinner_layout, arrayList_edit);
                                                    spinner.setAdapter(customSpinnerAdapter);
                                                    spinner.setSelection(book.getKind_id()-1);

                                                    final EditText input_name = (EditText) contentView_edit.findViewById(R.id.editText_ten);
                                                    final EditText input_money = (EditText) contentView_edit.findViewById(R.id.editText_sotien);
                                                    final EditText input_note = (EditText) contentView_edit.findViewById(R.id.editText_ghichu);

                                                    input_name.setText(book.getName());
                                                    input_money.setText(""+book.getMoney());
                                                    input_note.setText(book.getNote());

                                                    builder_edit.setTitle("Chỉnh sửa");
                                                    builder_edit.setView(contentView_edit)
                                                            // Add action buttons
                                                            .setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    int kind_id = (int) customSpinnerAdapter.getItemId(spinner.getSelectedItemPosition());
                                                                    db.updateBook(book.getId(),input_name.getText().toString(), kind_id, Integer.parseInt(input_money.getText().toString()), date, input_note.getText().toString(), book.getBook_type());
                                                                    clear();
                                                                    addAll(db.getAllBookByDate(date));
                                                                    updateListView();
                                                                }
                                                            })
                                                            .setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    dialog.cancel();
                                                                }
                                                            });
                                                    builder_edit.show();
                                                }

                                                break;
                                            case R.id.remove:
                                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                builder.setMessage("Bạn muốn xoá ghi chú này?")
                                                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                db.deleteBook(arrayList.get(pos).getId());
                                                                clear();
                                                                addAll(db.getAllBookByDate(date));
                                                                updateListView();
                                                            }
                                                        })
                                                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                builder.show();
                                                break;

                                            default:
                                                break;
                                        }

                                        return true;
                                    }
                                });

                                break;

                            default:
                                break;
                        }


                    }
                });

            } catch (Exception e) {

                e.printStackTrace();
            }
            return v;
        }
    }

    public static class switchButtonListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TAG", "test");
        }

    }
}
