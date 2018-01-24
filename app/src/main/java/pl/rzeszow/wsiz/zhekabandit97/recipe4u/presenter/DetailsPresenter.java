package pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.view.DetailsActivity;

/**
 * Created by hladlyev on 24.01.2018.
 */

public class DetailsPresenter implements SearchRecipesContract.Presenter {

    private DetailsActivity view;

    @Override
    public void bindView(SearchRecipesContract.View view) {
        this.view = (DetailsActivity) view;
    }

    @Override
    public void unbindView() {
        this.view = null;
    }

}
