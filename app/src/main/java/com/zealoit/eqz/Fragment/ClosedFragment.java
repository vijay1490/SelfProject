package com.zealoit.eqz.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.zealoit.eqz.Adapter.GetAllBusinessProviderAdapter;
import com.zealoit.eqz.List.GetAllBusinessProviderList;
import com.zealoit.eqz.R;
import com.zealoit.eqz.Utils.Constants;
import com.zealoit.eqz.Utils.FontChangeCrawler;
import com.zealoit.eqz.Utils.MyFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zealoit.eqz.Utils.Constants.NGROK_BASE_URL;

public class ClosedFragment extends Fragment {
    String latitude , longitude , locationAddress;
    TextView txt_nodata;
    EditText ET_search;
    RequestQueue requestQueue;
    String status_code, userid, mobileno, quser, servicetype_name, servicetype_id, AuthorizationToken , servicetype_icon, servicegroup_id, status, created_on, statusMessage, userMessage, internalMessage, created_by, particpantid, user_email, user_contact_no;
    private String reurl = NGROK_BASE_URL;
    List<GetAllBusinessProviderList> getAllBusinessProviderLists;
    RecyclerView rv_getallbusinessprovider;
    LinearLayout lin_empty;
    ImageView imageView;
    public ClosedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewlayout = inflater.inflate(R.layout.fragment_closed, container, false);
        String Token_Authorization = MyFunctions.getSharedPrefs(getActivity(), Constants.USERTYPE,"");
        AuthorizationToken = Token_Authorization;
        userid = MyFunctions.getSharedPrefs(getActivity(), Constants.USER_ID, "");
        latitude = MyFunctions.getSharedPrefs(getActivity(), Constants.LATITUDE, "");
        longitude  = MyFunctions.getSharedPrefs(getActivity(), Constants.LONGITUDE, "");
        rv_getallbusinessprovider = (RecyclerView)viewlayout.findViewById(R.id.rv_getallbusinessprovider);
        lin_empty =  viewlayout.findViewById(R.id.lin_empty);
        ET_search =     viewlayout.findViewById(R.id.ET_search);
        txt_nodata =  viewlayout.findViewById(R.id.txt_nodata);
        imageView = (ImageView) viewlayout.findViewById(R.id.imganims);
        rv_getallbusinessprovider.setHasFixedSize(true);
        rv_getallbusinessprovider.setLayoutManager(new LinearLayoutManager(getContext()));
        requestQueue = Volley.newRequestQueue(getActivity());
        getAllBusinessProviderLists = new ArrayList<>();
        if(container == null)
        {
            FontChangeCrawler fontChanger = new FontChangeCrawler(getActivity().getAssets(), "CenturyGothic.ttf");
            fontChanger.replaceFonts((ViewGroup) viewlayout.findViewById(android.R.id.content));
        }

        ET_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());

                // filters(editable.toString());
            }
        });
        FetchAllBusiness();
        return viewlayout;
    }



    public void FetchAllBusiness(){

        StringRequest request = new StringRequest(Request.Method.GET, reurl+"Business/GetAllBusinessProvider?APIKEY=15JRAKYTGQMXTH967COKVV27F", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals(null)) {
                    try {
                        Log.d("TAG", response.toString());
                        JSONObject jsonObj = new JSONObject(response);
                        System.out.println("Json string is:" + jsonObj);

                        // JSONArray jsonArray = jsonObj.getJSONArray("serviceData");

/*
                    String resultStr = jsonObj.getString("AddRegistrationResult");
*/
                        //  JSONObject jsonObject = jsonObj.getJSONObject("response");

                        status_code = jsonObj.getString("statusCode");

                        if (status_code.equals("225")) {

                            statusMessage = (String) jsonObj.get("userMessage");
                            System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                            statusMessage = (String) jsonObj.get("statusMessage");
                            System.out.println("Check statusMessage of Login Activity:" + statusMessage);

                            userMessage = (String) jsonObj.get("userMessage");
                            System.out.println("Check userMessage of Login Activity:" + userMessage);

                            internalMessage = (String) jsonObj.get("internalMessage");
                            System.out.println("Check Customer_Email of Login Activity:" + internalMessage);
                            JSONObject user = jsonObj.getJSONObject("serviceData");
                            System.out.println("Check  usertype of Login Activity:" + servicetype_name);

                            JSONArray businessOpen = user.getJSONArray("businessClose");
                            if (businessOpen != null){
                                for (int i = 0 ; i < businessOpen.length() ; i++){
                                    GetAllBusinessProviderList getAllBusinessProviderList = new GetAllBusinessProviderList();
                                    rv_getallbusinessprovider.setVisibility(View.VISIBLE);
                                    JSONObject BusinessProviderList = businessOpen.getJSONObject(i);
                                    getAllBusinessProviderList.setBusiness_id(BusinessProviderList.getInt("business_id"));
                                    getAllBusinessProviderList.setBusiness_name(BusinessProviderList.getString("business_name"));
                                    getAllBusinessProviderList.setQueueCount(BusinessProviderList.getInt("queueCount"));
                                    getAllBusinessProviderList.setBusiness_area(BusinessProviderList.getString("business_area"));
                                    getAllBusinessProviderList.setStatus(BusinessProviderList.getInt("status"));
                                    getAllBusinessProviderLists.add(getAllBusinessProviderList);
                                }

                                GetAllBusinessProviderAdapter adapter = new GetAllBusinessProviderAdapter(getContext() ,getAllBusinessProviderLists);
                                //setting adapter to recyclerview
                                rv_getallbusinessprovider.setAdapter(adapter);// notify adapter

                            }else {
                                lin_empty.setVisibility(View.VISIBLE);
                                rv_getallbusinessprovider.setVisibility(View.GONE);
                                txt_nodata.setText("Oops! Sorry nothing to show");
                                Glide.with(getActivity()).load(R.drawable.nodata).into(imageView);
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
                lin_empty.setVisibility(View.VISIBLE);
                rv_getallbusinessprovider.setVisibility(View.GONE);
                Glide.with(getActivity()).load(R.raw.empty_data).into(imageView);
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


    private void filter(String text) {
        //new array list that will hold the filtered data
        List<GetAllBusinessProviderList>filterdNames = new ArrayList<>();

        //looping through existing elements
        for (GetAllBusinessProviderList s : getAllBusinessProviderLists) {
            //if the existing elements contains the search input
            if (s.getBusiness_name().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        rv_getallbusinessprovider.setAdapter(new GetAllBusinessProviderAdapter(getActivity(), filterdNames));
    }
}
