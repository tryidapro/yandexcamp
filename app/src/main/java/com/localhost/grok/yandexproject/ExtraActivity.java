package com.localhost.grok.yandexproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.localhost.grok.yandexproject.Retrofit.ArtistGson;
import com.squareup.picasso.Picasso;

/**
 * Created by grok on 4/12/16.
 */
public class ExtraActivity extends AppCompatActivity {
    ImageView fullImage;
    TextView fullGenres;
    TextView fullAlbums;
    TextView fullInfo;

    @Override
    public boolean onOptionsItemSelected(MenuItem item)  {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        loadData();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

    }

    protected void onResume() {
        super.onResume();
        loadData();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void init()
    {
        fullImage=(ImageView)findViewById(R.id.fullImage);
        fullGenres=(TextView)findViewById(R.id.fullGenres);
        fullAlbums=(TextView)findViewById(R.id.fullAlbums);
        fullInfo=(TextView)findViewById(R.id.fullInfo);
    }

    //displaying data on the screen.
    //data loaded from list of artistGson
    //required item number provided by main activity
    private void loadData()
    {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            actionBar.setDisplayShowHomeEnabled(true);

            Intent intent = getIntent();
            Integer position = intent.getIntExtra("position", 0);
            init();
            ArtistGson artistGson = MainActivity.artistModel.getExtraInfo(position);
            actionBar.setTitle(artistGson.getName());
            Picasso.with(this).load(artistGson.getFullIcon()).into(fullImage);
            fullGenres.setText(artistGson.getGenres());
            fullGenres.setTextColor(Color.GRAY);
            fullAlbums.setText(artistGson.getAlbums() + " albums - " + artistGson.getTracks() + " tracks");
            fullAlbums.setTextColor(Color.GRAY);
            fullInfo.setText("Биография\n" + MainActivity.artistModel.getExtraInfo(position).getDescription());
            fullInfo.setTextColor(Color.BLACK);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), " " + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }
}
