package com.example.test1.util;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

public class Prefs {
    private SharedPreferences preference;

    public Prefs(Activity activity){
        this.preference = activity.getPreferences(activity.MODE_PRIVATE);
    }
    public void saveTeamName(String team){
        String currentName = team;
        String lastTeam = preference.getString("team", "");

        if(currentName != lastTeam){
            preference.edit().putString("team",currentName).apply();
        }
    }
    public String getTeamName(){
        return preference.getString("team","");
    }

}
