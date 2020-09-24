package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class resetpassword extends AppCompatActivity {

    private EditText pas;
    private Button reset;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        pas=findViewById(R.id.etpassword);
        reset=findViewById(R.id.btnreset);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userpassword=pas.getText().toString();
                progressDialog.setMessage("Resetting");
                progressDialog.show();
                if(userpassword.isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(resetpassword.this, "Enter the password to change", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseUser=firebaseAuth.getCurrentUser();
                    firebaseUser.updatePassword(userpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(resetpassword.this,"Successfully changed the password",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                finish();
                                startActivity(new Intent(resetpassword.this,MainActivity.class));
                            }
                            else
                                Toast.makeText(resetpassword.this,"Failed",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}