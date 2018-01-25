package pl.rzeszow.wsiz.zhekabandit97.recipe4u.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
        Log.i("Data", data.toString());

        tvTitle.setText(data.getName());
        tvPublisher.setText(data.getPublisher());
        tvDescripton.setText(data.getRate());

        float rate = Float.parseFloat(data.getRate());
        ratingBar.setRating(rate/20);
//        Picasso.with(this)
//                .load(data.getUrl_image())
//                .fit()
//                .into(new Target() {
//                    @Override
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                        background.setBackground( new BitmapDrawable(getResources(),bitmap));
//                    }
//
//                    @Override
//                    public void onBitmapFailed(Drawable errorDrawable) {
//                        Log.i("Bitmap", "failed");
//                    }
//
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                    }
//                });
    }
}
