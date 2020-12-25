package com.googleplay.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class editingnote extends AppCompatActivity {
    Intent data;
    EditText editNoteTitle,editNoteContent;
    FirebaseFirestore fStore;
    ProgressBar spinner;
    FirebaseUser user;

   mnote note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editingnote);
        Toolbar toolbar = findViewById(R.id.toolbar);

        fStore = fStore.getInstance();
        spinner = findViewById(R.id.progressBar2);
        editNoteContent = findViewById(R.id.content);
        editNoteTitle = findViewById(R.id.title);
        user = FirebaseAuth.getInstance().getCurrentUser();

        data = getIntent();
        note = (mnote) data.getSerializableExtra("note");

        editNoteTitle.setText(note.getTitle());
        editNoteContent.setText(note.getDesc());


        FloatingActionButton fab = findViewById(R.id.save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nTitle = editNoteTitle.getText().toString();
                String nContent = editNoteContent.getText().toString();

                if(nTitle.isEmpty() || nContent.isEmpty()){
                    Toast.makeText(editingnote.this, "this is empty cant save it!", Toast.LENGTH_SHORT).show();
                    return;
                }

                spinner.setVisibility(View.VISIBLE);
                DocumentReference docref = fStore.collection("Notes").document(user.getUid()).collection("all notes").document(note.getId());
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.getDefault());
                String formattedDate = df.format(c);
                Map<String,Object> note = new HashMap<>();

                note.put("subject",nTitle);
                note.put("maintext",nContent);
                note.put("date",formattedDate);

                docref.update(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(editingnote.this, "Note Updated.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(editingnote.this, "Ooops, Try again please.", Toast.LENGTH_SHORT).show();
                        spinner.setVisibility(View.VISIBLE);
                    }

                });


            }
        });
    }
}