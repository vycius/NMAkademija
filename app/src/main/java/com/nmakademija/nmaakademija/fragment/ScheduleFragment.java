package com.nmakademija.nmaakademija.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.ScheduleAdapter;
import com.nmakademija.nmaakademija.adapter.ScheduleSectionsAdapter;
import com.nmakademija.nmaakademija.api.FirebaseRealtimeApi;
import com.nmakademija.nmaakademija.api.listener.SchedulesLoadedListener;
import com.nmakademija.nmaakademija.entity.ScheduleEvent;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.NMAPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScheduleFragment extends BaseSceeenFragment implements SchedulesLoadedListener {

    private View loadingView;
    private RecyclerView scheduleRecyclerView;

    private int sectionId;


    public static ScheduleFragment getInstance() {
        return new ScheduleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        scheduleRecyclerView = (RecyclerView) view.findViewById(R.id.schedule_list);
        loadingView = view.findViewById(R.id.loading_view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AppEvent.getInstance(getContext()).trackCurrentScreen(getActivity(), "open_schedules");

        sectionId = NMAPreferences.getSection(getContext());

        loadScheduleEvents();
    }

    private void loadScheduleEvents() {
        showLoading();

        FirebaseRealtimeApi.getSchedules(this, sectionId);
    }

    @Override
    public void onSchedulesLoaded(ArrayList<ScheduleEvent> sectionEvents) {
        if (isAdded()) {

            ScheduleAdapter adapter = new ScheduleAdapter(getContext(), sectionEvents);
            adapter.setHasStableIds(true);
            scheduleRecyclerView.setAdapter(adapter);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            String lastScheduleDay = "";
            List<ScheduleSectionsAdapter.Section> sections = new ArrayList<>();
            int position = 0, position1 = 0;

            Date now = new Date();

            for (int i = 0; i < sectionEvents.size(); i++) {
                Date date = sectionEvents.get(i).getDate();
                String dateString = dateFormat.format(date);

                if (!lastScheduleDay.equals(dateString)) {
                    lastScheduleDay = dateString;
                    sections.add(new ScheduleSectionsAdapter.Section(i, dateString));
                    if (date.before(now)) {
                        position1++;
                    }
                }
                if (date.before(now)) {
                    position = i;
                }
            }

            ScheduleSectionsAdapter.Section[] dummy =
                    new ScheduleSectionsAdapter.Section[sections.size()];
            ScheduleSectionsAdapter mSectionedAdapter = new
                    ScheduleSectionsAdapter(getContext(), adapter);
            mSectionedAdapter.setSections(sections.toArray(dummy));

            scheduleRecyclerView.setAdapter(mSectionedAdapter);
            scheduleRecyclerView.scrollToPosition(position + position1);

            hideLoading();
        }
    }

    @Override
    public void onSchedulesLoadingFailed(Exception exception) {
        if (isAdded()) {
            hideLoading();

            //noinspection ConstantConditions
            Snackbar.make(getView(), R.string.get_request_failed, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadScheduleEvents();
                        }
                    })
                    .show();
        }

    }

    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
        scheduleRecyclerView.setVisibility(View.GONE);
    }

    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
        scheduleRecyclerView.setVisibility(View.VISIBLE);
    }
}
