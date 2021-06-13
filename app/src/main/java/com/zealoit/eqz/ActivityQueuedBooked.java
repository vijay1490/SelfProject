package com.zealoit.eqz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zealoit.eqz.Utils.FontChangeCrawler;

public class ActivityQueuedBooked extends AppCompatActivity {
    TextView txt_queue_number , txt_current_queue_number , txt_wait_queue;
    int queuecurrent , queuewaiting ;
    String waitingQueue , currentqueue;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queued_booked);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "CenturyGothic.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        txt_queue_number = findViewById(R.id.txt_queue_number);
        String queuenumber = getIntent().getStringExtra("QUEUENUMBER");
        txt_queue_number.setText(queuenumber);
        txt_current_queue_number = findViewById(R.id.txt_current_queue_number);
       // queuecurrent = getIntent().getIntExtra("currentQueue",0);
        currentqueue = getIntent().getStringExtra("currentQueue");
        txt_current_queue_number.setText(currentqueue);
        txt_wait_queue = findViewById(R.id.txt_wait_queue);
       // queuewaiting = getIntent().getIntExtra("waitingQueue",0);
      //  waitingQueue = Integer.toString(queuewaiting);
      //  txt_wait_queue.setText(waitingQueue);
    }


    public void ok(View view) {
        startActivity(new Intent(ActivityQueuedBooked.this,HomeActivity.class));
        finish();
    }
}
