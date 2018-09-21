package commaciejprogramuje.facebook.timetablevulcan;

import android.app.Application;
import android.content.SharedPreferences;
import android.view.Menu;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class App extends Application {
    public static final String FAVOURIVE_TIMETABLE_LINK = "favouriveTimetableLink";
    public static final String FAVOURITE_TIMETABLE_TITLE = "favouriteTimetableTitle";
    public static final String BASE_URL = "baseUrl";

    private SharedPreferences sharedPreferences;
    private String baseUrl;
    private String favouriveTimetableLink;
    private String favouriteTimetableTitle;

    private Menu menu;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getDefaultSharedPreferences(this);
        baseUrl = sharedPreferences.getString(BASE_URL, "");
        favouriveTimetableLink = sharedPreferences.getString(FAVOURIVE_TIMETABLE_LINK, "");
        favouriteTimetableTitle = sharedPreferences.getString(FAVOURITE_TIMETABLE_TITLE, "");
    }

    public void saveFavouriveTimetable(String favouriveTimetableLink, String favouriteTimetableTitle) {
        sharedPreferences.edit()
                .putString(FAVOURIVE_TIMETABLE_LINK, favouriveTimetableLink)
                .putString(FAVOURITE_TIMETABLE_TITLE, favouriteTimetableTitle)
                .apply();
    }

    public void saveBaseUrl(String baseUrl) {
        sharedPreferences.edit()
                .putString(BASE_URL, baseUrl)
                .apply();
    }

    public void setFavouriteTimetableTitle(String favouriteTimetableTitle) {
        this.favouriteTimetableTitle = favouriteTimetableTitle;
    }

    public void setFavouriveTimetableLink(String favouriveTimetableLink) {
        this.favouriveTimetableLink = favouriveTimetableLink;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getFavouriveTimetableLink() {
        return favouriveTimetableLink;
    }

    public String getFavouriteTimetableTitle() {
        return favouriteTimetableTitle;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}

// https://www.flaticon.com
// Place the attribution on the credits/description page of the application.
// Icon made by Freepik from www.flaticon.com
