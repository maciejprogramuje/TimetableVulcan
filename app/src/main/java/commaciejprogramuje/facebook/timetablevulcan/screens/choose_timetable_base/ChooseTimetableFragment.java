package commaciejprogramuje.facebook.timetablevulcan.screens.choose_timetable_base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import commaciejprogramuje.facebook.timetablevulcan.App;
import commaciejprogramuje.facebook.timetablevulcan.R;
import commaciejprogramuje.facebook.timetablevulcan.screens.MainActivity;
import commaciejprogramuje.facebook.timetablevulcan.screens.timetable.TimetableStatic;
import commaciejprogramuje.facebook.timetablevulcan.screens.unit.UnitFragment;

public abstract class ChooseTimetableFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.unit_recycler_view)
    RecyclerView unitRecyclerView;

    private View view;
    private App app;
    protected List<Link> linksToTimetable;
    protected String baseUrl;

    public ChooseTimetableFragment() {
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
        view = inflater.inflate(R.layout.fragment_choose_timetable, container, false);
        unbinder = ButterKnife.bind(this, view);

        app = (App) view.getContext().getApplicationContext();

        linksToTimetable = new ArrayList<>();
        baseUrl = "http://www.paderewski.lublin.pl/plany/lic/";

        return view;
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

    public abstract void fillLinksToTimetables();
}