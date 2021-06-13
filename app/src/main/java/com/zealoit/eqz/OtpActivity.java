package com.zealoit.eqz;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.zealoit.eqz.Utils.Constants.NGROK_BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.zealoit.eqz.Utils.Constants;
import com.zealoit.eqz.Utils.MyFunctions;
import com.zealoit.eqz.Utils.ServiceHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class OtpActivity extends AppCompatActivity {

    TextView txt_otpss , txt_wrong_otp;
    EditText et1,et2,et3,et4,et5,et6;
    Button empsignup;
    LinearLayout lin_wrong_otp;
    ImageView img_wrong_otp;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    String REG , REGLOG , customerId , mobile , expiration , token , name;

    private String reurl = NGROK_BASE_URL;

    ProgressDialog pDialog;



    private String url = NGROK_BASE_URL + "VERIFY";

    String status_code ,userid , mobileno , details,quser , emailId ,FCMTOKEN,otpnumber, statusMessage , userMessage ,internalMessage ,useid , particpantid ,user_email ,user_contact_no;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        txt_otpss = findViewById(R.id.txt_otpss);
        img_wrong_otp = findViewById(R.id.img_wrong_otp);
        txt_wrong_otp  = findViewById(R.id.txt_wrong_otp);
        lin_wrong_otp  = findViewById(R.id.lin_wrong_otp);
        REG = getIntent().getStringExtra("REG");
       // REGLOG = getIntent().getStringExtra("LOG");
       String mobiles = getIntent().getStringExtra("details");
       // String mobile = MyFunctions.getSharedPrefs(OtpActivity.this, Constants.MOBILE,"");
       // mobileno =  MyFunctions.getSharedPrefs(OtpActivity.this, Constants.MOBILE,"");
                //getIntent().getStringExtra("mobileno");
        txt_otpss.setText("OTP is send to +91"+" "+mobiles);
        details = mobiles;

        pref = getSharedPreferences("OtpActivity", 0);
        editor = pref.edit();

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    et2.requestFocus();
                }
                else if(s.length()==0)
                {
                    et1.clearFocus();
                }
            }
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    et3.requestFocus();
                }
                else if(s.length()==0)
                {
                    et1.requestFocus();
                }
            }
        });

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    et4.requestFocus();
                }
                else if(s.length()==0)
                {
                    et2.requestFocus();
                }
            }
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    et5.requestFocus();
                }
                else if(s.length()==0)
                {
                    et3.requestFocus();
                }
            }
        });

        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    et6.requestFocus();
                }
                else if(s.length()==0)
                {
                    et4.requestFocus();
                }
            }
        });

        et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                et6.getText().toString();
                if(s.length()==1)
                {
                    et6.clearFocus();
                }
                else if(s.length()==0)
                {
                    et5.requestFocus();
                }


            }
        });

        et6.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                InputMethodManager keyboard = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (focused)
                    keyboard.showSoftInput(et6, 0);
                else
                    keyboard.hideSoftInputFromWindow(et6.getWindowToken(), 0);
            }
        });

    }


    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
              //  your_edittext.setText(message);
                //Do whatever you want with the code here
            }
        }
    };

    public void home(View view) {
        otpnumber =et1.getText().toString()+et2.getText().toString()+et3.getText().toString()+et4.getText().toString()+et5.getText().toString()+et6.getText().toString();
        FCMTOKEN = MyFunctions.getSharedPrefs(OtpActivity.this,Constants.TOKEN,"");
        new VerifyOtp().execute();
       // startActivity(new Intent(OtpActivity.this,HomeActivity.class));
       // finish();
    }

    public void resendotp(View view) {
        new VerifyReOtp().execute();
    }

    public void end(View view) {
        startActivity(new Intent(OtpActivity.this, LoginActivity.class));
        finish();

    }



    // Async task class to get json by making HTTP call start
    private class VerifyOtp extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(OtpActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            try {

                JSONObject inner = new JSONObject();
                Log.d("JSON object now is: ", "> " + inner);

                inner.put("mobileNummber",details);
                inner.put("otp",otpnumber);
                inner.put("fcmToken",FCMTOKEN);
                inner.put("userType", "1");


/*
                String jsonstring = "{\"c\":" + inner.toString() + "}";
*/
                String jsonstring = inner.toString();
                Log.d("jsonstring now is: ", "> " + jsonstring);


                sh.AddJSONContents(jsonstring);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String jsonStr = sh.makePOSTServiceCall(reurl+"Authenticate/VerifyOTP?APIKEY=15JRAKYTGQMXTH967COKVV27F", ServiceHandler.POST);

            Log.d("Response: ", "> " + jsonStr);
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    System.out.println("Json string is:" + jsonObj);

/*
                    String resultStr = jsonObj.getString("AddRegistrationResult");
*/
                    //  JSONObject jsonObject = jsonObj.getJSONObject("response");

                    status_code = jsonObj.getString("statusCode");

                    if(status_code.equals("203")){

                        statusMessage = (String) jsonObj.get("userMessage");
                        System.out.println("Check statusMessage of Login Activity:" + statusMessage);


                        for (int i = 0; i < jsonObj.length(); i++) {

                            statusMessage = (String) jsonObj.get("statusMessage");
                            System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                            userMessage = (String) jsonObj.get("userMessage");
                            System.out.println("Check userMessage of Login Activity:" + userMessage);

                            internalMessage = (String) jsonObj.get("internalMessage");
                            System.out.println("Check Customer_Email of Login Activity:" + internalMessage);

                            JSONObject user = jsonObj.getJSONObject("serviceData");

                            userid = (String) user.get("userId");
                            System.out.println("Check userid of Login Activity:" + userid);
                            int customerid = (Integer) user.get("customerId");
                            System.out.println("Check  participantid of Login Activity:" + customerId);

                            customerId = Integer.toString(customerid);

                            token = (String)user.get("token");
                            System.out.println("Check  usertype of Login Activity:" + token);

                            name = (String)user.get("name");
                            System.out.println("Check  usertype of Login Activity:" + name);

                            expiration = (String)user.get("expiration");
                            System.out.println("Check  usertype of Login Activity:" + expiration);

                            mobile = (String)user.get("mobileNumber");
                            System.out.println("Check  usertype of Login Activity:" + mobile);

                            emailId = (String)user.get("emailId");
                            System.out.println("Check  usertype of Login Activity:" + mobile);


                        }

                        editor.putString("userid",userid);
                        editor.putString("customerId",customerId);
                        editor.putString("token",token);
                        editor.putString("name",name);
                        editor.putString("expiration",expiration);
                        editor.putString("mobile",mobile);
                        editor.putString("emailId",emailId);
                        editor.commit();
                        MyFunctions.setSharedPrefs(OtpActivity.this, Constants.IS_LOGIN, true);
                        MyFunctions.setSharedPrefs(OtpActivity.this, Constants.USER_ID,userid);
                        MyFunctions.setSharedPrefs(OtpActivity.this,Constants.CUSTOMERID,customerId);
                        MyFunctions.setSharedPrefs(OtpActivity.this,Constants.USERTYPE,token);
                        MyFunctions.setSharedPrefs(OtpActivity.this, Constants.MOBILE,mobile);
                        MyFunctions.setSharedPrefs(OtpActivity.this, Constants.EMAIL,emailId);
                        MyFunctions.setSharedPrefs(OtpActivity.this, Constants.USERNAME,name);
                        Toast.makeText(OtpActivity.this, statusMessage, Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(OtpActivity.this,HomeActivity.class));
                        finish();


//                        editor.putString("userid",useid);
                        //    editor.putString("pariticipantid",particpantid);

//                        editor.commit();


                    }else if (status_code.equals("400")){
                        statusMessage = (String) jsonObj.get("userMessage");
                        lin_wrong_otp.setVisibility(View.VISIBLE);
                        txt_wrong_otp.setText(statusMessage);
                        Glide.with(OtpActivity.this).load(R.raw.wront_otp).into(img_wrong_otp);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
                lin_wrong_otp.setVisibility(View.VISIBLE);
            }
        }
    }


    // Async task class to get json by making HTTP call start
    private class VerifyReOtp extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(OtpActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            String jsonStr = sh.makeServiceCall(reurl+"/"+mobileno+"/"+"AUTOGEN"+"/"+"GeneralOTP", ServiceHandler.GET);

            System.out.println("test"+jsonStr);

            Log.d("Response: ", "> " + jsonStr);
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    System.out.println("Json string is:" + jsonObj);

/*
                    String resultStr = jsonObj.getString("AddRegistrationResult");
*/
                    //  JSONObject jsonObject = jsonObj.getJSONObject("response");

                    status_code = jsonObj.getString("Status");

                    if(status_code.equals("Success")){

                        statusMessage = (String) jsonObj.get("Details");
                        System.out.println("Check statusMessage of Login Activity:" + statusMessage);
                       // startActivity(new Intent(LoginActivity.this,OtpActivity.class).putExtra("details",statusMessage));
                      //  finish();


//                        editor.putString("userid",useid);
                        //    editor.putString("pariticipantid",particpantid);

//                        editor.commit();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
        }
    }
}
