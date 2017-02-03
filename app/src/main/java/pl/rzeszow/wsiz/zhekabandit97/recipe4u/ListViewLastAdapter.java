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
public class ListViewLastAdapter extends ArrayAdapter<Recipe> {
    public ListViewLastAdapter(Context context, int resource) {
        super(context, resource);
    }
    public ListViewLastAdapter(Context context, int resource, List<Recipe> items) {
        super(context, resource, items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view  = convertView;
        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.customlist, null);
        }
        final Recipe r = getItem(position);
        if (r != null) {
            TextView tt1 = (TextView) view.findViewById(R.id.textCustom);
            ImageView iv1 = (ImageView)view.findViewById(R.id.imageCustom);


            if (tt1 != null) {
                tt1.setText(r.getName());
            }

            if (iv1 != null) {
                Picasso.with(this.getContext())
                        .load(r.getUrl_image())
                        .fit().into(iv1);
            }
        }
        view.setLayoutParams(new ViewGroup.LayoutParams(600,350));

        return view;
    }
}
