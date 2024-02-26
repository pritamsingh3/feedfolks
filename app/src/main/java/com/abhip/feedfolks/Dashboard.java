package com.abhip.feedfolks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity {

    CardView donate,receive,foodpt,pins,hist,logout;
    FirebaseAuth auth;
    AlertDialog.Builder builder;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        donate = findViewById(R.id.donate);
        receive = findViewById(R.id.receive);
        logout = findViewById(R.id.logout);
        foodpt = findViewById(R.id.foodmap);
        pins = findViewById(R.id.mypins);
        hist = findViewById(R.id.history);

        auth= FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        if(auth.getCurrentUser() ==null){
            Intent intent = new Intent(Dashboard.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        donate.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Donate.class);
                startActivity(intent);
            }
        });
        receive.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Receive.class);
                startActivity(intent);
            }
        });
        foodpt.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FoodPoint.class);
                startActivity(intent);
            }
        });

        pins.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPins.class);
                startActivity(intent);
            }
        });

        hist.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserData.class);
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                builder.show();
            }
        });

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Log out");
        builder.setMessage("Are You Sure to log out?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (firebaseUser != null) {
                    auth.signOut();
                    finish();
                    startActivity(new Intent(Dashboard.this, Login.class));
                }

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

    }
}