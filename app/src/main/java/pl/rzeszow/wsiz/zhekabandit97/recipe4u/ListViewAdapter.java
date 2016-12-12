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
 * Created by ggladko97 on 11.12.16.
 */
public class ListViewAdapter extends ArrayAdapter<Recipe> {
    public ListViewAdapter(Context context, int resource) {
        super(context, resource);
    }
    public ListViewAdapter(Context context, int resource, List<Recipe> items) {
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

        Recipe r = getItem(position);

        if (r != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.textCustom);
            ImageView iv1 = (ImageView)v.findViewById(R.id.imageCustom);

            if (tt1 != null) {
                tt1.setText(r.getName());
            }

            if (iv1 != null) {
                Picasso.with(this.getContext())
                        .load(r.getUrl_image())
                        .fit().into(iv1);
            }
        }
        return v;
    }
}
