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
    private ArrayList<ScheduleEvent> allScheduleEvents;
    private boolean needScroll = true;
    private int lastFilter = 0;

    private String EXTRA_EVENTS = "schedule_events";
    private String EXTRA_FILTER = "last_section_id";

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
            allScheduleEvents = savedInstanceState.getParcelableArrayList(EXTRA_EVENTS);
            needScroll = false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(EXTRA_EVENTS, allScheduleEvents);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AppEvent.getInstance(getContext()).trackCurrentScreen(getActivity(), "open_schedules");
        scheduleRecyclerView = (RecyclerView) getView().findViewById(R.id.schedule_list);

        if (allScheduleEvents == null)
            getScheduleEvents();
        else
            setScheduleItems();
    }

    @Override
    public void onPause() {
        needScroll = false;
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (lastFilter != NMAPreferences.getSection(getContext())) {
            if (allScheduleEvents == null)
                getScheduleEvents();
            else
                setScheduleItems();
        }
    }

    private void getScheduleEvents() {
        API.nmaService.getEvents().enqueue(new Callback<List<ScheduleEvent>>() {
            @Override
            public void onResponse(Call<List<ScheduleEvent>> call,
                                   Response<List<ScheduleEvent>> response) {
                allScheduleEvents = new ArrayList<>(response.body());

                setScheduleItems();
            }

            @Override
            public void onFailure(Call<List<ScheduleEvent>> call, Throwable t) {
                Error.getData(getView(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getScheduleEvents();
                    }
                });
            }
        });
    }

    private void setScheduleItems() {
        if (isAdded()) {
            int sectionId = NMAPreferences.getSection(getContext());
            ArrayList<ScheduleEvent> sectionEvents = new ArrayList<>();

            for (ScheduleEvent s : allScheduleEvents) {
                if (s.getSectionId() == 0 || s.getSectionId() == sectionId) {
                    sectionEvents.add(s);
                }
            }
            Collections.sort(sectionEvents, new ScheduleEventComparator());

            lastFilter = sectionId;

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

            scheduleRecyclerView.setAdapter(mSectionedAdapter);
            if (needScroll)
                scheduleRecyclerView.scrollToPosition(position + position1);
        }
    }

}
