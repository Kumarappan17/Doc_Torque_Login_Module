package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText name, password;
    private TextView info,forgotpassword;
    private Button btnlogin, btnsign;
    private int count = 5;

    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.etname);
        password = findViewById(R.id.etpas);
        btnlogin = findViewById(R.id.btlogin);
        info = findViewById(R.id.tvinfo);
        btnsign = findViewById(R.id.btsign);
        forgotpassword=findViewById(R.id.tvforgotpassword);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            finish();
            startActivity(new Intent(MainActivity.this, homeui.class));
        }

        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, signup.class));
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,forgotpassword_activity.class));
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = name.getText().toString();
                String upas = password.getText().toString();
                validate(uname, upas);
            }
        });
    }

    private void validate(String name, String pas) {

        progressDialog.setMessage("wait for a second to load to your home UI");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(name, pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    checkemailverification();
                } else {
                    count--;
                    if (count == 0) {
                        btnlogin.setEnabled(false);
                    }
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Enter the correct Credentials", Toast.LENGTH_SHORT).show();
                    info.setText("Incorrect password times : " + count);
                }
            }
        });
    }

    private void checkemailverification(){
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
            if(firebaseUser.isEmailVerified()){
                finish();
                Toast.makeText(MainActivity.this,"Login Successfull",Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this,homeui.class));
            }
            else
            {
                Toast.makeText(MainActivity.this,"Please verified with your mail to sign in",Toast.LENGTH_LONG).show();
                firebaseAuth.signOut();
                finish();
            }
    }
}