package pl.rzeszow.wsiz.zhekabandit97.recipe4u.old;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.R;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter.FirstPresenter;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter.SearchRecipesContract;

public class InfoFragment extends Fragment implements SearchRecipesContract.View{

    private TextView plainTextInfo;
    private FirstPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new FirstPresenter();
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        plainTextInfo = (TextView)view.findViewById(R.id.plainText);
        sayHello();
        return view;
    }


    @Override
    public void sayHello() {
        Toast.makeText(getContext(), presenter.load(), Toast.LENGTH_LONG).show();
    }
}
