package com.example.lungcancerwithoutml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class prescription extends AppCompatActivity {

    Button back;
    TextView prescription1, nad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        back = findViewById(R.id.back);

        prescription1= findViewById(R.id.prescription_image);
        nad= findViewById(R.id.nad);


        String patient_name = getIntent().getStringExtra("name");
        String patient_age = getIntent().getStringExtra("age");
        String date = getIntent().getStringExtra("date");

        String prescription = getIntent().getStringExtra("Prescription");


        nad.setText("Patient Name: "+patient_name + "\nPatient Age: "+ patient_age +"\nDate: "+date);


        if (prescription.equals("Adenocercenoma") ){
            prescription1.setText( "We regret to inform you that the biopsy results have confirmed the presence of "+ prescription +" cell lung cancer. It is essential to address this condition promptly. We recommend initiating a comprehensive treatment plan, which may include surgery, chemotherapy, or radiation therapy, depending on the stage and extent of cancer. Our team of healthcare professionals will work closely with you to devise the best course of action tailored to your specific needs. We understand the challenges you may face and assure you of our unwavering support throughout your treatment journey. Together, we will strive for the best possible outcome and improved quality of life. Please don't hesitate to reach out with any questions or concerns.");
        }
        else if (prescription.equals("Squamous")){
            prescription1.setText( "The patient presented with symptoms of cough, dyspnea, and weight loss. A comprehensive evaluation, including imaging studies and biopsies, confirmed the diagnosis of "+prescription+" cell lung cancer. The cancer is localized to the lungs. Treatment options, including surgery, radiation and chemotherapy, should be discussed with the patient's doctor. A multidisciplinary approach involving oncology, pulmonology, and supportive care teams also should be initiate. Regular follow-ups and monitoring of the patient's response to treatment will be conducted to ensure the best possible outcome and quality of life. ");
        }
        else if(prescription.equals("Normal")){
            prescription1.setText( "The lung cancer test results show the presence of "+prescription+" cells, indicating a negative finding for lung cancer. This is a promising outcome, suggesting no malignancy or abnormal cellular activity in the lung tissue. However, further evaluations and regular follow-ups are essential to monitor the patient's respiratory health and ensure early detection of any potential changes. We recommend maintaining a healthy lifestyle, avoiding smoking or exposure to harmful substances, and seeking immediate medical attention if any respiratory symptoms arise. Our team remains available for any further assistance or concerns.");
        }
        else{
            prescription1.setText("There is nothing to show");
        }




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(prescription.this, Classification.class);
                startActivity(i);
            }
        });
    }
}