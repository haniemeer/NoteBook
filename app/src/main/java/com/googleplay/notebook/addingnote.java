package com.googleplay.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class addingnote extends AppCompatActivity {
    BottomNavigationView bottomNav;
    EditText edtext;
    EditText edsubject;
    ImageView imgsave;
    TextView txtdate;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FloatingActionButton btnAdd;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    String keyId;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addingnote);
        bottomNav=findViewById(R.id.bottomnav);
        edsubject=findViewById(R.id.sub);
        edtext=findViewById(R.id.Text);
        imgsave=findViewById(R.id.saver);
        txtdate=findViewById(R.id.date);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd    HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        txtdate.setText(currentDateandTime);


        progressDialog = new ProgressDialog(this);

       bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.allnotes){
                    Intent intent=new Intent(addingnote.this,newnote.class);
                    startActivity(intent);
                }
                else if (item.getItemId()==R.id.add){
                    Intent intent=new Intent(addingnote.this,addingnote.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        imgsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txsubject=edsubject.getText().toString();
                String txmaintext=edtext.getText().toString();
                String txdate=txtdate.getText().toString();

                progressDialog.setMessage("adding.....");


                if (TextUtils.isEmpty(txsubject) || TextUtils.isEmpty(txmaintext)){
                    Toast.makeText(addingnote.this,"please fill subject & text",Toast.LENGTH_LONG).show();
                }
                else
                {
                    progressDialog.show();
                    mnote note = new mnote();
                    note.setId(keyId);
                    note.setTitle(edsubject.getText().toString().trim());
                    note.setDesc(edtext.getText().toString().trim());
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy/mm/dd hh:mm:ss", Locale.getDefault());
                    String formattedDate = df.format(c);

                    note.setDate(formattedDate);
                    save(note);
                }
            }
        });
    }
    private void save(mnote note) {
        DocumentReference docref = firebaseFirestore.collection("Notes").document(firebaseUser.getUid()).collection("all Notes").document();
        docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(addingnote.this, " recent Note Added.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addingnote.this, "Ooops, Try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}