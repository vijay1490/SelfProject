package com.zealoit.eqz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NoDataActivity extends AppCompatActivity {
    TextView txt_message;
    String message;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_no_data);
        txt_message = findViewById(R.id.txt_message);
        message = getIntent().getStringExtra("message");
        txt_message.setText(message);
    }

    public void gohome(View view) {
        startActivity(new Intent(NoDataActivity.this,HomeActivity.class));
        finish();
    }

    public void baktoprevious(View view) {
        startActivity(new Intent(NoDataActivity.this,ActivitySaloan.class));
        finish();
    }

}
