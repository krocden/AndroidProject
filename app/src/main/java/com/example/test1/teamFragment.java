package com.example.test1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.test1.util.Prefs;


public class teamFragment extends Fragment {

    private EditText teamNameET;
    private Button submitBTN;

    public teamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        // Initialise Variables
        teamNameET = view.findViewById(R.id.teamName);
        submitBTN = view.findViewById(R.id.submitTeam);
        final Prefs p = new Prefs(getActivity());
        submitBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                p.saveTeamName(teamNameET.getText().toString());
                sendData();

            }
        });
    }
    private void sendData()
    {
        //INTENT OBJ
        Intent i = new Intent(getActivity().getBaseContext(), MainActivity.class);

        //PACK DATA
        i.putExtra("SENDER_KEY", "teamFragment");
        i.putExtra("team", teamNameET.getText().toString());

        //START ACTIVITY
        getActivity().startActivity(i);
    }

}
