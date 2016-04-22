package com.localhost.grok.yandexproject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.localhost.grok.yandexproject.ArtistData.ArtistAdapter;
import com.localhost.grok.yandexproject.MVP.DataLoader;
import com.localhost.grok.yandexproject.Retrofit.ArtistGson;
import com.localhost.grok.yandexproject.Retrofit.RetrofitInterface;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by grok on 4/11/16.
 */
public class ArtistLoader implements DataLoader {
    String url;
    private static List<ArtistGson> artistDataList;


    ArtistLoader(String url) {
        this.url = url;
    }
    //Check if network available. Otherwise, cashed data will be loaded
    private boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager cm;
        cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        Boolean isOnline= activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        Log.i("Connection", " " + isOnline);
        return isOnline;
    }

    public void retrieveData(Context context, RecyclerView recyclerView, int number) {
        loadData(context, url, recyclerView, number);



    }

    //varius ways to load data may be implemented. Now availible retrofit only
    private void loadData(Context context, String url, RecyclerView recyclerView, int number) {

       if ((artistDataList==null)){
           Log.i("artistDataList null", "load data");
          retrofitLoad(url, recyclerView, context);
       }
        else //it's not neccesary to call retrofit every time.
       {   loadCached(recyclerView, number);

       }

    }



    public ArtistGson getArtistData(int position) {

        return artistDataList.get(position);
    }


    //Retrofit implementation
    //GSON parsing, caching support aded
    private void retrofitLoad(String url, final RecyclerView recyclerView, final Context context)

    {

        OkHttpClient okHttpClient = createCachedClient(context);
        RetrofitInterface retrofitInterface;

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<List<ArtistGson>> call = retrofitInterface.loadArtists();
        call.enqueue(new Callback<List<ArtistGson>>() {
            @Override
            public void onResponse(Response<List<ArtistGson>> response, Retrofit retrofit) {


                artistDataList = response.body();
                loadCached(recyclerView, MainActivity.numberRowsPerLoad+MainActivity.firstPosition);
                recyclerView.scrollToPosition(MainActivity.firstPosition);

            }

            @Override
            public void onFailure(Throwable t) {

                Log.e("Retrofit error", " " + t.getMessage());
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    //caching request
    private OkHttpClient createCachedClient(final Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), "cache_file");

        Cache cache = new Cache(httpCacheDirectory, 20 * 1024 * 1024);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(cache);
        okHttpClient.interceptors().add(
                new Interceptor() {
                    @Override
                    public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        String cacheHeaderValue = isNetworkAvailable(context)
                                ? "public, max-age=2419200"
                                : "public, only-if-cached, max-stale=2419200";
                        Request request = originalRequest.newBuilder().build();
                        com.squareup.okhttp.Response response = chain.proceed(request);
                        return response.newBuilder()
                                .removeHeader("Pragma")
                                .removeHeader("Cache-Control")
                                .header("Cache-Control", cacheHeaderValue)
                                .build();
                    }
                }
        );
        okHttpClient.networkInterceptors().add(
                new Interceptor() {
                    @Override
                    public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        String cacheHeaderValue = isNetworkAvailable(context)
                                ? "public, max-age=2419200"
                                : "public, only-if-cached, max-stale=2419200";
                        Request request = originalRequest.newBuilder().build();
                        com.squareup.okhttp.Response response = chain.proceed(request);
                        return response.newBuilder()
                                .removeHeader("Pragma")
                                .removeHeader("Cache-Control")
                                .header("Cache-Control", cacheHeaderValue)
                                .build();
                    }
                }
        );
        return okHttpClient;
    }


//Helps to create endless scroll. Dynamically loads data into List that attached to recycler view
    public void loadCached(RecyclerView recyclerView, int number) {
        try {
            int size = MainActivity.itemDisplayed.size();
            if ((size + number) > artistDataList.size()) {
                number = artistDataList.size() - size;
            }
            MainActivity.itemDisplayed.addAll(artistDataList.subList(size, size + number));
            recyclerView.getAdapter().notifyItemRangeInserted(size, number);
            Log.i("loadedCached", " number:" + number + " size:" + size + " sizeAfter:" + MainActivity.itemDisplayed.size());
        } catch (Exception e)
        {
           Log.e("Error", " "+e.getMessage());
        }
    }
}
