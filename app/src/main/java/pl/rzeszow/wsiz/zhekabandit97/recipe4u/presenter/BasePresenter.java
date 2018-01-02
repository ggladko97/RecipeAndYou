package pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter;

/**
 * Created by hladlyev on 02.01.2018.
 */

public interface BasePresenter<V> {
    void bindView(V view);
    void unbindView();
}