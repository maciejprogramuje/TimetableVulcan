package commaciejprogramuje.facebook.timetablevulcan.screens.timetable;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import commaciejprogramuje.facebook.timetablevulcan.R;


public class TimetableFragment extends Fragment {
    public TimetableFragment() {
        // Required empty public constructor
    }

    public static TimetableFragment newInstance() {
        TimetableFragment fragment = new TimetableFragment();
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
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);

        return view;
    }

}
