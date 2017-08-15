package com.ssd.a00818881.tinyarcade;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class GameplayHistoryActivity extends AppCompatActivity {

    private ListView listView;

    private static final String TAG = MainActivity.class.getSimpleName();

    private GameplayCursorAdapter customAdapter;
    DBAdapter db;
    int contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay_history);

        db = new DBAdapter(this);

        listView = (ListView) findViewById(R.id.history_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "clicked on item: " + position);
            }
        });


        Bundle b = getIntent().getExtras();
        contactId = b.getInt("contactId");
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                db.open();
                customAdapter = new GameplayCursorAdapter(GameplayHistoryActivity.this, db.getGameplayByContact(contactId));
                listView.setAdapter(customAdapter);
                db.close();
            }
        });
    }

    /* This method is called implicilty from the receiving activity, EnterDataActivity.
     * The new data is received and then added to the db. Then the ContactCursorAdapter is
     * called to update the listView after fetching all the data dta including the new records.
     */
    private static final int ENTER_DATA_REQUEST_CODE = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ENTER_DATA_REQUEST_CODE && resultCode == RESULT_OK) {

            //db.insertContact(data.getExtras().getString("tag_gameplay"), data.getExtras().getString("tag_person_pin"),"","","","","");

            customAdapter.changeCursor(db.getAllGameplays());
        }
    }

}
