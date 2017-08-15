package com.ssd.a00818881.tinyarcade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

public class GameActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String STICK = "l";
    Spinner splitSpinner;
    TextView gameText;
    int currentSticks;
    int choice = 0;
    boolean gameInProgress;
    boolean winner;
    boolean debugMode = false;

    int contactId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        splitSpinner = (Spinner) findViewById(R.id.moveSpinner);
        gameText = (TextView) findViewById(R.id.gameText);

        // set array adapter for spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.choice_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        splitSpinner.setAdapter(adapter);

        // set the listeners
        splitSpinner.setOnItemSelectedListener(this);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            contactId = b.getInt("contactId");
        }

        initGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_game_reset:
                onClickGameReset();
                return true;
            case R.id.action_debug_mode:
                debugMode = !debugMode;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initGame(){
        winner = false;
        gameInProgress=true;
        gameText.setText("--> ");
        int initSticks = 20+(int) Math.round(Math.random()*10);

        for (int i = 0; i < initSticks; i++) {
            gameText.append(STICK);
        }
        currentSticks = initSticks;

        if(debugMode){
            gameText.append("-"+Integer.toString(currentSticks));
        }
    }

    public void onClickMakeMove(View view){
        if(gameInProgress){
            gameText.append("\nYou:");
            currentSticks -= choice;
            if(currentSticks <= 0){
                currentSticks = 0;
                winner = false;
                gameInProgress=false;
                gameText.append("Took the Last Stick.\nYou have lost!");
                if(contactId!=0){
                    insertGameplay();
                }
            }else{
                for (int i = 0; i < currentSticks; i++) {
                    gameText.append(STICK);
                }
                aiMakeMove();
            }
        }else{
            gameText.append("\n");
            gameText.append("Game is over.");
        }
    }

    public void aiMakeMove(){
//        SystemClock.sleep(1000);
        int aiChoice = (currentSticks-1)%4!=0 ? (currentSticks-1)%4 : (int) Math.ceil(Math.random()*3);

        if(gameInProgress){
            gameText.append("\nAI :");
            currentSticks -= aiChoice;
            if(currentSticks <= 0){
                currentSticks = 0;
                winner = true;
                gameInProgress=false;
                gameText.append("Took the Last Stick.\nYou have won.");
                if(contactId!=0){
                    insertGameplay();
                }
            }else{
                for (int i = 0; i < currentSticks; i++) {
                    gameText.append(STICK);
                }
            }
        }
        if(debugMode){
            gameText.append("-"+Integer.toString(currentSticks));
        }
    }

    public void onClickGameReset(){
        initGame();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        choice = position + 1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void insertGameplay(){
        //String firstName = firstNameText.getText().toString();
        String date =  new Date().toString();
        String gameplay = gameText.getText().toString();

        DBAdapter db = new DBAdapter(this);
        db.open();
        db.insertGameplay(contactId,date,gameplay,winner);
        db.close();
    }
}
