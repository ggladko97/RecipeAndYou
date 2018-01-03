package pl.rzeszow.wsiz.zhekabandit97.recipe4u.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by hladlyev on 03.01.2018.
 */
public class RecipeDAOImplTest {

    private RecipeDAOImpl recipeDAO;

    @Before
    public void init(){
        recipeDAO = new RecipeDAOImpl();
    }

    @Test
        public void getRecipesByAllTitles_Returns_recipeList() throws Exception {
        String [] prods = new String[] {"Onion","Tomato"};
        List<Recipe> recipesByAllTitles = recipeDAO.getRecipesByAllTitles(prods);
        System.out.println("Recipes: " + recipesByAllTitles);
        assertNotNull(recipesByAllTitles);
    }

}