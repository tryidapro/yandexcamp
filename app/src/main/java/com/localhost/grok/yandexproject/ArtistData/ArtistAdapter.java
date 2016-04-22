package com.localhost.grok.yandexproject.ArtistData;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.localhost.grok.yandexproject.R;
import com.localhost.grok.yandexproject.Retrofit.ArtistGson;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grok on 4/11/16.
 */
public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistElements>   {
    private List<ArtistGson> artistDataList = new ArrayList<ArtistGson>();


    public class ArtistElements extends RecyclerView.ViewHolder {

        TextView artistName;
        TextView itemGenres;
        TextView itemRelises;
        ImageView itemImage;
        ArtistElements(View element)
        {
            super(element);
            artistName = (TextView)element.findViewById(R.id.artistName);
            itemGenres=(TextView)element.findViewById(R.id.itemGenres);
            itemRelises = (TextView) element.findViewById(R.id.itemRelises);
            itemImage= (ImageView)element.findViewById(R.id.itemImage);

        }



    }


   public ArtistAdapter(List<ArtistGson> artistDataList)
    {
        if (artistDataList!=null) {
            this.artistDataList = artistDataList;
        }
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ArtistElements onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_brief, parent, false);
        ArtistElements artistAdapter = new ArtistElements(v);

        return artistAdapter;

    }

    public void addItems(List<ArtistGson>listArtist)
    {
        artistDataList.addAll(listArtist);
            }


    @Override
    public void onBindViewHolder(ArtistElements holder, int position) {
        holder.artistName.setText(artistDataList.get(position).getName());
        holder.artistName.setTextColor(Color.BLACK);
        holder.itemGenres.setText(artistDataList.get(position).getGenres());
        holder.itemGenres.setTextColor(Color.GRAY);
        String relises = artistDataList.get(position).getAlbums()+" albums " + artistDataList.get(position).getTracks()+ " tracks";
        holder.itemRelises.setText(relises);
        holder.itemRelises.setTextColor(Color.GRAY);
        Picasso.with(holder.itemImage.getContext()).load(artistDataList.get(position).getSmallIcon()).into(holder.itemImage);
    }


    @Override
    public int getItemCount() {
        return artistDataList.size();
    }


}
