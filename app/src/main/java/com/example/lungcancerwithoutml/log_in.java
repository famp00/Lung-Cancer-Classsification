package com.example.lungcancerwithoutml;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class log_in extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(getApplicationContext(), HomePage.class);
            startActivity(i);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        Button back = findViewById(R.id.backButtton);
        Button login = findViewById(R.id.loginButton);
        EditText Passswordlogin,Emailogin;
        mAuth = FirebaseAuth.getInstance();


        Passswordlogin=findViewById(R.id.passwordEditText);
        Emailogin=findViewById(R.id.usernameEditText);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(log_in.this, sign_in.class);
                startActivity(i);
                finish();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email= String.valueOf(Emailogin.getText());
                String password= String.valueOf(Passswordlogin.getText());

                if (email.equals("") && password.equals("")) {

                    Toast.makeText(getApplicationContext(), "Please enter some data", Toast.LENGTH_SHORT).show();

                } else if (email.equals("") || password.equals("")) {

                    Toast.makeText(getApplicationContext(), "Please enter all data", Toast.LENGTH_SHORT).show();

                } else {

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        Intent i = new Intent(getApplicationContext(), HomePage.class);
                                        startActivity(i);
                                        finish();


                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(log_in.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });



                }
            }



        });


    }
}
