package commaciejprogramuje.facebook.timetablevulcan.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import commaciejprogramuje.facebook.timetablevulcan.App;
import commaciejprogramuje.facebook.timetablevulcan.R;
import commaciejprogramuje.facebook.timetablevulcan.utils.Utils;

public class MainActivity extends AppCompatActivity {
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (App) getApplication();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_unit:
                        changeFragmentInMainActivity(UnitFragment.newInstance());
                        return true;
                    case R.id.navigation_class:
                        changeFragmentInMainActivity(ClassFragment.newInstance());
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
