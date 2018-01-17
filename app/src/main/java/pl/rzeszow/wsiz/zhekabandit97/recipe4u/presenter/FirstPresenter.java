package pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter;

import android.text.Editable;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.FoodApiInterfaceImpl;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.dao.RecipeDAO;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.dao.RecipeDAOStaticImpl;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.entity.Recipe;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.entity.RecipeResponse;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.view.RecipeAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hladlyev on 02.01.2018.
 */

public class FirstPresenter implements SearchRecipesContract.Presenter {

    private SearchRecipesContract.View view;
    private RecipeAdapter adapter;
    private String data = "hello world";
    private FoodApiInterfaceImpl helper;
    private List<String> searchParams;
    private RecipeDAO recipeDao;

    public FirstPresenter(RecipeAdapter adapter) {
        helper = new FoodApiInterfaceImpl();
        searchParams = new ArrayList<>();
        recipeDao = new RecipeDAOStaticImpl();
        this.adapter = adapter;
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
                    recipeDao.addAllRecipesFromLoad(body.getRecipes());
                    adapter.setRecipes(recipeDao.listrecipes());
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<RecipeResponse> call, Throwable t) {
                    view.onCallError(t);
                }
            });
        }
    }

    public void addSavedRecipe(Recipe recipe) {
        Log.i("RDao", recipeDao.listSavedRecipes().toString());
        if (!recipeDao.listSavedRecipes().contains(recipe)) {
            recipeDao.addSavedRecipe(recipe);
        }
    }

    public void processSavedRecipes() {
        adapter.setRecipes(recipeDao.listSavedRecipes());
//        adapter.notifyDataSetChanged();
    }
}
