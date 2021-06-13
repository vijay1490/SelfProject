package com.zealoit.eqz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zealoit.eqz.Utils.Constants;
import com.zealoit.eqz.Utils.FontChangeCrawler;
import com.zealoit.eqz.Utils.MyFunctions;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConformPinActivity extends AppCompatActivity {

    @BindViews({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn_clear})
    List<View> btnNumPads;

    @BindViews({R.id.dot_1, R.id.dot_2, R.id.dot_3, R.id.dot_4})
    List<ImageView> dots;

    String TRUE_CODE , pincode  ;
    String pin = "";
    private static final int MAX_LENGHT = 4;
    private String codeString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conform_pin);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "CenturyGothic.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        pincode = TRUE_CODE = MyFunctions.getSharedPrefs(ConformPinActivity.this, Constants.SETPIN,"");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_clear)
    public void onClear() {
        if (codeString.length() > 0) {
            //remove last character of code
            codeString = removeLastChar(codeString);

            //update dots layout
            setDotImagesState();
        }
    }

    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9})
    public void onClick(Button button) {
        getStringCode(button.getId());

        pin = pin + button.getText().toString();

        if (codeString.length() == MAX_LENGHT) {
            if (pin.equals(pincode)){
                Intent intent = new Intent(ConformPinActivity.this, LockActivity.class);
                startActivity(intent);
                MyFunctions.setSharedPrefs(ConformPinActivity.this, Constants.TRUE_CODE, pin);
                finish();
            }else {
                shakesAnimation();
                pin = "";

            }

        } else if (codeString.length() > MAX_LENGHT){
            //reset the input code
            codeString = "";
            getStringCode(button.getId());
        }
        setDotImagesState();
    }

    private void shakesAnimation() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.vibrate_anim);
        findViewById(R.id.dot_layout).startAnimation(shake);
        Toast.makeText(ConformPinActivity.this, "Pin not matched!!!", Toast.LENGTH_SHORT).show();
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.vibrate_anim);
        findViewById(R.id.dot_layout).startAnimation(shake);
        Toast.makeText(this, "Filled All", Toast.LENGTH_SHORT).show();
    }

    public void back(View view) {
        Intent intent = new Intent();
        intent.setClass(ConformPinActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    private void getStringCode(int buttonId) {
        switch (buttonId) {
            case R.id.btn0:
                codeString += "0";
                break;
            case R.id.btn1:
                codeString += "1";
                break;
            case R.id.btn2:
                codeString += "2";
                break;
            case R.id.btn3:
                codeString += "3";
                break;
            case R.id.btn4:
                codeString += "4";
                break;
            case R.id.btn5:
                codeString += "5";
                break;
            case R.id.btn6:
                codeString += "6";
                break;
            case R.id.btn7:
                codeString += "7";
                break;
            case R.id.btn8:
                codeString += "8";
                break;
            case R.id.btn9:
                codeString += "9";
                break;
            default:
                break;
        }
    }

    private void setDotImagesState() {
        for (int i = 0; i < codeString.length(); i++) {
            dots.get(i).setImageResource(R.drawable.dot_enable);
        }
        if (codeString.length()<4) {
            for (int j = codeString.length(); j<4; j++) {
                dots.get(j).setImageResource(R.drawable.dot_disable);
            }
        }
    }

    private String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length() - 1);
    }

    private void setIsPass() {
        SharedPreferences.Editor editor = getSharedPreferences("PASS_CODE", MODE_PRIVATE).edit();
        editor.putBoolean("is_pass", true);
        editor.apply();
    }
}




