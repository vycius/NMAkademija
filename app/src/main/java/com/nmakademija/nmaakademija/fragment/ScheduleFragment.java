package com.nmakademija.nmaakademija.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.nmakademija.nmaakademija.utils.ScheduleEventComparator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScheduleFragment extends Fragment implements SchedulesLoadedListener {

    private View loadingView;
    private RecyclerView scheduleRecyclerView;


    public static ScheduleFragment getInstance() {
        return new ScheduleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AppEvent.getInstance(getContext()).trackCurrentScreen(getActivity(), "open_schedules");

        scheduleRecyclerView = (RecyclerView) getView().findViewById(R.id.schedule_list);
        loadingView = getView().findViewById(R.id.loading_view);

        loadScheduleEvents();
    }

    private void loadScheduleEvents() {
        FirebaseRealtimeApi.getSchedules(this);
    }

    @Override
    public void onSchedulesLoaded(ArrayList<ScheduleEvent> allScheduleEvents) {
        if (isAdded()) {
            int sectionId = NMAPreferences.getSection(getContext());
            ArrayList<ScheduleEvent> sectionEvents = new ArrayList<>();

            for (ScheduleEvent s : allScheduleEvents) {
                if (s.getSectionId() == 0 || s.getSectionId() == sectionId) {
                    sectionEvents.add(s);
                }
            }
            Collections.sort(sectionEvents, new ScheduleEventComparator());

            ScheduleAdapter adapter = new ScheduleAdapter(getContext(), sectionEvents);
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

            loadingView.setVisibility(View.GONE);
            scheduleRecyclerView.setVisibility(View.VISIBLE);

            scheduleRecyclerView.setAdapter(mSectionedAdapter);
            scheduleRecyclerView.scrollToPosition(position + position1);
        }
    }

    @Override
    public void onSchedulesLoadingFailed(Exception exception) {

    }
}
