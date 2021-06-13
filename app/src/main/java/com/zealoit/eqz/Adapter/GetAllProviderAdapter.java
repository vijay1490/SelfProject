package com.zealoit.eqz.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zealoit.eqz.ActivityIndividual;
import com.zealoit.eqz.List.AllProvidersList;
import com.zealoit.eqz.NoDataActivity;
import com.zealoit.eqz.R;
import com.zealoit.eqz.Utils.Constants;
import com.zealoit.eqz.Utils.FontChangeCrawler;
import com.zealoit.eqz.Utils.MyFunctions;

import java.util.List;

public class GetAllProviderAdapter extends RecyclerView.Adapter<GetAllProviderAdapter.GetAllProviderViewHolder> {


    //this context we will use to inflate the activity_sign_in
    private Context mCtx;

    Activity activity;

    String Servicetype_id , GETCOUNT ;

    int ProviderId;

    //we are storing all the products in a list
    private List<AllProvidersList> allProvidersLists;

   // private String reurl = NGROK_BASE_URL;
    RequestQueue requestQueue;
    String status_code, userid, mobileno, quser, otp , mobilenumber , servicetype_name, MobileNumber , AuthorizationToken , servicetype_id, servicetype_icon, servicegroup_id, status, created_on, statusMessage, userMessage, internalMessage, created_by, particpantid, user_email, user_contact_no;


    //getting the context and product list with constructor
    public GetAllProviderAdapter(Context mCtx, List<AllProvidersList> getAllProviderLists) {
        this.mCtx = mCtx;
        this.allProvidersLists = getAllProviderLists;
    }

    @Override
    public GetAllProviderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_allserviceprovider, null);
        requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        if(parent == null)
        {
            FontChangeCrawler fontChanger = new FontChangeCrawler(mCtx.getAssets(), "CenturyGothic.ttf");
            fontChanger.replaceFonts((ViewGroup) view.findViewById(android.R.id.content));
        }
        return new GetAllProviderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GetAllProviderViewHolder holder, int position) {
        //getting the product of the specified position
        final AllProvidersList getAllProviderList = allProvidersLists.get(position);

        //binding the data with the viewholder views
        // String leave_action_name = product.getLeave_action_name();
        holder.txt_proviter_title.setText(getAllProviderList.getProviderTitle());

        holder.txt_proviter_spec.setText(getAllProviderList.getServiceName());

        holder.txt_queue_count.setText(getAllProviderList.getQueueCount());

        int status = getAllProviderList.getStatus();
        if (status == 1){
            holder.txt_queue_status_waiting.setVisibility(View.VISIBLE);
            holder.txt_queue_count.setVisibility(View.VISIBLE);
        }else if (status == 0){
            holder.txt_queue_status.setText("CLOSED");
            holder.txt_queue_status.setVisibility(View.VISIBLE);
            holder.txt_queue_count.setVisibility(View.GONE);
            holder.relativelayout.setEnabled(false);

        }

        holder.relativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (v.getId() == holder.relativelayout.getId()){
                        ProviderId = getAllProviderList.getProviderId();
                       int STATUS = getAllProviderList.getStatus();
                        GETCOUNT = getAllProviderList.getQueueCount();
                        String iD = Integer.toString(ProviderId);
                        String PROVIDERSTATUS = Integer.toString(STATUS);
                      //  Toast.makeText(mCtx, iD, Toast.LENGTH_SHORT).show();
                        MyFunctions.setSharedPrefs(mCtx , Constants.PROVIDERSTATUS,PROVIDERSTATUS);
                        MyFunctions.setSharedPrefs(mCtx , Constants.PROVIDERID,iD);
                        MyFunctions.setSharedPrefs(mCtx , Constants.GETCOUNT,GETCOUNT);
                        holder.imganim.setVisibility(View.VISIBLE);
                        holder.txt_queue_count.setVisibility(View.GONE);
                        holder.txt_queue_status.setVisibility(View.GONE);
                        holder.txt_queue_status_waiting.setVisibility(View.GONE);
                        Glide.with(mCtx).load(R.raw.blue_tick).listener(new RequestListener<Drawable>() {
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
                        }).into(holder.imganim);
                    }else {
                        Toast.makeText(mCtx, "Please Choose the Option", Toast.LENGTH_SHORT).show();
                        holder.imganim.setVisibility(View.GONE);
                        holder.txt_queue_count.setVisibility(View.VISIBLE);
                        holder.txt_queue_status.setVisibility(View.VISIBLE);
                    }

            }
        });

    }

    @Override
    public int getItemCount() {
        return allProvidersLists.size();
    }


    class GetAllProviderViewHolder extends RecyclerView.ViewHolder {


        TextView txt_proviter_title, txt_proviter_spec, txt_remove_provider, txt_fromdate , txt_timesheetdate, txt_surgerytt,
                txt_queue_status ,txt_queue_count , txt_queue_status_waiting , surgery ;

        RelativeLayout relativelayout;

        ImageView imganim , img_images , img_image_up , pdf_imge;


        public GetAllProviderViewHolder(View itemView) {
            super(itemView);
            txt_proviter_title = itemView.findViewById(R.id.txt_proviter_title);
            txt_proviter_spec = itemView.findViewById(R.id.txt_proviter_spec);
            txt_queue_status = itemView.findViewById(R.id.txt_queue_status);
            txt_queue_count = itemView.findViewById(R.id.txt_queue_count);
            txt_queue_status_waiting = itemView.findViewById(R.id.txt_queue_status_waiting);
            relativelayout = itemView.findViewById(R.id.relativelayout);
            imganim = itemView.findViewById(R.id.imganim);
        }
    }
}
