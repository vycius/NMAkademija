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
import com.nmakademija.nmaakademija.api.API;
import com.nmakademija.nmaakademija.entity.ScheduleEvent;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.Error;
import com.nmakademija.nmaakademija.utils.NMAPreferences;
import com.nmakademija.nmaakademija.utils.ScheduleEventComparator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleFragment extends Fragment {

    private RecyclerView scheduleRecyclerView;
    private ArrayList<ScheduleEvent> scheduleEvents;
    private int sectionId = -1;

    private String EXTRA_EVENTS = "schedule_events";
    private String EXTRA_SECTION = "section";

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            scheduleEvents = savedInstanceState.getParcelableArrayList(EXTRA_EVENTS);
            sectionId = savedInstanceState.getInt(EXTRA_SECTION);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(EXTRA_EVENTS, scheduleEvents);
        outState.putInt(EXTRA_SECTION, sectionId);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AppEvent.getInstance(getContext()).trackCurrentScreen(getActivity(), "open_schedules");

        scheduleRecyclerView = (RecyclerView) getView().findViewById(R.id.schedule_list);
        int sectionTemp = NMAPreferences.getSection(getContext());
        if (scheduleEvents == null || sectionId != sectionTemp)
            getScheduleEvents(sectionTemp);
        else
            setScheduleItems();
    }

    private void getScheduleEvents(final int realSectionId) {
        API.nmaService.getEvents().enqueue(new Callback<List<ScheduleEvent>>() {
            @Override
            public void onResponse(Call<List<ScheduleEvent>> call,
                                   Response<List<ScheduleEvent>> response) {
                List<ScheduleEvent> scheduleEventsList = response.body();
                scheduleEvents = new ArrayList<>();
                sectionId = realSectionId;
                for (ScheduleEvent s : scheduleEventsList) {
                    if (s.getSectionId() == 0 || s.getSectionId() == sectionId) {
                        scheduleEvents.add(s);
                    }
                }
                Collections.sort(scheduleEvents, new ScheduleEventComparator());
                setScheduleItems();
            }

            @Override
            public void onFailure(Call<List<ScheduleEvent>> call, Throwable t) {
                Error.getData(getView(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getScheduleEvents(realSectionId);
                    }
                });
            }
        });
    }

    private void setScheduleItems() {
        if (isAdded()) {

            ScheduleAdapter adapter = new ScheduleAdapter(getContext(), scheduleEvents);
            scheduleRecyclerView.setAdapter(adapter);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            String lastScheduleDay = "";
            List<ScheduleSectionsAdapter.Section> sections = new ArrayList<>();
            int position = 0, position1 = 0;

            Date now = new Date();

            for (int i = 0; i < scheduleEvents.size(); i++) {
                Date date = scheduleEvents.get(i).getDate();
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
        }
    }

}
