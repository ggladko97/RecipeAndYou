package pl.rzeszow.wsiz.zhekabandit97.recipe4u.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.R;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.entity.Recipe;

/**
 * Created by hladlyev on 09.01.2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> recipes;
    private OnRecipeChangeDetection detector;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public RecipeAdapter(OnRecipeChangeDetection detector) {
        this.detector = detector;
    }

    public RecipeAdapter() {
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FrameLayout itemView = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customlist, parent, false);
        Log.i("ADAPTER", "data is: " + recipes);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Recipe recipe = recipes.get(position);

        holder.getTt1().setText(recipe.getName());

        Picasso.with(holder.getIv1().getContext())
                .load(recipe.getUrl_image())
                .fit()
                .into(holder.getIv1());
        holder.getIv1().setOnClickListener(v -> {
            //ToDo: add an interface and implement it in StarterActivity
            // to handle saving recipies. Pass this instance to adapter.
            detector.changeRecipes(recipe);
            Log.i("Adapter", "SAVE " + recipe.toString());
        });
        holder.itemView.setOnClickListener( l -> {
            detector.expandRecipe(recipe);
        });

    }

    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }

    public void clearDataSet() {
        recipes = new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tt1;
        ImageView iv1;

        public ViewHolder(View itemView) {
            super(itemView);
            tt1 = itemView.findViewById(R.id.textCustom);
            iv1 = itemView.findViewById(R.id.imageCustom);
        }

        public TextView getTt1() {
            return tt1;
        }

        public ImageView getIv1() {
            return iv1;
        }
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        Log.i("AdapterSet: ", recipes.toString());
        this.recipes = recipes;
    }

}
