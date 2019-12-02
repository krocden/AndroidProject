package com.example.test1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.test1.data.DatabaseHandler;
import com.example.test1.model.Player;

import java.util.ArrayList;
import java.util.List;


public class reviewFragment extends Fragment {

    private Button returnBTN;
    private TextView adviceTV;
    private ArrayList<Player> rosterArrayList;

    public reviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        adviceTV = view.findViewById(R.id.suggestion);
        returnBTN = view.findViewById(R.id.back);

        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

        rosterArrayList = new ArrayList<Player>();
        List<Player> playerList = db.getAllPlayers();
        rosterArrayList.addAll(playerList);

        List<String> posList = new ArrayList<String>();

        int rostersize = db.getPlayerCount();
        double avgf = 0;
        double avgi = 0;
        double totalf = 0;
        double totali = 0;
        for (Player p: rosterArrayList) {
            totalf += p.getHeight_feet();
            totali += p.getHeight_inches();
            posList.add(p.getPosition());
        }
        avgf = totalf/5;
        avgi = totali/5;
        boolean tooShort = false;

        if(avgf < 6)
            tooShort = true;
        else if(avgf == 6 && avgi < 5)
            tooShort = true;

        if(rostersize < 5)
            adviceTV.setText("You should have 5 players before reviewing a team.");
        else if(!posList.contains("C") && tooShort)
            adviceTV.setText("It would be preferable to have at least a center in the team considering your players are short.");
        else if(!posList.contains("C") && !tooShort)
            adviceTV.setText("It would be preferable to have at least a center in the team but your players are tall so it isn't that bad.");
        else if(!posList.contains("F") && tooShort)
            adviceTV.setText("It would be preferable to have at least a forward in the team to score. Your players are also on the shorter end.");
        else if(!posList.contains("F") && !tooShort)
            adviceTV.setText("It would be preferable to have at least a forward in the team so your team can have a better offense.");
        else if(!posList.contains("G") && tooShort)
            adviceTV.setText("It would be preferable to have at least a guard in the team to control the offense.");
        else if(!posList.contains("G") && !tooShort)
            adviceTV.setText("It would be preferable to have at least a guard in the team.");
        else if(tooShort)
            adviceTV.setText("Your players are pretty short. It would help to have taller players.");
        else
            adviceTV.setText("Team looks balanced.");

        returnBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getActivity().onBackPressed();
            }
        });
    }

}
