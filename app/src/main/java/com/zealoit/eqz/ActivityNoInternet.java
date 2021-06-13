package com.zealoit.eqz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zealoit.eqz.Utils.CheckNetwork;

public class ActivityNoInternet extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_no_internet);

    }

    public void tryagain(View view) {
        if(CheckNetwork.isInternetAvailable(ActivityNoInternet.this)) //returns true if internet available
        {
            Intent intent = new Intent();
            intent.setClass(ActivityNoInternet.this, SplashScreenActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Please wait till internet not connected", Toast.LENGTH_SHORT).show();
        }
    }

}
