package com.example.digitalclock;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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

    Button bg_black_btn, bg_white_btn,tx_green_btn,tx_white_btn,tx_red_btn,tx_blue_btn, tx_black_btn;

    View bottomSheet;

    SharedPreferences prefs;

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
        tx_black_btn = findViewById(R.id.tx_black_btn);


        //各buttonのリスナークラス登録
        setting_button.setOnClickListener(new setting_button_listener());
        exit_btn.setOnClickListener(new exit_btn_listener());

        //画面幅を取得
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screen_width = displayMetrics.widthPixels;
        int screen_height = displayMetrics.heightPixels;


        prefs = getSharedPreferences("Digital_clock_set", Context.MODE_PRIVATE);


        if(prefs.contains("bg_color") == false){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("bg_color","black");
            editor.apply();
        }


        if(prefs.contains("tx_color") == false) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("tx_color", "green");
            editor.apply();
        }

        if(prefs.contains("bg_color_btn") == false){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("bg_color_btn", "dark");
            editor.apply();
        }

        if(prefs.contains("screen_width") == false) {
            SharedPreferences.Editor editor = prefs.edit();
            float textSize_width = (float)screen_width * 0.22f;
            editor.putFloat("screen_width", textSize_width);
            editor.apply();
        }

        if(prefs.contains("screen_height") == false){
            SharedPreferences.Editor editor = prefs.edit();
            float textSize_height = (float)screen_height * 0.25f;
            editor.putFloat("screen_height", textSize_height);
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
        if(bg_color_btn_name.equals("dark")){
            int background_resource_id = R.drawable.round_button_bg;
            setting_button.setBackgroundResource(background_resource_id);
            setting_button.setTextColor(getResources().getColor(R.color.black, getTheme()));
            getWindow().setStatusBarColor(getResources().getColor(R.color.black, getTheme()));
        } else if (bg_color_btn_name.equals("light")){
            int background_resource_id = R.drawable.round_button_bg_in_light;
            setting_button.setBackgroundResource(background_resource_id);
            setting_button.setTextColor(getResources().getColor(R.color.white, getTheme()));
            getWindow().setStatusBarColor(getResources().getColor(R.color.white, getTheme()));
        }

        //テキストサイズ設定適用
        adjust(getResources().getConfiguration(),findViewById(R.id.Texttime));

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                //現在時刻を取得
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                final String currenttime = sdf.format(new Date());

                handler.post(new Runnable() {
                    @Override
                    public void run() {Texttime.setText(currenttime);
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
                setting_button.setTextColor(getResources().getColor(R.color.black, getTheme()));
                getWindow().setStatusBarColor(getResources().getColor(R.color.black, getTheme()));
                tx_black_btn.setVisibility(View.INVISIBLE);
                tx_black_btn.setEnabled(false);
                tx_white_btn.setVisibility(View.VISIBLE);
                tx_white_btn.setEnabled(true);
                String color="black";
                if(color.equals(prefs.getString("tx_color","black"))){
                    editor.putString("tx_color","white");
                    editor.apply();
                    Texttime.setTextColor(getResources().getColor(R.color.white,getTheme()));
                }


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
                setting_button.setTextColor(getResources().getColor(R.color.white, getTheme()));
                getWindow().setStatusBarColor(getResources().getColor(R.color.white, getTheme()));
                tx_white_btn.setVisibility(View.INVISIBLE);
                tx_white_btn.setEnabled(false);
                tx_black_btn.setVisibility(View.VISIBLE);
                tx_black_btn.setEnabled(true);
                String color = "white";
                if(color.equals(prefs.getString("tx_color","white"))){
                    editor.putString("tx_color","black");
                    editor.apply();
                    Texttime.setTextColor(getResources().getColor(R.color.black,getTheme()));
                }
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

        tx_black_btn.setOnClickListener(new tx_black_btn_listener(){
            @Override
            public void onClick(View view){
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("tx_color","black");
                editor.apply();
                Texttime.setTextColor(getResources().getColor(R.color.black,getTheme()));
            }
        });

        setting_button.setOnClickListener(new setting_button_listener(){
            @Override
            public void onClick(View view){
                bottomSheet.setVisibility(View.VISIBLE);
            }
        });

        exit_btn.setOnClickListener(new exit_btn_listener() {
            @Override
            public void onClick(View view) {
                bottomSheet.setVisibility(view.GONE);
            }
        });
        exit_btn_trans.setOnClickListener(new exit_btn_trans_listener(){
            @Override
            public void onClick(View view) {
                bottomSheet.setVisibility(View.GONE);
            }
        });
    }

    public void onConfigurationChanged(Configuration newConfig, SharedPreferences prefs){
        super.onConfigurationChanged(newConfig);
        adjust(newConfig,findViewById(R.id.Texttime));
    }

    private void adjust(Configuration newConfig, TextView Texttime){
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            float text_size_height = prefs.getFloat("screen_height",100f);
            Texttime.setTextSize(TypedValue.COMPLEX_UNIT_PX, text_size_height);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            float text_size_width = prefs.getFloat("screen_width", 70f);
            Texttime.setTextSize(TypedValue.COMPLEX_UNIT_PX, text_size_width);
        }
    }

    static class setting_button_listener implements View.OnClickListener{
        @Override
        public void onClick(View view){
        }
    }

    static class exit_btn_listener implements View.OnClickListener{
        @Override
        public void onClick(View view){
        }
    }

    static class exit_btn_trans_listener implements View.OnClickListener{
        @Override
        public void onClick(View view){
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

    static public class tx_black_btn_listener implements View.OnClickListener{
        @Override
        public void onClick(View view) {

        }
    }
}
