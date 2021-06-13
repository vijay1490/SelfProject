package com.zealoit.eqz;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.zealoit.eqz.Utils.BottomNavigationViewHelper;
import com.zealoit.eqz.Utils.FontChangeCrawler;
import com.zealoit.eqz.Utils.GPSTracker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Locale;

public class ActivityClinic extends AppCompatActivity {

    TextView user_location_address , sub_txt , username;

    String latitude , longitude , locationAddress;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    GPSTracker gps;

    BottomNavigationView bottomNavigationView;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    String address , area;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic);
        user_location_address = findViewById(R.id.current_location);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "CenturyGothic.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        setupNavigationView();

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
        gps = new GPSTracker(ActivityClinic.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitudes = gps.getLatitude();
            double longitudes = gps.getLongitude();

            latitude = String.valueOf(latitudes);
            longitude = String.valueOf(longitudes);

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitudes, Toast.LENGTH_LONG).show();
            //  MyFunctions.setSharedPrefs(MainActivitySecond.this, Constants.LATITUDE, latitudes);

            //  MyFunctions.setSharedPrefs(MainActivitySecond.this, Constants.LONGITUDE,longitudes);
            Geocoder geocoder = new Geocoder(ActivityClinic.this, Locale.getDefault());
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
        intent.setClass(ActivityClinic.this, HomeActivity.class);
        startActivity(intent);
    }

    public void location(View view) {
        Intent intent = new Intent();
        intent.setClass(ActivityClinic.this, ActivityLocationName.class);
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
                Intent intent = new Intent(ActivityClinic.this, HomeActivity.class);
                startActivity(intent);
                break;

            case R.id.action_user:
                Intent intent2 = new Intent(ActivityClinic.this, ActivityAccountList.class);
                startActivity(intent2);
                break;
        }
        // return loadFragment(fragment);
    }


    public void individual(View view) {
        Intent intent2 = new Intent(ActivityClinic.this, ActivityIndividual.class);
        startActivity(intent2);
        finish();
    }
}
