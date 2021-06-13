package com.zealoit.eqz;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zealoit.eqz.Adapter.ExpandableListViewAdapter;
import com.zealoit.eqz.Utils.BottomNavigationViewHelper;
import com.zealoit.eqz.Utils.FontChangeCrawler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelpAndSupport extends AppCompatActivity {

    private ExpandableListView expandableListView;
    public ExpandableListAdapter expandableListViewAdapter;
    private List<String> listDataGroup;
    private HashMap<String, List<String>> listDataChild;

    BottomNavigationView bottomNavigationView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        TextView text = (TextView) findViewById(R.id.txt_frq);
        Typeface tf = Typeface.createFromAsset(getAssets(), "gothicb.ttf");
        text.setTypeface(tf);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "CenturyGothic.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        // initializing the views
        initViews();
        // initializing the listeners
        initListeners();
        // initializing the objects
        initObjects();
        // preparing list data
        initListData();
        setupNavigationView();
    }

    /**
     * method to initialize the views
     */
    private void initViews() {
        expandableListView = findViewById(R.id.simpleExpandableListView);
    }
    /**
     * method to initialize the listeners
     */
    private void initListeners() {
        // ExpandableListView on child click listener
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listDataGroup.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataGroup.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
        // ExpandableListView Group expanded listener
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
              //  Toast.makeText(getApplicationContext(),
                    //    listDataGroup.get(groupPosition) + " " + getString(R.string.text_collapsed),
                     //   Toast.LENGTH_SHORT).show();
            }
        });
        // ExpandableListView Group collapsed listener
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
               // Toast.makeText(getApplicationContext(),
                      //  listDataGroup.get(groupPosition) + " " + getString(R.string.text_collapsed),
                      //  Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * method to initialize the objects
     */
    private void initObjects() {
        // initializing the list of groups
        listDataGroup = new ArrayList<>();
        // initializing the list of child
        listDataChild = new HashMap<>();
        // initializing the adapter object
        expandableListViewAdapter = new ExpandableListViewAdapter(this, listDataGroup, listDataChild);
        // setting list adapter

        expandableListView.setAdapter(expandableListViewAdapter);

    }
    /*
     * Preparing the list data
     *
     * Dummy Items
     */
    private void initListData() {
        // Adding group data
        listDataGroup.add(getString(R.string.text_alcohol));
        listDataGroup.add(getString(R.string.text_coffee));
        listDataGroup.add(getString(R.string.text_pasta));
        listDataGroup.add(getString(R.string.text_cold_drinks));
        listDataGroup.add(getString(R.string.text_can));
        listDataGroup.add(getString(R.string.text_cold_cancel));
        listDataGroup.add(getString(R.string.text_cold_queue));
        // array of strings
        String[] array;
        // list of alcohol
        List<String> alcoholList = new ArrayList<>();
        array = getResources().getStringArray(R.array.string_array_alcohol);
        for (String item : array) {
            alcoholList.add(item);
        }
        // list of coffee
        List<String> coffeeList = new ArrayList<>();
        array = getResources().getStringArray(R.array.string_array_coffee);
        for (String item : array) {
            coffeeList.add(item);
        }
        // list of pasta
        List<String> pastaList = new ArrayList<>();
        array = getResources().getStringArray(R.array.string_array_pasta);
        for (String item : array) {
            pastaList.add(item);
        }
        // list of cold drinks
        List<String> coldDrinkList = new ArrayList<>();
        array = getResources().getStringArray(R.array.string_array_cold_drinks);
        for (String item : array) {
            coldDrinkList.add(item);
        }
        // list of cold multilist
        List<String> multulist = new ArrayList<>();
        array = getResources().getStringArray(R.array.string_array_multiple_drinks);
        for (String item : array) {
            multulist.add(item);
        }
        // list of cold cancellist
        List<String> cancellist = new ArrayList<>();
        array = getResources().getStringArray(R.array.string_array_cancel_drinks);
        for (String item : array) {
            cancellist.add(item);
        }
        // list of cold honourlist
        List<String> honourlist = new ArrayList<>();
        array = getResources().getStringArray(R.array.string_array_honou_drinks);
        for (String item : array) {
            honourlist.add(item);
        }
        // Adding child data
        listDataChild.put(listDataGroup.get(0), alcoholList);
        listDataChild.put(listDataGroup.get(1), coffeeList);
        listDataChild.put(listDataGroup.get(2), pastaList);
        listDataChild.put(listDataGroup.get(3), coldDrinkList);
        listDataChild.put(listDataGroup.get(4), multulist);
        listDataChild.put(listDataGroup.get(5), cancellist);
        listDataChild.put(listDataGroup.get(6), honourlist);
        // notify the adapter
        expandableListViewAdapter= new ExpandableListViewAdapter(this, listDataGroup, listDataChild);
        // setting list adapter

        expandableListView.setAdapter(expandableListViewAdapter);
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
                Intent intent = new Intent(HelpAndSupport.this, HomeActivity.class);
                startActivity(intent);
                break;

            case R.id.action_user:
                Intent intent2 = new Intent(HelpAndSupport.this, ActivityAccountList.class);
                startActivity(intent2);
                break;
        }
        // return loadFragment(fragment);
    }


    public void back(View view) {
        Intent intent = new Intent();
        intent.setClass(HelpAndSupport.this, ActivityAccountList.class);
        startActivity(intent);
    }
}
