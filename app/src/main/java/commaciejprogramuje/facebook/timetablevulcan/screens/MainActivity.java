package commaciejprogramuje.facebook.timetablevulcan.screens;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import commaciejprogramuje.facebook.timetablevulcan.App;
import commaciejprogramuje.facebook.timetablevulcan.R;
import commaciejprogramuje.facebook.timetablevulcan.screens.choose_timetable_base.ChooseTimetableFragment;
import commaciejprogramuje.facebook.timetablevulcan.screens.timetable.TimetableFragment;
import commaciejprogramuje.facebook.timetablevulcan.utils.Utils;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigation;
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        navigation = findViewById(R.id.navigation);
        removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_unit:
                        changeFragmentInMainActivity(ChooseTimetableFragment.newInstance("o"));
                        return true;
                    case R.id.navigation_class:
                        changeFragmentInMainActivity(ChooseTimetableFragment.newInstance("s"));
                        return true;
                    case R.id.navigation_teacher:
                        changeFragmentInMainActivity(ChooseTimetableFragment.newInstance("n"));
                        return true;
                    case R.id.navigation_favorite:
                        app = (App) getApplication();
                        String favouriveTimetableLink = app.getFavouriveTimetableLink();
                        String favouriteTimetableTitle = app.getFavouriteTimetableTitle();
                        if(!favouriveTimetableLink.isEmpty()) {
                            changeFragmentInMainActivity(TimetableFragment.newInstance(favouriteTimetableTitle, favouriveTimetableLink, false));
                        }
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        app = (App) getApplication();
        String favouriveTimetableLink = app.getFavouriveTimetableLink();
        String favouriteTimetableTitle = app.getFavouriteTimetableTitle();

        if (favouriveTimetableLink.isEmpty()) {
            changeFragmentInMainActivity(ChooseTimetableFragment.newInstance("o"));
        } else {
            changeFragmentInMainActivity(TimetableFragment.newInstance(favouriteTimetableTitle, favouriveTimetableLink, false));
            navigation.getMenu().getItem(3).setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void changeFragmentInMainActivity(Fragment fragment) {
        if (Utils.isInternetConnection(this)) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @SuppressLint("RestrictedApi")
    void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }
}
