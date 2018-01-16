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

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.R;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.dao.RecipeDAO;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.dao.RecipeDAOStaticImpl;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.entity.Recipe;

/**
 * Created by hladlyev on 09.01.2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private RecipeDAOStaticImpl recipes;
    private boolean savedMode = false;

    public RecipeAdapter(RecipeDAO recipes, boolean savedMode) {
        this.recipes = (RecipeDAOStaticImpl) recipes;
        this.savedMode = savedMode;
    }

    public RecipeAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FrameLayout itemView = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customlist, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe;

        if (savedMode) {
            recipe = recipes.listSavedRecipes().get(position);
        } else {
            recipe = recipes.listrecipes().get(position);
        }

        holder.getTt1().setText(recipe.getName());

        Picasso.with(holder.getIv1().getContext())
                .load(recipe.getUrl_image())
                .fit()
                .into(holder.getIv1());
        holder.getIv1().setOnClickListener(v -> {
            recipes.saveRecipe(recipe);
            Log.i("Adapter", "SAVE " + recipe.toString());
        });

    }

    @Override
    public int getItemCount() {
        return recipes.listrecipes().size();
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


    public void setSavedMode(boolean savedMode) {
        this.savedMode = savedMode;
    }

    public void setRecipes(RecipeDAOStaticImpl recipes) {
        this.recipes = recipes;
    }
}
