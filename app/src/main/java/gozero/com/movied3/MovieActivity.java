package gozero.com.movied3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {
    private final String LOG_TAG = FetchTrailers.class.getSimpleName();
    private ProgressBar pbMOvie;
    public String posterURl, originalTitle, overview, rating, release;
    public String id;
    public  String reviewsJsonStr = null;

    int[] imageId = {
            R.drawable.abc,
            R.drawable.abc,
            R.drawable.abc, R.drawable.abc,
            R.drawable.abc, R.drawable.abc
            , R.drawable.abc, R.drawable.abc,
            R.drawable.abc, R.drawable.abc, R.drawable.abc, R.drawable.abc,
            R.drawable.abc, R.drawable.abc
            , R.drawable.abc, R.drawable.abc,
            R.drawable.abc, R.drawable.abc

    };

    ArrayList<MovieFlavors> movieFlavorsArrayList;
    String[] myPath;
    GridView grid;
    public String jsonStr;
    Intent i2 ;
 public   POJO pojoYT = new POJO();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Intent i = getIntent();
        myPath = i.getStringArrayExtra("IMAGEURL");
        jsonStr = i.getStringExtra("JSONDATA");

        Log.v(LOG_TAG, jsonStr);
        movieFlavorsArrayList = i.getParcelableArrayListExtra("flavors");

        pbMOvie = (ProgressBar) findViewById(R.id.progressBar_Movie);


        CustomGrid adapter = new CustomGrid(getApplicationContext(), myPath);


        grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            //    Toast.makeText(getApplicationContext(), "You Clicked at " + myPath[+position], Toast.LENGTH_SHORT).show();
                //    Toast.makeText(getApplicationContext(), "You Clicked at " + jsonStr , Toast.LENGTH_SHORT).show();

              //  pbMOvie.setVisibility(View.VISIBLE);

                try {
                    getJSONdata(jsonStr, position);

                    Log.v(LOG_TAG, "  " + overview);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });




    }



    private void getJSONdata(String jsonString, int position) throws JSONException {


        final String POSTER_IMAGE = "poster_path";

        final String RESULT_JSON = "results";

        final String TITLE = "original_title";

        final String OVERVIEW = "overview";

        final String USER_RATING = "vote_average";

        final String RELEASE_DATE = "release_date";

        final String ID = "id";


        JSONObject movieJson = new JSONObject(jsonString);

        JSONArray moviePosterArray = movieJson.getJSONArray(RESULT_JSON);

        for (int i = 0; i < moviePosterArray.length(); i++) {

            JSONObject poster = moviePosterArray.getJSONObject(i);


            posterURl = poster.getString(POSTER_IMAGE);

            originalTitle = poster.getString(TITLE);

            overview = poster.getString(OVERVIEW);

            rating = poster.getString(USER_RATING);

            release = poster.getString(RELEASE_DATE);

            id = poster.getString(ID);

            if (i == position) {


                i2 = new Intent(getApplicationContext(),Detail_Activity.class);
                i2.putExtra("PTR", posterURl);
                i2.putExtra("OT", originalTitle);
                i2.putExtra("OV", overview);
                i2.putExtra("RT", rating);
                i2.putExtra("RL", release);
                i2.putExtra("ID",id);

                //      startActivity(i2);

                pbMOvie.setVisibility(View.VISIBLE);
                FetchTrailers ft = new FetchTrailers();


                ft.execute(id);


            }
        }


    }


    public class FetchTrailers extends AsyncTask<String, Void, String[]> {


        @Override
        protected void onPostExecute(String[] strings) {

            if(strings==null)

            {
                Log.v(LOG_TAG,"IS  NULL");
            }
            String[] youtube_path = pojoYT.getCompleteYoutubeUrl();
            for (String s : youtube_path) {
                Log.v("ON POST YOUTUBE PATHS", "  " + s);
            }


            //Intent i2 = new Intent(getBaseContext(), Detail_Activity.class);

//
//            i2.putExtra("PTR", posterURl);
//            i2.putExtra("OT", originalTitle);
//            i2.putExtra("OV", overview);
//            i2.putExtra("RT", rating);
//            i2.putExtra("RL", release);
            i2.putExtra("REVIEW_JSON",reviewsJsonStr);
            i2.putExtra("YOUTUBE_URL", youtube_path);

            pbMOvie.setVisibility(View.GONE);
             startActivity(i2);

        }

        @Override
        protected String[] doInBackground(String... params) {


            if (params.length == 0) {
                return null;
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            HttpURLConnection urlConnectionR = null;
            BufferedReader readerR = null;


            // Will contain the raw JSON response as a string.
            String trailerJsonStr = null;

            try {


                String buildUrl = "http://api.themoviedb.org/3/movie/" + params[0] + "/videos?api_key=ed4b53075dfd1c0b419f9b2180562cbe";
                String buildUrlR = "http://api.themoviedb.org/3/movie/" + params[0] + "/reviews?api_key=ed4b53075dfd1c0b419f9b2180562cbe";

                Log.v(LOG_TAG, buildUrl);
                Log.v(LOG_TAG, buildUrlR);

                URL url = new URL(buildUrl);
                URL urlR = new URL(buildUrlR);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                urlConnectionR = (HttpURLConnection) urlR.openConnection();
                urlConnectionR.setRequestMethod("GET");
                urlConnectionR.connect();


                Log.v(LOG_TAG, " "+buildUrl);

                InputStream inputStream = urlConnection.getInputStream();

                InputStream inputStreamR = urlConnectionR.getInputStream();

                StringBuffer buffer = new StringBuffer();

                StringBuffer bufferR = new StringBuffer();

                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                if (inputStreamR == null) {
                    // Nothing to do.
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                readerR = new BufferedReader(new InputStreamReader(inputStreamR));

                String line;

                String lineR;

                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                while ((lineR = readerR.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    bufferR.append(lineR + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                if (bufferR.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }


                trailerJsonStr = buffer.toString();
                reviewsJsonStr = bufferR.toString();
                Log.i(LOG_TAG," BBUFFER "+reviewsJsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null&&urlConnectionR!=null) {
                    urlConnection.disconnect();
                    urlConnectionR.disconnect();
                }
                if (reader != null&&readerR!=null) {
                    try {
                        reader.close();
                        readerR.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {

                Log.v(LOG_TAG, "THE JSON STR" +trailerJsonStr);
                String[] RETURNED =getTrailerFromJson(trailerJsonStr);

                for(String s :RETURNED)
                {
                    Log.v(LOG_TAG,"THE RETURED ARRAY " +s);
                }
                return RETURNED;


            } catch (Exception e) {
                Log.e(LOG_TAG,"ERROR FROM "+ e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }
    }

    public String[] getTrailerFromJson(String trailerJson) throws JSONException {

        final String RESULT_TRAILER = "results";

        final String TRAILER_KEY = "key";

        JSONObject trailer_JsonObj = new JSONObject(trailerJson);
        JSONArray trailer_Array = trailer_JsonObj.getJSONArray(RESULT_TRAILER);
        String [] resultStr  = new String[trailer_Array.length()];
        for (int i = 0; i < trailer_Array.length(); i++) {
            Log.v(LOG_TAG, "  " + trailer_Array.length());

            JSONObject keyObj = trailer_Array.getJSONObject(i);

            String keyUrl = keyObj.getString(TRAILER_KEY);
            Log.v(LOG_TAG,"  "+keyUrl);
            resultStr[i] = keyUrl;


        }

        for(String s :resultStr)
        {
            Log.v(LOG_TAG, "THE STRING OF  "+s);
        }

        pojoYT.setYoutube_key(resultStr);
        pojoYT.doYoutubeConcat();
        return resultStr;
    }


}
