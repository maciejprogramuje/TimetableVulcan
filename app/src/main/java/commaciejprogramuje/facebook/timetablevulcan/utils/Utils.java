package commaciejprogramuje.facebook.timetablevulcan.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

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
}
