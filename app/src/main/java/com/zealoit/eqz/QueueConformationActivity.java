package com.zealoit.eqz;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zealoit.eqz.Utils.Constants;
import com.zealoit.eqz.Utils.FontChangeCrawler;
import com.zealoit.eqz.Utils.MyFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.zealoit.eqz.Utils.Constants.NGROK_BASE_URL;

public class QueueConformationActivity extends AppCompatActivity {

    TextView txt_queue_time , txt_queue_number;
    RequestQueue requestQueue;
    RecyclerView rv_oldQueue;
    int customerId , providerId;
    LinearLayout camcelqueue;
    String status_code, userid, mobileno, quser, queueNumber ,  AuthorizationToken , servicetype_name, servicetype_id, servicetype_icon, servicegroup_id, status, created_on, statusMessage, userMessage, internalMessage, created_by, particpantid, user_email, user_contact_no;
    private String reurl = NGROK_BASE_URL;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_queue_conformation);
        String Token_Authorization = MyFunctions.getSharedPrefs(QueueConformationActivity.this, Constants.USERTYPE, "");
        userid = MyFunctions.getSharedPrefs(QueueConformationActivity.this, Constants.USER_ID, "");
        String CUST = MyFunctions.getSharedPrefs(QueueConformationActivity.this,Constants.CUSTOMERID,"");
        customerId = Integer.parseInt(CUST);
        AuthorizationToken = Token_Authorization;
        txt_queue_time = findViewById(R.id.txt_queue_time);
        txt_queue_number = findViewById(R.id.txt_queue_number);
        queueNumber = getIntent().getStringExtra("queueNumber");
        txt_queue_number.setText(queueNumber);
        camcelqueue = findViewById(R.id.lin_canel_queue);
        requestQueue = Volley.newRequestQueue(QueueConformationActivity.this);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "CenturyGothic.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        camcelqueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Idprovider = MyFunctions.getSharedPrefs(QueueConformationActivity.this, Constants.PROVIDERID, "");
                providerId = Integer.parseInt(Idprovider);
                JSONObject object = new JSONObject();
                try {
                    //input your API parameters
                    object.put("queueNumber",queueNumber);
                    object.put("providerId",providerId);
                    object.put("customerId",customerId);
                    object.put("userType","1");

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        reurl+"Queue/CancelQueue?APIKEY=15JRAKYTGQMXTH967COKVV27F" , object,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d("TAG", response.toString());
                                    status_code = response.getString("statusCode");
                                    if(status_code.equals("225")) {

                                        statusMessage = (String) response.get("userMessage");
                                        System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                                        statusMessage = (String) response.get("statusMessage");
                                        System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                                        userMessage = (String) response.get("userMessage");
                                        System.out.println("Check userMessage of Login Activity:" + userMessage);

                                        internalMessage = (String) response.get("internalMessage");
                                        System.out.println("Check Customer_Email of Login Activity:" + internalMessage);

                                        JSONObject user = response.getJSONObject("serviceData");

                                        String queueNumber = (String) user.get("queueNumber");

                                        int providerId = (Integer) user.get("providerId");

                                        int customerId = (Integer) user.get("customerId");

                                        int userType = (int) user.get("userType");

                                        startActivity(new Intent(QueueConformationActivity.this,HomeActivity.class));
                                        finish();
                                    }else{
                                        userMessage = (String) response.get("userMessage");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(QueueConformationActivity.this);
                                        builder.setTitle("USER MESSAGE");
                                        builder.setMessage(userMessage);
                                        builder.setCancelable(true);
                                        final AlertDialog closedialog= builder.create();
                                        closedialog.show();

                                        final Timer timer2 = new Timer();
                                        timer2.schedule(new TimerTask() {
                                            public void run() {
                                                closedialog.dismiss();
                                                timer2.cancel(); //this will cancel the timer of the system
                                            }
                                        }, 3000); // the timer will count 5 seconds....

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("TAG", "Error: " + error.getMessage());
//                        pDialog.hide();
                    }
                }) {

                    //This is for Headers If You Needed
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        //  params.put("Content-Type", "application/json; charset=UTF-8");
                        params.put("Authorization", "Bearer " + AuthorizationToken);
                        return params;
                    }

                };
                requestQueue.add(jsonObjReq);
                System.out.println("Businessstatus" + object.toString());
            }
        });


    }

    public void gohomeconf(View view) {

    }

    public void cancel(View view) {
        startActivity(new Intent(QueueConformationActivity.this,HomeActivity.class));
        finish();
    }

}
