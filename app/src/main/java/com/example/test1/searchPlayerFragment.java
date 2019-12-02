package com.example.test1;

import android.content.Context;
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

public class searchPlayerFragment extends Fragment {

    private Button searchBTN;
    private EditText playerInputET;
    private String playerInput;

    public searchPlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        // Initialise variables
        playerInputET = view.findViewById(R.id.playerName);
        searchBTN = view.findViewById(R.id.submitPlayer);
        // Button set to launch another fragment when clicked
        searchBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                playerInput = playerInputET.getText().toString();
                Bundle packageData = new Bundle();
                packageData.putString("input",playerInput);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment packageFrag = new searchResultFragment();
                packageFrag.setArguments(packageData);

                ft.replace(R.id.placeholder_playerlist, packageFrag);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}
