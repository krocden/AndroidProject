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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.data.DatabaseHandler;
import com.example.test1.model.Player;


public class playerInfoFragment extends Fragment {

    private TextView fnTV;
    private TextView lnTV;
    private TextView teamTV;
    private TextView posTV;
    private TextView heightTV;
    private TextView weightTV;

    private EditText feetET;
    private EditText inchET;

    private Button returnBTN;
    private Button updateBTN;

    public playerInfoFragment() {
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
        return inflater.inflate(R.layout.fragment_player_info, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

        final DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());

        final int id = getArguments().getInt("id");
        final String fn = getArguments().getString("fname");
        final String ln = getArguments().getString("lname");
        final String team = getArguments().getString("team");
        String pos = getArguments().getString("pos");
        final String height = (int)getArguments().getDouble("feet")+"'"+(int)getArguments().getDouble("inch");
        Double weight = getArguments().getDouble("weight");
        final Double feet = getArguments().getDouble("feet");
        final Double inch = getArguments().getDouble("inch");

        fnTV = view.findViewById(R.id.fn);
        lnTV = view.findViewById(R.id.ln);
        teamTV = view.findViewById(R.id.team);
        posTV = view.findViewById(R.id.pos);
        heightTV = view.findViewById(R.id.height);
        weightTV = view.findViewById(R.id.lbs);

        feetET = view.findViewById(R.id.editheightfeet);
        inchET = view.findViewById(R.id.editheightinches);
        returnBTN = view.findViewById(R.id.back);
        updateBTN = view.findViewById(R.id.edit);

        fnTV.setText(fn);
        lnTV.setText(ln);
        teamTV.setText(team);
        posTV.setText("Position: " + pos);
        heightTV.setText("Height: "+height);
        weightTV.setText("Weight: "+weight.toString());

        int currentPos = 0;

        if(pos == null)
            currentPos = 0;
        else if(pos.equals("F"))
            currentPos = 0;
        else if(pos.equals("G"))
            currentPos = 1;
        else
            currentPos = 2;

        String [] values =
                {"F","G","C"};
        // Spinner element
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setSelection(currentPos);

        // initiate the Seek bar
        final SeekBar seekbar = (SeekBar) view.findViewById(R.id.seekBar);
        seekbar.setProgress(weight.intValue());
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                weightTV.setText("Weight: " + String.valueOf(new Integer(i)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        returnBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
                startActivity(getActivity().getIntent());
                getActivity().overridePendingTransition(0, 0);

                //getActivity().onBackPressed();
            }
        });

        updateBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try{
                    double newfeet = 0.0;
                    double newinch = 0.0;
                    if(feetET.getText().toString().isEmpty()){
                        newfeet = feet;
                    }
                    else newfeet = Double.parseDouble(feetET.getText().toString());
                    if(inchET.getText().toString().isEmpty()){
                        newinch = inch;
                    }
                    else newinch = Double.parseDouble(inchET.getText().toString());
                    String newpos = spinner.getSelectedItem().toString();
                    Double newweight = (double) seekbar.getProgress();
                    Player p = new Player(id,fn,ln,newfeet,newinch,newpos,team,newweight);
                    db.updatePlayer(p);
                    Toast.makeText(view.getContext(), "Successfully updated.", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(view.getContext(), "Invalid" +e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
