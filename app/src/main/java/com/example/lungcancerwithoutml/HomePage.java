package com.example.lungcancerwithoutml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {

    FirebaseAuth auth;
    Button logout;
   // TextView textView;
    FirebaseUser user;

    Button About_Lung_Cancer, Classification, About_Us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        About_Lung_Cancer= findViewById(R.id.about_lung_cancer_button);
        Classification= findViewById(R.id.classification_button);
        About_Us= findViewById(R.id.about_us_button);

        auth = FirebaseAuth.getInstance();
        logout= findViewById(R.id.logout);
        //textView= findViewById(R.id.logouttextview);
        user= auth.getCurrentUser();
        if(user== null){
            Intent i = new Intent(getApplicationContext(), log_in.class);
            startActivity(i);
            finish();
        }
       // else {
            //textView.setText(user.getEmail());
       // }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), log_in.class);
                startActivity(i);
                finish();
            }
        });

        About_Lung_Cancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, About_Lung_Cancer.class);
                startActivity(i);
                finish();
            }
        });

        Classification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, Patient_Details.class);
                startActivity(i);
                finish();

            }
        });

        About_Us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, About_us.class);
                startActivity(i);
                finish();
            }
        });
    }
}