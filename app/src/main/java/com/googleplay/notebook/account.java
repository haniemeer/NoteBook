package com.googleplay.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class account extends AppCompatActivity {
    EditText emailaccount;
    EditText passaccount;
    Button buttonaccount;
    FirebaseAuth firebaseauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        emailaccount=findViewById(R.id.editemail);
        passaccount=findViewById(R.id.editpass);
        firebaseauth=FirebaseAuth.getInstance();
        buttonaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailtext=emailaccount.getText().toString();
                String passwordtext=passaccount.getText().toString();

            }
        });
    }

}
