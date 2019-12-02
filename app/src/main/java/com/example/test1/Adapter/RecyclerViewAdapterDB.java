package com.example.test1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.R;
import com.example.test1.SecondActivity;
import com.example.test1.data.DatabaseHandler;
import com.example.test1.model.Player;
import com.example.test1.playerInfoFragment;
import com.example.test1.teamFragment;

import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapterDB extends RecyclerView.Adapter<RecyclerViewAdapterDB.ViewHolder> {
    private Context context;
    private List<Player> playerList;
    private int selectedPos = RecyclerView.NO_POSITION;

    public RecyclerViewAdapterDB(Context context, List<Player> playerList){
        this.context = context;
        this.playerList = playerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_row_db,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Player player = playerList.get(position);
        holder.itemView.setSelected(selectedPos == position);
        holder.fname.setText(player.getFname());
        holder.lname.setText(player.getLname());
        holder.team.setText(player.getTeam());
        final DatabaseHandler db = new DatabaseHandler(context);

        if(player.getPosition() == null)
            holder.position.setText("Position: ?");
        else
            holder.position.setText("Position: "+player.getPosition());

        String height = "Height: "+(int)player.getHeight_feet() + "' "+ (int)player.getHeight_inches();
        if((int)player.getHeight_feet() == 0)
            holder.height.setText("Height: ?");
        else
            holder.height.setText(height);
        if((int)player.getWeight_pounds() == 0)
            holder.weight.setText("Weight: ?");
        else
            holder.weight.setText("Weight: "+player.getWeight_pounds());

        holder.removeBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try{
                    playerList.remove(player);
                    db.deletePlayer(player);
                    notifyItemRemoved(position);
                    Toast.makeText(view.getContext(),
                            "Successfully removed "+player.getFname()+" "+player.getLname()+" from Roster.", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(view.getContext(), "Invalid.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.editBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try{
                    //Player selected = playerList.get(selectedPos);
                    FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    Fragment packageFrag = new playerInfoFragment();

                    Bundle data = new Bundle();
                    data.putInt("id",player.getId());
                    data.putString("fname",player.getFname());
                    data.putString("lname",player.getLname());
                    data.putString("team",player.getTeam());
                    data.putString("pos",player.getPosition());
                    //data.putDouble("height",player.getHeight_feet() + player.getHeight_inches());
                    data.putDouble("feet",player.getHeight_feet());
                    data.putDouble("inch",player.getHeight_inches());
                    data.putDouble("weight",player.getWeight_pounds());

                    packageFrag.setArguments(data);
                    ft.replace(R.id.specificPlayer, packageFrag);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                catch (Exception e){
                    Toast.makeText(view.getContext(), "Invalid." + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView fname;
        private TextView lname;
        private TextView height;
        private TextView position;
        private TextView team;
        private TextView weight;
        private Button editBTN;
        private Button removeBTN;
        private DatabaseHandler db;
        public ViewHolder(@NonNull final View itemView){
            super(itemView);
//            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            fname = itemView.findViewById(R.id.fn);
            lname = itemView.findViewById(R.id.ln);
            team = itemView.findViewById(R.id.team);
            height = itemView.findViewById(R.id.height);
            position = itemView.findViewById(R.id.pos);
            weight = itemView.findViewById(R.id.lbs);
            editBTN = itemView.findViewById(R.id.edit);
            removeBTN = itemView.findViewById(R.id.delete);

        }

        @Override
        public void onClick(View view) {
            Log.d("Clicked", "onClick: " + view.getId());
//            notifyItemChanged(selectedPos);
//            selectedPos = getLayoutPosition();
//            notifyItemChanged(selectedPos);
//            int position = getAdapterPosition();
//            Player player = playerList.get(position);
//            Log.d("Clicked", "onClick: " + player.getFname());
//            Log.d("Clicked", "onClick: " + player.getLname());
//
//            Intent intent = new Intent(context, activity_details.class);
//            intent.putExtra("First name ", player.getFname());
//            intent.putExtra("last name ", player.getLname());
//
//            context.startActivity(intent);
        }
    }

    public Player getSelectedPlayer(){
        Player player = playerList.get(selectedPos);
        return player;
    }


}