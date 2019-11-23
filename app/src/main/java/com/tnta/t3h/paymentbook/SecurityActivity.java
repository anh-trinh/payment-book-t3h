package com.tnta.t3h.paymentbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecurityActivity extends AppCompatActivity {

    String fileSharePreferences = "USER";
    EditText editText_pass;
    Button button_security;
    TextView textView_security;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(fileSharePreferences,MODE_PRIVATE);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        boolean firstRunCheck = sharedPreferences.getBoolean("firstRun",true);
        if (firstRunCheck){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstRun",false);
            editor.commit();
            Intent i = new Intent(SecurityActivity.this,SlashScreenActivity.class);
            startActivity(i);
        }

        setContentView(R.layout.activity_security);

        editText_pass = (EditText) findViewById(R.id.editText_checkpass);
        button_security = (Button) findViewById(R.id.button_security);
        textView_security = (TextView) findViewById(R.id.textView_security);

        if (prefs.getBoolean("pass_security",false)){
            final String saved_pass = prefs.getString("password",null);
            Log.i("saved_pass",saved_pass);

            button_security.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editText_pass.getText().toString().equals(saved_pass)){
                        Intent intent = new Intent(SecurityActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        textView_security.setText("Mật khẩu không chính xác!");
                        textView_security.setTextColor(Color.RED);
                    }
                }
            });
        }
        else {
            Intent intent = new Intent(SecurityActivity.this,MainActivity.class);
            startActivity(intent);
        }


    }
}
