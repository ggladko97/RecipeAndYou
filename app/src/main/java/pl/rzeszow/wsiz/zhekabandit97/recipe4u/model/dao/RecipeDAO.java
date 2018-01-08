package pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.dao;

import java.util.List;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.entity.Recipe;

/**
 * Created by hladlyev on 08.01.2018.
 */

public interface RecipeDAO {
    Recipe getRecipeByTitle(String title);
    List<Recipe> listrecipes();
}
