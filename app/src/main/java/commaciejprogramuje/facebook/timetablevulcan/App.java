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
        linkToFavouriveTimetable = sharedPreferences.getString(LINK_TO_FAVOURIVE_TIMETABLE, "");
    }

    public void setSharedPreferencesLinkToFavouriveTimetable(String string) {
        sharedPreferences.edit()
                .putString(LINK_TO_FAVOURIVE_TIMETABLE, string)
                .apply();
    }

    public String getLinkToFavouriveTimetable() {
        return linkToFavouriveTimetable;
    }

    public void setLinkToFavouriveTimetable(String linkToFavouriveTimetable) {
        this.linkToFavouriveTimetable = linkToFavouriveTimetable;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
