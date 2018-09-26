package commaciejprogramuje.facebook.timetablevulcan.screens.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private String baseUrl;
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        app = (App) getApplication();
        baseUrl = app.getBaseUrl();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (baseUrl != null && !baseUrl.isEmpty()) {
            goToMainActivity();
        }
    }

    @OnClick(R.id.enter_link_button)
    public void onViewClicked() {
        String schoolLink = formatLink(enterLinkEdittext.getText().toString());
        Log.w("UWAGA", "schoolLink=" + schoolLink);
        app.setBaseUrl(schoolLink);
        app.saveBaseUrl(schoolLink);
        goToMainActivity();
    }

    private String formatLink(String s) {
        if (!s.startsWith("http://www.")) {
            s = "http://www." + s;
        } else if (s.startsWith("www.")) {
            s = "http://" + s;
        }

        if (!s.endsWith("/")) {
            s = s + "/";
        }

        return s;
    }

    private void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
