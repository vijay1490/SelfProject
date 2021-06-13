package com.zealoit.eqz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.zealoit.eqz.Adapter.OldQueueAdapter;
import com.zealoit.eqz.List.OldQueueList;
import com.zealoit.eqz.Utils.CheckNetwork;
import com.zealoit.eqz.Utils.Constants;
import com.zealoit.eqz.Utils.MyFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.zealoit.eqz.Utils.Constants.NGROK_BASE_URL;

public class SplashScreenActivity extends AppCompatActivity {

    Animation animationUptoDown;
    ImageView imageView;
    String LOCKON;
    TextView txt_versionname;
    private Thread mSplashThread;
    RequestQueue requestQueue;
    String status_code, version, storelink , forcedownload , statusMessage, userMessage, internalMessage, AuthorizationToken, VersionNumber;
    private String reurl = NGROK_BASE_URL;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        txt_versionname = (TextView)findViewById(R.id.txt_versionname);
        int versionCode = BuildConfig.VERSION_CODE;
        VersionNumber = Integer.toString(versionCode);
        // System.out.println("dfsjijdf"+VersionNumber);
        txt_versionname.setText("VERSION"+"\t"+VersionNumber);
        requestQueue = Volley.newRequestQueue(SplashScreenActivity.this);
        String Token_Authorization = MyFunctions.getSharedPrefs(SplashScreenActivity.this, Constants.USERTYPE, "");
        AuthorizationToken = Token_Authorization;
        if(CheckNetwork.isInternetAvailable(SplashScreenActivity.this)) //returns true if internet available
        {
            FetchAppInfo();
        }
        else
        {
            Intent intent = new Intent();
            intent.setClass(SplashScreenActivity.this, ActivityNoInternet.class);
            startActivity(intent);
        }

        imageView = (ImageView) findViewById(R.id.imageView2); // Declare an imageView to show the animation.
        // imageview = (ImageView) findViewById(R.id.imageView3);
//        imageview.setVisibility(View.VISIBLE);
        animationUptoDown = AnimationUtils.loadAnimation(SplashScreenActivity.this,R.anim.zoom_out);
        //  animationDownToUp = AnimationUtils.loadAnimation(SplashScreenActivity.this,R.anim.zoom_in);
       // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fff")));
        imageView.setAnimation(animationUptoDown);
        //   imageview.setAnimation(animationDownToUp);
        LOCKON = MyFunctions.getSharedPrefs(SplashScreenActivity.this, Constants.LOCKON,"");

    }

    public void FetchAppInfo(){
        StringRequest request = new StringRequest(Request.Method.GET, reurl + "Authenticate/FetchAppInfo?APIKEY=15JRAKYTGQMXTH967COKVV27F"+"&"+"VersionNumber"+"="+VersionNumber, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals(null)) {
                    try {
                        JSONObject jsonObj = new JSONObject(response);

                        Log.d("TAG",response.toString());

                        status_code = jsonObj.getString("statusCode");

                        if (status_code.equals("250")) {

                            statusMessage = (String) jsonObj.get("userMessage");
                            System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                            statusMessage = (String) jsonObj.get("statusMessage");
                            System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                            userMessage = (String) jsonObj.get("userMessage");
                            System.out.println("Check userMessage of Login Activity:" + userMessage);

                            internalMessage = (String) jsonObj.get("internalMessage");
                            System.out.println("Check Customer_Email of Login Activity:" + internalMessage);

                            JSONObject user = jsonObj.getJSONObject("serviceData");

                            JSONObject android = user.getJSONObject("android");

                            version = android.getString("version");
                            storelink = android.getString("storelink");
                             boolean downloadforce= android.getBoolean("forcedownload");
                            forcedownload = Boolean.toString(downloadforce);
                            if (forcedownload.equals("true")){
                                mSplashThread = new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            synchronized (this) {
                                                wait(5000);
                                            }
                                        } catch (InterruptedException ex) {
                                        }
                                        finish();

                                        if (LOCKON.equals("ON")){
                                            Intent intent = new Intent();
                                            intent.setClass(SplashScreenActivity.this, LockActivity.class);
                                            startActivity(intent);
                                        }else if (LOCKON.equals("OFF")){
                                            Intent intent = new Intent();
                                            intent.setClass(SplashScreenActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                        }else {
                                            Intent intent = new Intent();
                                            intent.setClass(SplashScreenActivity.this, WelcomeActivity.class);
                                            startActivity(intent);
                                        }


                                    }
                                };

                                mSplashThread.start();
                            }else {
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(storelink)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(storelink)));
                                }
                            }

                        }else{
                            userMessage = (String) jsonObj.get("userMessage");
                            AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenActivity.this);
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

                } else {
                    Log.e("Your Array Response", "Data Null");
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error is ", "" + error);
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
        requestQueue.add(request);
        Log.d("tag", request.toString());
    }



}
