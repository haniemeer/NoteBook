package com.googleplay.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class register extends AppCompatActivity {
 TextView textaccount;
 EditText username;
 EditText em;
 EditText pass;
 Button submit;
 DatabaseReference database;
 FirebaseAuth auth;
 ProgressDialog progdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textaccount=findViewById(R.id.account);
        username=findViewById(R.id.name);
        em=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        submit =findViewById(R.id.but);



        database= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        progdialog=new ProgressDialog(this);


        textaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(register.this,com.googleplay.notebook.account.class);
                startActivity(intent);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textname=username.getText().toString();
                String textemail=em.getText().toString();
                String textpassword=pass.getText().toString();
                if(TextUtils.isEmpty(textname)||TextUtils.isEmpty(textemail)||TextUtils.isEmpty(textpassword)){
                    Toast.makeText(register.this,"fill all the fieldes",Toast.LENGTH_LONG).show();

                }
                else if (textpassword.length()<8){
                    Toast.makeText(register.this,"your pass must be longer than 8",Toast.LENGTH_LONG).show();
                }
                else registering(textname,textemail,textpassword);
            }

        });

    }
    public void registering(final String name,final String email,final String password){
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String,Object> data= new HashMap<>();
                data.put("name" , name);
                data.put("email",email);
                data.put("password",password);
                database.child("username").child(auth.getCurrentUser().getUid()).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progdialog.dismiss();
                            Toast.makeText(register.this,"you are registered.",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(register.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }
}
