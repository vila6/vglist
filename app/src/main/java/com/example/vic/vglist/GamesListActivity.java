package com.example.vic.vglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GamesListActivity extends AppCompatActivity {
    public ArrayList<Game> gamesList;
    final CustomAdapter adapter = new CustomAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DBManager dbManager = new DBManager(getApplicationContext());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameslist);

         ListView listGames = (ListView) findViewById(R.id.listGames);
         gamesList = dbManager.getAllGames();
         listGames.setAdapter(adapter);

        listGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Game juegoClicado = adapter.getItem(i);
                Intent toGameDetails = new Intent(view.getContext(), GameDetailsActivity.class);
                toGameDetails.putExtra("game", juegoClicado);
                startActivity(toGameDetails);
                final CustomAdapter adapter = new CustomAdapter();
            }
        });

    }

    @Override
    protected void onResume() {
        DBManager dbManager = new DBManager(getApplicationContext());
        super.onResume();
        ListView listGames = (ListView) findViewById(R.id.listGames);
        gamesList = dbManager.getAllGames();
        listGames.setAdapter(adapter);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return gamesList.size();
        }


        @Override
        public Game getItem(int i) {
            return gamesList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.list_item_rated,null);

            ImageView imageView = (ImageView)view.findViewById(R.id.imgCover);
            TextView textGame = (TextView) view.findViewById(R.id.textGameName);
            RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            RatingBar ratingBarUser = (RatingBar) view.findViewById(R.id.ratingBarUser);
            TextView textState = (TextView) view.findViewById(R.id.textState);

            if(gamesList.size()>i) {
                Game actualGame = gamesList.get(i);
                Glide.with(getApplicationContext())
                        .load(actualGame.getCoverUrl())
                        .into(imageView);
                textGame.setText((CharSequence) actualGame.getName());
                ratingBar.setRating((float)(actualGame.getRating()/10));
                ratingBarUser.setRating(actualGame.getRatinguser());
                textState.setText((CharSequence) actualGame.getNameState());
            }

            return view;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }
}
