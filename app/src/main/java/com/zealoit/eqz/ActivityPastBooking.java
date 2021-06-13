package com.zealoit.eqz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.zealoit.eqz.Adapter.PastBookingAdapter;
import com.zealoit.eqz.List.OldQueueList;
import com.zealoit.eqz.List.PastbookingList;
import com.zealoit.eqz.Utils.BottomNavigationViewHelper;
import com.zealoit.eqz.Utils.Constants;
import com.zealoit.eqz.Utils.FontChangeCrawler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zealoit.eqz.Utils.MyFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.zealoit.eqz.Utils.Constants.NGROK_BASE_URL;

public class ActivityPastBooking extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView txt_nodata;
    RequestQueue requestQueue;
    RecyclerView rv_oldQueue;
    String status_code, userid, mobileno, quser, queueNumber , CustomerId , AuthorizationToken , servicetype_name, servicetype_id, servicetype_icon, servicegroup_id, status, created_on, statusMessage, userMessage, internalMessage, created_by, particpantid, user_email, user_contact_no;
    List<PastbookingList> pastbookingLists;
    private String reurl = NGROK_BASE_URL;
    LinearLayout lin_empty;
    ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_booking);
        imageView = (ImageView)findViewById(R.id.imganims);
        CustomerId = MyFunctions.getSharedPrefs(ActivityPastBooking.this, Constants.CUSTOMERID, "");
        requestQueue = Volley.newRequestQueue(ActivityPastBooking.this);
        lin_empty =  findViewById(R.id.lin_empty);
        txt_nodata =  findViewById(R.id.txt_nodata);
        String Token_Authorization = MyFunctions.getSharedPrefs(ActivityPastBooking.this, Constants.USERTYPE, "");
        AuthorizationToken = Token_Authorization;
        rv_oldQueue = (RecyclerView)findViewById(R.id.rv_oldQueue);
        rv_oldQueue.setHasFixedSize(true);
        rv_oldQueue.setLayoutManager(new LinearLayoutManager(ActivityPastBooking.this));
        pastbookingLists = new ArrayList<>();
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "CenturyGothic.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        setupNavigationView();
        FetchUserHomeList();
    }

    public void back(View view) {
        Intent intent = new Intent();
        intent.setClass(ActivityPastBooking.this, ActivityAccountList.class);
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
                Intent intent1 = new Intent(ActivityPastBooking.this, ActivitySaloan.class);
                startActivity(intent1);
                break;


            case R.id.action_loudspeaker:
                Intent intent = new Intent(ActivityPastBooking.this, HomeActivity.class);
                startActivity(intent);
                break;

            case R.id.action_user:
                 Intent intent2 = new Intent(ActivityPastBooking.this, ActivityAccountList.class);
                  startActivity(intent2);
                break;
        }
        // return loadFragment(fragment);://e7c550309835.ngrok.io
    }

    public void FetchUserHomeList() {
        StringRequest request = new StringRequest(Request.Method.GET, reurl + "Queue/GetCustomerPastBooking?APIKEY=15JRAKYTGQMXTH967COKVV27F"+"&"+"CustomerId"+"="+CustomerId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals(null)) {
                    try {
                        JSONObject jsonObj = new JSONObject(response);
                        System.out.println("Json string is:" + jsonObj);

                        // JSONArray jsonArray = jsonObj.getJSONArray("serviceData");

/*
                    String resultStr = jsonObj.getString("AddRegistrationResult");
*/
                        //  JSONObject jsonObject = jsonObj.getJSONObject("response");

                        status_code = jsonObj.getString("statusCode");

                        if (status_code.equals("244")) {

                            statusMessage = (String) jsonObj.get("userMessage");
                            System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                            statusMessage = (String) jsonObj.get("statusMessage");
                            System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                            userMessage = (String) jsonObj.get("userMessage");
                            System.out.println("Check userMessage of Login Activity:" + userMessage);

                            internalMessage = (String) jsonObj.get("internalMessage");
                            System.out.println("Check Customer_Email of Login Activity:" + internalMessage);

                           // JSONObject user = jsonObj.getJSONObject("serviceData");

                            JSONArray oldQueue = jsonObj.getJSONArray("serviceData");

                         //   JSONArray Queuelive = user.getJSONArray("liveQueue");

                          //  System.out.println("Businessstatus" + Queuelive.toString());

                            if (!oldQueue.isNull(0)){
                                for (int i = 0; i < oldQueue.length(); i++) {
                                    PastbookingList pastbookingList = new PastbookingList();
                                    rv_oldQueue.setVisibility(View.VISIBLE);
                                    JSONObject Queueold = oldQueue.getJSONObject(i);
                                    pastbookingList.setProviderId(Queueold.getInt("providerId"));
                                    pastbookingList.setBusinessName(Queueold.getString("businessName"));
                                    pastbookingList.setProviderTitele(Queueold.getString("providerTitle"));
                                    pastbookingList.setServiceName(Queueold.getString("serviceName"));
                                    pastbookingList.setQueueNumber(Queueold.getString("queueNumber"));
                                    pastbookingList.setDate(Queueold.getString("date"));
                                    pastbookingList.setStatus(Queueold.getInt("status"));
                                    pastbookingLists.add(pastbookingList);

                                }

                                PastBookingAdapter adapter = new PastBookingAdapter(ActivityPastBooking.this,pastbookingLists);
                                //setting adapter to recyclerview
                                rv_oldQueue.setAdapter(adapter);// notify adapter

                            }else {
                                lin_empty.setVisibility(View.VISIBLE);
                                rv_oldQueue.setVisibility(View.GONE);
                                txt_nodata.setText("Oops! Sorry nothing to show");
                                Glide.with(ActivityPastBooking.this).load(R.drawable.nodata).into(imageView);
                            }
                        }else{
                            userMessage = (String) jsonObj.get("userMessage");
                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPastBooking.this);
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
    }
}
