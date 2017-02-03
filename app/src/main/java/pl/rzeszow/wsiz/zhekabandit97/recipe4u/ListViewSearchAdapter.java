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

        //View view = super.getView(position,convertView,parent);

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.customlist, null);
        }

        final Recipe r = getItem(position);

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
//        ibLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("I'm onClick LV","yes");
//                r.setSearched(true);
////                SearchFragment fragmnt = new SearchFragment ();
////                Bundle bundle  =  new Bundle();
////                bundle.putBoolean("flag",true);
////                fragmnt.setArguments(bundle);
//            }
//        });
//       ViewGroup.LayoutParams params = view.getLayoutParams();
//
//        // Set the height of the Item View
//        params.height = 150;
//        view.setLayoutParams(params);
        v.setLayoutParams(new ViewGroup.LayoutParams(600,350));

        return v;
    }
}
