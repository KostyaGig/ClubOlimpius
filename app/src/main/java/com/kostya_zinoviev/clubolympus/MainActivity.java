package com.kostya_zinoviev.clubolympus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import data.ClubOlimpusContract;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private TextView dataTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.floatingActionButton);
        dataTextView = findViewById(R.id.dataTextView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AddMemberGroup.class);
                startActivity(i);
            }
        });
    }
    //Создадим метод,с помощью которого мы будем отображать наши данные

    private void displayData(){
        String[] projection = {
                ClubOlimpusContract.MemberEntry.COLUMN_ID,
                ClubOlimpusContract.MemberEntry.COLUMN_FIRST_NAME,
                ClubOlimpusContract.MemberEntry.COLUMN_LAST_NAME,
                ClubOlimpusContract.MemberEntry.COLUMN_GENDER,
                ClubOlimpusContract.MemberEntry.COLUMN_SPORT};
       Cursor cursor =  getContentResolver().query(ClubOlimpusContract.MemberEntry.CONTENT_URI,projection,
                null,null,
                null,null);
       dataTextView.setText("All members\n\n");
       dataTextView.append(
               ClubOlimpusContract.MemberEntry.COLUMN_ID + " " +
               ClubOlimpusContract.MemberEntry.COLUMN_FIRST_NAME + " " +
               ClubOlimpusContract.MemberEntry.COLUMN_LAST_NAME + " " +
               ClubOlimpusContract.MemberEntry.COLUMN_GENDER + " " +
               ClubOlimpusContract.MemberEntry.COLUMN_SPORT);
       //Получаем индекс каждой колонки
        int idColumnIndex = cursor.getColumnIndex(ClubOlimpusContract.MemberEntry.COLUMN_ID);
        int firstNameColumnIndex = cursor.getColumnIndex(ClubOlimpusContract.MemberEntry.COLUMN_FIRST_NAME);
        int lastNameColumnIndex = cursor.getColumnIndex(ClubOlimpusContract.MemberEntry.COLUMN_LAST_NAME);
        int genderColumnIndex = cursor.getColumnIndex(ClubOlimpusContract.MemberEntry.COLUMN_GENDER);
        int sportColumnIndex = cursor.getColumnIndex(ClubOlimpusContract.MemberEntry.COLUMN_SPORT);
        while(cursor.moveToNext()){
            int currentId = cursor.getInt(idColumnIndex);
            String currentFirstName = cursor.getString(firstNameColumnIndex);
            String currentLastName = cursor.getString(lastNameColumnIndex);
            int currentGender = cursor.getInt(genderColumnIndex);
            String currentSport = cursor.getString(sportColumnIndex);
            dataTextView.append("\n" + " " + currentId + " " + currentFirstName + " " + currentLastName + " " + currentGender  + " " + currentSport);
            cursor.close();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

}
