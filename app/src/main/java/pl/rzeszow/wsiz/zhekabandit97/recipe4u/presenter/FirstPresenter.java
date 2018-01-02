package pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter;

/**
 * Created by hladlyev on 02.01.2018.
 */

public class FirstPresenter implements SearchRecipesContract.Presenter {

    private SearchRecipesContract.View view;
    private String data = "hello world";

    @Override
    public void bindView(SearchRecipesContract.View view) {
        this.view = view;
    }

    @Override
    public void unbindView() {
        this.view = null;
    }

    @Override
    public String load() {
        return data;
    }

}
