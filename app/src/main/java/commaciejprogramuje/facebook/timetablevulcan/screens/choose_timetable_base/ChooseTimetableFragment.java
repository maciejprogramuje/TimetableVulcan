package commaciejprogramuje.facebook.timetablevulcan.screens.choose_timetable_base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import commaciejprogramuje.facebook.timetablevulcan.App;
import commaciejprogramuje.facebook.timetablevulcan.R;
import commaciejprogramuje.facebook.timetablevulcan.screens.login.SchoolLinkActivity;
import commaciejprogramuje.facebook.timetablevulcan.screens.timetable.TimetableStatic;
import commaciejprogramuje.facebook.timetablevulcan.utils.Utils;

public class ChooseTimetableFragment extends Fragment {
    public static final String LETTER = "letter";
    Unbinder unbinder;
    @BindView(R.id.unit_recycler_view)
    RecyclerView unitRecyclerView;

    private App app;
    protected List<Link> linksToTimetable;
    protected String letter;
    private Menu menu;

    public ChooseTimetableFragment() {
        // Required empty public constructor
    }

    public static ChooseTimetableFragment newInstance(String letter) {
        ChooseTimetableFragment fragment = new ChooseTimetableFragment();
        Bundle args = new Bundle();
        args.putString(LETTER, letter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.letter = arguments.getString(LETTER);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_timetable, container, false);
        unbinder = ButterKnife.bind(this, view);

        app = (App) view.getContext().getApplicationContext();

        linksToTimetable = new ArrayList<>();
        //baseUrl = "http://www.paderewski.lublin.pl/plany/lic/";

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        inflater.inflate(R.menu.choose_timetable_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fillLinksToTimetables();

        unitRecyclerView.setHasFixedSize(true);
        unitRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        unitRecyclerView.setItemAnimator(new DefaultItemAnimator());
        unitRecyclerView.setAdapter(new ChooseTimetableAdapter(app, linksToTimetable));
    }

    @Override
    public void onResume() {
        super.onResume();

        TimetableStatic.hideBackArrow(getContext());
        TimetableStatic.setTitleBarText(getActivity(), getString(R.string.app_name));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void fillLinksToTimetables() {
        String baseUrl = app.getBaseUrl();
        Log.w("UWAGA", "baseUrl=" + baseUrl);

        Document document = null;
        try {
            document = Jsoup.connect(baseUrl + "lista.html").get();
            Elements elements = document.select("a");
            for (Element element : elements) {
                String tempLink = baseUrl + element.attr("href");
                if (Utils.isLetterInLink(letter, tempLink)) {
                    linksToTimetable.add(new Link(element.text(), tempLink));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}