package commaciejprogramuje.facebook.timetablevulcan.screens.timetable;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import commaciejprogramuje.facebook.timetablevulcan.App;
import commaciejprogramuje.facebook.timetablevulcan.R;
import commaciejprogramuje.facebook.timetablevulcan.screens.login.SchoolLinkActivity;


public class TimetableFragment extends Fragment {
    public static final String LINK_TO_TIMETABLE = "timetableLink";
    public static final String TEXT_TO_TIMETABLE = "timetableTitle";
    public static final String HAS_OPTIONS_MENU = "hasBackArrow";

    Unbinder unbinder;
    @BindView(R.id.tableLayout)
    TableLayout tableLayout;

    private String timetableLink;
    private String timetableTitle;
    private ArrayList<RowInput> tableOutput;
    private App app;
    private Menu menu;
    private String favouriveTimetableLink;
    private boolean hasBackArrow;

    public TimetableFragment() {
        // Required empty public constructor
    }

    public static TimetableFragment newInstance(String timetableTitle, String timetableLink, boolean hasBackArrow) {
        TimetableFragment fragment = new TimetableFragment();
        Bundle args = new Bundle();
        args.putString(TEXT_TO_TIMETABLE, timetableTitle);
        args.putString(LINK_TO_TIMETABLE, timetableLink);
        args.putBoolean(HAS_OPTIONS_MENU, hasBackArrow);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.timetableTitle = arguments.getString(TEXT_TO_TIMETABLE);
            this.timetableLink = arguments.getString(LINK_TO_TIMETABLE);
            this.hasBackArrow = arguments.getBoolean(HAS_OPTIONS_MENU);
        }

        setHasOptionsMenu(true);

        app = (App) Objects.requireNonNull(getContext()).getApplicationContext();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (hasBackArrow) {
            TimetableStatic.showBackArrow(getContext());
        } else {
            TimetableStatic.hideBackArrow(getContext());
        }
        TimetableStatic.setTitleBarText(getActivity(), timetableTitle);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        favouriveTimetableLink = app.getFavouriveTimetableLink();
        if (favouriveTimetableLink.equals(timetableLink)) {
            menu.getItem(0).setVisible(false);
        } else {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_favorite_border, null));
        }

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                (Objects.requireNonNull(getActivity())).onBackPressed();
                return true;
            case R.id.menu_favorite:
                favouriveTimetableLink = app.getFavouriveTimetableLink();
                if (favouriveTimetableLink.equals(timetableLink)) {
                    app.setFavouriteTimetableTitle("");
                    app.setFavouriveTimetableLink("");
                    app.saveFavouriveTimetable("", "");
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_favorite_border, null));
                } else {
                    app.setFavouriteTimetableTitle(timetableTitle);
                    app.setFavouriveTimetableLink(timetableLink);
                    app.saveFavouriveTimetable(timetableLink, timetableTitle);
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_favorite, null));
                }
                return true;
            case R.id.menu_change_school:
                app.setFavouriteTimetableTitle("");
                app.setFavouriveTimetableLink("");
                app.saveFavouriveTimetable("", "");
                startActivity(new Intent(getContext(), SchoolLinkActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        unbinder = ButterKnife.bind(this, view);

        //todo
        //Views.adRequest(adView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getTimetableFromWeb();
        fillTableLayoutByTimeteableData();
    }

    private void fillTableLayoutByTimeteableData() {
        for (RowInput rowInput : tableOutput) {
            TableRow tableRow = new TableRow(this.getContext());
            tableRow.setDividerDrawable(Objects.requireNonNull(getContext()).getResources().getDrawable(R.drawable.table_divider, null));
            tableRow.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            tableRow.setPadding(5, 5, 5, 5);
            for (String rr : rowInput.getCells()) {
                tableRow.addView(generateTextView(rr));
            }
            tableLayout.addView(tableRow);
        }
    }

    private TextView generateTextView(String text) {
        TextView textView = new TextView(this.getContext());
        textView.setMaxLines(10);
        textView.setPadding(5, 0, 5, 0);
        textView.setText(text);

        return textView;
    }

    private void getTimetableFromWeb() {
        Document document = null;
        try {
            document = Jsoup.connect(timetableLink).get();
            tableOutput = new ArrayList<>();

            Element tableInput = document.getElementsByClass("tabela").get(0);

            ArrayList<String> ths = new ArrayList<>();
            for (Element th : tableInput.select("th")) {
                ths.add(th.text());
            }
            tableOutput.add(new RowInput(ths));

            for (Element row : tableInput.select("tr")) {
                ArrayList<String> tds = new ArrayList<>();
                for (Element td : row.select("td")) {
                    tds.add(TimetableStatic.cleanHtmlText(td.html()));
                }
                tableOutput.add(new RowInput(tds));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class RowInput {
        private ArrayList<String> cells;

        RowInput(ArrayList<String> cells) {
            this.cells = cells;
        }

        ArrayList<String> getCells() {
            return cells;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
