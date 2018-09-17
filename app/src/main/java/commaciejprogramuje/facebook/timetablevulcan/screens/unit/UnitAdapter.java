package commaciejprogramuje.facebook.timetablevulcan.screens.unit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import commaciejprogramuje.facebook.timetablevulcan.App;
import commaciejprogramuje.facebook.timetablevulcan.R;
import commaciejprogramuje.facebook.timetablevulcan.screens.data_from_web.Link;
import commaciejprogramuje.facebook.timetablevulcan.utils.Utils;

class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.UnitAdapterHolder> {
    private App app;
    private List<Link> linksToTimetable;
    private String className;

    UnitAdapter(App app, List<Link> linksToTimetable, String className) {
        this.app = app;
        this.linksToTimetable = linksToTimetable;
        this.className = className;
    }

    @NonNull
    @Override
    public UnitAdapterHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timetables, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetConnection(app)) {
                    TextView textToTimetableTextview = v.findViewById(R.id.text_to_timetable_textview);
                    String textToTimetable = textToTimetableTextview.getText().toString();

                    TextView urlToTimetableTextView = v.findViewById(R.id.url_to_timetable_textview);
                    String ulrToTimetable = urlToTimetableTextView.getText().toString();

                    //Views.changeFragment(view.getContext(), PaderewskiTimetableFragment.newInstance(textToTimetable, ulrToTimetable, className));
                } else {
                    //Snackbars.noInternetSnackbar(v);
                }
            }
        });

        return new UnitAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitAdapterHolder holder, int position) {
        holder.inputData(position);
    }

    @Override
    public int getItemCount() {
        return linksToTimetable.size();
    }

    class UnitAdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_to_timetable_textview)
        TextView linkToTimetableTextview;
        @BindView(R.id.url_to_timetable_textview)
        TextView urlToTimetableTextview;

        UnitAdapterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void inputData(int position) {
            Link link = linksToTimetable.get(position);
            linkToTimetableTextview.setText(link.getText());
            urlToTimetableTextview.setText(link.getLinkToTimetable());
        }
    }
}