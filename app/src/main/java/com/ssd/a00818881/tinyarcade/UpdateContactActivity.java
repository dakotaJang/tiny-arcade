package com.ssd.a00818881.tinyarcade;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateContactActivity extends AppCompatActivity {
    DBAdapter db;
    public int id;
    EditText firstNameText;
    EditText lastNameText;
    EditText emailText;
    EditText phoneText;
    EditText streetAddressText;
    EditText cityText;
    EditText provinceStateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);
        firstNameText = (EditText) findViewById(R.id.firstNameText);
        lastNameText = (EditText) findViewById(R.id.lastNameText);
        emailText = (EditText) findViewById(R.id.emailText);
        phoneText = (EditText) findViewById(R.id.phoneText);
        streetAddressText = (EditText) findViewById(R.id.streetAddressText);
        cityText = (EditText) findViewById(R.id.cityText);
        provinceStateText = (EditText) findViewById(R.id.provinceStateText);

        Bundle b = getIntent().getExtras();
        id = b.getInt("id");

        db = new DBAdapter(this);
        //---get a single contact---
        //Demonstrates displaying a single record by getting a contact at row 2.
        db.open();
        Cursor c = db.getContact(id);
        if (c.moveToFirst())
            displayContact(c);
        db.close();
    }


    public void displayContact(Cursor c) {
        firstNameText.setText(c.getString(1));
        lastNameText.setText(c.getString(2));
        emailText.setText(c.getString(3));
        phoneText.setText(c.getString(4));
        streetAddressText.setText(c.getString(5));
        cityText.setText(c.getString(6));
        provinceStateText.setText(c.getString(7));
    }

    public void onClickUpdate(View view) {

        String firstName = firstNameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        String email = emailText.getText().toString();
        String phone = phoneText.getText().toString();
        String streetAddress = streetAddressText.getText().toString();
        String city = cityText.getText().toString();
        String provinceState = provinceStateText.getText().toString();

        //---update contact---
        //Updates the record at row 1 by changing the email address.
        //The change is verified by the Toast.
        db.open();
        if (db.updateContact(this.id, firstName, lastName,email,phone,streetAddress,city,provinceState))
            Toast.makeText(this, "Update successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Update failed.", Toast.LENGTH_LONG).show();
        db.close();
        finish();
    }
}
