package com.example.internationalnews;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FirebaseDatabaseHelper {
    List<String>list=new ArrayList<>();
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    public FirebaseDatabaseHelper() {
        mDatabase=FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference("UriNews");
    }
    public void writeData(String uri){
        mReference.push().child("uri").setValue(uri);
    }
    public void readData(){
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             for(DataSnapshot node:dataSnapshot.getChildren()){
                 list.add(String.valueOf(node.getValue()));
             }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
