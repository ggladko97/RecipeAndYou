package pl.rzeszow.wsiz.zhekabandit97.recipe4u;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SavedFragment extends Fragment {
    private ListView listViewSaved;
    private List<Recipe> savedRecipes;
    private Set<Recipe> tempSet;
    private StarterActivity activity;
    private SharedPreferences settings;

    public SavedFragment() {
        savedRecipes = new ArrayList<>();
        tempSet = new HashSet<>();
        activity = new StarterActivity();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("onPause", activity.getArrayResultForAdapter().toString());
        SharedPreferences.Editor editor;
        settings = getActivity().getSharedPreferences(
                "Saved", Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String itemsJson = gson.toJson(savedRecipes);
        editor.putString("itemsJson", itemsJson);
        editor.apply();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getActivity().getSharedPreferences(
                "Saved", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (settings.contains("itemsJson")) {
            String jsonItems = settings.getString("itemsJson", null);
            Gson gson = new Gson();
            Recipe[] items = gson.fromJson(jsonItems, Recipe[].class);

            for (int i = 0; i < items.length; i++) {
                //savedRecipes.add(items[i]);
                tempSet.add(items[i]);
            }
            savedRecipes.addAll(tempSet);
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        activity = (StarterActivity) getActivity();

        List<Recipe> arrivedRecipes = new ArrayList<>(activity.getArrayResultForAdapter());
        savedRecipes.addAll(arrivedRecipes);

        tempSet.addAll(savedRecipes);
        savedRecipes.clear();
        savedRecipes.addAll(tempSet);


        listViewSaved = (ListView) view.findViewById(R.id.listViewSavedMain);
        final ListViewSavedAdapter myAdapter = new ListViewSavedAdapter(this.getActivity(), R.layout.customlist, savedRecipes);

        listViewSaved.setAdapter(myAdapter);

        listViewSaved.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<Recipe> recipesTempList = new ArrayList<>(savedRecipes);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(recipesTempList.get(position).getUrl_recipe()));
                startActivity(i);

            }
        });
        listViewSaved.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                myAdapter.remove(savedRecipes.get(position));
                Log.i("onlongclick", "true");
                myAdapter.notifyDataSetChanged();
                return false;
            }
        });


        return view;


    }

}
