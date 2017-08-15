package com.ssd.a00818881.tinyarcade;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewGameplayActivity extends AppCompatActivity {

    DBAdapter db;
    public int gameplayId;
    TextView firstNameText;
    TextView dateText;
    TextView gameplayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_gameplay);
//        firstNameText = (TextView) findViewById(R.id.firstNameText);
        dateText = (TextView) findViewById(R.id.dateText);
        gameplayText = (TextView) findViewById(R.id.gameplayText);

        Bundle b = getIntent().getExtras();
        gameplayId = b.getInt("gameplayId");

        db = new DBAdapter(this);
        //---get a single Gameplay---
        //Demonstrates displaying a single record by getting a contact at row 2.
        db.open();
        Cursor c = db.getGameplay(gameplayId);
        if (c.moveToFirst())
            displayGameplay(c);
        db.close();
    }


    public void displayGameplay(Cursor c) {
//        firstNameText.setText(c.getString(1));
        dateText.setText(c.getString(2));
        gameplayText.setText(c.getString(3));
    }
}
