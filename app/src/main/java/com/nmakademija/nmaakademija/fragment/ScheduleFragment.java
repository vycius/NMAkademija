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
import com.nmakademija.nmaakademija.api.NMAService;
import com.nmakademija.nmaakademija.entity.ScheduleEvent;
import com.nmakademija.nmaakademija.utils.Error;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleFragment extends Fragment {

    private RecyclerView scheduleRecyclerView;

    public static ScheduleFragment getInstance() {
        return new ScheduleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_schedule, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        scheduleRecyclerView = (RecyclerView) getView().findViewById(R.id.schedule_list);

        getScheduleEvents(API.nmaService);

    }

    private void getScheduleEvents(final NMAService nmaService) {
        nmaService.getEvents().enqueue(new Callback<List<ScheduleEvent>>() {
            @Override
            public void onResponse(Call<List<ScheduleEvent>> call,
                                   Response<List<ScheduleEvent>> response) {
                List<ScheduleEvent> scheduleEvents = response.body();
                if (getContext() != null) {
                    setScheduleItems(scheduleEvents);
                }
            }

            @Override
            public void onFailure(Call<List<ScheduleEvent>> call, Throwable t) {
                Error.getData(getView());
            }
        });
    }

    private void setScheduleItems(List<ScheduleEvent> scheduleEvents) {
        /*ArrayList<ScheduleItem> scheduleItems = new ArrayList<>();
        scheduleItems.addAll(scheduleEvents);

        ArrayList<Date> scheduleDates = new ArrayList<>();
        for (ScheduleEvent scheduleEvent : scheduleEvents) {
            Date eventDate = scheduleEvent.getDate();
            Date date = new Date(eventDate.getYear(), eventDate.getMonth(), eventDate.getDate());

            if (!scheduleDates.contains(date)) {
                scheduleDates.add(date);
                scheduleItems.add(new ScheduleDayBanner(date));
            }
        }

        Collections.sort(scheduleItems, new ScheduleItemComparator());

        int i;
        Date now = new Date();
        for (i = 0; i < scheduleItems.size() && scheduleItems.get(i).getDate().before(now); ++i);

        ScheduleAdapter adapter = new ScheduleAdapter(getContext(), scheduleItems);
        scheduleRecyclerView.setAdapter(adapter);

        scheduleRecyclerView.scrollToPosition(Math.min(i, scheduleItems.size() - 1));
        */

        ScheduleAdapter adapter = new ScheduleAdapter(getContext(), scheduleEvents);
        scheduleRecyclerView.setAdapter(adapter);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        ArrayList<Date> scheduleDates = new ArrayList<>();
        List<ScheduleSectionsAdapter.Section> sections = new ArrayList<>();
        for (int i = 0; i < scheduleEvents.size(); i++) {
            Date eventDate = scheduleEvents.get(i).getDate();
            Date date = new Date(eventDate.getYear(), eventDate.getMonth(), eventDate.getDate());

            if (!scheduleDates.contains(date)) {
                scheduleDates.add(date);
                sections.add(new ScheduleSectionsAdapter.Section(i, dateFormat.format(date)));
            }
        }

        int i;
        Date now = new Date();
        for (i = 0; i < scheduleEvents.size() && scheduleEvents.get(i).getDate().before(now); ++i) ;

        ScheduleSectionsAdapter.Section[] dummy =
                new ScheduleSectionsAdapter.Section[sections.size()];
        ScheduleSectionsAdapter mSectionedAdapter = new
                ScheduleSectionsAdapter(getContext(), adapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        scheduleRecyclerView.setAdapter(mSectionedAdapter);
        scheduleRecyclerView.scrollToPosition(Math.min(i, scheduleEvents.size() - 1));
    }

}