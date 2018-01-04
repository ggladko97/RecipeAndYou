package pl.rzeszow.wsiz.zhekabandit97.recipe4u.model;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.Map;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.entity.RecipeResponse;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by hladlyev on 02.01.2018.
 */

public class FoodApiInterfaceImpl {

    private Retrofit retrofit;
    private FoodApiInterface foodApi;

    public FoodApiInterfaceImpl() {
        RetrofitRecipeConnector connector =
                new RetrofitRecipeConnector("http://food2fork.com/");
        this.retrofit = connector.getRetrofit();
        this.foodApi = retrofit.create(FoodApiInterface.class);
    }

    public Call<RecipeResponse> getRecipesByAllTitles(String[] titles) throws IOException, JSONException {
        System.out.println("URL: " + buildURL(titles));

        Call<RecipeResponse> callback = foodApi.getRecipes(buildURL(titles));
        return callback;
    }


    private Map<String, String> buildURL(String[] params) throws MalformedURLException {
        Map<String, String> sendParams = new LinkedHashMap<>();
        sendParams.put("key", "df1d6675a7c13f706153981911c5bdd8");
        StringBuilder tail = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            if (params[i].contains(" ")) {
                try {
                    params[i].replace(" ", "_");
                } catch (NullPointerException e) {
                    //do nothing
                }
            }
            tail.append(params[i]);
            if (i != params.length - 1) {
                tail.append("&");
            }
        }
        sendParams.put("q", tail.toString());
        return sendParams;
    }
}
