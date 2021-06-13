package com.zealoit.eqz;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.zealoit.eqz.Adapter.GetAllBusinessProviderAdapter;
import com.zealoit.eqz.Adapter.OldQueueAdapter;
import com.zealoit.eqz.List.OldQueueList;
import com.zealoit.eqz.Utils.AppLocationService;
import com.zealoit.eqz.Utils.BottomNavigationViewHelper;
import com.zealoit.eqz.Utils.Constants;
import com.zealoit.eqz.Utils.GPSTracker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zealoit.eqz.Utils.MyFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.zealoit.eqz.Utils.Constants.NGROK_BASE_URL;

public class HomeActivity extends AppCompatActivity {
    ImageView img_profile , imageView;
    BottomNavigationView bottomNavigationView;
    AppLocationService appLocationService;
    TextView user_location_address, txt_nodata, username , txt_cancel_queue_number , txt_current_queue_number , txt_my_queue_number
            , txt_waiting , txt_waiting_business_name , txt_waiting_srvice_name;

    String latitude, longitude, locationAddress;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private String reurl = NGROK_BASE_URL;
    GPSTracker gps;

    ObjectAnimator textColorAnim;

    View floatingview;

    List<OldQueueList>oldQueueLists;

     LinearLayout lin_move_zoom_rotate;
    private Timer myTimer;
    String address, area, AuthorizationToken;

    private Resources mResources;
    private Bitmap mBitmap;

    LinearLayout lin_empty , rowsdd;

    public static int sCorner = 15;
    public static int sMargin = 2;
    TextView txt_good, txt_service , txt_name;
    int windowwidth; // Actually the width of the RelativeLayout.
    int windowheight; // Actually the height of the RelativeLayout.
    private LinearLayout mImageView;
    RelativeLayout mRrootLayout;
    RelativeLayout root;
    private int _xDelta;
    private int _yDelta;
    ImageView Img_profile;
    private int xDelta;
    private int yDelta;
    int customerId , providerId;
    String msg = "HomeActivity";
    private android.widget.RelativeLayout.LayoutParams layoutParams;
    RequestQueue requestQueue;
    RecyclerView rv_oldQueue;
    String businessName , providerTitele , serviceName, date;
    String status_code, userid, mobileno, quser, queueNumber , servicetype_name, servicetype_id, servicetype_icon, servicegroup_id, status, created_on, statusMessage, userMessage, internalMessage, created_by, particpantid, user_email, user_contact_no;
    LayoutInflater inflater ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //  root = findViewById(R.id.root);
        user_location_address = findViewById(R.id.user_location_address);
        username = findViewById(R.id.txt_title);
        txt_good = findViewById(R.id.sub_txt_good);
        txt_service = findViewById(R.id.sub_tts);
        txt_name = findViewById(R.id.txt_name);
        rowsdd = findViewById(R.id.rowsdd);
       // txt_waiting = findViewById(R.id.txt_waiting_queue_number);
      //  txt_waiting_business_name  = findViewById(R.id.txt_waiting_business_name);
      //  txt_waiting_srvice_name = findViewById(R.id.txt_waiting_srvice_name);
        inflater =  getLayoutInflater();
      //  txt_cancel_queue_number = findViewById(R.id.txt_cancel_queue_number);
      //  txt_current_queue_number = findViewById(R.id.txt_current_queue_number);
       // txt_my_queue_number = findViewById(R.id.txt_my_queue_number);
        String CUST = MyFunctions.getSharedPrefs(HomeActivity.this,Constants.CUSTOMERID,"");
        customerId = Integer.parseInt(CUST);
        String name = MyFunctions.getSharedPrefs(HomeActivity.this,Constants.USERNAME,"");
        txt_name.setText("Hi,"+ "\t" +name);
        mRrootLayout = (RelativeLayout) findViewById(R.id.root);
        // Img_profile = findViewById(R.id.im_move_zoom_rotate);
        mImageView = (LinearLayout) findViewById(R.id.lin_move_zoom_rotates);
        imageView = (ImageView)findViewById(R.id.imganims);
        lin_empty =  findViewById(R.id.lin_empty);
        txt_nodata =  findViewById(R.id.txt_nodata);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        String Token_Authorization = MyFunctions.getSharedPrefs(HomeActivity.this, Constants.USERTYPE, "");
        userid = MyFunctions.getSharedPrefs(HomeActivity.this, Constants.USER_ID, "");
        AuthorizationToken = Token_Authorization;
        rv_oldQueue = (RecyclerView)findViewById(R.id.rv_oldQueue);
        rv_oldQueue.setHasFixedSize(true);
        rv_oldQueue.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        oldQueueLists = new ArrayList<>();
        //mImageView.setOnTouchListener(onTouchListener());
        //   Glide.with(HomeActivity.this).load(R.raw.loader).into(mImageView);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        requestQueue = Volley.newRequestQueue(HomeActivity.this);
        Typeface typeface = Typeface.createFromAsset(
                getAssets(),
                "CenturyGothic.ttf");
        txt_good.setTypeface(typeface);


        Typeface typeface1 = Typeface.createFromAsset(
                getAssets(),
                "CenturyGothic.ttf");
        txt_service.setTypeface(typeface1);
        Bitmap mbitmap = ((BitmapDrawable) HomeActivity.this.getResources().getDrawable(R.drawable.avatar)).getBitmap();
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 150, 150, mpaint); // Round Image Corner 100 100 100 100
        //  holder.linmatch.setBackgroundResource(imageRounded);

        // img_profile.setBackground(new BitmapDrawable(getApplicationContext().getResources(), imageRounded));

        if (timeOfDay >= 0 && timeOfDay < 12) {
            txt_good.setText("Good Morning");
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            txt_good.setText("Good Afternoon");
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            txt_good.setText("Good Evening");
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            txt_good.setText("Good Night");
        }

        //  mImageView.setOnTouchListener(onTouchListener());
        // Glide.with(HomeActivity.this).load(R.raw.loader).into(Img_profile);



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
        gps = new GPSTracker(HomeActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitudes = gps.getLatitude();
            double longitudes = gps.getLongitude();

            latitude = String.valueOf(latitudes);
            longitude = String.valueOf(longitudes);

            // \n is for new line
          //  Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
               //     + latitude + "\nLong: " + longitudes, Toast.LENGTH_LONG).show();
            //  MyFunctions.setSharedPrefs(MainActivitySecond.this, Constants.LATITUDE, latitudes);

            //  MyFunctions.setSharedPrefs(MainActivitySecond.this, Constants.LONGITUDE,longitudes);
            Geocoder geocoder = new Geocoder(HomeActivity.this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
                String address = addresses.get(0).getSubLocality();
                String stateName = addresses.get(0).getSubAdminArea();
                String cityName = addresses.get(0).getLocality();

                area = cityName;
                //   MyFunctions.setSharedPrefs(ProfileActivity.this, Constants.LOCADDRESS, cityName+","+"\t"+stateName+","+"\t"+address);
                //  city.setVisibility(View.VISIBLE);
                // area = tvAddress.getText().toString();
                user_location_address.setText(address + "," + "\t" + area);


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = token;
                        Log.d("TAG", msg);
                      //  Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                FetchUserHomeList();
            }

        }, 4000,10000);;

        FetchUserHomeList();
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
                Intent intent = new Intent(HomeActivity.this, ActivitySaloan.class);
                startActivity(intent);
                break;


            case R.id.action_loudspeaker:

                break;

            case R.id.action_user:
                Intent intent2 = new Intent(HomeActivity.this, ActivityAccountList.class);
                startActivity(intent2);
                break;
        }
        // return loadFragment(fragment);
    }

    public void resturant(View view) {
        Intent intent2 = new Intent(HomeActivity.this, ActivityClinic.class);
        startActivity(intent2);
        finish();
    }

    public void saloan(View view) {
        Intent intent2 = new Intent(HomeActivity.this, ActivitySaloan.class);
        startActivity(intent2);
        finish();
    }

    public void clinic(View view) {
        Intent intent2 = new Intent(HomeActivity.this, ActivityResuaurant.class);
        startActivity(intent2);
        finish();
    }

    public void now(View view) {
        Intent intent2 = new Intent(HomeActivity.this, ActivitySaloan.class);
        startActivity(intent2);
        finish();
    }

    public void FetchUserHomeList() {
        StringRequest request = new StringRequest(Request.Method.GET, reurl + "Home/FetchUserHome?APIKEY=15JRAKYTGQMXTH967COKVV27F"+"&"+"UserId"+"="+userid, new Response.Listener<String>() {
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

                        if (status_code.equals("216")) {

                            statusMessage = (String) jsonObj.get("userMessage");
                            System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                            statusMessage = (String) jsonObj.get("statusMessage");
                            System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                            userMessage = (String) jsonObj.get("userMessage");
                            System.out.println("Check userMessage of Login Activity:" + userMessage);

                            internalMessage = (String) jsonObj.get("internalMessage");
                            System.out.println("Check Customer_Email of Login Activity:" + internalMessage);

                            JSONObject user = jsonObj.getJSONObject("serviceData");

                           String userID = user.getString("userID");

                            JSONArray oldQueue = user.getJSONArray("oldQueue");

                            JSONArray Queuelive = user.getJSONArray("liveQueue");

                            System.out.println("Businessstatus" + Queuelive.toString());

                            if (!oldQueue.isNull(0)){
                                oldQueueLists.clear();
                                for (int i = 0; i < oldQueue.length(); i++) {
                                    OldQueueList oldQueueList = new OldQueueList();
                                    rv_oldQueue.setVisibility(View.VISIBLE);
                                    JSONObject Queueold = oldQueue.getJSONObject(i);
                                    oldQueueList.setBusinessName(Queueold.getString("businessName"));
                                    oldQueueList.setProviderTitele(Queueold.getString("providerTitele"));
                                    oldQueueList.setServiceName(Queueold.getString("serviceName"));
                                    oldQueueList.setQueueNumber(Queueold.getString("queueNumber"));
                                    oldQueueList.setDate(Queueold.getString("date"));
                                    oldQueueList.setStatus(Queueold.getInt("status"));
                                    oldQueueLists.add(oldQueueList);

                                }

                                OldQueueAdapter adapter = new OldQueueAdapter(HomeActivity.this,oldQueueLists);
                                //setting adapter to recyclerview
                                rv_oldQueue.setAdapter(adapter);// notify adapter

                            }else {
                                lin_empty.setVisibility(View.VISIBLE);
                                rv_oldQueue.setVisibility(View.GONE);
                                txt_nodata.setText("Oops! Sorry nothing to show");
                                if (!HomeActivity.this.isFinishing()) {
                                    Glide.with(HomeActivity.this).load(R.drawable.nodata).into(imageView);
                                }

                            }

                            mImageView.removeAllViews();
                            for (int i = 0; i < Queuelive.length(); i++) {
                                JSONObject liveQueue = Queuelive.getJSONObject(i);
                                String currentQueueNo = liveQueue.getString("currentQueue");
                             //   txt_current_queue_number.setText(currentQueueNo);

                                  //  queueNumber =  liveQueue.getString("queueNumber");
                                   // txt_my_queue_number.setText(queueNumber);
                                    queueNumber =  liveQueue.getString("queueNumber");
                                    int customerId =  liveQueue.getInt("customerId");
                                    int providerId =  liveQueue.getInt("providerId");
                                    businessName =  liveQueue.getString("businessName");
                                    //txt_waiting_business_name.setText(businessName);
                                    providerTitele =  liveQueue.getString("providerTitele");
                                    date =  liveQueue.getString("date");
                                    serviceName =  liveQueue.getString("serviceName");
                                  //  txt_waiting_srvice_name.setText(providerTitele+","+"\t"+serviceName);
                                    int status =  liveQueue.getInt("status");
                                    int myQueueWaitingmy =  liveQueue.getInt("myQueueWaiting");
                                    String myQueueWaitings = Integer.toString(myQueueWaitingmy);
                                   // txt_waiting.setText(myQueueWaitings);

                                      floatingview = inflater.inflate(R.layout.layout_floating, null);
                                      txt_my_queue_number = floatingview.findViewById(R.id.txt_my_queue_number);
                                      txt_current_queue_number = floatingview.findViewById(R.id.txt_current_queue_number);
                                      txt_cancel_queue_number = floatingview.findViewById(R.id.txt_cancel_queue_number);
                                      txt_current_queue_number.setText(currentQueueNo);
                                      txt_my_queue_number.setText(queueNumber);
                                      txt_waiting = floatingview.findViewById(R.id.txt_waiting_queue_number);
                                      txt_waiting.setText(myQueueWaitings);
                                      txt_waiting_business_name  = floatingview.findViewById(R.id.txt_waiting_business_name);
                                      txt_waiting_business_name.setText(businessName);
                                      txt_waiting_srvice_name = floatingview.findViewById(R.id.txt_waiting_srvice_name);
                                      txt_waiting_srvice_name.setText(providerTitele+","+"\t"+serviceName);

                                txt_cancel_queue_number.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(HomeActivity.this,QueueConformationActivity.class).putExtra("queueNumber",queueNumber));
                                        finish();
                                    }
                                });

                                    if (queueNumber != null){
                                        mImageView.setVisibility(View.VISIBLE);
                                    }else {
                                        mImageView.setVisibility(View.GONE);
                                    }
                                    if (currentQueueNo.equals(queueNumber)){
                                        txt_waiting.setText("LIVE");
                                        txt_cancel_queue_number.setVisibility(View.GONE);
                                        Animation anim = new AlphaAnimation(0.0f, 1.0f);
                                        anim.setDuration(100); //You can manage the time of the blink with this parameter
                                        anim.setStartOffset(20);
                                        anim.setRepeatMode(Animation.START_ON_FIRST_FRAME);
                                        anim.setRepeatCount(Animation.INFINITE);
                                        txt_waiting.startAnimation(anim);
                                    }


                                mImageView.addView(floatingview);


                            }

                        }else{
                            userMessage = (String) jsonObj.get("userMessage");
                            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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

                    lin_empty.setVisibility(View.VISIBLE);
                    rv_oldQueue.setVisibility(View.GONE);
                    if (!HomeActivity.this.isFinishing()) {
                        Glide.with(HomeActivity.this).load(R.raw.empty_data).into(imageView);
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error is ", "" + error);
                lin_empty.setVisibility(View.VISIBLE);
                rv_oldQueue.setVisibility(View.GONE);
                if (!HomeActivity.this.isFinishing()) {
                    Glide.with(HomeActivity.this).load(R.raw.empty_data).into(imageView);
                }
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
        Log.d("TAG", request.toString());
    }


    //  floatingview = inflater.inflate(R.layout.layout_floating, null);
    //  txt_my_queue_number = floatingview.findViewById(R.id.txt_my_queue_number);
    //  txt_current_queue_number = floatingview.findViewById(R.id.txt_current_queue_number);
    //  txt_cancel_queue_number = floatingview.findViewById(R.id.txt_cancel_queue_number);
    //   txt_current_queue_number.setText(currentQueueNo);
                              //  txt_my_queue_number.setText(queueNumber);

    //  mImageView.addView(floatingview);
}






