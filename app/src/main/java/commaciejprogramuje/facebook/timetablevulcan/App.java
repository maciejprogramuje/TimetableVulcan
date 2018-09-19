package commaciejprogramuje.facebook.timetablevulcan;

import android.app.Application;
import android.content.SharedPreferences;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class App extends Application {
    public static final String LINK_TO_FAVOURIVE_TIMETABLE = "linkToFavouriveTimetable";

    private String linkToFavouriveTimetable;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getDefaultSharedPreferences(this);
        getLinkToFavouriveTimetable();
    }

    public void setLinkToFavouriveTimetableToSharedPreferences(String string) {
        sharedPreferences.edit()
                .putString(LINK_TO_FAVOURIVE_TIMETABLE, string)
                .apply();
    }

    public String getLinkToFavouriveTimetable() {
        linkToFavouriveTimetable = sharedPreferences.getString(LINK_TO_FAVOURIVE_TIMETABLE, "");
        return linkToFavouriveTimetable;
    }

    public void setLinkToFavouriveTimetable(String linkToFavouriveTimetable) {
        this.linkToFavouriveTimetable = linkToFavouriveTimetable;
        setLinkToFavouriveTimetableToSharedPreferences(linkToFavouriveTimetable);
    }
}

// https://www.flaticon.com
// Place the attribution on the credits/description page of the application.
// Icon made by Freepik from www.flaticon.com
