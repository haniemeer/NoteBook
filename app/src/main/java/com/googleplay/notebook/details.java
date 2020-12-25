package com.googleplay.notebook;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;

public class details extends AppCompatActivity {
    Intent data;
   mnote mnote;
    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = getIntent();
        mnote = (mnote) data.getSerializableExtra("note");


        TextView content = findViewById(R.id.Content);
        TextView title = findViewById(R.id.titlesedit);
        TextView date = findViewById(R.id.the_date);
        content.setMovementMethod(new ScrollingMovementMethod());

        content.setText(mnote.getDesc());
        title.setText(mnote.getTitle());
        date.setText(mnote.getDate());
        content.setBackgroundColor(getResources().getColor(data.getIntExtra("code",0),null));

        FloatingActionButton fab = findViewById(R.id.fabedit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), editingnote.class);
                i.putExtra("note", (Serializable) mnote);
                startActivity(i);
            }
        });
        }


        @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}