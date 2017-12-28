package pl.rzeszow.wsiz.zhekabandit97.recipe4u;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ggladko97 on 02.01.17.
 */
public class ListViewLastAdapter extends ListViewSearchAdapter {

    public ListViewLastAdapter(Context context, int resource) {
        super(context, resource);
    }

    public ListViewLastAdapter(Context context, int resource, List<Recipe> items) {
        super(context, resource, items);
    }
}
