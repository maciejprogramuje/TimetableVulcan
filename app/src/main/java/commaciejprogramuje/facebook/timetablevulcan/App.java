package commaciejprogramuje.facebook.timetablevulcan;

import android.app.Application;
import android.content.SharedPreferences;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class App extends Application {
    public static final String FAVOURIVE_TIMETABLE_LINK = "favouriveTimetableLink";
    public static final String FAVOURITE_TIMETABLE_TITLE = "favouriteTimetableTitle";

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getDefaultSharedPreferences(this);
        getFavouriveTimetableLink();
        getFavouriteTimetableTitle();
    }

    public void setFavouriveTimetable(String favouriveTimetableLink, String favouriteTimetableTitle) {
        sharedPreferences.edit()
                .putString(FAVOURIVE_TIMETABLE_LINK, favouriveTimetableLink)
                .putString(FAVOURITE_TIMETABLE_TITLE, favouriteTimetableTitle)
                .apply();
    }

    public String getFavouriveTimetableLink() {
        return sharedPreferences.getString(FAVOURIVE_TIMETABLE_LINK, "");
    }

    public String getFavouriteTimetableTitle() {
        return sharedPreferences.getString(FAVOURITE_TIMETABLE_TITLE, "");
    }
}

// https://www.flaticon.com
// Place the attribution on the credits/description page of the application.
// Icon made by Freepik from www.flaticon.com
