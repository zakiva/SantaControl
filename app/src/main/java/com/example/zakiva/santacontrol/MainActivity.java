package com.example.zakiva.santacontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.*;
import com.google.firebase.database.*;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = ">>>>>>>Debug: ";

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
    public void clickToSaveSheet(View v) {
        try {
            Log.d(MainActivity.TAG, "start try =  ");
            Parser p = new Parser();
            int id = getResources().getIdentifier("sheets", "raw", this.getApplicationContext().getPackageName());
            InputStream raw = this.getApplicationContext().getResources().openRawResource(id);
            p.saveSheetToFirebase("questions", raw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void clickToSaveQuestions(View v) {
        try {
            Log.d(MainActivity.TAG, "start try =  ");
            Parser p = new Parser();
            int id = getResources().getIdentifier("questions", "raw", this.getApplicationContext().getPackageName());
            InputStream raw = this.getApplicationContext().getResources().openRawResource(id);
            p.saveQuestionsToFirebase("questions4", raw);
            Log.d(MainActivity.TAG, "END try =  ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void clickToCheckSheet(View view){
        Infra.checkDataIsCorrect("triviaQuestions","questions1");
    }

}
