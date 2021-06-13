 package com.zealoit.eqz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.zealoit.eqz.Utils.Constants;
import com.zealoit.eqz.Utils.MyFunctions;
import com.zealoit.eqz.Utils.PrefManager;
import com.zealoit.eqz.Utils.ServiceHandler;

import org.json.JSONException;
import org.json.JSONObject;

import static com.zealoit.eqz.Utils.Constants.NGROK_BASE_URL;

public class LoginActivity extends AppCompatActivity {
   // ImageView img_profile;
    LinearLayout img_profile;
    EditText edit_usermobile;
    String usermobile , msg;
    private String reurl = NGROK_BASE_URL;
    ProgressDialog pDialog;

    private PrefManager prefManager;
    String status_code ,userid ,mobileno,serviceData ,participantid,otpnumber, statusMessage , userMessage ,internalMessage ,useid , particpantid ,user_email ,user_contact_no;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        img_profile = findViewById(R.id.img_profile);
        edit_usermobile = findViewById(R.id.edit_usermobile);

        Bitmap mbitmap=((BitmapDrawable) LoginActivity.this.getResources().getDrawable(R.drawable.asvatar)).getBitmap();
        Bitmap imageRounded=Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas=new Canvas(imageRounded);
        Paint mpaint=new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 10, 10, mpaint); // Round Image Corner 100 100 100 100
        //  holder.linmatch.setBackgroundResource(imageRounded);

       img_profile.setBackground(new BitmapDrawable(getApplicationContext().getResources(), imageRounded));

        if(MyFunctions.getSharedPrefs(LoginActivity.this, Constants.IS_LOGIN,false)) {
            if (MyFunctions.getSharedPrefs(LoginActivity.this, "userid", "").length() > 0) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
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
                        msg = token;
                        MyFunctions.setSharedPrefs(LoginActivity.this, Constants.TOKEN,msg);
                        Log.d("TAG", msg);
                       // Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void login(View view) {
        usermobile = edit_usermobile.getText().toString();

        mobileno = usermobile;

        if ((mobileno.equals("")) || (mobileno.toString().trim().length() < 10)) {
            edit_usermobile.setError("Invalid Mobile");
        }else {
            new ValidateAccount().execute();
        }

       // startActivity(new Intent(LoginActivity.this,OtpActivity.class).putExtra("details",statusMessage).putExtra("mobileno",mobileno));
        //  finish();


    }

    public void startreg(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        finish();
    }

    public void end(View view) {
        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
        finish();
    }




    // Async task class to get json by making HTTP call start
    private class ValidateAccount extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            String jsonStr = sh.makeServiceCall(reurl+"Authenticate/ValidateAccount?"+"MobileNumber="+mobileno+"&APIKEY="+"15JRAKYTGQMXTH967COKVV27F", ServiceHandler.POST);

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

                    status_code = jsonObj.getString("statusCode");

                    if(status_code.equals("200")){

                        userMessage = (String) jsonObj.get("userMessage");
                            System.out.println("Check statusMessage of Login Activity:" + statusMessage);
                          //  startActivity(new Intent(LoginActivity.this,OtpActivity.class).putExtra("details",statusMessage).putExtra("mobileno",mobileno));
                          //  finish();

                        serviceData = (String) jsonObj.get("serviceData");

                        Toast.makeText(LoginActivity.this, userMessage, Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginActivity.this,RegisterActivity.class).putExtra("mobilenumber",serviceData));
                        finish();


//                        editor.putString("userid",useid);
                        //    editor.putString("pariticipantid",particpantid);

//                        editor.commit();
                    }else if (status_code.endsWith("215")){
                        serviceData = (String) jsonObj.get("serviceData");
                        startActivity(new Intent(LoginActivity.this,OtpActivity.class).putExtra("details",serviceData));
                        finish();

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
