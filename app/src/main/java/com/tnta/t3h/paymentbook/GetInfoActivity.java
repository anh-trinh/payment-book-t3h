package com.tnta.t3h.paymentbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class GetInfoActivity extends AppCompatActivity {

    String fileSharePreferences = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);

        SharedPreferences sharedPreferences = getSharedPreferences(fileSharePreferences,MODE_PRIVATE);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final SharedPreferences.Editor editor2 = prefs.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(GetInfoActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = GetInfoActivity.this.getLayoutInflater();

        View view = inflater.inflate(R.layout.name_request, null);
        final TextView name = (TextView) view.findViewById(R.id.nhap_ten);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                .setTitle("Tên bạn là gì?")
                // Add action buttons
                .setNeutralButton("Xong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        editor2.putString("name",name.getText().toString());
                        editor2.commit();
                        // Use the Builder class for convenient dialog construction
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(GetInfoActivity.this);
                        builder2.setTitle("Bạn có muốn tạo mật khẩu?")
                                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        AlertDialog.Builder builder3 = new AlertDialog.Builder(GetInfoActivity.this);
                                        // Get the layout inflater
                                        LayoutInflater inflater = GetInfoActivity.this.getLayoutInflater();

                                        View view1 = inflater.inflate(R.layout.password_request, null);

                                        // Inflate and set the layout for the dialog
                                        // Pass null as the parent view because its going in the dialog layout
                                        builder3.setView(view1)
                                                .setTitle("Nhập mật khẩu")
                                                // Add action buttons
                                                .setNegativeButton("Xong", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        editor2.putString("password",name.getText().toString());
                                                        editor2.commit();
                                                    }
                                                });
                                        builder3.show();
                                        Intent intent = new Intent(GetInfoActivity.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(GetInfoActivity.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                        // Create the AlertDialog object and return it
                        builder2.show();
                    }
                });
        builder.show();
    }
}
