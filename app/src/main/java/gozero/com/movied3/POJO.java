package gozero.com.movied3;

import android.util.Log;

/**
 * Created by Pratik G on 19/08/2016.
 */
public class POJO {



    public POJO(String POSTER_IMAGE, String TITLE, String OVERVIEW, String USER_RATING, String RELEASE_DATE) {
        this.POSTER_IMAGE = POSTER_IMAGE;

        this.TITLE = TITLE;
        this.OVERVIEW = OVERVIEW;
        this.USER_RATING = USER_RATING;
        this.RELEASE_DATE = RELEASE_DATE;
    }


    public POJO() {
    }


    private String POSTER_IMAGE;

    private String TITLE;

    private String OVERVIEW;

    private String USER_RATING;

    private String RELEASE_DATE;

    public String completeUrl[] = new String[20];
    public String completeYoutubeUrl[] ;

    public void setCompleteYoutubeUrl(String[] completeYoutubeUrl) {
        this.completeYoutubeUrl = completeYoutubeUrl;
    }

    public String[] getCompleteYoutubeUrl() {
        return completeYoutubeUrl;
    }

    public String[] url;


    public String[] getYoutube_key() {
        return youtube_key;
    }

    public void setYoutube_key(String[] youtube_key) {
        this.youtube_key = youtube_key;
    }

    public String[] youtube_key;


    String BASE_IMG_URL = "http://image.tmdb.org/t/p/w342";


    String YOUTUBE_URL ="http://www.youtube.com/watch?v=";


    public static String[] Author;
    public static String[] Content;
    public static String[] URL;








    public String[] getCompleteUrl() {
        return completeUrl;
    }


    public String[] getUrl() {
        return url;
    }


    public void setUrl(String[] url) {
        this.url = url;
    }


    public void doConcat() {
        if (url != null) {

            for (int i = 0; i < url.length; i++) {

                completeUrl[i] = BASE_IMG_URL + url[i];

            }

            for (String s : completeUrl) {
                Log.v("IMAGE PATH", "  " + s);
            }


        }
    }
    public void doYoutubeConcat() {
        if (youtube_key != null) {


            int size = youtube_key.length;
            String[] yurl = new String[size];
            for (int i = 0; i < youtube_key.length; i++) {

                yurl[i] = YOUTUBE_URL + youtube_key[i];

            }

            setCompleteYoutubeUrl(yurl);
            completeYoutubeUrl=yurl;

            for (String s : completeYoutubeUrl) {
                Log.v("YOUTUBE PATH", "  " + s);
            }


        }
    }

}
