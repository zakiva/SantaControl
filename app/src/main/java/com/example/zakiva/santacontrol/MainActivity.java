package com.example.zakiva.santacontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.*;
import com.google.firebase.database.*;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkFirebase();
    }

    public void checkFirebase () {
        DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();
        myDatabase.child("santaControl").setValue("isWorking!");
    }
}
