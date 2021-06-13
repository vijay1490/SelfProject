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
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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

public class RegisterActivity extends AppCompatActivity {

    ImageView  imageView , imageViews , imageViewss , imganimsss;

    LinearLayout img_profile;

    EditText edit_username ,  edit_useremail , edit_usermobile;

    String EMAIL , Name , MOBILENUMBER , msg ;

    private String reurl = NGROK_BASE_URL;
    ProgressDialog pDialog;

    private PrefManager prefManager;
    String status_code ,userid ,mobileno,quser ,participantid,otpnumber, statusMessage , userMessage ,internalMessage ,useid , particpantid ,user_email ,user_contact_no;

    private Thread mSplashThread;

    @Override
    public void onCreate(Bundle savedInstancestate){
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_register);
        img_profile = findViewById(R.id.img_profile);
        edit_username = findViewById(R.id.edit_username);
        edit_useremail = findViewById(R.id.edit_useremail);
        edit_usermobile = findViewById(R.id.edit_usermobile);
        String mobilenumber = getIntent().getStringExtra("mobilenumber");
        edit_usermobile.setText(mobilenumber);

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
                         MyFunctions.setSharedPrefs(RegisterActivity.this,Constants.TOKEN,msg);
                        Log.d("TAG", msg);
                      //Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        /*Glide.with(RegisterActivity.this).load(R.drawable.avatar)
                .circleCrop()
                //  .thumbnail(0.5f)
                //  .bitmapTransform(new Circul(ActivityHome.this))
                //  .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_profile);*/

        Bitmap mbitmap=((BitmapDrawable) RegisterActivity.this.getResources().getDrawable(R.drawable.asvatar)).getBitmap();
        Bitmap imageRounded=Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas=new Canvas(imageRounded);
        Paint mpaint=new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 10, 10, mpaint); // Round Image Corner 100 100 100 100
        //  holder.linmatch.setBackgroundResource(imageRounded);

        img_profile.setBackground(new BitmapDrawable(getApplicationContext().getResources(), imageRounded));

        edit_username.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(s.length() > 0)
                {
                    Name = edit_username.getText().toString();
                    if(Name.length()==0 ||(Name.toString().trim().length() < 3))
                    {
                        edit_username.requestFocus();
                        edit_username.setError("FIELD CANNOT BE EMPTY");
                    }
                    else if(!Name.matches("[a-zA-Z ]+"))
                    {
                        edit_username.requestFocus();
                        edit_username.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                    }
                    else
                    {
                        imageView = (ImageView) findViewById(R.id.imganim);
                        imageView.setVisibility(View.VISIBLE);
                        Glide.with(RegisterActivity.this).load(R.raw.blue_tick).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                if (resource instanceof GifDrawable) {
                                    ((GifDrawable) resource).setLoopCount(1);
                                }
                                return false;
                            }
                        }).into(imageView);
                    }

                }
            }
        });


        edit_useremail.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(s.length() > 0)
                {
                    EMAIL = edit_useremail.getText().toString();
                    if (EMAIL.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()) {
                        edit_useremail.setError("Invalid Email");

                    }else {
                        imageViews = (ImageView) findViewById(R.id.imganims);
                        imageViews.setVisibility(View.VISIBLE);
                        Glide.with(RegisterActivity.this).load(R.raw.blue_tick).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                if (resource instanceof GifDrawable) {
                                    ((GifDrawable) resource).setLoopCount(1);
                                }
                                return false;
                            }
                        }).into(imageViews);

                    }
                }
            }
        });


        edit_usermobile.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(s.length() > 0)
                {
                    MOBILENUMBER = edit_usermobile.getText().toString();
                    if ((MOBILENUMBER.equals("")) && (MOBILENUMBER.toString().trim().length() < 10)) {
                        edit_usermobile.setError("Invalid Mobile");
                    }else {
                        imageViewss = (ImageView) findViewById(R.id.imganimss);
                       // imganimsss = (ImageView) findViewById(R.id.imganimsss);
                        imageViewss.setVisibility(View.VISIBLE);
                       // imganimsss.setVisibility(View.VISIBLE);
                        Glide.with(RegisterActivity.this).load(R.raw.blue_tick).into(imageViewss);
                       // Glide.with(RegisterActivity.this).load(R.raw.bubble).into(imganimsss);

                    }
                }
            }
        });

    }

    public void register(View view) {
        MOBILENUMBER = edit_usermobile.getText().toString();
       // USERNAME = edit_username.getText().toString();
      //  USEREMAIL = edit_useremail.getText().toString();
        MyFunctions.setSharedPrefs(RegisterActivity.this, Constants.MOBILE,MOBILENUMBER);


        mobileno = MOBILENUMBER;
        new Register().execute();


          //  startActivity(new Intent(RegisterActivity.this,OtpActivity.class).putExtra("REG","REG"));
        //    finish();

    }

    public void end(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }

    public void start(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();

    }


    // Async task class to get json by making HTTP call start
    private class Register extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(RegisterActivity.this);
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

                inner.put("username",Name );
                inner.put("email",EMAIL);
                inner.put("phoneNumber",MOBILENUMBER);
                inner.put("fcmToken", msg);
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
            String jsonStr = sh.makePOSTServiceCall(reurl+"Authenticate/Register?APIKEY=15JRAKYTGQMXTH967COKVV27F", ServiceHandler.POST);

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

                    if(status_code.equals("202")){

                        statusMessage = (String) jsonObj.get("userMessage");
                        System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                        Toast.makeText(RegisterActivity.this, statusMessage, Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(RegisterActivity.this,OtpSuccessActivity.class).putExtra("details",mobileno).putExtra("mobileno",mobileno));
                        finish();


                      //  startActivity(new Intent(RegisterActivity.this,OtpActivity.class).putExtra("details",statusMessage).putExtra("mobileno",mobileno));
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
