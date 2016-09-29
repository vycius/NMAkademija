package com.nmakademija.nmaakademija.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.ScheduleAdapter;
import com.nmakademija.nmaakademija.adapter.ScheduleSectionsAdapter;
import com.nmakademija.nmaakademija.api.API;
import com.nmakademija.nmaakademija.entity.ScheduleEvent;
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

    public static ScheduleFragment getInstance() {
        return new ScheduleFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        getScheduleEvents();
    }

    @Override
    public void onStop() {
        ScheduleSectionsAdapter scheduleSectionsAdapter = (ScheduleSectionsAdapter) scheduleRecyclerView.getAdapter();
        if (scheduleSectionsAdapter != null) {
            ScheduleAdapter scheduleAdapter = (ScheduleAdapter) scheduleSectionsAdapter.getAdapter();
            if (scheduleAdapter != null)
                scheduleAdapter.deleteAll();
        }
        super.onStop();
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
        View view = getView();
        scheduleRecyclerView = (RecyclerView) view.findViewById(R.id.schedule_list);
    }

    private void getScheduleEvents() {
        API.nmaService.getEvents().enqueue(new Callback<List<ScheduleEvent>>() {
            @Override
            public void onResponse(Call<List<ScheduleEvent>> call,
                                   Response<List<ScheduleEvent>> response) {
                Context context = getContext();
                if (context != null) {
                    List<ScheduleEvent> scheduleEvents = response.body();
                    int sectionId = NMAPreferences.getSection(getContext());
                    if (sectionId == 0) {
                        Log.e("Section ID", "0");
                    } else {
                        List<ScheduleEvent> scheduleEventList = new ArrayList<>();
                        for (ScheduleEvent s : scheduleEvents) {
                            if (s.getSectionId() == sectionId) {
                                scheduleEventList.add(s);
                            }
                        }
                        setScheduleItems(scheduleEventList);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ScheduleEvent>> call, Throwable t) {
                Error.getData(getView());
            }
        });
    }

    private void setScheduleItems(List<ScheduleEvent> scheduleEvents) {
        Collections.sort(scheduleEvents, new ScheduleEventComparator());

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
        Log.i("scrollToPosition", String.valueOf(position + position1));
        scheduleRecyclerView.scrollToPosition(position + position1);
    }

}
