package pl.rzeszow.wsiz.zhekabandit97.recipe4u.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hladlyev on 02.01.2018.
 */

public class RetrofitRecipeConnector {

    private String base_url;

    private Gson gson;

    private Retrofit retrofit;

    public RetrofitRecipeConnector(String baseUrl) {
        this.base_url = baseUrl;
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
