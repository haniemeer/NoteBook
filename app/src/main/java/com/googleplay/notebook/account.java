package com.googleplay.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
                     if (TextUtils.isEmpty(emailtext)||TextUtils.isEmpty(passwordtext)){
                         Toast.makeText(account.this,"fields are not filled",Toast.LENGTH_LONG).show();
                     }
                    else
                         accountlogin(emailtext,passwordtext);
            }
        });
    }

    public void accountlogin(String emailaddress, String passwordnumber) {
        firebaseauth.signInWithEmailAndPassword(emailaddress,passwordnumber).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
             if(task.isSuccessful())   {
                 Toast.makeText(account.this,"logined in! it was succed.",Toast.LENGTH_LONG).show();
                 Intent intent=new Intent(account.this,MainActivity.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(intent);
                 finish();
             }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(account.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
