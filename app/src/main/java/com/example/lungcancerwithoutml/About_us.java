package com.example.lungcancerwithoutml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class About_us extends AppCompatActivity {
    Button  back, supervisor, students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        back= findViewById(R.id.back);
        supervisor= findViewById(R.id.supervisor);
        students= findViewById(R.id.students);




        supervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(About_us.this, supervisor.class);
                startActivity(i);
                finish();
            }
        });

        students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(About_us.this, Students.class);
                startActivity(i);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(About_us.this, HomePage.class);
                startActivity(i);
                finish();
            }
        });




    }
}