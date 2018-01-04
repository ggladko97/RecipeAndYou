package pl.rzeszow.wsiz.zhekabandit97.recipe4u.model;

import java.util.Map;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.entity.RecipeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by hladlyev on 02.01.2018.
 */

public interface FoodApiInterface {

    @GET("/api/search")
    Call<RecipeResponse> getRecipes(@QueryMap Map<String, String> params);
}
