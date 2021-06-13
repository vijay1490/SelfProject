package com.zealoit.eqz.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.zealoit.eqz.ActivityClinic;
import com.zealoit.eqz.ActivityIndividual;
import com.zealoit.eqz.List.GetAllBusinessProviderList;
import com.zealoit.eqz.R;
import com.zealoit.eqz.Utils.FontChangeCrawler;
import com.zealoit.eqz.Utils.MyFunctions;

import java.util.List;

public class GetAllBusinessProviderAdapter extends RecyclerView.Adapter<GetAllBusinessProviderAdapter.GetAllBusinessProviderViewHolder> {

    //this context we will use to inflate the activity_sign_in
    private Context mCtx;

    public int[] mColors = {R.drawable.shadow_women , R.drawable.shape_men , R.drawable.shape_kids , R.drawable.shape_four ,  R.drawable.shape_five ,
            R.drawable.shape_six ,  R.drawable.shape_seven , R.drawable.shape_eight ,  R.drawable.shape_nine ,  R.drawable.shape_ten};

    Activity activity;

    String Servicetype_id , Businessname ;

    int status;

    //we are storing all the products in a list
    private List<GetAllBusinessProviderList> getAllBusinessProviderLists;


    //getting the context and product list with constructor
    public GetAllBusinessProviderAdapter(Context mCtx, List<GetAllBusinessProviderList> getAllBusinessProviderLists) {
        this.mCtx = mCtx;
        this.getAllBusinessProviderLists = getAllBusinessProviderLists;
    }

    @Override
    public GetAllBusinessProviderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_getallbusinessprovider, null);
        if(parent == null)
        {
            FontChangeCrawler fontChanger = new FontChangeCrawler(mCtx.getAssets(), "CenturyGothic.ttf");
            fontChanger.replaceFonts((ViewGroup) view.findViewById(android.R.id.content));
        }

        return new GetAllBusinessProviderViewHolder(view);
    }

        @Override
        public void onBindViewHolder ( final GetAllBusinessProviderViewHolder holder, int position){
            //getting the product of the specified position
            final GetAllBusinessProviderList getAllServicetypeList = getAllBusinessProviderLists.get(position);

            //binding the data with the viewholder views
            // String leave_action_name = product.getLeave_action_name();


            String test = getAllServicetypeList.getBusiness_name();

            if (test.length() > 2)
            {
                Businessname = test.substring(0, 2);
            }
            else
            {
                Businessname = test;
            }


            status = getAllServicetypeList.getStatus();

            holder.txt_color_name_box.setText(Businessname);
            holder.txt_color_name_box.setBackgroundResource(mColors[position % 10]);

            holder.text_business_name.setText(getAllServicetypeList.getBusiness_name());

            holder.text_business_area.setText(getAllServicetypeList.getBusiness_area());

            int CountQueue = getAllServicetypeList.getQueueCount();

            String QueueCount = Integer.toString(CountQueue);

            holder.texthourglassoraingqueue.setText("Wait" + "\t" + "\t" + QueueCount + "\t" + "Queues");

            holder.relativelayout.setOnClickListener(new View.OnClickListener() {
                private boolean stateChanged;

                public void onClick(View view) {
                   int IdBusiness = getAllServicetypeList.getBusiness_id();
                   String BusinessId = Integer.toString(IdBusiness);
                  // MyFunctions.setSharedPrefs(mCtx.getApplicationContext(),BusinessId,"");
                    Intent intent2 = new Intent(mCtx, ActivityIndividual.class).putExtra("BusinessId",BusinessId);
                    mCtx.startActivity(intent2);
                    ((Activity)mCtx).finish();

                }
            });

        }


        @Override
        public int getItemCount () {
            return getAllBusinessProviderLists.size();
        }


        class GetAllBusinessProviderViewHolder extends RecyclerView.ViewHolder {


            TextView txt_color_name_box, text_business_name, text_business_area, texthourglassoraingqueue, txt_timesheetdate, txt_surgerytt,
                    txt_button, txt_days, txt_cancel, surgery;

            RelativeLayout relativelayout;

            ImageView txt_date, img_images, img_image_up, pdf_imge;


            public GetAllBusinessProviderViewHolder(View itemView) {
                super(itemView);
                txt_color_name_box = itemView.findViewById(R.id.txt_color_name_box);
                text_business_name = itemView.findViewById(R.id.text_business_name);
                text_business_area  = itemView.findViewById(R.id.text_business_area);
                texthourglassoraingqueue  = itemView.findViewById(R.id.texthourglassoraingqueue);
                relativelayout = itemView.findViewById(R.id.relativelayout);
            }
        }
    }
