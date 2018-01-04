package pl.rzeszow.wsiz.zhekabandit97.recipe4u.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.R;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.entity.Recipe;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.old.ListViewSearchAdapter;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter.FirstPresenter;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter.SearchRecipesContract;

/**
 * Created by hladlyev on 03.01.2018.
 */

public class StarterActivity extends AppCompatActivity implements SearchRecipesContract.View {

    //root
    private ImageButton ibSearch, ibLastSearched, ibSaved, ibAbout;

    //searches
    private AutoCompleteTextView searchInput;
    private ImageButton ibOk, ibSearchRecipe;
    private ListView listView;
    private LinearLayout sbLayout;
    private ListViewSearchAdapter adapter;
    //presenters
    private FirstPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        presenter = new FirstPresenter();
        presenter.bindView(StarterActivity.this);

        ibSearch = (ImageButton) findViewById(R.id.btnSearch);
        ibLastSearched = (ImageButton) findViewById(R.id.ibLastSearched);
        ibSaved = (ImageButton) findViewById(R.id.btnFavorite);
        ibAbout = (ImageButton) findViewById(R.id.btnAbout);
        searchInput = (AutoCompleteTextView) findViewById(R.id.editTextInput);
        sbLayout = (LinearLayout) findViewById(R.id.searchBarLayout);
        listView = (ListView) findViewById(R.id.listResults);

        ibOk = (ImageButton) findViewById(R.id.imageButton);
        if (ibOk != null) {
            ibOk.setImageResource(R.drawable.greenok);
        }
        ibSearchRecipe = (ImageButton) findViewById(R.id.ibSearch);

        ibSearch.setOnClickListener(v -> {
            presenter.expandSearchBox();

            ibOk.setOnClickListener(l -> {
                presenter.addToSearchParams(searchInput.getText());
                searchInput.setText("");
            });
            ibSearchRecipe.setOnClickListener(l -> {
                try {
                    presenter.processRecipesSearch();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });

        });
        ibLastSearched.setOnClickListener(v -> presenter.hideSearchBox());
        ibSaved.setOnClickListener(v -> presenter.hideSearchBox());
        ibAbout.setOnClickListener(v -> {


        });
    }

    @Override
    public void sayHello() {

    }

    @Override
    public void displayRecipes(List<Recipe> recievedRecipes) {

    }

    @Override
    public void hideSearchBox() {
        sbLayout.setVisibility(View.GONE);
    }

    @Override
    public void expandSearchBox() {
        sbLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void notifyEmptySearch() {
        Toast.makeText(this, "Please provide some products", Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateRecipes(List<Recipe> recipes) {
        adapter = new ListViewSearchAdapter(StarterActivity.this, R.layout.customlist, recipes);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCallError(Throwable t) {
        Toast.makeText(this, "Error: \n" + t.getMessage(), Toast.LENGTH_LONG).show();
    }
}
