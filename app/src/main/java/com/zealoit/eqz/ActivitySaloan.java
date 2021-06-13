package com.zealoit.eqz;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zealoit.eqz.Adapter.HelpDeskAdapter;
import com.zealoit.eqz.Utils.BottomNavigationViewHelper;
import com.zealoit.eqz.Utils.Constants;
import com.zealoit.eqz.Utils.FontChangeCrawler;
import com.zealoit.eqz.Utils.GPSTracker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.zealoit.eqz.Utils.MyFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import static com.zealoit.eqz.Utils.Constants.NGROK_BASE_URL;

public class ActivitySaloan extends AppCompatActivity {

    TextView user_location_address , sub_txt , username;
    EditText ET_search;

    String latitude , longitude , locationAddress;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    BottomNavigationView bottomNavigationView;
    private String reurl = NGROK_BASE_URL;
    GPSTracker gps;

    String address , area;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    RequestQueue requestQueue;
    String status_code, userid, mobileno, quser, servicetype_name, servicetype_id, AuthorizationToken , servicetype_icon, servicegroup_id, status, created_on, statusMessage, userMessage, internalMessage, created_by, particpantid, user_email, user_contact_no;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon);
        String Token_Authorization = MyFunctions.getSharedPrefs(ActivitySaloan.this, Constants.USERTYPE,"");
        AuthorizationToken = Token_Authorization;
        userid = MyFunctions.getSharedPrefs(ActivitySaloan.this, Constants.USER_ID, "");
        requestQueue = Volley.newRequestQueue(ActivitySaloan.this);
        user_location_address = findViewById(R.id.current_location);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "CenturyGothic.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("OPEN"));
        tabLayout.addTab(tabLayout.newTab().setText("CLOSED"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ff6600"));
        tabLayout.setSelectedTabIndicatorHeight((int) (2 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#ff6600"));
        setupNavigationView();


        final HelpDeskAdapter adapter = new HelpDeskAdapter(ActivitySaloan.this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                // execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // create class object
        gps = new GPSTracker(ActivitySaloan.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitudes = gps.getLatitude();
            double longitudes = gps.getLongitude();

            latitude = String.valueOf(latitudes);
            longitude = String.valueOf(longitudes);

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitudes, Toast.LENGTH_LONG).show();
              MyFunctions.setSharedPrefs(ActivitySaloan.this, Constants.LATITUDE, latitude);

              MyFunctions.setSharedPrefs(ActivitySaloan.this, Constants.LONGITUDE,longitude);
            Geocoder geocoder = new Geocoder(ActivitySaloan.this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(gps.getLatitude(),  gps.getLongitude(),1);
                String address = addresses.get(0).getSubLocality();
                String stateName = addresses.get(0).getSubAdminArea();
                String cityName = addresses.get(0).getLocality();

                area = cityName;
                //   MyFunctions.setSharedPrefs(ProfileActivity.this, Constants.LOCADDRESS, cityName+","+"\t"+stateName+","+"\t"+address);
                //  city.setVisibility(View.VISIBLE);
                // area = tvAddress.getText().toString();
                user_location_address.setText(address + "\t" + area);


            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    public void back(View view) {
        Intent intent = new Intent();
        intent.setClass(ActivitySaloan.this, HomeActivity.class);
        startActivity(intent);
    }

    public void location(View view) {
        Intent intent = new Intent();
        intent.setClass(ActivitySaloan.this, ActivityLocationName.class);
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

                break;


            case R.id.action_loudspeaker:
                Intent intent = new Intent(ActivitySaloan.this, HomeActivity.class);
                startActivity(intent);
                break;

            case R.id.action_user:
                 Intent intent2 = new Intent(ActivitySaloan.this, ActivityAccountList.class);
                  startActivity(intent2);
                break;
        }
        // return loadFragment(fragment);

       // FetchAllBusiness();
    }

    public void clinicchoose(View view) {
        Intent intent2 = new Intent(ActivitySaloan.this, ActivityIndividual.class);
        startActivity(intent2);
        finish();
    }


    public void FetchAllBusiness(){

        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("unique_id",userid);
            object.put("business_longitude",longitude);
            object.put("business_latitude",latitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                reurl+"Provider/CreateProvider?APIKEY=15JRAKYTGQMXTH967COKVV27F" , object,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            status_code = response.getString("statusCode");
                            if(status_code.equals("210")) {

                                statusMessage = (String) response.get("userMessage");
                                System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                                statusMessage = (String) response.get("statusMessage");
                                System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                                userMessage = (String) response.get("userMessage");
                                System.out.println("Check userMessage of Login Activity:" + userMessage);

                                internalMessage = (String) response.get("internalMessage");
                                System.out.println("Check Customer_Email of Login Activity:" + internalMessage);

                                JSONObject user = response.getJSONObject("serviceData");

                              //  ProviderTitle = (String) user.get("providerTitle");

                             //   serviceid = (Integer) user.get("serviceid");

                               // Businessid = (Integer) user.get("businessId");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
              //  pDialog.hide();
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
    }
}
