package com.localhost.grok.yandexproject;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.localhost.grok.yandexproject.MVP.Model;
import com.localhost.grok.yandexproject.Retrofit.ArtistGson;

import org.w3c.dom.Text;

/**
 * Created by grok on 4/11/16.
 */
public class ArtistModel implements Model {

    ArtistLoader loader;
    ArtistView view;
    ArtistModel(ArtistView view, ArtistLoader loader)
    {
        this.loader=loader;
        this.view=view;
    }


    //load data to recyclerview
    @Override
    public void getData(Context context, int number) {

        loader.retrieveData(context, view.getRecyclerView(), number);

    }





    //load more information about artist
    public ArtistGson getExtraInfo(int position)
    {
        return loader.getArtistData(position);
    }

}
