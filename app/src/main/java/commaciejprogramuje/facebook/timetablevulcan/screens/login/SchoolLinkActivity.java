package commaciejprogramuje.facebook.timetablevulcan.screens.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import commaciejprogramuje.facebook.timetablevulcan.App;
import commaciejprogramuje.facebook.timetablevulcan.R;
import commaciejprogramuje.facebook.timetablevulcan.screens.MainActivity;

public class SchoolLinkActivity extends Activity {
    @BindView(R.id.enter_link_edittext)
    EditText enterLinkEdittext;
    private String favouriveTimetableLink;
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        app = (App) getApplication();
        favouriveTimetableLink = app.getFavouriveTimetableLink();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (favouriveTimetableLink != null && !favouriveTimetableLink.isEmpty()) {
            goToMainActivity();
        }
    }

    @OnClick(R.id.enter_link_button)
    public void onViewClicked() {
        String schoolLink = enterLinkEdittext.getText().toString();
        app.setBaseUrl(schoolLink);
        app.saveBaseUrl(schoolLink);
        goToMainActivity();
    }

    private void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
