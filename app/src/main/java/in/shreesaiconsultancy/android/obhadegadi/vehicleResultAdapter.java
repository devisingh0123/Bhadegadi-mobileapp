package in.shreesaiconsultancy.android.obhadegadi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by athulnair on 19/07/17.
 */

public class vehicleResultAdapter extends RecyclerView.Adapter<vehicleResultAdapter.ViewHolder>{

    private List<vehicleResultItem> listItems;
    private Context context;

    public vehicleResultAdapter(List<vehicleResultItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public vehicleResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicle_result_item, parent, false);

        return new vehicleResultAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(vehicleResultAdapter.ViewHolder holder, int position) {
        Typeface Comfortaa = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Black.otf");


        final vehicleResultItem listItem = listItems.get(position);

        holder.TVheading.setText(listItem.getHeading());
        holder.TVheading.setTypeface(Comfortaa);
        holder.TVperkm.setText(listItem.getPerkm() + " ₹ Per Km");
        if(listItem.getVerified().equals("true")) {
            holder.verified.setText("Verified");
        } else {
            holder.verified.setText("Not Verified");
        }

        if(listItem.getImageUrl().equals("noimage")) {

            holder.IVimage.setImageResource(R.drawable.no_car);

        } else {

            Picasso.with(context)
                    .load(listItem.getImageUrl())
                    .into(holder.IVimage);
        }

        if(listItem.getVehicleType().equals("Luxury Taxi")){
            holder.IVtype.setImageResource(R.drawable.luxury);
        } else if (listItem.getVehicleType().equals("Loading Taxi")) {
            holder.IVtype.setImageResource(R.drawable.truck);
        } else {
            holder.IVtype.setImageResource(R.drawable.heavy);
        }

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, vehicleSearchDetailsActivity.class);
                Bundle extras = new Bundle();
                extras.putString("image_url", listItem.getImageUrl());
                extras.putString("vehicle_name",listItem.getHeading());
                extras.putString("vehicle_type",listItem.getVehicleType());
                extras.putString("vehicle_pkc",listItem.getPerkm());
                extras.putString("vehicle_state",listItem.getState());
                extras.putString("vehicle_city",listItem.getCity());
                extras.putString("vehicle_id",listItem.getVehicleID());
                extras.putString("user_id",listItem.getUserID());
                intent.putExtras(extras);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
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
        public TextView verified;
        public LinearLayout root;

        public ViewHolder(View itemView) {

            super(itemView);

            TVheading = (TextView) itemView.findViewById(R.id.tv_heading);
            TVperkm = (TextView) itemView.findViewById(R.id.tv_perkm);
            IVimage = (ImageView) itemView.findViewById(R.id.IV_car_image);
            IVtype = (ImageView) itemView.findViewById(R.id.IV_vehicle_type);
            root = (LinearLayout) itemView.findViewById(R.id.vehicle_item_root);
            verified = (TextView) itemView.findViewById(R.id.tv_vehicle_status);
        }

    }

}
