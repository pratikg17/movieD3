package gozero.com.movied3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class See_Review_Activity extends AppCompatActivity {


    String [] a,c,u;
    ListView listView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_review);

        Intent ireview = getIntent();
        a=ireview.getStringArrayExtra("AUTHOR");
        c=ireview.getStringArrayExtra("CONTENT");
        u=ireview.getStringArrayExtra("URL");
        String title = ireview.getStringExtra("TITLE");

        listView=(ListView) findViewById(R.id.list_view_reviews);
        textView =(TextView)findViewById(R.id.title_text_view_Review);

        textView.setText(title);
        Review_Grid adapter = new Review_Grid(getApplicationContext(),a,c,u);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String links = u[position];
                Log.v("LINK FOR YOUTUBE", " " + links);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(links)));
            }
        });

    }
}
