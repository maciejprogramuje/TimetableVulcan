package commaciejprogramuje.facebook.timetablevulcan.screens.timetable;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
import commaciejprogramuje.facebook.timetablevulcan.R;
import commaciejprogramuje.facebook.timetablevulcan.screens.MainActivity;
import commaciejprogramuje.facebook.timetablevulcan.utils.Utils;


public class TimetableFragment extends Fragment {
    public static final String LINK_TO_TIMETABLE = "linkToTimetable";
    public static final String TEXT_TO_TIMETABLE = "textToTimetable";
    public static final String SIMPLE_NAME = "simpleName";

    Unbinder unbinder;
    @BindView(R.id.tableLayout)
    TableLayout tableLayout;


    private String linkToTimetable;
    private String textToTimetable;
    private String simpleName;
    private ArrayList<RowInput> tableOutput;

    public TimetableFragment() {
        // Required empty public constructor
    }

    public static TimetableFragment newInstance(String textToTimetable, String linkToTimetable, String sipmleName) {
        TimetableFragment fragment = new TimetableFragment();
        Bundle args = new Bundle();
        args.putString(TEXT_TO_TIMETABLE, textToTimetable);
        args.putString(LINK_TO_TIMETABLE, linkToTimetable);
        args.putString(SIMPLE_NAME, sipmleName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.textToTimetable = arguments.getString(TEXT_TO_TIMETABLE);
            this.linkToTimetable = arguments.getString(LINK_TO_TIMETABLE);
            this.simpleName = arguments.getString(SIMPLE_NAME);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        android.support.v7.app.ActionBar supportActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Log.w("UWAGA", "home clicked!");
                (getActivity()).onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        unbinder = ButterKnife.bind(this, view);

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
            document = Jsoup.connect(linkToTimetable).get();
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
