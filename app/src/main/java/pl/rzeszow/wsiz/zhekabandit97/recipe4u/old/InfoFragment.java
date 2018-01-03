package pl.rzeszow.wsiz.zhekabandit97.recipe4u.old;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.R;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.Recipe;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.RecipeDAOImpl;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter.FirstPresenter;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter.SearchRecipesContract;

public class InfoFragment extends Fragment implements SearchRecipesContract.View{

    private TextView plainTextInfo;
    private FirstPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new FirstPresenter();
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        plainTextInfo = (TextView)view.findViewById(R.id.plainText);
        sayHello();
        return view;
    }


    @Override
    public void sayHello() {
        RecipeDAOImpl dao = new RecipeDAOImpl();
        String [] prods = new String[] {"Onion","Tomato"};
        List<Recipe> recipesByAllTitles = null;
        try {
            recipesByAllTitles = dao.getRecipesByAllTitles(prods);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("Recipes: " + recipesByAllTitles);
        Toast.makeText(getContext(), presenter.load(), Toast.LENGTH_LONG).show();
    }
}
