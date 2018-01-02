package pl.rzeszow.wsiz.zhekabandit97.recipe4u.old;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.R;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.Recipe;


public class SearchFragment extends Fragment {
    private View view;
    private ImageButton ibOk, ibSearch;
    private AutoCompleteTextView editTextInput;
    private ListView listView;
    private KeyListener mKeyListener;
    private StarterActivity activity;
    private OnFragmentInteractionListener listener;
    private OnLastSearchFragmentListener lastListener;

    private List<String> etResults;
    private Set<String> allQueriesTyped;
    private StringBuilder URL;
    private List<Recipe> recipeResultList;
    private List<Recipe> recipeSavedList;
    private ArrayList<String> listOfIngredientsFromTxt;


    public SearchFragment() {
        etResults = new ArrayList<>();
        allQueriesTyped = new HashSet<>();
        URL = new StringBuilder();
        recipeSavedList = new ArrayList<>(1000);
        recipeResultList = new ArrayList<>(1000);
        listOfIngredientsFromTxt = new ArrayList<>();
    }


    public interface OnFragmentInteractionListener {
        void onFragmentSetRecipes(List<Recipe> input);
    }

    public interface OnLastSearchFragmentListener {
        void onLastSearchSetRecipes(List<Recipe> input);
    }

    private List<Recipe> addRecipesToListInMain(List<Recipe> inputList, List<Recipe> outputList) {
        outputList.clear();
        for (Recipe r : inputList) {
            outputList.add(r);
        }
        return outputList;
    }

    private void fillListView(List<Recipe> inputList) {
        ListViewSearchAdapter myAdapter = new ListViewSearchAdapter(this.getActivity(), R.layout.customlist, inputList);
        listView.setAdapter(myAdapter);
    }

    private void enableEditing(EditText editText, ImageButton button) {
        editText.setKeyListener(mKeyListener);
        button.setEnabled(true);
    }

    private void doQueryTask(String[] inputParams) {
        BackgroundHelper helper = new BackgroundHelper();
        helper.execute(inputParams);
    }

    private void removeFailedData(List<Recipe> inputList) {
        for (Recipe r : inputList) {
            if (r.getName().contains("&amp")) {
                r.getName().replace("&amp", "");
            }
        }
    }

    public class BackgroundHelper extends AsyncTask<String, List<Recipe>, List<Recipe>> {

        int recepiesCount = 0;

        public BackgroundHelper() {
        }

        public int getRecepiesCount() {
            return recepiesCount;
        }

        @Override
        protected List<Recipe> doInBackground(String... params) {
            List<Recipe> resultArrayRecipe = new ArrayList<>();

            HttpURLConnection connection = null;
            InputStream inputStream = null;
            String recievedData = "";

            try {
                connection = (HttpURLConnection) buildURL(params).openConnection();
                Log.i("Connection", connection.getURL().toString());
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

                inputStream = connection.getInputStream();
                BufferedReader bufferedReader = null;
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    recievedData += line;

                }
                JSONObject json = (JSONObject) new JSONTokener(recievedData).nextValue();

                JSONArray recipes = json.getJSONArray("recipes");
                resultArrayRecipe = parseArrayFromAPI(recipes);

                inputStream.close();

                bufferedReader.close();
                connection.disconnect();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return resultArrayRecipe;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Recipe> resultFromDoInBack) {
            addRecipesToListInMain(resultFromDoInBack, recipeResultList);
            checkIfResultDataIsEmpty();
            //insert function against &amp here
            removeFailedData(recipeResultList);
            fillListView(recipeResultList);
            lastListener.onLastSearchSetRecipes(recipeResultList);

            enableEditing(editTextInput, ibOk);

        }

        public List<Recipe> parseArrayFromAPI(JSONArray jsonString) throws JSONException {
            List<Recipe> alTemp = new ArrayList<>();

            for (int i = 0; i < jsonString.length(); i++) {
                JSONObject jsonobject = jsonString.getJSONObject(i);
                String recipe_id = jsonobject.getString("recipe_id");
                String publisher = jsonobject.getString("publisher");
                String f2f_url = jsonobject.getString("f2f_url");
                String title = jsonobject.getString("title");
                String source_url = jsonobject.getString("source_url");
                String image_url = jsonobject.getString("image_url");
                Double social_rank = jsonobject.getDouble("social_rank");
                String social_rank_string = String.valueOf(social_rank);
                Recipe recipe = new Recipe(title, f2f_url, image_url, social_rank_string, publisher);

                alTemp.add(recipe);
            }
            return alTemp;
        }

        @Override
        protected void onProgressUpdate(List<Recipe>... values) {
            super.onProgressUpdate(values);
            Toast.makeText(getActivity(), "We are searching recipes for you...", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkIfResultDataIsEmpty() {
        if (recipeResultList.isEmpty()) {
            Toast.makeText(getActivity(), "Sorry, no matches found, try again...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.i("ONCREATE", " TRUE");
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("onsaveInstance", "true");

        outState.putBoolean("flag", true);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnFragmentInteractionListener) getActivity();
        lastListener = (OnLastSearchFragmentListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        listOfIngredientsFromTxt = (ArrayList<String>) new ReaderFromTxt(getActivity()).ReadTextFromFile("list.txt");
        ibOk = (ImageButton) view.findViewById(R.id.imageButton);
        ibOk.setImageResource(R.drawable.greenok);
        ibSearch = (ImageButton) view.findViewById(R.id.ibSearch);

        editTextInput = (AutoCompleteTextView) view.findViewById(R.id.editTextInput);
        listView = (ListView) view.findViewById(R.id.listResults);
        mKeyListener = editTextInput.getKeyListener();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listOfIngredientsFromTxt);
        editTextInput.setAdapter(adapter);


        ibOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputText = editTextInput.getText().toString();
                if (!inputText.equals("")) {
                    etResults.add(inputText);
                }
                editTextInput.setText("");
                if (etResults.size() == 0) {
                    Log.i("Search", "Empty input");
                }
            }
        });
        ibSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String[] paramsArray;
                paramsArray = etResults.toArray(new String[0]);

                doQueryTask(paramsArray);
                Log.i("array: ", etResults.toString());
                allQueriesTyped.addAll(etResults);
                etResults.clear();
                editTextInput.setText("");
                editTextInput.setKeyListener(null);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(recipeResultList.get(position).getUrl_recipe()));

                recipeSavedList.add(recipeResultList.get(position));
                Log.i("RecipeSavedList", recipeSavedList.toString());
                listener.onFragmentSetRecipes(recipeSavedList);
                startActivity(i);
            }
        });
        return view;
    }

    private URL buildURL(String[] params) throws MalformedURLException {
        String baseURL = "http://food2fork.com/api/search?key=df1d6675a7c13f706153981911c5bdd8&q=";
        StringBuilder builder = new StringBuilder(baseURL);
        for (int i = 0; i < params.length; i++) {
            if (params[i].contains(" ")) {
                try {
                params[i].replace( " ", "_");
                } catch (NullPointerException e) {
                    //do nothing
                }
            }
            builder.append(params[i]);
            if (i < (params.length - 1))
                builder.append("&");
        }
        return new URL(builder.toString());
    }
}
