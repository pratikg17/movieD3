package gozero.com.movied3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class SplashActivity extends AppCompatActivity {

    private ProgressBar spinner;
    POJO pojo = new POJO();


    ArrayList<MovieFlavors> movieFlavorsArrayList;

    MovieFlavors[] movieFlavorses;

    public String getJson_String() {
        return Json_String;
    }

    public void setJson_String(String json_String) {
        Json_String = json_String;
    }

    public String Json_String;

    public MovieFlavors[] getMovieFlavorses() {
        return movieFlavorses;
    }

    public void setMovieFlavorses(MovieFlavors[] movieFlavorses) {
        this.movieFlavorses = movieFlavorses;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("flavors", movieFlavorsArrayList);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        spinner = (ProgressBar) findViewById(R.id.progressBar);

        FetchMovie fm = new FetchMovie();
        fm.execute("popular");


        spinner.setVisibility(View.VISIBLE);


    }


    public class FetchMovie extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = FetchMovie.class.getName();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(String[] result) {


            String[] imagepath = pojo.getCompleteUrl();
//
//            for(String s : imagepath)
//            {
//                Log.v("MOVIE PATHS", "  "+s);
//            }


//
            Intent i = new Intent(getApplicationContext(), MovieActivity.class);

//            i.putParcelableArrayListExtra("flavor",movieFlavorsArrayList);

            String str = getJson_String();
       //     Toast.makeText(getApplicationContext(), "  " + str, Toast.LENGTH_LONG).show();

            i.putExtra("IMAGEURL", imagepath);
            i.putExtra("JSONDATA", str);

            startActivity(i);
            finish();


        }

        @Override

        protected String[] doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

//        String movie = "movie";
//        String popular = "popular";
//        String top_rated ="top_rated";
//
//        String apikey ="ed4b53075dfd1c0b419f9b2180562cbe";


            try {

//            final String MOVIE_BASE_URL ="http://api.themoviedb.org/3/movie/";
//            final String TOP_RATED = top_rated;
//            final String POPULAR = popular;
//            final  String API_KEY =apikey;


                String buildUrl = "http://api.themoviedb.org/3/movie/" + params[0] + "?api_key=ed4b53075dfd1c0b419f9b2180562cbe";
                Log.v(LOG_TAG, buildUrl);
                URL url = new URL(buildUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {

                setJson_String(movieJsonStr);
                return getMovieDataFromJson(movieJsonStr);


            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        private String[] getMovieDataFromJson(String movieJsonStr) throws JSONException {


            final String POSTER_IMAGE = "poster_path";

            final String RESULT_JSON = "results";

            final String TITLE = "original_title";

            final String OVERVIEW = "overview";

            final String USER_RATING = "vote_average";

            final String RELEASE_DATE = "release_date";

            final String ID = "id";

            setJson_String(movieJsonStr);

            JSONObject movieJson = new JSONObject(movieJsonStr);

            JSONArray moviePosterArray = movieJson.getJSONArray(RESULT_JSON);

//            JSONArray trailerArray = movieJson.getJSONArray(ID);


            String[] resultStr = new String[20];
            for (int i = 0; i < moviePosterArray.length(); i++) {
                Log.v(LOG_TAG, "  " + moviePosterArray.length());

                JSONObject poster = moviePosterArray.getJSONObject(i);

                String posterURl = poster.getString(POSTER_IMAGE);

                String originalTitle = poster.getString(TITLE);

                String overview = poster.getString(OVERVIEW);

                String rating = poster.getString(USER_RATING);

                String release = poster.getString(RELEASE_DATE);

                // MovieFlavors [] movieFlavorArrayObj={  new MovieFlavors(posterURl,originalTitle,overview,rating,release)};


                //
                // setMovieFlavorses(movieFlavorArrayObj);

                // Log.v(LOG_TAG, "  " + posterURl);

                resultStr[i] = posterURl;

                //  String FINAL_IMG_URL=BASE_IMG_URL+posterURl;
                // Log.v(LOG_TAG,"  "+FINAL_IMG_URL);
                // imgArr[i] =FINAL_IMG_URL;


            }


            pojo.setUrl(resultStr);
            pojo.doConcat();

            return resultStr;

        }

    }
}
