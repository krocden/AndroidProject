package com.example.test1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.test1.util.MySingleton;
import com.example.test1.util.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView teamNameTV;
    Prefs p;
    private String teamname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Information to reset activity if needed.
        Intent i = getIntent();
        String reset = "";
        if (i.getStringExtra("recreate") != null){
            i.removeExtra("recreate");
            finish();
            System.exit(0);
        }

        // Initialise variables
        p = new Prefs(MainActivity.this);
        teamNameTV = findViewById(R.id.teamName);
        teamname = p.getTeamName();

        // Check if a team has already been set. Decide which fragment to open.
        if(teamname.equals("") || teamname.isEmpty()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment packageFrag = new teamFragment();

            ft.replace(R.id.placeholder_search, packageFrag);
            ft.addToBackStack(null);
            ft.commit();
        }else{
            teamNameTV.setText(teamname);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment packageFrag = new searchPlayerFragment();

            ft.replace(R.id.placeholder_search, packageFrag);
            ft.addToBackStack(null);
            ft.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
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
        alertDialogBuilder.setMessage("Welcome to NBA Manager 2019\n\n" +
                "The purpose of the application is to simulate a basketball manager game and draft a team.\n\n" +
                "You can view your current roster by pressing the right arrow in the top right corner.\n\n" +
                "*Not all players will have a height or a weight.");
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
        Intent i = new Intent(this, SecondActivity.class);
        i.putExtra("team", teamname);
        startActivity(i);
    }

}

