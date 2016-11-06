package gozero.com.movied3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pratik G on 23/08/2016.
 */

class SingleRow{

    String content;

    String author;
    String url;

    public SingleRow(String author, String content, String url) {
        this.author = author;
        this.content = content;
        this.url = url;
    }
}


public class Review_Grid extends BaseAdapter {

    private Context context;

    ArrayList<SingleRow> list;
    String[] content;

    String[] author;
    String[] url;



    public Review_Grid(Context context, String[] author, String[] content, String[] url) {
      list =new ArrayList<SingleRow>();

        this.context =context;
       int size= author.length;
        for(int i =0 ;i<size;i++)

        {
            list.add(new SingleRow(author[i],content[i],url[i]));
        }

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            row = new View(context);
            row = inflater.inflate(R.layout.review_list, null);
            Log.v("REVIEW" ," "+list.size());
            Log.v("REVIEW POSTIION" ," "+position);



            TextView tvContent = (TextView) row.findViewById(R.id.text_view_reviews_content);
            TextView tvAuthor = (TextView) row.findViewById(R.id.text_view_reviews_author);
            TextView tvUrl = (TextView) row.findViewById(R.id.text_view_reviews_url);

            SingleRow temp = list.get(position);
            tvAuthor.setText("By "+temp.author);
            tvContent.setText(temp.content);
            tvUrl.setText(temp.url);
        }else {
            row = (View) convertView;
        }


        return row;


    }
}
