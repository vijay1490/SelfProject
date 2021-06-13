package com.zealoit.eqz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zealoit.eqz.Utils.BottomNavigationViewHelper;
import com.zealoit.eqz.Utils.Constants;
import com.zealoit.eqz.Utils.FontChangeCrawler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zealoit.eqz.Utils.MyFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.zealoit.eqz.Utils.Constants.NGROK_BASE_URL;

public class AcountActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    EditText edt_name , edt_email , edt_number;
    RequestQueue requestQueue;
    private String reurl = NGROK_BASE_URL;
    String status_code, userid, mobileno, quser, otp , username , useremail , userphoneNumber , fcmtoken ,  mobilenumber , servicetype_name, MobileNumber , AuthorizationToken , servicetype_id, servicetype_icon, servicegroup_id, status, created_on, statusMessage, userMessage, internalMessage, created_by, particpantid, user_email, user_contact_no;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        edt_name = (EditText)findViewById(R.id.edit_username);
        edt_email = (EditText)findViewById(R.id.edit_useremail);
        edt_number = (EditText)findViewById(R.id.edit_usermobile);
        requestQueue = Volley.newRequestQueue(AcountActivity.this);
        String Token_Authorization = MyFunctions.getSharedPrefs(AcountActivity.this, Constants.USERTYPE, "");
        userid = MyFunctions.getSharedPrefs(AcountActivity.this, Constants.USER_ID, "");
        fcmtoken = MyFunctions.getSharedPrefs(AcountActivity.this, Constants.TOKEN, "");
        AuthorizationToken = Token_Authorization;
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "CenturyGothic.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        setupNavigationView();
        FetchBusinessAccountDetails();
    }


    public void back(View view) {
        Intent intent = new Intent();
        intent.setClass(AcountActivity.this, ActivityAccountList.class);
        startActivity(intent);
    }
    private void setupNavigationView() {
        //  bottomNavigationView.setItemHorizontalTranslationEnabled(false);
        // bottomNavigationView.getMenu().findItem(R.id.action_loudspeaker).setChecked(true);

        //  bottomNavigationView.setItemHorizontalTranslationEnabled(false);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        if (bottomNavigationView != null) {

            // Select first menu item by default and show Fragment accordingly.
            //   Menu menu = bottomNavigationView.getMenu();
            //  selectFragment(menu.getItem(0));

            // Set action to perform when any menu-item is selected.
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem item) {
                            selectFragment(item);
                            return false;
                        }
                    });
        }
    }

    /**
     * Perform action when any item is selected.
     *
     * @param item Item that is selected.
     */
    protected void selectFragment(MenuItem item) {

        //  Fragment fragment = null;

        item.setChecked(true);

        switch (item.getItemId()) {

            case R.id.action_newspaper:
                Intent intent1 = new Intent(AcountActivity.this, ActivitySaloan.class);
                startActivity(intent1);
                break;


            case R.id.action_loudspeaker:
                Intent intent = new Intent(AcountActivity.this, HomeActivity.class);
                startActivity(intent);
                break;

            case R.id.action_user:
                 Intent intent2 = new Intent(AcountActivity.this, ActivityAccountList.class);
                  startActivity(intent2);
                break;
        }
        // return loadFragment(fragment);
    }

    public void register(View view) {
        username = edt_name.getText().toString();
        useremail =  edt_email.getText().toString();
        userphoneNumber = edt_number.getText().toString();
        ProfileUpdate();

    }

    public void ProfileUpdate(){

        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("username",username);
            object.put("email",useremail);
            object.put("fcmToken",fcmtoken);
            object.put("phoneNumber",userphoneNumber);
            object.put("userType","1");
            object.put("userId",userid);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                reurl+"User/ProfileUpdate?APIKEY=15JRAKYTGQMXTH967COKVV27F" , object,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("TAG", response.toString());
                            status_code = response.getString("statusCode");
                            if(status_code.equals("222")) {

                                statusMessage = (String) response.get("userMessage");
                                System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                                statusMessage = (String) response.get("statusMessage");
                                System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                                userMessage = (String) response.get("userMessage");
                                System.out.println("Check userMessage of Login Activity:" + userMessage);

                                internalMessage = (String) response.get("internalMessage");
                                System.out.println("Check Customer_Email of Login Activity:" + internalMessage);

                                JSONObject user = response.getJSONObject("serviceData");


                                Intent intent = new Intent(AcountActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();

                                // business_id = (Integer) user.get("business_id");

                                //  provider_status = (Integer) user.get("provider_status");
                            }else{
                                userMessage = (String) response.get("userMessage");
                                AlertDialog.Builder builder = new AlertDialog.Builder(AcountActivity.this);
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
        System.out.println("adsjjjckj" +  object.toString());
    }

    public void FetchBusinessAccountDetails(){
        StringRequest request = new StringRequest(Request.Method.GET, reurl+"User/Getuserbyid?APIKEY=15JRAKYTGQMXTH967COKVV27F"+"&"+"userId"+"="+userid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals(null)) {
                    try {
                        Log.d("TAG", response.toString());
                        JSONObject jsonObj = new JSONObject(response);
                        System.out.println("Json string is:" + jsonObj);

                        // JSONArray jsonArray = jsonObj.getJSONArray("serviceData");

/*
                    String resultStr = jsonObj.getString("AddRegistrationResult");
*/
                        //  JSONObject jsonObject = jsonObj.getJSONObject("response");

                        status_code = jsonObj.getString("statusCode");

                        if (status_code.equals("207")) {

                            statusMessage = (String) jsonObj.get("userMessage");
                            System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                            statusMessage = (String) jsonObj.get("statusMessage");
                            System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                            userMessage = (String) jsonObj.get("userMessage");
                            System.out.println("Check userMessage of Login Activity:" + userMessage);

                            internalMessage = (String) jsonObj.get("internalMessage");
                            System.out.println("Check Customer_Email of Login Activity:" + internalMessage);
                            JSONObject user = jsonObj.getJSONObject("serviceData");

                            String businessname = (String)user.get("name");
                            edt_name.setText(businessname);
                            System.out.println("Check  usertype of Login Activity:" + servicetype_name);

                            String  businessemail = (String)user.get("email");
                            edt_email.setText(businessemail);
                            System.out.println("Check  usertype of Login Activity:" + servicetype_name);

                            String Mobilenumber = (String)user.get("mobileNumber");
                            System.out.println("Check  usertype of Login Activity:" + servicetype_name);
                            edt_number.setText(Mobilenumber);

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
    }

}
