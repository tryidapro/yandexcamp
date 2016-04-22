package com.localhost.grok.yandexproject.Retrofit;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grok on 4/12/16.
 */
public class ArtistGson {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("genres")
    @Expose
    private List<String> genres = new ArrayList<String>();
    @SerializedName("tracks")
    @Expose
    private Integer tracks;
    @SerializedName("albums")
    @Expose
    private Integer albums;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("cover")
    @Expose
    private Cover cover;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }


    /**
     *
     * @return
     * The genres
     */
    public  String getGenres() {

        String joined = TextUtils.join(", ", genres);
        return joined;
    }


    /**
     *
     * @return
     * The tracks
     */
    public Integer getTracks() {
        return tracks;
    }



    /**
     *
     * @return
     * The albums
     */
    public Integer getAlbums() {
        return albums;
    }


    /**
     *
     * @return
     * The link
     */
    public String getLink() {
        return link;
    }


    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The cover
     */
    public Cover getCover() {
        return cover;
    }

    public String getSmallIcon()
    {
        return cover.getSmall();
    }
    public String getFullIcon()
    {
        return cover.getBig();
    }






     class Cover {

        @SerializedName("small")
        @Expose
        private String small;
        @SerializedName("big")
        @Expose
        private String big;

        /**
         *
         * @return
         * The small
         */
        public String getSmall() {
            return small;
        }


        /**
         *
         * @return
         * The big
         */
        public String getBig() {
            return big;
        }


    }

}
