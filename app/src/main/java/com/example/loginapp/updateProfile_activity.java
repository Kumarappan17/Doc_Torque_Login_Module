package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updateProfile_activity extends AppCompatActivity {

    private EditText name,age,email,phoneno;
    private TextView changepassword;
    private Button update;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_activity);

        name=findViewById(R.id.etname);
        age=findViewById(R.id.etage);
        phoneno=findViewById(R.id.etphoneno);
        email=findViewById(R.id.etemail1);
        update=findViewById(R.id.btnupdate);
        changepassword=findViewById(R.id.tvchangepassword);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                database database=snapshot.getValue(com.example.loginapp.database.class);
                name.setText(database.getUsername());
                age.setText(database.getAge());
                email.setText(database.getMail_id());
                phoneno.setText(database.getPhone_no());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updateProfile_activity.this,error.getCode(),Toast.LENGTH_LONG).show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String username=name.getText().toString();
                    String userage=age.getText().toString();
                    String userphoneno=phoneno.getText().toString();
                    String useremail=email.getText().toString();
                    database database=new database(username,useremail,userphoneno,userage);
                    databaseReference.setValue(database);
                    finish();
                    Toast.makeText(updateProfile_activity.this,"Updated Personal Info",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(updateProfile_activity.this,homeui.class));
            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(updateProfile_activity.this,resetpassword.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}