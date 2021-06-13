package com.zealoit.eqz;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.bumptech.glide.Glide;
import com.zealoit.eqz.Utils.BottomNavigationViewHelper;
import com.zealoit.eqz.Utils.Constants;
import com.zealoit.eqz.Utils.FontChangeCrawler;
import com.zealoit.eqz.Utils.MyFunctions;
import com.zealoit.eqz.Utils.RequestPermissionHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import static com.zealoit.eqz.Utils.Constants.NGROK_BASE_URL;

public class ActivityAccountList extends AppCompatActivity implements ImageChooserListener {
    TextView version , txt_customer_name , txt_customer_email_name;
    ImageChooserManager imageChooserManager;
    private int chooserType;
    String filePath, uploadImagePath, uploadImageFile = "", selectedCity = "", selectedCityName = "";
    private final int CAMERA_REQUEST = 20;
    private static int RESULT_LOAD_IMAGE = 1;
    ImageView imyg_profile;
    BottomNavigationView bottomNavigationView;
    String upload = "";
    long totalSize = 0;
    private String reurl = NGROK_BASE_URL;
    RequestQueue requestQueue;
    String status_code, userid, mobileno, quser, email , otp , mobilenumber , servicetype_name, MobileNumber , AuthorizationToken , servicetype_id, servicetype_icon, servicegroup_id, status, created_on, statusMessage, userMessage, internalMessage, created_by, particpantid, user_email, user_contact_no;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "CenturyGothic.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        txt_customer_name = findViewById(R.id.txt_customer_name);
        txt_customer_email_name = findViewById(R.id.txt_customer_email_name);
        email = MyFunctions.getSharedPrefs(ActivityAccountList.this, Constants.EMAIL, "");
        txt_customer_email_name.setText(email);
        String business_name = MyFunctions.getSharedPrefs(ActivityAccountList.this, Constants.USERNAME, "");
        txt_customer_name.setText(business_name);
        requestQueue = Volley.newRequestQueue(ActivityAccountList.this);
        String Token_Authorization = MyFunctions.getSharedPrefs(ActivityAccountList.this, Constants.USERTYPE, "");
        userid = MyFunctions.getSharedPrefs(ActivityAccountList.this, Constants.USER_ID, "");
        AuthorizationToken = Token_Authorization;
        MobileNumber = MyFunctions.getSharedPrefs(ActivityAccountList.this, Constants.MOBILE, "");
        version = findViewById(R.id.version);
        int versionCode = BuildConfig.VERSION_CODE;
        String versions = Integer.toString(versionCode);
        version.setText("Version"+"\t"+versions);
        imyg_profile = findViewById(R.id.imyg_profile);
        imyg_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                RequestPermissionHandler.requestPermission(ActivityAccountList.this, permissions,1, new RequestPermissionHandler.RequestPermissionListener() {
                    @Override
                    public void onSuccess() {
                        takePicture();
                    }

                    @Override
                    public void onFailed() {
                        // MyFunctions.toastShort(getActivity(),"Permission denied to open camera");
                    }
                } );
            }
        });

        setupNavigationView();
    }

    public void myaccount(View view) {
        Intent intent = new Intent();
        intent.setClass(ActivityAccountList.this, AcountActivity.class);
        startActivity(intent);
    }

    public void back(View view) {
        Intent intent = new Intent();
        intent.setClass(ActivityAccountList.this, HomeActivity.class);
        startActivity(intent);
    }


    private void takePicture() {
        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_CAPTURE_PICTURE, true);
        imageChooserManager.setImageChooserListener(ActivityAccountList.this);
        try {

            filePath = imageChooserManager.choose();
            //  Toast.makeText(ProfileActivity.this, filePath, Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chooseImage() {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, true);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.clearOldFiles();
        try {

            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType, true);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                case ChooserType.REQUEST_PICK_PICTURE:
                case ChooserType.REQUEST_CAPTURE_PICTURE:
                    if (imageChooserManager == null) {
                        reinitializeImageChooser();
                    }
                    imageChooserManager.submit(requestCode, data);
                    break;
            }
        }
    }


    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }


    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String TAG = "ICA";
                Log.i(TAG, "Chosen Image: O - " + image.getFilePathOriginal());
                Log.i(TAG, "Chosen Image: T - " + image.getFileThumbnail());
                Log.i(TAG, "Chosen Image: Ts - " + image.getFileThumbnailSmall());


                uploadImagePath = image.getFilePathOriginal();

                //  uploadImagePath = image.getFileThumbnailSmall();
                // uploadImagePath = MyFunctions.compressImage(ProfileFragment.this,image.getFilePathOriginal(),80);
                // Bitmap bitmap = BitmapFactory.decodeFile(tt);

                //encode image to base64 string
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap = BitmapFactory.decodeFile(uploadImagePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                upload = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                // uploadImagePath = bitmap.toString();

                if (uploadImagePath != null) {
                    // uploadImagePath = MyFunctions.convertImageToString(bitmap);
                    // loadImage(updateprofileIMG, image.getFileThumbnail());
                    Log.i(TAG, "Chosen Image: Is not null");
                  //  new UploadFileToServer().execute();
                    Upload();

                    Glide.with(ActivityAccountList.this).load(uploadImagePath)
                            .circleCrop()
                            //  .thumbnail(0.5f)
                            //  .bitmapTransform(new Circul(ActivityHome.this))
                            //  .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imyg_profile);
                }
                //  if (uploadImagePath != null) {

                //loadImage(updateprofileIMG, image.getFileThumbnail());
                // }
                else {
                    Log.i(TAG, "Chosen Image: Is null");
                }
            }
        });
    }

    @Override
    public void onError(String s) {
        //  MyFunctions.toastShort(this, s);
        Toast.makeText(getApplicationContext(), "Image Upload Not Done", Toast.LENGTH_SHORT).show();
    }




    // Async task class to get json by making HTTP call end
    private void Upload() {
        // Bitmap bitmap = BitmapFactory.decodeFile(uploadImagePath);

        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("userImage",upload);
            object.put("userId",userid);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                reurl+"User/UpdateProfileImage?APIKEY=15JRAKYTGQMXTH967COKVV27F" , object,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("TAG", response.toString());
                            status_code = response.getString("statusCode");
                            if(status_code.equals("205")) {

                                statusMessage = (String) response.get("userMessage");
                                System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                                statusMessage = (String) response.get("statusMessage");
                                System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                                userMessage = (String) response.get("userMessage");
                                System.out.println("Check userMessage of Login Activity:" + userMessage);

                                internalMessage = (String) response.get("internalMessage");
                                System.out.println("Check Customer_Email of Login Activity:" + internalMessage);

                                JSONObject user = response.getJSONObject("serviceData");


                                // Intent intent = new Intent(AcountActivity.this, HomeActivity.class);
                                // startActivity(intent);
                                // finish();

                                // business_id = (Integer) user.get("business_id");

                                //  provider_status = (Integer) user.get("provider_status");
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
    }

    public void setting(View view) {
        startActivity(new Intent(ActivityAccountList.this,ActivitySettingOptions.class));
        finish();
    }

    public void helpandsupport(View view) {
        Intent intent2 = new Intent(ActivityAccountList.this, HelpAndSupport.class);
        startActivity(intent2);
        finish();
    }

    public void logout(View view) {
        final Dialog dialog = new Dialog(ActivityAccountList.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_logout);

        Button yes = (Button) dialog.findViewById(R.id.btn_yes);
        Button no = (Button) dialog.findViewById(R.id.btn_no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // MyFunctions.setSharedPrefs(ProfileActivity.this, Constants.IS_LOGIN, false);
               // MyFunctions.setSharedPrefs(ProfileActivity.this, Constants.USER_ID, "");
               // MyFunctions.setSharedPrefs(ProfileActivity.this, Constants.CUSTOMERID, "");


                StringRequest request = new StringRequest(Request.Method.GET, reurl+"Authenticate/LogOut?APIKEY=15JRAKYTGQMXTH967COKVV27F"+"&"+"MobileNumber"+"="+MobileNumber, new Response.Listener<String>() {
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

                                if (status_code.equals("209")) {

                                    statusMessage = (String) jsonObj.get("userMessage");
                                    System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                                    statusMessage = (String) jsonObj.get("statusMessage");
                                    System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                                    userMessage = (String) jsonObj.get("userMessage");
                                    System.out.println("Check userMessage of Login Activity:" + userMessage);

                                    internalMessage = (String) jsonObj.get("internalMessage");
                                    System.out.println("Check Customer_Email of Login Activity:" + internalMessage);
                                    JSONObject user = jsonObj.getJSONObject("serviceData");

                                    otp = (String)user.get("otp");
                                    System.out.println("Check  usertype of Login Activity:" + servicetype_name);

                                    mobilenumber = (String)user.get("mobilenumber");
                                    System.out.println("Check  usertype of Login Activity:" + servicetype_name);

                                    Toast.makeText(ActivityAccountList.this, statusMessage, Toast.LENGTH_SHORT).show();
                                    MyFunctions.setSharedPrefs(ActivityAccountList.this, Constants.IS_LOGIN, false);
                                    MyFunctions.setSharedPrefs(ActivityAccountList.this, Constants.USER_ID, "");
                                    MyFunctions.setSharedPrefs(ActivityAccountList.this, Constants.CUSTOMERID, "");
                                    Intent intent = new Intent(ActivityAccountList.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                            Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();

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
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void pastbooking(View view) {
        startActivity(new Intent(ActivityAccountList.this,ActivityPastBooking.class));
        finish();
    }


    public void businessprofile(View view) {
        //startActivity(new Intent(ActivityAccountList.this,BusinessProfileActivity.class));
       // finish();
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
                Intent Saloan = new Intent(ActivityAccountList.this, ActivitySaloan.class);
                startActivity(Saloan);
                break;


            case R.id.action_loudspeaker:
                Intent intent2 = new Intent(ActivityAccountList.this, HomeActivity.class);
                startActivity(intent2);
                break;

            case R.id.action_user:

                break;
        }
        // return loadFragment(fragment);
    }

    public void share(View view) {


        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        Bitmap b =BitmapFactory.decodeResource(getResources(),R.drawable.ezqshare);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, "Title", null);
        Uri imageUri =  Uri.parse(path);
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        whatsappIntent.setType("image/png");
        whatsappIntent.setType("text/plain");
        // whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Image Sending Success");
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            ActivityAccountList.this.startActivity(Intent.createChooser(whatsappIntent, "Share image via:"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
