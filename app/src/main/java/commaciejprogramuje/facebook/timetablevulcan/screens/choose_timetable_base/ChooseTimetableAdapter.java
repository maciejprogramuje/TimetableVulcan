package commaciejprogramuje.facebook.timetablevulcan.screens.choose_timetable_base;

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
import commaciejprogramuje.facebook.timetablevulcan.screens.timetable.TimetableFragment;
import commaciejprogramuje.facebook.timetablevulcan.utils.Utils;

public class ChooseTimetableAdapter extends RecyclerView.Adapter<ChooseTimetableAdapter.ChooseAdapterHolder> {
    private App app;
    private List<Link> linksToTimetable;

    ChooseTimetableAdapter(App app, List<Link> linksToTimetable) {
        this.app = app;
        this.linksToTimetable = linksToTimetable;
    }

    @NonNull
    @Override
    public ChooseTimetableAdapter.ChooseAdapterHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_timetable, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetConnection(app)) {
                    TextView textToTimetableTextview = v.findViewById(R.id.text_to_timetable_textview);
                    String textToTimetable = textToTimetableTextview.getText().toString();

                    TextView urlToTimetableTextView = v.findViewById(R.id.url_to_timetable_textview);
                    String ulrToTimetable = urlToTimetableTextView.getText().toString();

                    Utils.changeFragment(view.getContext(), TimetableFragment.newInstance(textToTimetable, ulrToTimetable));
                } else {
                    //Snackbars.noInternetSnackbar(v);
                }
            }
        });

        return new ChooseTimetableAdapter.ChooseAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseTimetableAdapter.ChooseAdapterHolder holder, int position) {
        holder.inputData(position);
    }

    @Override
    public int getItemCount() {
        return linksToTimetable.size();
    }

    class ChooseAdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_to_timetable_textview)
        TextView linkToTimetableTextview;
        @BindView(R.id.url_to_timetable_textview)
        TextView urlToTimetableTextview;

        ChooseAdapterHolder(View view) {
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
