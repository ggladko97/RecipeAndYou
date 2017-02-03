package pl.rzeszow.wsiz.zhekabandit97.recipe4u;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class StarterActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener,SearchFragment.OnLastSearchFragmentListener {
    ImageButton ibSearch,ibLastSearched,ibSaved,ibAbout;
    SearchFragment searchFragment;
    SavedFragment savedFragment;
    LastFragment lastFragment;
    InfoFragment infoFragment;
    ArrayList<Recipe>result = new ArrayList<>();
    ArrayList<Recipe>arrayResultForAdapter=null;
    ArrayList<Recipe>allRecipesFromSearch = new ArrayList<>();


    public ArrayList<Recipe> getAllRecipesFromSearch() {
        return allRecipesFromSearch;
    }

    public ArrayList<Recipe> getArrayResultForAdapter() {
        return arrayResultForAdapter;
    }

    public ArrayList<Recipe> getResult() {
        if(result!=null){
            return result;
        }
        else{
            throw new NullPointerException();
        }

    }


private boolean isNetworkConnected() {
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

    return cm.getActiveNetworkInfo() != null;
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            //Restore the fragment's instance
            searchFragment = (SearchFragment) getSupportFragmentManager().getFragment(savedInstanceState, "search");

        }
        checkNetworkConnection();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        ibSearch = (ImageButton)findViewById(R.id.btnSearch);
        ibLastSearched = (ImageButton)findViewById(R.id.ibLastSearched);
        ibSaved = (ImageButton)findViewById(R.id.btnFavorite);
        ibAbout = (ImageButton)findViewById(R.id.btnAbout);


        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchFragment =  new SearchFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameContainer, searchFragment).commit();

            }
        });
        ibLastSearched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastFragment = new LastFragment();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameContainer, lastFragment).commit();
            }
        });
        ibSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<Recipe> resultSet = new HashSet<>(getResult());
                arrayResultForAdapter = new ArrayList<>(resultSet);
                savedFragment = new SavedFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameContainer,savedFragment).commit();

            }
        });
        ibAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoFragment = new InfoFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameContainer, infoFragment).commit();

            }
        });

    }

    private void checkNetworkConnection() {
        if(isNetworkConnected()==false){
            Toast.makeText(this,"No internet connection",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkNetworkConnection();
    }

    @Override
    protected void onPause() {
        super.onPause();



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNetworkConnection();

    }


    @Override
    public void onFragmentSetRecipes(ArrayList<Recipe> input) {
        result=input;
    }

    @Override
    public void onLastSearchSetRecipes(ArrayList<Recipe> input) {
        allRecipesFromSearch=input;
    }
}
