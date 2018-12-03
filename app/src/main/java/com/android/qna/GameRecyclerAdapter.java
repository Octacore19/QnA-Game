package com.android.qna;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.qna.model.Game;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class GameRecyclerAdapter extends RealmRecyclerViewAdapter<Game, GameRecyclerAdapter.MyViewHolder> {

    public GameRecyclerAdapter(OrderedRealmCollection<Game> data) {
        super(data, true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Game game = getItem(position);
        holder.setGame(game);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Game mGame;

        MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.body);
        }

        void setGame(Game game){
            this.mGame = game;
            this.textView.setText(game.getQuestions());
        }
    }
}
