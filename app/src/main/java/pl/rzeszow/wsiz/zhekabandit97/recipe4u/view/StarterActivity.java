package pl.rzeszow.wsiz.zhekabandit97.recipe4u.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.R;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.entity.Recipe;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter.FirstPresenter;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter.SearchRecipesContract;

/**
 * Created by hladlyev on 03.01.2018.
 */

public class StarterActivity extends AppCompatActivity implements SearchRecipesContract.View, OnRecipeChangeDetection {

    //root
    private ImageButton ibSearch, ibLastSearched, ibSaved, ibAbout;

    //searches
    private AutoCompleteTextView searchInput;
    private ImageButton ibOk, ibSearchRecipe;
    private RecyclerView listView;
    private LinearLayout sbLayout;
    //presenters
    private FirstPresenter presenter;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        adapter = new RecipeAdapter(StarterActivity.this);
        presenter = new FirstPresenter(adapter);
        presenter.bindView(StarterActivity.this);

        ibSearch = findViewById(R.id.btnSearch);
        ibLastSearched = findViewById(R.id.ibLastSearched);
        ibSaved = findViewById(R.id.btnFavorite);
        ibAbout = findViewById(R.id.btnAbout);
        searchInput = findViewById(R.id.editTextInput);
        sbLayout = findViewById(R.id.searchBarLayout);

        listView = findViewById(R.id.listResults);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);

        ibOk = findViewById(R.id.imageButton);
        ibSearchRecipe = findViewById(R.id.ibSearch);


        if (ibOk != null) {
            ibOk.setImageResource(R.drawable.greenok);
        }


        ibSearch.setOnClickListener(v -> {
            adapter.clearDataSet();
            presenter.expandSearchBox();

            ibOk.setOnClickListener(l -> {
                presenter.addToSearchParams(searchInput.getText());
                searchInput.setText("");
            });
            ibSearchRecipe.setOnClickListener(l -> {
                try {
                    presenter.processRecipesSearch();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            });
//            presenter.hideSearchBox();
        });

        ibLastSearched.setOnClickListener(v -> presenter.hideSearchBox());
        ibSaved.setOnClickListener(v -> {
            presenter.hideSearchBox();
            presenter.processSavedRecipes();
        });
        ibAbout.setOnClickListener(v -> presenter.hideSearchBox());
    }

//    @Override
    public void hideSearchBox() {
        sbLayout.setVisibility(View.GONE);
    }

//    @Override
    public void expandSearchBox() {
        sbLayout.setVisibility(View.VISIBLE);
    }

//    @Override
    public void notifyEmptySearch() {
        Toast.makeText(this, "Please provide some products", Toast.LENGTH_LONG).show();
    }


//    @Override
    public void onCallError(Throwable t) {
        Toast.makeText(this, "Error: \n" + t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeRecipes(Recipe recipe) {
        presenter.addSavedRecipe(recipe);
    }

    @Override
    public void expandRecipe(Recipe recipe) {
        Intent intent = new Intent(StarterActivity.this, DetailsActivity.class);
        intent.putExtra("data", recipe);
        startActivity(intent);
    }
}
