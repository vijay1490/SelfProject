package com.zealoit.eqz;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.zealoit.eqz.Utils.BottomNavigationViewHelper;
import com.zealoit.eqz.Utils.FontChangeCrawler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BusinessProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_user);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "CenturyGothic.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        setupNavigationView();
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
                Intent intent = new Intent(BusinessProfileActivity.this, HomeActivity.class);
                startActivity(intent);
                break;

            case R.id.action_user:
                Intent intent2 = new Intent(BusinessProfileActivity.this, ActivityAccountList.class);
                startActivity(intent2);
                break;
        }
        // return loadFragment(fragment);
    }


    public void back(View view) {
        Intent intent = new Intent();
        intent.setClass(BusinessProfileActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void edit(View view) {
       // Intent intent = new Intent();
       // intent.setClass(BusinessProfileActivity.this, ActivityEstablishement.class);
       // startActivity(intent);
    }
}
