package com.zealoit.eqz.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.zealoit.eqz.List.AllProvidersList;
import com.zealoit.eqz.List.OldQueueList;
import com.zealoit.eqz.R;
import com.zealoit.eqz.Utils.Constants;
import com.zealoit.eqz.Utils.FontChangeCrawler;
import com.zealoit.eqz.Utils.MyFunctions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OldQueueAdapter extends RecyclerView.Adapter<OldQueueAdapter.OldQueueAdapterViewHolder> {

    //this context we will use to inflate the activity_sign_in
    private Context mCtx;

    Activity activity;

    String Servicetype_id ;

    int ProviderId;

    //we are storing all the products in a list
    private List<OldQueueList> oldQueueLists;

    // private String reurl = NGROK_BASE_URL;
    RequestQueue requestQueue;
    String status_code, userid, mobileno, quser, otp , mobilenumber , servicetype_name, MobileNumber , AuthorizationToken , servicetype_id, servicetype_icon, servicegroup_id, status, created_on, statusMessage, userMessage, internalMessage, created_by, particpantid, user_email, user_contact_no;


    //getting the context and product list with constructor
    public OldQueueAdapter(Context mCtx, List<OldQueueList> oldQueueLists) {
        this.mCtx = mCtx;
        this.oldQueueLists = oldQueueLists;
    }

    @Override
    public OldQueueAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_oldqueue, null);
        requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        if(parent == null)
        {
            FontChangeCrawler fontChanger = new FontChangeCrawler(mCtx.getAssets(), "CenturyGothic.ttf");
            fontChanger.replaceFonts((ViewGroup) view.findViewById(android.R.id.content));
        }
        return new OldQueueAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OldQueueAdapterViewHolder holder, int position) {
        //getting the product of the specified position
        final OldQueueList getAllProviderList = oldQueueLists.get(position);

        //binding the data with the viewholder views
        // String leave_action_name = product.getLeave_action_name();
        holder.txt_business_name.setText(getAllProviderList.getBusinessName());

        holder.txt_provider_name.setText(getAllProviderList.getProviderTitele()+"," + "\t" + getAllProviderList.getServiceName());


        String todate = getAllProviderList.getDate();
        SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        try {

            Date newDate = spf.parse(todate);
            spf = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
            String newDateString = spf.format(newDate);
            holder.txt_queue_date.setText(newDateString);
        }catch (Exception e){
            e.printStackTrace();
        }


        holder.txt_queue_number.setText(getAllProviderList.getQueueNumber());

        int status = getAllProviderList.getStatus();

        if (status==4){
            Glide.with(mCtx).load(R.drawable.xmark).into( holder.ImageView);
            holder.txt_queue_status.setText("CANCEL");
        }else if (status==3){
            Glide.with(mCtx).load(R.drawable.checked).into( holder.ImageView);
            holder.txt_queue_status.setText("COMPLETED");
        }else if (status==1){
            holder.txt_queue_status.setText("NEW QUEUE");
        }else if (status==2){
            holder.txt_queue_status.setText("ON GOING");
        }else if (status==5){
            holder.txt_queue_status.setText("REJECTED");
        }else if (status==6){
            holder.txt_queue_status.setText("NOT PROCESSED");
        }

        holder.relativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  holder.imganim.setVisibility(View.VISIBLE);
             //   Glide.with(mCtx).load(R.raw.blue_tick).into( holder.imganim);
            //    if (holder.imganim.getVisibility() == View.VISIBLE){
               //     ProviderId = getAllProviderList.getProviderId();
               //     String iD = Integer.toString(ProviderId);
               //     Toast.makeText(mCtx, iD, Toast.LENGTH_SHORT).show();
                //    MyFunctions.setSharedPrefs(mCtx , Constants.PROVIDERID,iD);
               // }else {
                 //   Toast.makeText(mCtx, "Please Choose the Option", Toast.LENGTH_SHORT).show();
               // }

            }
        });

    }

    @Override
    public int getItemCount() {
        return oldQueueLists.size();
    }


    class OldQueueAdapterViewHolder extends RecyclerView.ViewHolder {


        TextView txt_business_name, txt_provider_name, txt_queue_date, txt_queue_number , txt_queue_status, txt_surgerytt,
                txt_button ,txt_days , txt_cancel , surgery ;

        RelativeLayout relativelayout;

        ImageView ImageView , img_images , img_image_up , pdf_imge;


        public OldQueueAdapterViewHolder(View itemView) {
            super(itemView);
            txt_business_name = itemView.findViewById(R.id.txt_business_name);
            txt_provider_name = itemView.findViewById(R.id.txt_provider_name);
            txt_queue_date = itemView.findViewById(R.id.txt_queue_date);
            txt_queue_number = itemView.findViewById(R.id.txt_queue_number);
            txt_queue_status  = itemView.findViewById(R.id.txt_queue_status);
            relativelayout = itemView.findViewById(R.id.relativelayout);
            ImageView = itemView.findViewById(R.id.img_queue);
        }
    }
}
