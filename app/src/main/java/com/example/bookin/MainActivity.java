package com.example.bookin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.bookin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private Context context;

    RecyclerView hotelsRecyclerView;
    private List<DataSnapshot> hotels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("hotels");
        hotelsRecyclerView = findViewById(R.id.hotels_recycler_view);
        hotelsRecyclerView.setHasFixedSize(true);
        hotelsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        } else {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapShot:dataSnapshot.getChildren()) {
                        hotels.add(snapShot);
                    }
                    HotelsAdapter adapter = new HotelsAdapter(context, hotels);
                    hotelsRecyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
