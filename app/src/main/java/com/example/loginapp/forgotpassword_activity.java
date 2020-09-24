package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword_activity extends AppCompatActivity {

    private EditText forgotemail;
    private Button reset;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword_activity);

        firebaseAuth=FirebaseAuth.getInstance();
        forgotemail=findViewById(R.id.etforgotemail);
        reset=findViewById(R.id.btnreset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=forgotemail.getText().toString();

                if(email.isEmpty())
                {
                    Toast.makeText(forgotpassword_activity.this,"Enter the email address",Toast.LENGTH_LONG).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(forgotpassword_activity.this,"Reset link sent to your mail id",Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(forgotpassword_activity.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(forgotpassword_activity.this,"Enter valid mail id",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}