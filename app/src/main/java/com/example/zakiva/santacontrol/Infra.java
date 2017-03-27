package com.example.zakiva.santacontrol;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by MLB on 3/27/2017.
 */

class Infra {
    public static DatabaseReference myDatabase;
    public static boolean flag;
    public static int count;


    public static void addSheet(String name, ArrayList<HashMap<String, Object>> data) {
        Log.d(MainActivity.TAG, "name=" + name);
        Log.d(MainActivity.TAG, "data=" + data);
        myDatabase = FirebaseDatabase.getInstance().getReference();
        myDatabase.child("triviaDataSheets").child(name).setValue(data);
    }

    public static void addQuestions(String name, ArrayList<HashMap<String, Object>> data) {
        Log.d(MainActivity.TAG, "name=" + name);
        Log.d(MainActivity.TAG, "data=" + data);
        myDatabase = FirebaseDatabase.getInstance().getReference();
        myDatabase.child("triviaQuestions").child(name).setValue(data);
        Log.d(MainActivity.TAG, "got here!");
    }
    public static void checkDataIsCorrect(String parent, String child) {
        count = 0;
        myDatabase = FirebaseDatabase.getInstance().getReference();
        myDatabase.child(parent).child(child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    flag = false;
                    HashMap map = (HashMap)snapshot.getValue();
                    Set set = new HashSet(map.values());
                    Log.d(MainActivity.TAG,"set size: "+set.size());
                    Log.d(MainActivity.TAG,"map size: "+map.size());
                    count++;
                    if (map.size()==6 && set.size()==5) {
                        flag = true;
                    }
                    Log.d(MainActivity.TAG,"flag ? "+flag);
                }
                Log.d(MainActivity.TAG,"count is: "+count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(MainActivity.TAG,"error database!");
            }
        });
        Log.d(MainActivity.TAG,"count is: "+count);
    }
}

