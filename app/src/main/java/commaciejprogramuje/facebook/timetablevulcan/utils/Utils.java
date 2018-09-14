package commaciejprogramuje.facebook.timetablevulcan.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import commaciejprogramuje.facebook.timetablevulcan.App;
import commaciejprogramuje.facebook.timetablevulcan.R;
import commaciejprogramuje.facebook.timetablevulcan.screens.MainActivity;

public class Utils {
    public static String cleanHtmlText(String text) {
        Document document = Jsoup.parse(text);
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
        document.select("br").append("\\n");
        document.select("p").prepend("\\n\\n");
        String s = document.html().replaceAll("\\\\n", "\n");
        String processedText = Jsoup.clean(s, "", whitelist().none(), new Document.OutputSettings().prettyPrint(false));
        return processedText.replaceAll("&nbsp;", " ");
    }

    private static Whitelist whitelist() {
        return new Whitelist().addTags("b", "i", "u",  "ul", "li", "ol", "p", "cite", "sub", "sup", "strike",  "strong", "small", "pre");
    }

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
}
