package com.example.test1.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.R;
import com.example.test1.model.Player;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Player> playerList;
    private int selectedPos = RecyclerView.NO_POSITION;

    public RecyclerViewAdapter(Context context, List<Player> playerList){
        this.context = context;
        this.playerList = playerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_row,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Player player = playerList.get(position);
        holder.itemView.setSelected(selectedPos == position);
        holder.fname.setText(player.getFname());
        holder.lname.setText(player.getLname());
//        holder.heightf.setText(String.format(Locale.CANADA,"%.2f",player.getHeight_feet()));
//        holder.heighti.setText(String.format(Locale.CANADA,"%.2f",player.getHeight_inches()));
//        holder.position.setText(player.getPosition());
        holder.team.setText(player.getTeam());
//        holder.weight.setText(String.format(Locale.CANADA,"%.2f",player.getWeight_pounds()));
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView fname;
        private TextView lname;
        private TextView heightf;
        private TextView heighti;
        private TextView position;
        private TextView team;
        private TextView weight;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
//            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            fname = itemView.findViewById(R.id.fn);
            lname = itemView.findViewById(R.id.ln);
//            heightf = itemView.findViewById(R.id.hf);
//            heighti = itemView.findViewById(R.id.hi);
//            position = itemView.findViewById(R.id.pos);
            team = itemView.findViewById(R.id.team);
//            weight = itemView.findViewById(R.id.lbs);
        }

        @Override
        public void onClick(View view) {
            Log.d("Clicked", "onClick: " + view.getId());
            notifyItemChanged(selectedPos);
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);
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