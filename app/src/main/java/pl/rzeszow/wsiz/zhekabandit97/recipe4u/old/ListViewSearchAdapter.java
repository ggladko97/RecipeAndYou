package pl.rzeszow.wsiz.zhekabandit97.recipe4u.old;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pl.rzeszow.wsiz.zhekabandit97.recipe4u.R;
import pl.rzeszow.wsiz.zhekabandit97.recipe4u.model.entity.Recipe;

/**
 * Created by ggladko97 on 11.12.16.
 */
public class ListViewSearchAdapter extends ArrayAdapter<Recipe> {

    public ListViewSearchAdapter(Context context, int resource) {
        super(context, resource);
    }

    public ListViewSearchAdapter(Context context, int resource, List<Recipe> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.customlist, null);
        }
        final Recipe r = getItem(position);

        if (r != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.textCustom);
            ImageView iv1 = (ImageView) v.findViewById(R.id.imageCustom);


            if (tt1 != null) {
                tt1.setText(r.getName());
            }

            if (iv1 != null) {
                Picasso.with(this.getContext())
                        .load(r.getUrl_image())
                        .fit().into(iv1);
            }
        }
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return v;
    }
}
