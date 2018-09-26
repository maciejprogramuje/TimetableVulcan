package commaciejprogramuje.facebook.timetablevulcan.screens.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.EditText;

import org.jsoup.Jsoup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import commaciejprogramuje.facebook.timetablevulcan.App;
import commaciejprogramuje.facebook.timetablevulcan.R;
import commaciejprogramuje.facebook.timetablevulcan.screens.MainActivity;
import commaciejprogramuje.facebook.timetablevulcan.utils.Utils;

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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
        if (Utils.isInternetConnection(getApplicationContext())) {
            String schoolLink = formatLink(enterLinkEdittext.getText().toString());
            Log.w("UWAGA", "schoolLink=" + schoolLink);
            try {
                Log.w("UWAGA", "schoolLink=OK");
                Jsoup.connect(schoolLink).get();
                app.setBaseUrl(schoolLink);
                app.saveBaseUrl(schoolLink);
                goToMainActivity();
            } catch (Exception e) {
                Log.w("UWAGA", "schoolLink=PROBLEM --->>> ");
                e.printStackTrace();
                app.clearCredentials();
                Utils.showSnackbar(enterLinkEdittext, getResources().getString(R.string.bad_link));
            }
        } else {
            Utils.noInternetSnackbar(enterLinkEdittext);
        }
    }

    private String formatLink(String s) {
        if (s.startsWith("www.")) {
            s = "http://" + s;
        } else if (!s.startsWith("http://www.")) {
            s = "http://www." + s;
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
