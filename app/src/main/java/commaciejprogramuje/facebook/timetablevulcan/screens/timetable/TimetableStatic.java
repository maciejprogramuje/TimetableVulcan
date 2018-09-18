package commaciejprogramuje.facebook.timetablevulcan.screens.timetable;

import android.app.Activity;
import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.util.Objects;

import commaciejprogramuje.facebook.timetablevulcan.screens.MainActivity;

public class TimetableStatic {
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

    public static void setTitleBarText(Activity activity, String name) {
        Objects.requireNonNull(((MainActivity) activity).getSupportActionBar()).setTitle(name);
    }

    public static void showBackArrow(Context context) {
        getSupportActionBar(context).setDisplayHomeAsUpEnabled(true);
    }

    public static void hideBackArrow(Context context) {
        getSupportActionBar(context).setDisplayHomeAsUpEnabled(false);
    }

    private static android.support.v7.app.ActionBar getSupportActionBar(Context context) {
        return ((MainActivity) context).getSupportActionBar();
    }
}
