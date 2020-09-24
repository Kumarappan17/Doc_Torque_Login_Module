package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class signup extends AppCompatActivity {
    private EditText name, pas, username, phoneno, age;
    private Button regbt;
    private ImageView profilepic;
    private String uname, upas, uusername, uphoneno, uage;
    private static int PickImage=123;
    Uri imagepath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==PickImage && resultCode==RESULT_OK && data.getData()!=null)
        {
         imagepath=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
                profilepic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = findViewById(R.id.etname);
        pas = findViewById(R.id.etpas);
        username = findViewById(R.id.etusername);
        phoneno = findViewById(R.id.etPhoneno);
        age = findViewById(R.id.etage);
        regbt = findViewById(R.id.btsignup);
        profilepic=findViewById(R.id.ivprofilepic);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setType("Images/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select image"),PickImage);
            }
        });

        regbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = name.getText().toString();
                upas = pas.getText().toString();
                uusername = username.getText().toString();
                uphoneno = phoneno.getText().toString();
                uage = age.getText().toString();

                if (validate(uname, upas, uusername, uphoneno, uage,profilepic) == true) {
                    firebaseAuth.createUserWithEmailAndPassword(uname, upas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendEmailVerification();
                            } else {
                                Toast.makeText(signup.this, "Sign up failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(signup.this, "enter the needed credentials to sign up", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean validate(String name, String pas, String uusername, String uphoneno, String uage, ImageView profilepic) {
        if (name.isEmpty() || pas.length() <= 3 || uusername.isEmpty() || uphoneno.isEmpty() || uage.isEmpty() || profilepic==null)
            return false;
        return true;
    }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        senduserdata();
                        Toast.makeText(signup.this, "Email verification sent", Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(signup.this, MainActivity.class));
                    } else {
                        Toast.makeText(signup.this, "Email verification Not sent, please enter valid mail id", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    private void senduserdata(){
        FirebaseDatabase firebaseDatabase;
        firebaseDatabase=FirebaseDatabase.getInstance();
        StorageReference storageReferenc=firebaseStorage.getReference();
        StorageReference storageReference= storageReferenc.child(firebaseAuth.getUid()).child("Images").child("profile_pic");
        UploadTask uploadTask=storageReference.putFile(imagepath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(signup.this,"Image updated failed",Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(signup.this,"Image updated ",Toast.LENGTH_LONG).show();
            }
        });
        DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());
        database database=new database(uusername,uname,uphoneno,uage);
        databaseReference.setValue(database);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}