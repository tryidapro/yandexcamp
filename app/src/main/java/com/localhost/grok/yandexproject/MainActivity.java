package com.localhost.grok.yandexproject;

import android.animation.AnimatorInflater;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.localhost.grok.yandexproject.ArtistData.ArtistAdapter;
import com.localhost.grok.yandexproject.Retrofit.ArtistGson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static ArtistModel artistModel;
    public static int numberRowsPerLoad=10;
    public static List<ArtistGson> itemDisplayed;
    ArtistLoader artistLoader;
    ArtistView artistView;
    RecyclerView recyclerView;
    public boolean isLoading;
    String URL="http://cache-default02g.cdn.yandex.net";
    public static Integer firstPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {

            isLoading=false;
            artistView = new ArtistView((RecyclerView) findViewById(R.id.artistList));
            artistLoader = new ArtistLoader(URL);
            //A little MVP pattern magic
            artistModel = new ArtistModel(artistView, artistLoader);
            recyclerView = (RecyclerView) findViewById(R.id.artistList);
            itemDisplayed = new ArrayList<ArtistGson>();
            recyclerView.setAdapter(new ArtistAdapter(itemDisplayed));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent extraIntent = new Intent(getApplicationContext(), ExtraActivity.class);
                            extraIntent.putExtra("position", position);
                            firstPosition = getFirstPosition(recyclerView);
                            startActivity(extraIntent);


                        }
                    })
            );

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (!(isLoading)) {
                        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        //position starts at 0
                        if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 2) {
                                isLoading=true;
                                artistModel.getData(getApplicationContext(),MainActivity.numberRowsPerLoad);
                                isLoading=false;

                              }
                    }
                }
            });


        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext()," "+e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }







    @Override
    protected void onResume() {
        super.onResume();

        try {
            if (firstPosition>=itemDisplayed.size()) {
                artistModel.getData(this, (firstPosition-itemDisplayed.size()+numberRowsPerLoad));
            } else
            {
                artistModel.getData(this,0);
            }
                recyclerView.scrollToPosition(firstPosition);
            
        } catch (Exception e)
        {
            Toast.makeText(getApplicationContext()," "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
        showMemoryUsage();
    }

    @Override
    protected void onPause() {
        super.onPause();
        firstPosition=getFirstPosition(recyclerView);

    }


    private Integer getFirstPosition(RecyclerView rv)
    {
        LinearLayoutManager staggeredGridLayoutManager = (LinearLayoutManager)rv.getLayoutManager();
        int position =staggeredGridLayoutManager.findFirstVisibleItemPosition();
        return position;
    }


    //observing memory usage status. just for debug
    private void showMemoryUsage()
    {
        try {
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(mi);
            long availableMegs = mi.availMem / 1048576L;
            Log.i("Memory avail:", " " + availableMegs);
        }  catch (Exception e)
        {
            Toast.makeText(getApplicationContext()," "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
