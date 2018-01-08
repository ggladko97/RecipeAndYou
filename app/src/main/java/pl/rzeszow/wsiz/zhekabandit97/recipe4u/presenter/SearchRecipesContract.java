package pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter;

import java.util.List;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.entity.Recipe;

/**
 * Created by hladlyev on 02.01.2018.
 */

public interface SearchRecipesContract {
    interface View {
        void hideSearchBox();
        void expandSearchBox();
        void notifyEmptySearch();
        void updateRecipes(List<Recipe> recipes);
        void onCallError(Throwable t);
        void clearListView();
    }

    interface Presenter extends BasePresenter<View> {

    }
}