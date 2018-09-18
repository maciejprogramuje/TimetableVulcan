package commaciejprogramuje.facebook.timetablevulcan.screens.timetable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

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
}
