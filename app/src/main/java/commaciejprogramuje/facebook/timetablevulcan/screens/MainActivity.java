package commaciejprogramuje.facebook.timetablevulcan.screens;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;
import commaciejprogramuje.facebook.timetablevulcan.App;
import commaciejprogramuje.facebook.timetablevulcan.R;
import commaciejprogramuje.facebook.timetablevulcan.screens.choose_timetable_base.ChooseTimetableFragment;
import commaciejprogramuje.facebook.timetablevulcan.utils.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        BottomNavigationView navigation = findViewById(R.id.navigation);
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
                }
                return false;
            }
        });

        App app = (App) getApplication();

        changeFragmentInMainActivity(ChooseTimetableFragment.newInstance("o"));

        /*String linkToFavouriveTimetable = app.getLinkToFavouriveTimetable();
        if (linkToFavouriveTimetable.isEmpty()) {
            changeFragmentInMainActivity(ChooseTimetableFragment.newInstance("o"));
        } else {
            String textToTimetable = "test";
            Log.w("UWAGA", "linkToFavouriveTimetable=" + linkToFavouriveTimetable);

            Utils.changeFragment(getApplicationContext(), TimetableFragment.newInstance(textToTimetable, linkToFavouriveTimetable));
        }*/
    }

    private void changeFragmentInMainActivity(Fragment fragment) {
        if (Utils.isInternetConnection(this)) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


}
