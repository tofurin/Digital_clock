package com.example.digitalclock;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView Texttime;

    Timer timer;

    Handler handler = new Handler();

    Button setting_button;
    Button exit_btn;

    View bottomSheet;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(Color.BLACK);

        //各buttonのid取得
        Texttime = findViewById(R.id.Texttime);
        setting_button = findViewById(R.id.setting_button);
        exit_btn = findViewById(R.id.exit_btn);
        bottomSheet = findViewById(R.id.bottomSheet);

        //各buttonのリスナークラス登録
        setting_button.setOnClickListener(new setting_button_listener());
        exit_btn.setOnClickListener(new exit_btn_listener());

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                //現在時刻を取得
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                final String currenttime = sdf.format(new Date());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Texttime.setText(currenttime);
                    }

                });
            }
        }, 0, 1000);
    }

    public class setting_button_listener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            bottomSheet.setVisibility(View.VISIBLE);
        }
    }

    public class exit_btn_listener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            bottomSheet.setVisibility(View.GONE);
        }
    }
}