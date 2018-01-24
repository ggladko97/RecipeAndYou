package pl.rzeszow.wsiz.zhekabandit97.recipe4u.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.R;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.entity.Recipe;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter.DetailsPresenter;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.presenter.SearchRecipesContract;

public class DetailsActivity extends AppCompatActivity implements SearchRecipesContract.View {

    private TextView tvTitle, tvPublisher, tvDescripton;
    private RatingBar ratingBar;
    private LinearLayout background;

    private DetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        presenter = new DetailsPresenter();
        tvTitle = findViewById(R.id.tvDetailsTitle);
        tvPublisher = findViewById(R.id.tvPublisher);
        tvDescripton = findViewById(R.id.tvDetails2);

        ratingBar = findViewById(R.id.ratingBar);
        background = findViewById(R.id.llDetails);

        Intent intent = getIntent();
        Recipe data = (Recipe) intent.getSerializableExtra("data");

    }
}
