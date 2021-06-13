package com.zealoit.eqz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zealoit.eqz.Utils.CheckNetwork;
import com.zealoit.eqz.Utils.Constants;
import com.zealoit.eqz.Utils.MyFunctions;

public class RegisterSuccessActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registersucces);
        if(MyFunctions.getSharedPrefs(RegisterSuccessActivity.this, Constants.IS_LOGIN,false)) {
            if (MyFunctions.getSharedPrefs(RegisterSuccessActivity.this, "userid", "").length() > 0) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public void getstart(View view) {
        startActivity(new Intent(RegisterSuccessActivity.this,LoginActivity.class));
        finish();
    }
}
