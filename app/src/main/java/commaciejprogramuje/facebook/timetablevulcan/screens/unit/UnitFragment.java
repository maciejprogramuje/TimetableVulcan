package commaciejprogramuje.facebook.timetablevulcan.screens.unit;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
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
import commaciejprogramuje.facebook.timetablevulcan.screens.data_from_web.Link;

public class UnitFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.unit_recycler_view)
    RecyclerView unitRecyclerView;

    private View view;
    private App app;
    private List<Link> linksToTimetable;
    private String baseUrl;

    public UnitFragment() {
        // Required empty public constructor
    }

    public static UnitFragment newInstance() {
        UnitFragment fragment = new UnitFragment();
        Bundle args = new Bundle();
        //args.putString(TEXT_TO_TIMETABLE, textToTimetable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            //this.simpleName = arguments.getString(SIMPLE_NAME);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_unit, container, false);
        unbinder = ButterKnife.bind(this, view);

        app = (App) view.getContext().getApplicationContext();

        linksToTimetable = new ArrayList<>();
        baseUrl = "http://www.paderewski.lublin.pl/plany/lic/";

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        //unitRecyclerView.setHasFixedSize(true);
        unitRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        unitRecyclerView.setItemAnimator(new DefaultItemAnimator());
        unitRecyclerView.setAdapter(new UnitAdapter(app, linksToTimetable, this.getClass().getName()));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
