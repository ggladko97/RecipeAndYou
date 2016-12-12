package pl.rzeszow.wsiz.zhekabandit97.recipe4u;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

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

public class MainActivity extends AppCompatActivity  {
    ImageButton ibOk;
    //ImageView iv;
    EditText editTextInput;
    ListView listView;
   // GridView gridView;
    KeyListener mKeyListener;
    ImageView target;


    ArrayList<String> etResults= new ArrayList<>();


    private StringBuilder URL =new StringBuilder();

    ArrayList<Recipe> recipeResultList = new ArrayList<>(1000);
    public ArrayList<Recipe> addRecipesToListInMain(ArrayList<Recipe>inputList, ArrayList<Recipe>outputList){
        outputList.clear();
        for(Recipe r : inputList){
            outputList.add(r);

        }

        return outputList;
    }
    public void instantiatingListView(ArrayList<Recipe>inputList){
//        ArrayList<String>titles = new ArrayList<>();
//        for(Recipe r: inputList){
//            Picasso.with(this).load(r.getUrl_image()).into(target);
//            titles.add(r.getName());
//
//        }
        ListViewAdapter myAdapter = new ListViewAdapter(this,R.layout.customlist,inputList);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
//                android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(myAdapter);
    }
    public void enableEditing(EditText editText, ImageButton button){
        editText.setKeyListener(mKeyListener);
        button.setEnabled(true);
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
            instantiatingListView(recipeResultList);
            enableEditing(editTextInput,ibOk);

        }
        public ArrayList<Recipe> parseArrayFromAPI(JSONArray jsonString) throws JSONException {
            ArrayList<Recipe> alTemp = new ArrayList<>();

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
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ibOk = (ImageButton)findViewById(R.id.imageButton);
        ibOk.setImageResource(R.drawable.greenok);
        editTextInput = (EditText)findViewById(R.id.editTextInput);
//        iv = (ImageView)findViewById(R.id.imageViewPublisher);
//        assert iv != null;
//        iv.setImageResource(R.drawable.weblogo);
        listView = (ListView) findViewById(R.id.listResults);
        mKeyListener = editTextInput.getKeyListener();
        //target = (ImageView)findViewById(R.id.imageView);



        ibOk.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String temp;
                temp = editTextInput.getText().toString();
                etResults.add(temp);


                editTextInput.setText("");
                if(etResults.size()==2){
                    doQueryTask(etResults.get(0),etResults.get(1));
                    Log.i("array: ",etResults.toString());
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


    }
    private void doQueryTask(String firstIngredient, String secondIngredient){
        BackgroundHelper helper  = new BackgroundHelper();
        helper.execute(firstIngredient,secondIngredient);


    }
//    public int realArrayLength(List<String> input){
//        for(String s:input){
//            if(null != s){
//
//            }
//        }
//    }


}
