package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class homeui extends AppCompatActivity {

    private Button signout;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeui);

        signout=findViewById(R.id.btsignout);

        firebaseAuth=FirebaseAuth.getInstance();

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.signoutmenu:{
                signout();
                break;
            }
            case R.id.update:{
                startActivity(new Intent(homeui.this,updateProfile_activity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void signout()
    {
        firebaseAuth.signOut();
        finish();
        Toast.makeText(homeui.this,"Signing off",Toast.LENGTH_LONG).show();
        startActivity(new Intent(homeui.this,MainActivity.class));
    }
}