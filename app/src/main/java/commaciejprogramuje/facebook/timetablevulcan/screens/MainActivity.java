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
import commaciejprogramuje.facebook.timetablevulcan.screens.classroom.ClassroomFragment;
import commaciejprogramuje.facebook.timetablevulcan.screens.teacher.TeacherFragment;
import commaciejprogramuje.facebook.timetablevulcan.screens.unit.UnitFragment;
import commaciejprogramuje.facebook.timetablevulcan.utils.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_unit:
                        changeFragmentInMainActivity(UnitFragment.newInstance());
                        return true;
                    case R.id.navigation_class:
                        changeFragmentInMainActivity(ClassroomFragment.newInstance());
                        return true;
                    case R.id.navigation_teacher:
                        changeFragmentInMainActivity(TeacherFragment.newInstance());
                        return true;
                }
                return false;
            }
        });

        changeFragmentInMainActivity(UnitFragment.newInstance());
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
