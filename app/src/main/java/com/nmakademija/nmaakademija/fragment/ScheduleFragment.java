package com.nmakademija.nmaakademija.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.ScheduleItem;
import com.nmakademija.nmaakademija.utils.ScheduleItemComparator;
import com.nmakademija.nmaakademija.adapter.ScheduleAdapter;
import com.nmakademija.nmaakademija.api.API;
import com.nmakademija.nmaakademija.api.NMAService;
import com.nmakademija.nmaakademija.entity.ScheduleDayBanner;
import com.nmakademija.nmaakademija.entity.ScheduleEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleFragment extends Fragment {


    private ListView scheduleListView;
    private ArrayList<ScheduleItem> scheduleItems;

    public static ScheduleFragment getInstance() {
        return new ScheduleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_schedule, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        scheduleListView = (ListView) getView().findViewById(R.id.schedule_list);

        getScheduleEvents(API.nmaService);

    }

    private void getScheduleEvents(final NMAService nmaService) {
        nmaService.getEvents().enqueue(new Callback<List<ScheduleEvent>>() {
            @Override
            public void onResponse(Call<List<ScheduleEvent>> call, Response<List<ScheduleEvent>> response) {
                List<ScheduleEvent> scheduleEvents = response.body();
                setScheduleItems(scheduleEvents);
            }

            @Override
            public void onFailure(Call<List<ScheduleEvent>> call, Throwable t) {
                Toast.makeText(getContext(), "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setScheduleItems(List<ScheduleEvent> scheduleEvents) {
        scheduleItems = new ArrayList<>();
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

        ScheduleAdapter adapter = new ScheduleAdapter(getContext(), scheduleItems);
        scheduleListView.setAdapter(adapter);
    }

}