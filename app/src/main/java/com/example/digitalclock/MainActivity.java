package com.example.digitalclock;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
    Button exit_btn, exit_btn_trans;

    Button bg_black_btn, bg_white_btn,tx_green_btn,tx_white_btn,tx_red_btn,tx_blue_btn;

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
        exit_btn_trans = findViewById(R.id.exit_btn_trans);
        bottomSheet = findViewById(R.id.bottomSheet);
        bg_black_btn=findViewById(R.id.bg_black_btn);
        bg_white_btn=findViewById(R.id.bg_white_btn);
        tx_green_btn=findViewById(R.id.tx_green_btn);
        tx_white_btn=findViewById(R.id.tx_white_btn);
        tx_red_btn=findViewById(R.id.tx_red_btn);
        tx_blue_btn=findViewById(R.id.tx_blue_btn);

        //各buttonのリスナークラス登録
        setting_button.setOnClickListener(new setting_button_listener());
        exit_btn.setOnClickListener(new exit_btn_listener());
        exit_btn_trans.setOnClickListener(new exit_btn_trans_listener());

        SharedPreferences prefs = getSharedPreferences("Digital_clock_set", Context.MODE_PRIVATE);

        if(prefs.contains("bg_color") != true){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("bg_color","black");
            editor.apply();
        }


        if(prefs.contains("tx_color") != true) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("tx_color", "green");
            editor.apply();
        }

        if(prefs.contains("bg_color_btn") != true){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("bg_color_btn", "dark");
            editor.apply();
        }

        //bgにカラー設定適用
        String bg_color_name = prefs.getString("bg_color","black");
        int bg_color_id = getResources().getIdentifier(bg_color_name,"color",getPackageName());
        Texttime.setBackgroundColor(getResources().getColor(bg_color_id,getTheme()));

        //txにカラー設定適用
        String tx_color_name = prefs.getString("tx_color","green");
        int tx_color_id = getResources().getIdentifier(tx_color_name, "color",getPackageName());
        Texttime.setTextColor(getResources().getColor(tx_color_id, getTheme()));

        //setting_btn設定適用
        String bg_color_btn_name = prefs.getString("bg_color_btn", "dark");
        if(bg_color_btn_name == "dark"){
            int background_resource_id = R.drawable.round_button_bg;
            setting_button.setBackgroundResource(background_resource_id);
        } else if (bg_color_btn_name == "light"){
            int background_resource_id = R.drawable.round_button_bg_in_light;
            setting_button.setBackgroundResource(background_resource_id);
        }



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

        bg_black_btn.setOnClickListener(new bg_black_btn_listener(){
            @Override
            public void onClick(View view){
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("bg_color","black");
                editor.putString("bg_color_btn", "dark");
                editor.apply();
                Texttime.setBackgroundResource(R.color.black);
                int background_resource_id = R.drawable.round_button_bg;
                setting_button.setBackgroundResource(background_resource_id);
            }
        });
        bg_white_btn.setOnClickListener(new bg_white_btn_listener(){
            @Override
            public void onClick(View view){
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("bg_color","white");
                editor.putString("bg_color_btn", "light");
                editor.apply();
                Texttime.setBackgroundResource(R.color.white);
                int background_resource_id = R.drawable.round_button_bg_in_light;
                setting_button.setBackgroundResource(background_resource_id);
            }
        });
        tx_green_btn.setOnClickListener(new tx_green_btn_listener(){
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("tx_color", "green");
                editor.apply();
                Texttime.setTextColor(getResources().getColor(R.color.green,getTheme()));
            }
        });
        tx_white_btn.setOnClickListener(new tx_white_btn_listener(){
            @Override
            public void onClick(View view){
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("tx_color","white");
                editor.apply();
                Texttime.setTextColor(getResources().getColor(R.color.white,getTheme()));
            }
        });
        tx_red_btn.setOnClickListener(new tx_red_btn_listener(){
            @Override
            public void onClick(View view){
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("tx_color","red");
                editor.apply();
                Texttime.setTextColor(getResources().getColor(R.color.red,getTheme()));
            }
        });
        tx_blue_btn.setOnClickListener(new tx_blue_btn_listener(){
            @Override
            public void onClick(View view){
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("tx_color","blue");
                editor.apply();
                Texttime.setTextColor(getResources().getColor(R.color.blue,getTheme()));
            }
        });
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

    public class exit_btn_trans_listener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            bottomSheet.setVisibility(View.GONE);
        }
    }

    static class bg_black_btn_listener implements View.OnClickListener{

        @Override
        public void onClick(View view){

        }
    }
    static public class bg_white_btn_listener implements View.OnClickListener{
        @Override
        public void onClick(View view){

        }
    }

    static public class tx_green_btn_listener implements View.OnClickListener{
        @Override
        public void onClick(View view){

        }
    }

    static public class tx_white_btn_listener implements View.OnClickListener{
        @Override
        public void onClick(View view){

        }
    }

    static public class tx_red_btn_listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
        }
    }
    static public class tx_blue_btn_listener implements View.OnClickListener{
        @Override
        public void onClick(View view) {

        }
    }
}
