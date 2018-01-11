package pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.entity.Recipe;

/**
 * Created by hladlyev on 08.01.2018.
 */

public class RecipeDAOStaticImpl implements RecipeDAO {

    private static List<Recipe> recipeMockRepository = new ArrayList<>();
    private static List<Recipe> savedRecipes = new ArrayList<>();

    @Override
    public Recipe getRecipeByTitle(String title) {
        for (Recipe r : recipeMockRepository) {
            if (r.getName().equals(title)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public List<Recipe> listrecipes() {
        return recipeMockRepository;
    }

    @Override
    public void addAllRecipesFromLoad(Recipe[] recipes) {
        savedRecipes.addAll(Arrays.asList(recipes));
    }

    public static void setRecipeMockRepository(List<Recipe> recipeMockRepository) {
        RecipeDAOStaticImpl.recipeMockRepository = recipeMockRepository;
    }

    public void saveRecipe(Recipe r) {
        if (!savedRecipes.contains(r)) {
            savedRecipes.add(r);
        }
    }

    public List<Recipe> listSavedRecipes() {
        return savedRecipes;
    }
}
