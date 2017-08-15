package com.ssd.a00818881.tinyarcade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_add_contact);
        firstNameText = (EditText) findViewById(R.id.firstNameText);
        lastNameText = (EditText) findViewById(R.id.lastNameText);
        emailText = (EditText) findViewById(R.id.emailText);
        phoneText = (EditText) findViewById(R.id.phoneText);
        streetAddressText = (EditText) findViewById(R.id.streetAddressText);
        cityText = (EditText) findViewById(R.id.cityText);
        provinceStateText = (EditText) findViewById(R.id.provinceStateText);
    }

    public void onClickAdd(View view){

        String firstName = firstNameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        String email = emailText.getText().toString();
        String phone = phoneText.getText().toString();
        String streetAddress = streetAddressText.getText().toString();
        String city = cityText.getText().toString();
        String provinceState = provinceStateText.getText().toString();

        DBAdapter db = new DBAdapter(this);

        //---add a contact---
        //opens the DB connection and inserts records.
        //Return a long int indicating the number of rows affected
        //Finally closes the DB, which is ALWAYS a good practice
        db.open();
        @SuppressWarnings("unused")
        long id = db.insertContact(firstName, lastName, email, phone, streetAddress, city, provinceState);
        db.close();
        finish();
    }

    public void onClickAddSample(View view){
        DBAdapter db = new DBAdapter(this);
        db.open();
        @SuppressWarnings("unused")
        long id = db.insertContact("Dakota", "Jang", "dj@domain.com",
                "604-123-5678", "123 ABC Street", "Vancouver", "BC");
        db.close();
        finish();
    }
}
