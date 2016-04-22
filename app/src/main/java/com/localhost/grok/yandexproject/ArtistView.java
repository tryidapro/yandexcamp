package com.localhost.grok.yandexproject;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.localhost.grok.yandexproject.ArtistData.ArtistAdapter;
import com.localhost.grok.yandexproject.MVP.View;
import com.localhost.grok.yandexproject.Retrofit.ArtistGson;

import java.util.List;

/**
 * Created by grok on 4/11/16.
 */
public class ArtistView implements View {
    RecyclerView recyclerView;
    List<ArtistGson> data;

    public ArtistView(RecyclerView v)
    {
        this.recyclerView=v;
    }


    public RecyclerView getRecyclerView()
    {
        return recyclerView;
    }


}
