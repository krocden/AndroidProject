package com.example.test1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.test1.Adapter.RecyclerViewAdapter;
import com.example.test1.data.DatabaseHandler;
import com.example.test1.model.Player;
import com.example.test1.util.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class searchResultFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Player> playerArrayList;
    private ArrayList<Player> rosterArrayList;

    private Button addPlayerBTN;

    public searchResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        MySingleton ms = MySingleton.getInstance(this.getActivity().getApplicationContext());

        //Initialise variables
        final DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

        addPlayerBTN = view.findViewById(R.id.submitPlayer);

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        playerArrayList = new ArrayList<Player>();

        Bundle bundle = this.getArguments();
        String player = bundle.getString("input");

        // Use api with user input as parameter
        String url = "https://www.balldontlie.io/api/v1/players?search=" + player;
        Log.d("URL","Requested API Url = " +url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i = 0; i <jsonArray.length(); i++){
                        JSONObject player = jsonArray.getJSONObject(i);
                        JSONObject team = player.getJSONObject("team");

                        // Add all player to array. Certain variables are left null if there is no data found in the api.
                        Player play = new Player();

                        String fn = player.getString("first_name");
                        String ln = player.getString("last_name");
                        String teamN = team.getString("full_name");

                        play.setFname(fn);
                        play.setLname(ln);
                        play.setTeam(teamN);

                        if(!player.getString("position").equals("")){
                            String pos = player.getString("position");
                            play.setPosition(pos);
                        }
                        if(!player.getString("height_feet").equals("null")){
                            Double hf = player.getDouble("height_feet");
                            play.setHeight_feet(hf);
                        }
                        if(!player.getString("height_inches").equals("null")){
                            Double hi = player.getDouble("height_inches");
                            play.setHeight_inches(hi);
                        }
                        if(!player.getString("weight_pounds").equals("null")){
                            Double lbs = player.getDouble("weight_pounds");
                            play.setWeight_pounds(lbs);
                        }
//                        Player play = new Player(fn,ln,hf,hi,pos,teamN,lbs);
                        playerArrayList.add(play);
                    }
                }
                catch (JSONException e) {
                    Log.d("ERROR","ERROR");
                    e.printStackTrace();
                }
                recyclerViewAdapter = new RecyclerViewAdapter(getActivity().getApplicationContext(),playerArrayList);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        ms.addToRequestQueue(jsonObjectRequest);

        addPlayerBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try{
                    rosterArrayList = new ArrayList<Player>();
                    List<Player> playerList = db.getAllPlayers();
                    rosterArrayList.addAll(playerList);
                    Player selected = recyclerViewAdapter.getSelectedPlayer();
                    boolean exist = false;
                    for (Player p: rosterArrayList) {
                        if(selected.getFname().equals(p.getFname()) && selected.getLname().equals(p.getLname()))
                            exist = true;
                    }
                    if(exist){
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Player is already in Roster.", Toast.LENGTH_SHORT).show();
                    }
                    else if(db.getPlayerCount() < 5){
                        db.addPlayer(selected);
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Successfully added "+selected.getFname()+" "+selected.getLname()+" to Roster.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Max player count is 5.", Toast.LENGTH_SHORT).show();
                    }
                    }
                catch (Exception e){
                    Toast.makeText(getActivity().getApplicationContext(), "Invalid.", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        recyclerViewAdapter = new RecyclerViewAdapter(getActivity().getApplicationContext(),playerArrayList);
//        recyclerView.setAdapter(recyclerViewAdapter);
        //recyclerViewAdapter.notifyDataSetChanged();
    }

}
