package com.tnta.t3h.paymentbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.tnta.t3h.paymentbook.model.Book;
import com.tnta.t3h.paymentbook.model.ChiKind;
import com.tnta.t3h.paymentbook.model.ThuKind;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by vudinhai on 5/20/17.
 */

public class DBHelper {

    String DATABASE_NAME = "paymentbook.sqlite";
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase db = null;

    Context context;

    int[] img_array_chi = new int[]{R.drawable.anuong, R.drawable.sinhhoat, R.drawable.dilai, R.drawable.giaitri, R.drawable.chi_khac};
    int[] img_array_thu = new int[]{R.drawable.luong, R.drawable.tietkiem, R.drawable.thu_khac};

    public DBHelper(Context context) {
        this.context = context;
        processSQLite();
    }

    private void processSQLite() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()){
            try{
                CopyDatabaseFromAsset();

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void CopyDatabaseFromAsset() {
        try{
            InputStream databaseInputStream = context.getAssets().open(DATABASE_NAME);

            String outputStream = getPathDatabaseSystem();

            File file = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!file.exists()){
                file.mkdir();
            }

            OutputStream databaseOutputStream = new FileOutputStream(outputStream);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = databaseInputStream.read(buffer)) > 0){
                databaseOutputStream.write(buffer,0,length);
            }


            databaseOutputStream.flush();
            databaseOutputStream.close();
            databaseInputStream.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private String getPathDatabaseSystem() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }


    ArrayList<Book> getAllBookByDate(String date){
        ArrayList<Book> arr = new ArrayList<>();

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery("SELECT * FROM book WHERE book.date = '"+date+"' ORDER BY id", null);

        while (cursor.moveToNext()){
            int img_book = 0;
            if (cursor.getInt(6)==1) {
                img_book = img_array_thu[cursor.getInt(2)-1];
            }
            else {
                img_book = img_array_chi[cursor.getInt(2)-1];
            }
            arr.add(new Book(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), img_book));
        }

        return  arr;
    }

    ArrayList<ThuKind> getKindThu(){
        ArrayList<ThuKind> arr = new ArrayList<>();

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery("SELECT * FROM thu_kind", null);

        while (cursor.moveToNext()){
            arr.add(new ThuKind(cursor.getInt(0),cursor.getString(1),img_array_thu[cursor.getInt(0)-1]));
        }

        return  arr;
    }

    ArrayList<ChiKind> getKindChi(){
        ArrayList<ChiKind> arr = new ArrayList<>();

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery("SELECT * FROM chi_kind", null);

        while (cursor.moveToNext()){
            arr.add(new ChiKind(cursor.getInt(0),cursor.getString(1),img_array_chi[cursor.getInt(0)-1]));
        }

        return  arr;
    }

    ArrayList<Book> getAllChiByDate(String date){
        ArrayList<Book> arr = new ArrayList<>();

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery("SELECT * FROM book WHERE date = '"+date+"'", null);

        while (cursor.moveToNext()){
            int img_book = 0;
            if (cursor.getInt(6)==1) {
                img_book = img_array_thu[cursor.getInt(2)-1];
            }
            else {
                img_book = img_array_chi[cursor.getInt(2)-1];
            }
            arr.add(new Book(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6),img_book));
        }

        return  arr;
    }

    int getSumChi(){
        int sum = 0;

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery("SELECT SUM(money) FROM book WHERE book_type = '0'", null);

        while (cursor.moveToNext()){
            sum = cursor.getInt(0);
        }

        return  sum;
    }

    int getSumThu(){
        int sum = 0;

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery("SELECT SUM(money) FROM book WHERE book_type = '1'", null);

        while (cursor.moveToNext()){
            sum = cursor.getInt(0);
        }

        return  sum;
    }

    int getSumByBookIDByDate(int book_id, String date){
        //book_id: 0 = chi, 1 = thu
        int sum = 0;

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery("SELECT SUM(money) FROM book WHERE book_type = '"+book_id+"' AND date = '"+date+"'", null);

        while (cursor.moveToNext()){
            sum = cursor.getInt(0);
        }

        return sum;
    }

    int getSumByKindIDByBookIDByDate(int kind_id, int book_id, String date){

        //kind id
        //cho thu: 1 = luong, 2 = tiet kiem
        //cho chi: 1 = an uong, 2 = sinh hoat, 3 = di lai, 4 = giai tri, 5 = khac
        int sum = 0;

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery("SELECT SUM(money) FROM book WHERE kind_id = '"+kind_id+"' AND book_type = '"+book_id+"' AND date = '"+date+"'", null);

        while (cursor.moveToNext()){
            sum = cursor.getInt(0);
        }

        return sum;
    }

    int getSumByBookIDByMonth(int book_id, String month){
        //book_id: 0 = chi, 1 = thu
        int sum = 0;

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery("SELECT SUM(money) FROM book WHERE book_type = '"+book_id+"' AND date LIKE '"+month+"-%'", null);

        while (cursor.moveToNext()){
            sum = cursor.getInt(0);
        }

        return sum;
    }

    int getSumByKindIDByBookIDByMonth(int kind_id, int book_id, String month){

        //kind id
        //cho thu: 1 = luong, 2 = tiet kiem
        //cho chi: 1 = an uong, 2 = sinh hoat, 3 = di lai, 4 = giai tri, 5 = khac
        int sum = 0;

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery("SELECT SUM(money) FROM book WHERE kind_id = '"+kind_id+"' AND book_type = '"+book_id+"' AND date LIKE '"+month+"-%'", null);

        while (cursor.moveToNext()){
            sum = cursor.getInt(0);
        }

        return sum;
    }

    void insertBook(String name, int kind_id, int money, String date, String note, int book_type){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("kind_id",kind_id);
        contentValues.put("money",money);
        contentValues.put("date",date);
        contentValues.put("note",note);
        contentValues.put("book_type",book_type);

        if(db.insert("book",null,contentValues) <= 0){
            Toast.makeText(context, "Thử lại", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteBook(int id){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);
        db.delete("book","id = "+id,null);
    }

    void updateBook(int id, String name, int kind_id, int money, String date, String note, int book_type){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("name",name);
        contentValues.put("kind_id",kind_id);
        contentValues.put("money",money);
        contentValues.put("date",date);
        contentValues.put("note",note);
        contentValues.put("book_type",book_type);

        db.update("book",contentValues,"id=?",new String[]{id+""});


    }


}
