package gozero.com.movied3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Pratik G on 19/08/2016.
 */
public class CustomGrid extends BaseAdapter {

    private Context mContext;

   // private final int[] Imageid;
    private String[] urlid;


    public CustomGrid(Context c, String[] urlid) {
        mContext = c;

        this.urlid = urlid;
    }

    @Override
    public int getCount() {
        return urlid.length;


    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.single_grid, null);
            ImageView imageView = (ImageView) grid.findViewById(R.id.image_view_grid);

            if (urlid == null) {
                //  imageView.setImageResource(Imageid[position]);
                Picasso.with(mContext).load("http://www.smartphonefixes.com/wp-content/uploads/2015/07/iphone-6-error-53-e1436840753944.jpg").into(imageView);

            } else


                Log.v("IN VIEW ",  "  " +urlid[position]);

            Picasso.with(mContext).load(urlid[position]).placeholder(R.drawable.abc).error(R.drawable.black).into(imageView);


        } else {
            grid = (View) convertView;
        }


        return grid;

    }
}
