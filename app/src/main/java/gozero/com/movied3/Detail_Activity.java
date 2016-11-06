package gozero.com.movied3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Detail_Activity extends AppCompatActivity {

    public static final String LOG_TAG = Detail_Activity.class.getSimpleName();

    String POSTER_IMAGE;

    String RESULT_JSON;

    String TITLE;

    String OVERVIEW;
    String USER_RATING;
    String RELEASE_DATE;
    public String REVIEW_JSON, ID;


    String[] youtube_Links;
    TextView tv, textView_Release, textView_Rating, textView_Overview,tvSeeReview;
    ImageView imageViewPoster;
    ListView listView;
    GridView listViewReviews;

    public String[] reviews_author, reviews_content, reviews_url;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent i2 = getIntent();
        POSTER_IMAGE = i2.getStringExtra("PTR");
        TITLE = i2.getStringExtra("OT");
        OVERVIEW = i2.getStringExtra("OV");
        USER_RATING = i2.getStringExtra("RT");
        RELEASE_DATE = i2.getStringExtra("RL");
        youtube_Links = i2.getStringArrayExtra("YOUTUBE_URL");
        REVIEW_JSON = i2.getStringExtra("REVIEW_JSON");
        ID = i2.getStringExtra("ID");

        final String TITLEFORREVIEW =TITLE;

//        Toast.makeText(getApplicationContext(),REVIEW_JSON,Toast.LENGTH_LONG).show();

        try {
            getReviewsFromJson(REVIEW_JSON, ID);


            for (String s : reviews_author) {
                Log.v("AUTHOR", " " + s);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }




        Log.v(LOG_TAG, "  " + REVIEW_JSON);

        listView = (ListView) findViewById(R.id.list_view_youtube);
      //  listViewReviews =(GridView)findViewById(R.id.list_view_reviews);

        YouTube_List adapter = new YouTube_List(getApplicationContext(), youtube_Links);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String youtube = youtube_Links[position];
                Log.v("LINK FOR YOUTUBE", " " + youtube);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtube)));
            }
        });



//        Review_Grid adapterR =new Review_Grid(getApplicationContext(),reviews_author,reviews_content,reviews_url);
//
//        listViewReviews.setAdapter(adapterR);






        imageViewPoster = (ImageView) findViewById(R.id.poster_image_view);
        textView_Release = (TextView) findViewById(R.id.release_text_view);
        textView_Rating = (TextView) findViewById(R.id.rating_text_view);
        textView_Overview = (TextView) findViewById(R.id.text_view_Overview);
        tv = (TextView) findViewById(R.id.title_text_view);
        tvSeeReview =(TextView)findViewById(R.id.text_view_See_Review);


        tvSeeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iReview = new Intent(getApplicationContext(),See_Review_Activity.class);
                iReview.putExtra("AUTHOR",reviews_author);
                iReview.putExtra("CONTENT",reviews_content);
                iReview.putExtra("URL",reviews_url);
                iReview.putExtra("TITLE",TITLEFORREVIEW);
                startActivity(iReview);

            }
        });

        String imageUrl = "http://image.tmdb.org/t/p/w342" + POSTER_IMAGE;
        Picasso.with(this).load(imageUrl).placeholder(R.drawable.youtube).into(imageViewPoster);

        String properDate;
        Format format = new SimpleDateFormat("yyyy-MM-dd");

        Date date;

        try {
            date = (Date) ((DateFormat) format).parse(RELEASE_DATE);
            format = new SimpleDateFormat("yyyy");
            properDate = ((DateFormat) format).format(date);
            textView_Release.setText(properDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        getIntent().setData(null);

        String c = TITLE;


        tv.setText(c);
        textView_Rating.setText(USER_RATING + "/10");
        textView_Overview.setText(OVERVIEW);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Detail_ Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://gozero.com.movied3/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Detail_ Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://gozero.com.movied3/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void getReviewsFromJson(String reviewsJson, String id) throws JSONException {

        final String RESULT_REVIEWS = "results";

        final String REVIEWS_AUTHOR = "author";

        final String REVIEWS_CONTENT = "content";

        final String REVIEWS_URL = "url";


        JSONObject reviews_JsonObj = new JSONObject(reviewsJson);
        JSONArray reviews_Array = reviews_JsonObj.getJSONArray(RESULT_REVIEWS);
        reviews_author = new String[reviews_Array.length()];
        reviews_content = new String[reviews_Array.length()];
        reviews_url = new String[reviews_Array.length()];


        for (int i = 0; i < reviews_Array.length(); i++) {
            Log.v(LOG_TAG, "  " + reviews_Array.length());


            JSONObject reviewsObj = reviews_Array.getJSONObject(i);

            reviews_author[i] = reviewsObj.getString(REVIEWS_AUTHOR);
            reviews_content[i] = reviewsObj.getString(REVIEWS_CONTENT);
            reviews_url[i] = reviewsObj.getString(REVIEWS_URL);


        }

        for (String s : reviews_author) {
            Log.v(LOG_TAG, "THE Author OF  " + s);
        }
        for (String s : reviews_content) {

            Log.v(LOG_TAG, "THE Content OF  " + s);
        }
        for (String s : reviews_url) {
            Log.v(LOG_TAG, "THE URL OF  " + s);
        }

    }

}
