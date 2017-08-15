package com.ssd.a00818881.tinyarcade;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ContactCursorAdapter customAdapter;
    private static final int ENTER_DATA_REQUEST_CODE = 1;
    private ListView listView;

    private static final String TAG = MainActivity.class.getSimpleName();

    DBAdapter db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBAdapter(this);

        listView = (ListView) findViewById(R.id.contacts_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "clicked on item: " + position);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                db.open();
                customAdapter = new ContactCursorAdapter(MainActivity.this, db.getAllContacts());
                listView.setAdapter(customAdapter);
                db.close();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context m = getApplicationContext();
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivity(new Intent(m, AddContactActivity.class));
                return true;
            case R.id.action_game:
                startActivity(new Intent(m, GameActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* This method is called implicilty from the receiving activity, EnterDataActivity.
     * The new data is received and then added to the db. Then the ContactCursorAdapter is
     * called to update the listView after fetching all the data dta including the new records.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ENTER_DATA_REQUEST_CODE && resultCode == RESULT_OK) {

            db.insertContact(data.getExtras().getString("tag_person_name"), data.getExtras().getString("tag_person_pin"),"","","","","");

            customAdapter.changeCursor(db.getAllContacts());
        }
    }
}
