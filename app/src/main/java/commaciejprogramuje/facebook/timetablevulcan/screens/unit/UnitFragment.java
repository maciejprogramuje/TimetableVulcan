package commaciejprogramuje.facebook.timetablevulcan.screens.unit;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import commaciejprogramuje.facebook.timetablevulcan.screens.choose_timetable_base.ChooseTimetableFragment;
import commaciejprogramuje.facebook.timetablevulcan.screens.choose_timetable_base.Link;

public class UnitFragment extends ChooseTimetableFragment {
    @Override
    public void fillLinksToTimetables() {
        Document document = null;
        try {
            document = Jsoup.connect(baseUrl + "lista.html").get();
            Elements elements = document.select("a");
            for (Element element : elements) {
                linksToTimetable.add(new Link(element.text(), baseUrl + element.attr("href")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
