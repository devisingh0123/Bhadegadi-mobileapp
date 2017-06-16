package com.example.android.slides;

import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by athulnair on 14/06/17.
 */

public class vehicleAdapter extends RecyclerView.Adapter<vehicleAdapter.ViewHolder> {


    private List<vehicleListItem> listItems;
    private Context context;

    public vehicleAdapter(List<vehicleListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicle_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Typeface Comfortaa = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Black.otf");


        vehicleListItem listItem = listItems.get(position);

        holder.TVheading.setText(listItem.getHeading());
        holder.TVheading.setTypeface(Comfortaa);
        holder.TVperkm.setText(listItem.getPerkm() + " ₹ Per Km");

        if(listItem.getImageUrl().equals("true")) {
            String url = "http://ec2-35-167-97-234.us-west-2.compute.amazonaws.com/image/"+ listItem.getUserID() + "/" + listItem.getVehicleID() + "/VI.jpg";

            Picasso.with(context)
                    .load(url)
                    .into(holder.IVimage);


        } else {
            holder.IVimage.setImageResource(R.drawable.no_car);
        }


        if(listItem.getVehicleType().equals("Luxury Taxi")){
            holder.IVtype.setImageResource(R.drawable.luxury);
        } else if (listItem.getVehicleType().equals("Loading Taxi")) {
            holder.IVtype.setImageResource(R.drawable.truck);
        } else {
            holder.IVtype.setImageResource(R.drawable.heavy);
        }



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TVheading;
        public TextView TVperkm;
        public ImageView IVimage;
        public ImageView IVtype;

        public ViewHolder(View itemView) {

            super(itemView);

            TVheading = (TextView) itemView.findViewById(R.id.tv_heading);
            TVperkm = (TextView) itemView.findViewById(R.id.tv_perkm);
            IVimage = (ImageView) itemView.findViewById(R.id.IV_car_image);
            IVtype = (ImageView) itemView.findViewById(R.id.IV_vehicle_type);
        }

    }

}
