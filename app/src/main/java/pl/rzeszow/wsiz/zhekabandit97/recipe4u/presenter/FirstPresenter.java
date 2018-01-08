package pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter;

import android.text.Editable;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.entity.Recipe;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.FoodApiInterfaceImpl;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.entity.RecipeResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hladlyev on 02.01.2018.
 */

public class FirstPresenter implements SearchRecipesContract.Presenter {

    private SearchRecipesContract.View view;
    private String data = "hello world";
    private FoodApiInterfaceImpl helper;
    private List<String> searchParams;
    private List<Recipe> searchedRecipes;
    private static List<Recipe> savedRecipes = new ArrayList<>();

    public FirstPresenter() {
        helper = new FoodApiInterfaceImpl();
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
            Call<RecipeResponse> recipesByAllTitles = helper.getRecipesByAllTitles(searchParams.toArray(new String[searchParams.size()]));

            String s = recipesByAllTitles.request().url().toString();
            Log.i("Search url = ", s);
            recipesByAllTitles.enqueue(new Callback<RecipeResponse>() {
                @Override
                public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                    RecipeResponse body = response.body();
                    searchedRecipes = Arrays.asList(body.getRecipes());

                    view.updateRecipes(searchedRecipes);
                }

                @Override
                public void onFailure(Call<RecipeResponse> call, Throwable t) {
                    view.onCallError(t);
                }
            });
        }
    }

    public void saveSelectedRecipe(int i) {
        Log.i("Save", searchedRecipes.get(i).toString());
        savedRecipes.add(searchedRecipes.get(i));
    }

    public void updateSavedRecipes() {
        if (savedRecipes.size() > 0) {
            view.updateRecipes(savedRecipes);
        }
    }

    public void clearRecipes() {
        view.clearListView();
    }
}
