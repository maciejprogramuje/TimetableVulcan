package commaciejprogramuje.facebook.timetablevulcan.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commaciejprogramuje.facebook.timetablevulcan.R;

public class Utils {
    public static boolean isInternetConnection(Context context) {
        ConnectivityManager con_manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert con_manager != null;
        return con_manager.getActiveNetworkInfo() != null;
    }

    public static void changeFragment(Context context, Fragment newFragment) {
        if (isInternetConnection(context)) {
            FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public static boolean isLetterInLink(String letter, String link) {
        Pattern pattern = Pattern.compile("plany/" + letter + "\\d+" + "\\.html");
        Matcher matcher = pattern.matcher(link);
        return matcher.find();
    }

    public static void adRequest(AdView adView) {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    //todo - ad
    /*public static void initializeAd(Context context) {
        MobileAds.initialize(context, context.getResources().getString(R.string.ad_unit_id_app));
    }*/

    public static void noInternetSnackbar(final View v) {
        showSnackbar(v, v.getResources().getString(R.string.turn_on_internet));
    }

    public static void showSnackbar(final View v, String message) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);

        snackbar.setActionTextColor(v.getResources().getColor(R.color.colorAccent));
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(v.getResources().getColor(R.color.colorWhite));
        snackbar.show();
    }
}
