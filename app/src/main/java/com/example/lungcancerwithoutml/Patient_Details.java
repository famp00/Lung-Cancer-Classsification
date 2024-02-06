package com.example.lungcancerwithoutml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Patient_Details extends AppCompatActivity {
    EditText name, age, date;
    Button next, back;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        name= findViewById(R.id.name);
        age= findViewById(R.id.age);
        date= findViewById(R.id.date);
        next= findViewById(R.id.next);
        back= findViewById(R.id.back);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  name1 = name.getText().toString();
                String  age1 = age.getText().toString();
                String  date1 = date.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("saved_name", name1);
                editor.putString("saved_age", age1);
                editor.putString("saved_date", date1);
                editor.apply();

                Intent i = new Intent(Patient_Details.this, Classification.class);


                startActivity(i);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Patient_Details.this, HomePage.class);
                startActivity(i);
                finish();

            }
        });
    }
}