package com.ssd.a00818881.tinyarcade;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewContactActivity extends AppCompatActivity {

    DBAdapter db;
    public int id;
    TextView firstNameText;
    TextView lastNameText;
    TextView emailText;
    TextView phoneText;
    TextView streetAddressText;
    TextView cityText;
    TextView provinceStateText;
    TextView gamesWonText;
    TextView gamesPlayedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        firstNameText = (TextView) findViewById(R.id.firstNameText);
        lastNameText = (TextView) findViewById(R.id.lastNameText);
        emailText = (TextView) findViewById(R.id.emailText);
        phoneText = (TextView) findViewById(R.id.phoneText);
        streetAddressText = (TextView) findViewById(R.id.streetAddressText);
        cityText = (TextView) findViewById(R.id.cityText);
        provinceStateText = (TextView) findViewById(R.id.provinceStateText);
        gamesWonText = (TextView) findViewById(R.id.gamesWonText);
        gamesPlayedText = (TextView) findViewById(R.id.gamesPlayedText);


        Bundle b = getIntent().getExtras();
        id = b.getInt("id");
    }


    @Override
    public void onResume() {
        super.onResume();

        db = new DBAdapter(this);
        //---get a single contact---
        //Demonstrates displaying a single record by getting a contact at row 2.
        db.open();
        Cursor c = db.getContact(id);
        if (c.moveToFirst())
            displayContact(c);
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_contact_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context m = getApplicationContext();
        switch (item.getItemId()) {
            case R.id.action_game:
                playGame();
                return true;
            case R.id.action_delete:
                alertDelete();
                return true;
            case R.id.action_update:
                Intent intent = new Intent(m, UpdateContactActivity.class);
                intent.putExtra("id",this.id);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void displayContact(Cursor c) {
        firstNameText.setText(": "+c.getString(1));
        lastNameText.setText(": "+c.getString(2));
        emailText.setText(": "+c.getString(3));
        phoneText.setText(": "+c.getString(4));
        streetAddressText.setText(": "+c.getString(5));
        cityText.setText(": "+c.getString(6));
        provinceStateText.setText(": "+c.getString(7));
        gamesWonText.setText(": "+c.getString(8));
        gamesPlayedText.setText(": "+c.getString(9));
    }

    public void alertDelete(){
        new AlertDialog.Builder(this)
                .setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete this contact?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        deleteContact();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void deleteContact(){
        //---delete a contact---
        //Removes the record at row 1.
        db.open();
        if (db.deleteContact(this.id))
            Toast.makeText(this, "Delete successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Delete failed.", Toast.LENGTH_LONG).show();
        db.close();
        finish();
    }

    public void onClickHistory(View view){
        Intent intent = new Intent(this, GameplayHistoryActivity.class);
        intent.putExtra("contactId",this.id);
        startActivity(intent);
    }
    public void onClickPlayGame(View view){
        playGame();
    }

    public void playGame(){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("contactId",this.id);
        startActivity(intent);
    }
}
