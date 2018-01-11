package pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.view.RecipeAdapter;

/**
 * Created by hladlyev on 02.01.2018.
 */

public interface SearchRecipesContract {
    interface View {
        void hideSearchBox();
        void expandSearchBox();
        void notifyEmptySearch();
        void onCallError(Throwable t);

        void setAdapter(RecipeAdapter adapter);
    }

    interface Presenter extends BasePresenter<View> {

    }
}