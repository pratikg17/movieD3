package gozero.com.movied3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Pratik G on 22/08/2016.
 */
public class YouTube_List extends BaseAdapter {
    private Context mContext;
    private String[] youTube_Links_Array;

    public YouTube_List(Context mContext, String[] youTube_Links_Array) {
        this.mContext = mContext;
        this.youTube_Links_Array = youTube_Links_Array;
    }

    @Override
    public int getCount() {
        return youTube_Links_Array.length;
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



        int count = getCount();
        View listR;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            listR = new View(mContext);
            listR = inflater.inflate(R.layout.youtube_list,null);
            Log.v("YOURUBE POSITION", " "+position);


            TextView textView = (TextView) listR.findViewById(R.id.text_view_Youtube);
            ImageView imageView =(ImageView) listR.findViewById(R.id.imageView_Youtube);

            if (youTube_Links_Array == null) {
                //  imageView.setImageResource(Imageid[position]);
              textView.setText("No Trailer Found!!");
            }
            else

                imageView.setImageResource(R.drawable.icon);

            int pos = position+1;
           textView.setText("Trailer "+ pos);

        } else {
            listR = (View) convertView;
        }


        return listR;



    }
}