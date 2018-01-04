package pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter;

import android.text.Editable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.Recipe;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.RecipeDAOImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hladlyev on 02.01.2018.
 */

public class FirstPresenter implements SearchRecipesContract.Presenter {

    private SearchRecipesContract.View view;
    private String data = "hello world";
    private RecipeDAOImpl helper;
    private List<String> searchParams;

    public FirstPresenter() {
        helper = new RecipeDAOImpl();
        searchParams = new ArrayList<>();
    }

    @Override
    public void bindView(SearchRecipesContract.View view) {
        this.view = view;
    }

    @Override
    public void unbindView() {
        this.view = null;
    }


    public void hideSearchBox() {
        view.hideSearchBox();
    }

    public void expandSearchBox() {
        view.expandSearchBox();
    }

    public void addToSearchParams(Editable text) {
        searchParams.add(text.toString());
    }

    public void processRecipesSearch() throws IOException, JSONException {
        if (searchParams.size() > 1) view.notifyEmptySearch();
        else {
            Log.i("Search params = ", searchParams.toString());
            Call<JSONObject> recipesByAllTitles = helper.getRecipesByAllTitles(searchParams.toArray(new String[searchParams.size()]));

            String s = recipesByAllTitles.request().url().toString();
            Log.i("Search url = ", s);
            recipesByAllTitles.enqueue(new Callback<JSONObject>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                    JSONObject body = response.body();
                    try {
                        Log.i("BODY: ", body.toString());
                        List<Recipe> recipes = (List<Recipe>) body.get("recipes");
                        System.out.println(recipes);
                        view.updateRecipes(recipes);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {
                    view.onCallError(t);
                }
            });
        }
    }
}
