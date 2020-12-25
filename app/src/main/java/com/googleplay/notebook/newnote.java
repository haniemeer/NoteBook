package com.googleplay.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class newnote extends AppCompatActivity {
    BottomNavigationView bottomNav;
    private RecyclerView recyclerView;
    private List<notedata> notedata;
    private List<String> followingList;
    private recycleadaptor adaptor;
    ImageView remove;
    String selectid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);
        remove=findViewById(R.id.remove);
        bottomNav=findViewById(R.id.bottomnav);
        recyclerView = findViewById(R.id.recycle);

        notedata = new ArrayList<>();
        followingList = new ArrayList<>();
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.allnotes){
                    Intent intent=new Intent(newnote.this,newnote.class);
                    startActivity(intent);
                }
                else if (item.getItemId()==R.id.add){
                    Intent intent=new Intent(newnote.this,addingnote.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        adaptor = new recycleadaptor(notedata);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptor);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.getItemId();
                        selectid=item.toString();
                        return false;
                    }
                });
                //  deletselectednote();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllNote();
    }

    private void getAllNote(){
        FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Note").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followingList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    followingList.add(snapshot.getKey());
                }
                FirebaseDatabase.getInstance().getReference().child("Notes").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       notedata.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                           notedata note = snapshot.getValue(notedata.class);

                            for (String id : followingList) {
                                if (note.getPublishId().equals(id)) {
                                    notedata.add(note);
                                }
                            }
                        }
                        adaptor.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void deletselectednote(String id){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query delet=databaseReference.child("Notes").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).orderByChild("id").equalTo(selectid);
        delet.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    dataSnapshot.getRef().removeValue();
                }
                Intent intent=new Intent(newnote.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Note","cancel the note",error.toException());
            }
        });

    }
}