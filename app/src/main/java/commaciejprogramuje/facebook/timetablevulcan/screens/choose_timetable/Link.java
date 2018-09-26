package commaciejprogramuje.facebook.timetablevulcan.screens.choose_timetable;

public class Link {
    private String text;
    private String linkToTimetable;

    Link(String text, String linkToTimetable) {
        this.text = text;
        this.linkToTimetable = linkToTimetable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLinkToTimetable() {
        return linkToTimetable;
    }

    public void setLinkToTimetable(String linkToTimetable) {
        this.linkToTimetable = linkToTimetable;
    }

    @Override
    public String toString() {
        return text + ": " + linkToTimetable;
    }
}
