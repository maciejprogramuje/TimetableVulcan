package commaciejprogramuje.facebook.timetablevulcan;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class App extends Application {
    public static final String LINK_TO_FAVOURIVE_TIMETABLE = "linkToFavouriveTimetable";

    private String linkToFavouriveTimetable;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getDefaultSharedPreferences(this);
        linkToFavouriveTimetable = getLinkToFavouriveTimetable();

        Log.w("UWAGA", "onCreate linkToFavouriveTimetable=" + linkToFavouriveTimetable);
    }

    public void setLinkToFavouriveTimetable(String linkToFavouriveTimetable) {
        sharedPreferences.edit()
                .putString(LINK_TO_FAVOURIVE_TIMETABLE, linkToFavouriveTimetable)
                .apply();
    }

    public String getLinkToFavouriveTimetable() {
        return sharedPreferences.getString(LINK_TO_FAVOURIVE_TIMETABLE, "");
    }
}

// https://www.flaticon.com
// Place the attribution on the credits/description page of the application.
// Icon made by Freepik from www.flaticon.com
