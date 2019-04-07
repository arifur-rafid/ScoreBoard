package com.example.scoreboard;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RankListAdapter extends RecyclerView.Adapter<RankListAdapter.ViewHolder> {

    private List<GameData>listItems;
    private Context context;

    public RankListAdapter(List<GameData> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rank_list,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GameData gameData =listItems.get(i);
        viewHolder.rank.setText(i+1+"");
        viewHolder.name.setText(gameData.getPlayer_name());
        viewHolder.amount.setText(Html.fromHtml("BDT "+"<b>"+gameData.getPlayer_amount()+"</b>"));
        viewHolder.profile_pic.setImageResource(gameData.getProfile_pic_name_id());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
    public int getSize(){
        return listItems.size();
    }
    public void clear() {
        final int size = listItems.size();
        listItems.clear();
        notifyItemRangeRemoved(0, size);
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView rank;
        public ImageView profile_pic;
        public TextView name;
        public TextView amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rank=(TextView)itemView.findViewById(R.id.ranktv);
            name=(TextView)itemView.findViewById(R.id.nametv);
            amount=(TextView)itemView.findViewById(R.id.amounttv);
            profile_pic=(ImageView) itemView.findViewById(R.id.profile_pic);
        }
    }
}
