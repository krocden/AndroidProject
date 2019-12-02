package com.example.test1.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.test1.R;
import com.example.test1.model.Player;
import com.example.test1.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYER_TABLE = "CREATE TABLE " +  Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY NOT NULL, "
                + Util.KEY_FNAME + " TEXT, "
                + Util.KEY_LNAME + " TEXT, "
                + Util.KEY_HEIGHTF + " REAL, "
                + Util.KEY_HEIGHTI + " REAL, "
                + Util.KEY_POSITION + " TEXT, "
                + Util.KEY_TEAM + " TEXT, "
                + Util.KEY_WEIGHT + " REAL"+ ")";
        db.execSQL(CREATE_PLAYER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});
        onCreate(db);
    }

    //add a player
    public void addPlayer(Player player){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_FNAME,player.getFname());
        values.put(Util.KEY_LNAME,player.getLname());
        values.put(Util.KEY_HEIGHTF,player.getHeight_feet());
        values.put(Util.KEY_HEIGHTI,player.getHeight_inches());
        values.put(Util.KEY_POSITION,player.getPosition());
        values.put(Util.KEY_TEAM,player.getTeam());
        values.put(Util.KEY_WEIGHT,player.getWeight_pounds());

        db.insert(Util.TABLE_NAME,null,values);
        Log.d("handler","addplayer: "+" item added");

        db.close();
    }

    public int updatePlayer(Player player){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_FNAME,player.getFname());
        values.put(Util.KEY_LNAME,player.getLname());
        values.put(Util.KEY_HEIGHTF,player.getHeight_feet());
        values.put(Util.KEY_HEIGHTI,player.getHeight_inches());
        values.put(Util.KEY_POSITION,player.getPosition());
        values.put(Util.KEY_TEAM,player.getTeam());
        values.put(Util.KEY_WEIGHT,player.getWeight_pounds());

        return db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?", new String[]{String.valueOf(player.getId())});
    }

    public void deletePlayer(Player player){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME,Util.KEY_ID+"=?", new String[]{String.valueOf(player.getId())});
    }

    //get a player
    public Player getPlayer(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.KEY_ID,Util.KEY_FNAME,Util.KEY_LNAME,Util.KEY_HEIGHTF, Util.KEY_HEIGHTI,
        Util.KEY_POSITION, Util.KEY_TEAM, Util.KEY_WEIGHT},Util.KEY_ID + "=?", new String[]{String.valueOf(id)},null,null,null);

        if(cursor != null ){
            cursor.moveToFirst();
            Player player = new Player();
            player.setId(Integer.parseInt(cursor.getString(0)));
            player.setFname(cursor.getString(1));
            player.setLname(cursor.getString(2));
            player.setHeight_feet(cursor.getDouble(3));
            player.setHeight_inches(cursor.getDouble(4));
            player.setPosition(cursor.getString(5));
            player.setTeam(cursor.getString(6));
            player.setWeight_pounds(cursor.getDouble(7));
            db.close();
            return player;
        }else{
            db.close();
            return null;
        }
    }

    public int getPlayerCount(){
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    public List<Player> getAllPlayers(){
        List<Player> playerList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if(cursor.moveToFirst()){
            do{
                Player player = new Player();
                player.setId(Integer.parseInt(cursor.getString(0)));
                player.setFname(cursor.getString(1));
                player.setLname(cursor.getString(2));
                player.setHeight_feet(cursor.getDouble(3));
                player.setHeight_inches(cursor.getDouble(4));
                player.setPosition(cursor.getString(5));
                player.setTeam(cursor.getString(6));
                player.setWeight_pounds(cursor.getDouble(7));

                playerList.add(player);
            }while(cursor.moveToNext());
        }
        return playerList;
    }
}