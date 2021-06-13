package com.zealoit.eqz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.zealoit.eqz.Adapter.GetAllBusinessProviderAdapter;
import com.zealoit.eqz.Adapter.GetAllProviderAdapter;
import com.zealoit.eqz.List.AllProvidersList;
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

public class ActivityIndividual extends AppCompatActivity {
    ImageView imageView;
    TextView txt_name_business , txt_door_no , txt_street , txt_area , txt_city , txt_nodata;
    LinearLayout lin_empty;
    Button getqueued;
    BottomNavigationView bottomNavigationView;
    RequestQueue requestQueue;
    String status_code, userid, mobileno, quser, GETCOUNT , Idprovider , Idcustomer , servicetype_name, QUEUENUMBER , BusinessId , servicetype_id, AuthorizationToken , servicetype_icon, servicegroup_id, status, created_on, statusMessage, userMessage, internalMessage, created_by, particpantid, user_email, user_contact_no;
    private String reurl = NGROK_BASE_URL;
    int BusinessID , customerId , providerId  , Queue_id , queue_number , waitingQueue , provider_id , customer_id , statu , createdby;
    String ProviderTitle , queue_closure_timestamp , businessstatus ,  queue_timestamp , currentQueue , queue_timetaken , updated_on , updated_by , queue_date ;
    String latitude , longitude , locationAddress ;
    List<AllProvidersList>allProvidersLists;
    RecyclerView rv_getallbusinessprovider;

    @Override

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_clinic);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "CenturyGothic.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        String Token_Authorization = MyFunctions.getSharedPrefs(ActivityIndividual.this, Constants.USERTYPE,"");
        AuthorizationToken = Token_Authorization;
         userid = MyFunctions.getSharedPrefs(ActivityIndividual.this, Constants.USER_ID, "");
        lin_empty =  findViewById(R.id.lin_empty);
        txt_nodata =  findViewById(R.id.txt_nodata);
      //  longitude  = MyFunctions.getSharedPrefs(getActivity(), Constants.LONGITUDE, "");
        BusinessId = getIntent().getStringExtra("BusinessId");
        BusinessID = Integer.parseInt(BusinessId);
        requestQueue = Volley.newRequestQueue(ActivityIndividual.this);
        imageView = findViewById(R.id.imganims);
        getqueued = findViewById(R.id.getqueued);
        txt_name_business = findViewById(R.id.txt_name_business);
        txt_door_no = findViewById(R.id.txt_door_no);
        txt_street = findViewById(R.id.txt_street);
        txt_area = findViewById(R.id.txt_area);
        txt_city = findViewById(R.id.txt_city);
        rv_getallbusinessprovider = (RecyclerView)findViewById(R.id.rv_getallbusinessprovider);
        rv_getallbusinessprovider.setHasFixedSize(true);
        rv_getallbusinessprovider.setLayoutManager(new LinearLayoutManager(ActivityIndividual.this));
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);

        allProvidersLists = new ArrayList<>();


        getqueued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (businessstatus.equals("1")){
                    Idprovider = MyFunctions.getSharedPrefs(ActivityIndividual.this, Constants.PROVIDERID, "");
                    providerId = Integer.parseInt(Idprovider);
                    Idcustomer = MyFunctions.getSharedPrefs(ActivityIndividual.this, Constants.CUSTOMERID, "");
                    customerId = Integer.parseInt(Idcustomer);
                    GETCOUNT  = MyFunctions.getSharedPrefs(ActivityIndividual.this, Constants.PROVIDERSTATUS, "");
                  ////  int getcount = Integer.parseInt(GETCOUNT);

                    System.out.println("kaskaskjadsk"+ GETCOUNT + Idprovider);


                    if (providerId != 0 & !GETCOUNT.equals("0")){
                        JSONObject object = new JSONObject();
                        try {
                            //input your API parameters
                            object.put("providerId",providerId);
                            object.put("customerId",customerId);
                            // object.put("businessId",businessId);
                            System.out.println("aclkmnckl"+object.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                reurl+"Queue/CreateQueueToken?APIKEY=15JRAKYTGQMXTH967COKVV27F" , object,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            Log.e("TTTTT", response.toString());
                                            status_code = response.getString("statusCode");
                                            if(status_code.equals("200")) {

                                                statusMessage = (String) response.get("userMessage");
                                                System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                                                statusMessage = (String) response.get("statusMessage");
                                                System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                                                userMessage = (String) response.get("userMessage");
                                                System.out.println("Check userMessage of Login Activity:" + userMessage);

                                                internalMessage = (String) response.get("internalMessage");
                                                System.out.println("Check Customer_Email of Login Activity:" + internalMessage);

                                                JSONObject user = response.getJSONObject("serviceData");

                                                currentQueue = (String)user.get("currentQueue");

                                                //  waitingQueue = (int)user.get("waitingQueue");

                                                JSONObject newqueue = user.getJSONObject("newqueue");

                                                Queue_id = (Integer) newqueue.get("queue_id");

                                                String idqueue = Integer.toString(Queue_id);

                                                ProviderTitle = (String) newqueue.get("preFix");

                                                QUEUENUMBER =  (String) newqueue.get("queuenumber");

                                                queue_number = (Integer) newqueue.get("queue_number");

                                                queue_timestamp = (String) newqueue.get("queue_timestamp");

                                                provider_id = (Integer) newqueue.get("provider_id");

                                                customer_id = (Integer) newqueue.get("customer_id");


                                                if(newqueue.isNull("queue_closure_timestamp"))
                                                {
                                                    System.out.println("inside null");
                                                    // jsonObject.put("parentId", 0);
                                                }
                                                else
                                                {
                                                    queue_closure_timestamp = (String)newqueue.get("queue_closure_timestamp");
                                                    System.out.println("Check  name of Login Activity:" + queue_closure_timestamp);
                                                }


                                                if(newqueue.isNull("queue_timetaken"))
                                                {
                                                    System.out.println("inside null");
                                                    // jsonObject.put("parentId", 0);
                                                }
                                                else
                                                {
                                                    queue_timetaken = (String)user.get("queue_timetaken");
                                                    System.out.println("Check  name of Login Activity:" + queue_timetaken);
                                                }

                                                statu = (Integer) newqueue.get("status");

                                                created_on  = (String) newqueue.get("created_on");

                                                createdby = (Integer) newqueue.get("created_by");

                                                if(user.isNull("updated_on"))
                                                {
                                                    System.out.println("inside null");
                                                    // jsonObject.put("parentId", 0);
                                                }
                                                else
                                                {
                                                    updated_on = (String)newqueue.get("updated_on");
                                                    System.out.println("Check  name of Login Activity:" + queue_timetaken);
                                                }

                                                if(user.isNull("updated_by"))
                                                {
                                                    System.out.println("inside null");
                                                    // jsonObject.put("parentId", 0);
                                                }
                                                else
                                                {
                                                    updated_by = (String)newqueue.get("updated_by");
                                                    System.out.println("Check  name of Login Activity:" + queue_timetaken);
                                                }
                                                queue_date = (String) newqueue.get("queue_date");
                                                startActivity(new Intent(ActivityIndividual.this, ActivityQueuedBooked.class).putExtra("QUEUENUMBER",QUEUENUMBER).putExtra("currentQueue",currentQueue).putExtra("waitingQueue",waitingQueue));
                                                finish();
                                            }else{
                                                userMessage = (String) response.get("userMessage");
                                                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityIndividual.this);
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
                    }else {
                        Toast.makeText(ActivityIndividual.this, "PLEASE SELECTE SERVICE NAME", Toast.LENGTH_SHORT).show();
                    }
                }else if (businessstatus.equals("0")){
                    startActivity(new Intent(ActivityIndividual.this, NoDataActivity.class).putExtra("message","WE ARE CLOSED NOW"));
                    finish();
                }



            }
        });

        setupNavigationView();
        FetchAllBusiness();
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
                Intent intent1 = new Intent(ActivityIndividual.this, ActivitySaloan.class);
                startActivity(intent1);
                break;


            case R.id.action_loudspeaker:
                Intent intent = new Intent(ActivityIndividual.this, HomeActivity.class);
                startActivity(intent);
                break;

            case R.id.action_user:
                 Intent intent2 = new Intent(ActivityIndividual.this, ActivityAccountList.class);
                  startActivity(intent2);
                break;
        }
        // return loadFragment(fragment);
    }

    public void backhome(View view) {
        Intent intent2 = new Intent(ActivityIndividual.this, ActivitySaloan.class);
        startActivity(intent2);
    }


    public void FetchAllBusiness() {

        StringRequest request = new StringRequest(Request.Method.POST, reurl+"Business/FetchBusiness?APIKEY=15JRAKYTGQMXTH967COKVV27F"+"&"+"BusinessID"+"="+BusinessID, new Response.Listener<String>() {
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

                        if (status_code.equals("220")) {

                            statusMessage = (String) jsonObj.get("userMessage");
                            System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                            statusMessage = (String) jsonObj.get("statusMessage");
                            System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                            userMessage = (String) jsonObj.get("userMessage");
                            System.out.println("Check userMessage of Login Activity:" + userMessage);

                            internalMessage = (String) jsonObj.get("internalMessage");
                            System.out.println("Check Customer_Email of Login Activity:" + internalMessage);

                            JSONArray user = jsonObj.getJSONArray("serviceData");

                            JSONArray aallProviders ;

                            for (int i = 0 ; i < user.length() ; i++){

                                JSONObject businessdetails = user.getJSONObject(i);

                                int BusinessId = businessdetails.getInt("businessId");

                                String businessname = businessdetails.getString("businessName");
                                txt_name_business.setText(businessname);

                                String businessPersonName = businessdetails.getString("businessPersonName");

                                String area = businessdetails.getString("area");
                                txt_area.setText(area);

                                String city = businessdetails.getString("city");
                                txt_city.setText(city);

                                 businessstatus = businessdetails.getString("status");

                                aallProviders = businessdetails.getJSONArray("aallProviders");

                                if (!aallProviders.isNull(0)){
                                    for (int j = 0 ; j < aallProviders.length() ; j++){

                                        AllProvidersList allProvidersList = new AllProvidersList();
                                        rv_getallbusinessprovider.setVisibility(View.VISIBLE);
                                        JSONObject ProvidersList = aallProviders.getJSONObject(j);

                                        allProvidersList.setStatus(ProvidersList.getInt("status"));
                                        allProvidersList.setQueueCount(ProvidersList.getString("queueCount"));
                                        allProvidersList.setServiceName(ProvidersList.getString("serviceName"));
                                        allProvidersList.setProviderId(ProvidersList.getInt("providerId"));
                                        allProvidersList.setProviderTitle(ProvidersList.getString("providerTitle"));
                                        allProvidersList.setSrviceid(ProvidersList.getInt("srviceid"));
                                        allProvidersList.setSrviceid(ProvidersList.getInt("businessId"));
                                        allProvidersLists.add(allProvidersList);
                                    }

                                    GetAllProviderAdapter adapter = new GetAllProviderAdapter(ActivityIndividual.this ,allProvidersLists);
                                    //setting adapter to recyclerview
                                    rv_getallbusinessprovider.setAdapter(adapter);// notify adapter
                                }else {
                                    lin_empty.setVisibility(View.VISIBLE);
                                    rv_getallbusinessprovider.setVisibility(View.GONE);
                                    txt_nodata.setText("Oops! Sorry nothing to show");
                                    Glide.with(ActivityIndividual.this).load(R.drawable.nodata).into(imageView);
                                }

                            }

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
