package com.example.test1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.Adapter.RecyclerViewAdapter;
import com.example.test1.Adapter.RecyclerViewAdapterDB;
import com.example.test1.data.DatabaseHandler;
import com.example.test1.model.Player;
import com.example.test1.util.Prefs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapterDB recyclerViewAdapter;
    private ArrayList<Player> playerArrayList;

    private TextView teamNameTV;
    private String teamname;
    private Button resetBTN;
    private Button reviewBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Initialise variables
        final DatabaseHandler db = new DatabaseHandler(SecondActivity.this);
        playerArrayList = new ArrayList<Player>();

        teamNameTV = findViewById(R.id.teamName);
        reviewBTN = findViewById(R.id.reviewTeam);
        resetBTN = findViewById(R.id.resetTeam);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SecondActivity.this));

        // Retrieve all players from DB and add to player list.
        final List<Player> playerList = db.getAllPlayers();

        for (int i = 0; i < playerList.size(); i++) {
            playerArrayList.add(playerList.get(i));
        }
        // Set recycler view adapter with player list
        recyclerViewAdapter = new RecyclerViewAdapterDB(this,playerArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);

        db.close();

        // Receive intent data, then set text for team name.
        receiveData();

        // Create new fragment on click for Review Button
        reviewBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment packageFrag = new reviewFragment();

                ft.replace(R.id.specificPlayer, packageFrag);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //Reset on click for Reset Button
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        resetBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                alertDialogBuilder.setMessage("Reset the team?\n\nThis action is irreversible.");
                        alertDialogBuilder.setPositiveButton("Reset",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        File sharedfile = new File("/data/data/com.example.test1/shared_prefs/MainActivity.xml");
                                        sharedfile.delete();
                                        for (Player p: playerList) {
                                            db.deletePlayer(p);
                                            System.out.println("DELETD" + p.getLname());
                                        }
                                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                        i.putExtra("recreate", "true");
                                        startActivity(i);
                                        finish();
                                    }
                                });

                        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.info:
                showHelp();
                return true;
            case R.id.change:
                changeActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showHelp(){
        // Popup Dialog in Options Menu
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        alertDialogBuilder.setMessage("This is your roster.\n\n" +
                "You can edit the player's data by pressing the edit button and you can remove a player by pressing the remove button." +
                "\n\nIt is advised to not modify the player's data too much in order to better simulate a team." +
                "\n\nWhen ready, press the review button to review your team.");
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void changeActivity(){
        // Method to change activity in Options Menu
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("team", teamname);
        startActivity(i);
    }

    private void receiveData()
    {
        //RECEIVE DATA VIA INTENT
        Intent i = getIntent();
        String teamname = i.getStringExtra("team");
        teamNameTV.setText(teamname);
    }
}
