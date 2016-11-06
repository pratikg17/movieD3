package gozero.com.movied3;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pratik G on 20/08/2016.
 */
public class MovieFlavors implements Parcelable

{

    public String POSTER_IMAGE;

    public String TITLE;

    public String OVERVIEW;

    public String USER_RATING;

    public String RELEASE_DATE;

    public MovieFlavors(String POSTER_IMAGE, String TITLE, String OVERVIEW, String USER_RATING, String RELEASE_DATE) {
        this.POSTER_IMAGE = POSTER_IMAGE;
        this.TITLE = TITLE;
        this.OVERVIEW = OVERVIEW;
        this.USER_RATING = USER_RATING;
        this.RELEASE_DATE = RELEASE_DATE;
    }

    public MovieFlavors(Parcel in) {
        POSTER_IMAGE = in.readString();
        TITLE = in.readString();
        OVERVIEW = in.readString();
        USER_RATING = in.readString();
        RELEASE_DATE = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(POSTER_IMAGE);
        parcel.writeString(TITLE);
        parcel.writeString(OVERVIEW);
        parcel.writeString(USER_RATING);
        parcel.writeString(RELEASE_DATE);
    }

    public final Parcelable.Creator<MovieFlavors> CREATOR = new Parcelable.Creator<MovieFlavors>() {

        @Override
        public MovieFlavors createFromParcel(Parcel parcel) {

            return new MovieFlavors(parcel);
        }

        @Override
        public MovieFlavors[] newArray(int i) {
            return new MovieFlavors[i];
        }
    };


}

