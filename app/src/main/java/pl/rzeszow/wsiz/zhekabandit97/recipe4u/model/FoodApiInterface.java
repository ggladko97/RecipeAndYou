package pl.rzeszow.wsiz.zhekabandit97.recipe4u.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by hladlyev on 02.01.2018.
 */

public interface FoodApiInterface {

    @GET("/api/search")
    Call<JSONObject> getRecipes(@QueryMap Map<String, String> params);
}
