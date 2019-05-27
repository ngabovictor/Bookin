package com.example.bookin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.ViewHolder> {
    private Context context;
    private List<DataSnapshot> hotels = new ArrayList<>();

    public HotelsAdapter(Context context, List<DataSnapshot> hotels) {
        this.context = context;
        this.hotels = hotels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.hotel_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        DataSnapshot hotel = hotels.get(i);

        viewHolder.nameView.setText(hotel.child("name").getValue().toString());
        viewHolder.addressView.setText(hotel.child("Address").getValue().toString());
        viewHolder.phoneView.setText(hotel.child("Phone").getValue().toString());
        viewHolder.emailView.setText(hotel.child("email").getValue().toString());

        for (DataSnapshot service:hotel.child("services").getChildren()) {
            TextView textView = new TextView(context);
            textView.setText(service.child("name").getValue().toString() + ": " +service.child("Price").getValue().toString());
            viewHolder.servicesContainer.addView(textView);
        }

        viewHolder.toggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.toggler.getText().toString().equals("Show services")){
                    viewHolder.toggler.setText("Show less");
                    viewHolder.servicesContainer.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.toggler.setText("Show services");
                    viewHolder.servicesContainer.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, addressView, phoneView, emailView, toggler;
        LinearLayout servicesContainer;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.hotel_name_view);
            addressView = itemView.findViewById(R.id.address_view);
            phoneView = itemView.findViewById(R.id.phone_view);
            emailView = itemView.findViewById(R.id.email_view);
            toggler = itemView.findViewById(R.id.services_toggle);
            servicesContainer = itemView.findViewById(R.id.services_holder);
        }
    }
}
