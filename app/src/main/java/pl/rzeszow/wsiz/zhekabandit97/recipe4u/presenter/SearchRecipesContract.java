package pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter;

/**
 * Created by hladlyev on 02.01.2018.
 */

public interface SearchRecipesContract {
    interface View {
        //        void addResults(List<Repository> repos);
        void sayHello();


    }

    interface Presenter extends BasePresenter<View> {
        String load();
        //        void repositoryClick(Repository repo);
    }
}