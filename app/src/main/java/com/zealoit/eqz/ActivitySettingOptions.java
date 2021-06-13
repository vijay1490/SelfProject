package com.zealoit.eqz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.zealoit.eqz.Utils.Constants;
import com.zealoit.eqz.Utils.FontChangeCrawler;
import com.zealoit.eqz.Utils.MyFunctions;

public class ActivitySettingOptions extends AppCompatActivity {

    Switch lock , notification;

    String LOCKON;

    @Override
    public void onCreate(Bundle savedInstancestate){
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_setting_option);
        lock=findViewById(R.id.switch1);
        notification=findViewById(R.id.switch2);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "CenturyGothic.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        LOCKON = MyFunctions.getSharedPrefs(ActivitySettingOptions.this, Constants.LOCKON,"");

        if (LOCKON.equals("ON")){
            lock.setChecked(true);
        }else {
            lock.setChecked(false);
        }

        lock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked== true){
                    startActivity(new Intent(ActivitySettingOptions.this,SetPinActivity.class));
                    MyFunctions.setSharedPrefs(ActivitySettingOptions.this, Constants.LOCKON, "ON");
                    finish();
                }else if (isChecked==false){
                    MyFunctions.setSharedPrefs(ActivitySettingOptions.this, Constants.LOCKON, "OFF");
                }
            }
        });

        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked== true){
                   // startActivity(new Intent(ActivitySettingOptions.this,SetPinActivity.class));
                  //  finish();
                }
            }
        });
    }

    public void back(View view) {
        Intent intent = new Intent();
        intent.setClass(ActivitySettingOptions.this, ActivityAccountList.class);
        startActivity(intent);
    }
}
