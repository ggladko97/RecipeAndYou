package pl.rzeszow.wsiz.zhekabandit97.recipe4u;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class SearchFragment extends Fragment {
    View view;
    ImageButton ibOk;
    //ImageView iv;
    AutoCompleteTextView editTextInput;
    ListView listView;
    // GridView gridView;
    KeyListener mKeyListener;
    StarterActivity activity;
    OnFragmentInteractionListener listener;
    OnLastSearchFragmentListener lastListener;

    ArrayList <String> etResults = new ArrayList <>();
    Set <String> allQueriesTyped = new HashSet<>();
    private StringBuilder URL =new StringBuilder();
    ArrayList <Recipe> recipeResultList = new ArrayList<>(1000);
    ArrayList <Recipe> recipeSavedList = new ArrayList<>(1000);
    ArrayList<String>listOfIngredientsFromTxt = new ArrayList<>();

    SharedPreferences prefs;
    Gson gson;
    String jsonRecipes;


    public interface OnFragmentInteractionListener {
        void onFragmentSetRecipes(ArrayList<Recipe> input);
    }
    public interface OnLastSearchFragmentListener {
        void onLastSearchSetRecipes(ArrayList<Recipe>input);
    }
    public ArrayList<Recipe> addRecipesToListInMain(ArrayList<Recipe>inputList, ArrayList<Recipe>outputList){
        outputList.clear();
        for(Recipe r : inputList){
            outputList.add(r);
        }
        return outputList;
    }
    public void instantiatingListView(ArrayList<Recipe>inputList){

        ListViewSearchAdapter myAdapter = new ListViewSearchAdapter(this.getActivity(),R.layout.customlist,inputList);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
//                android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(myAdapter);
    }
    public void enableEditing(EditText editText, ImageButton button){
        editText.setKeyListener(mKeyListener);
        button.setEnabled(true);
    }
    private void doQueryTask(String firstIngredient, String secondIngredient){
        BackgroundHelper helper  = new BackgroundHelper();
        helper.execute(firstIngredient,secondIngredient);


    }
    private ArrayList<Recipe> eraseFailedData(ArrayList<Recipe>inputList){
        ArrayList<Recipe> resultList = new ArrayList<>();
        for(Recipe r : inputList){
            if(r.getName().contains("&amp")){
                r.getName().replace("&amp","");
                resultList.add(r);
            }
            else {
                resultList.add(r);
            }
        }
        return resultList;
    }
    public class BackgroundHelper extends AsyncTask<String, ArrayList<Recipe>, ArrayList<Recipe>> {

        int recepiesCount=0;

        public BackgroundHelper(){
        }
        public int getRecepiesCount() {
            return recepiesCount;
        }
        @Override
        protected ArrayList<Recipe> doInBackground(String... params) {
            String firstIngredient = params[0];
            String secondIngridient = params[1];
            ArrayList<Recipe> resultArrayRecipe = new ArrayList<>();
            String login = "http://food2fork.com/api/search?key=df1d6675a7c13f706153981911c5bdd8&q="+firstIngredient+","+secondIngridient+"\"";
            java.net.URL url = null;
            HttpURLConnection connection= null;
            InputStream inputStream = null;
            String recieved_data="";
            try {
                url = new URL(login);

                connection = (HttpURLConnection)url.openConnection();

                connection.setRequestMethod("GET");

                connection.setRequestProperty("Accept", "application/json");

                inputStream = connection.getInputStream();
                BufferedReader bufferedReader  = null;
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                String line;
                while((line=bufferedReader.readLine())!=null){
                    recieved_data+=line;

                }
                JSONObject json = (JSONObject) new JSONTokener(recieved_data).nextValue();

                JSONArray recipes = json.getJSONArray("recipes");
                resultArrayRecipe=parseArrayFromAPI(recipes);

                inputStream.close();

                bufferedReader.close();
                connection.disconnect();

            }catch (IOException e) {
                e.printStackTrace();} catch (JSONException e) {
                e.printStackTrace();
            }

            return resultArrayRecipe;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> resultFromDoInBack) {
            //alertDialog.setMessage(aVoid);

            //db can be added ....but instead
            addRecipesToListInMain(resultFromDoInBack,recipeResultList);
            checkIfResultDataIsEmpty();
            //insert function against &amp here
            recipeResultList=eraseFailedData(recipeResultList);
            instantiatingListView(recipeResultList);
            lastListener.onLastSearchSetRecipes(recipeResultList);

            enableEditing(editTextInput,ibOk);

        }
        public ArrayList<Recipe> parseArrayFromAPI(JSONArray jsonString) throws JSONException {
            ArrayList<Recipe> alTemp = new ArrayList<>();

            //extracting data from json result string to normal Recipe object
            //and returning list of these objects

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
                Recipe recipe = new Recipe(title,f2f_url,image_url,social_rank_string,publisher);

                alTemp.add(recipe);
            }
            return alTemp;
        }

        @Override
        protected void onProgressUpdate(ArrayList<Recipe>... values) {
            super.onProgressUpdate(values);
            Toast.makeText(getActivity(),"We are searching recipes for you...",Toast.LENGTH_SHORT).show();
        }
    }

    private void checkIfResultDataIsEmpty() {
        if(recipeResultList.isEmpty()){
            Toast.makeText(getActivity(), "Sorry, no matches found, try again...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.i("ONCRATE"," TRUE");



    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Log.i("ONACTIVITYCREATED","true");
//
//        SharedPreferences prefs = getActivity().getSharedPreferences("SHARED_PREFS_FILE",Context.MODE_PRIVATE);
//        String myJSONArrayString = prefs.getString("recipeSavedList", "");
//        try {
//            JSONArray jsonArray = new JSONArray(myJSONArrayString);
//            ArrayList<Recipe> listFromJson = new ArrayList<>();
//            for (int i=0;i<jsonArray.length();i++){
//                listFromJson.add((Recipe) jsonArray.get(i));
//            }
//            Log.i("from json",listFromJson.toString());
//            recipeSavedList.addAll(listFromJson);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("onsaveInstance","true");
        //Save the fragment's state here

        outState.putBoolean("flag",true);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnFragmentInteractionListener)getActivity();
        lastListener = (OnLastSearchFragmentListener)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container,false);

//        Type type = new TypeToken<ArrayList<Recipe>>(){}.getType();
//
//        recipeSavedList= gson.fromJson(jsonRecipes,type);


        //instatiating autocomplete text view
        listOfIngredientsFromTxt=new ReaderFromTxt(getActivity()).ReadTextFromFile("list.txt");



        ibOk = (ImageButton)view.findViewById(R.id.imageButton);
        ibOk.setImageResource(R.drawable.greenok);
        editTextInput = (AutoCompleteTextView) view.findViewById(R.id.editTextInput);
        listView = (ListView) view.findViewById(R.id.listResults);
        mKeyListener = editTextInput.getKeyListener();



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,listOfIngredientsFromTxt);
        editTextInput.setAdapter(adapter);


        ibOk.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String temp;
                temp = editTextInput.getText().toString();
                etResults.add(temp);
//                ArrayAdapter<String> adapterForAutocomplition =
//                        new ArrayAdapter<String>(SearchFragment.class,android.R.layout.simple_list_item_1,new ArrayList<String>(allQueriesTyped));
               // editTextInput.setAdapter(adapterForAutocomplition);

                editTextInput.setText("");
                if(etResults.size()==2){
                    doQueryTask(etResults.get(0),etResults.get(1));
                    Log.i("array: ",etResults.toString());
                    allQueriesTyped.addAll(etResults);
                    etResults.clear();
                    editTextInput.setText("");
                    editTextInput.setKeyListener(null);
                    ibOk.setEnabled(false);
                }
                else{
                    editTextInput.setText("");
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(recipeResultList.get(position).getUrl_recipe()));

                recipeSavedList.add(recipeResultList.get(position));
//                gson = new Gson();
//                jsonRecipes = gson.toJson(recipeSavedList);
//                Log.i("onsaveinstancestate","true: "+jsonRecipes);
                Log.i("RecipeSavedList",recipeSavedList.toString());
                listener.onFragmentSetRecipes(recipeSavedList);
                startActivity(i);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
//    public ArrayList<String> readingDataFromTxtList(String path){
//        ArrayList<String>listOfIngredientsFromTxt = new ArrayList<>();
//
//        String data = "";
//        AssetManager assetManager = getActivity().getResources().getAssets();
//        InputStream inputStream = null;
//        try {
//            inputStream = assetManager.open(path);
//
//            BufferedReader in =
//                    new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//            String str;
//            while ((str = in.readLine()) != null) {
//               listOfIngredientsFromTxt.add(str);
//            }
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return listOfIngredientsFromTxt;
//    }



}
