package com.example.lungcancerwithoutml;






import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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
import com.google.firebase.ktx.Firebase;



public class sign_in extends AppCompatActivity {

    Button SignIn, LogIn;

    EditText Email, Password;
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
        setContentView(R.layout.activity_signin);




        mAuth = FirebaseAuth.getInstance();


        SignIn= findViewById(R.id.signInButton);
        LogIn= findViewById(R.id.loginButton);
        Email= findViewById(R.id.usernameEditText);
        Password= findViewById(R.id.passwordEditText);





       LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(sign_in.this, log_in.class);
                startActivity(i);
                finish();

            }
        });



        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                String email= String.valueOf(Email.getText());
                String password= String.valueOf(Password.getText());

                if( email.equals("")&& password.equals("")){

                    Toast.makeText(getApplicationContext(),"Please enter some data", Toast.LENGTH_SHORT).show();

                }

                else if(email.equals("")|| password.equals("")){

                    Toast.makeText(getApplicationContext(),"Please enter all data", Toast.LENGTH_SHORT).show();

                }

                else {
                      mAuth.createUserWithEmailAndPassword(email , password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(sign_in.this, "Account Created.",
                                            Toast.LENGTH_SHORT).show();

                                    Intent i= new Intent(sign_in.this, log_in.class);
                                    startActivity(i);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(sign_in.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                }



            }
        });






    }



}