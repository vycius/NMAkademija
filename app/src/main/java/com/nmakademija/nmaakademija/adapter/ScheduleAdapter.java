package com.nmakademija.nmaakademija.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.ScheduleDayBanner;
import com.nmakademija.nmaakademija.entity.ScheduleItem;
import com.nmakademija.nmaakademija.entity.ScheduleEvent;

import java.util.ArrayList;

/**
 * Created by dekedro on 16.8.22.
 */
public class ScheduleAdapter extends ArrayAdapter<ScheduleItem> {

    public ScheduleAdapter(Context context, ArrayList<ScheduleItem> events){
        super(context, R.layout.schedule_event, events);
    }

    @Override
    public boolean isEnabled (int position){
        return false;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        ScheduleItem scheduleItem = getItem (position);

        if (scheduleItem instanceof ScheduleEvent) {
            return getViewScheduleEvent((ScheduleEvent) scheduleItem, convertView, parent);
        }
        if (scheduleItem instanceof ScheduleDayBanner) {
            return getViewScheduleDayBanner((ScheduleDayBanner) scheduleItem, convertView, parent);
        }
        return convertView;
    }

    private View getViewScheduleEvent (ScheduleEvent scheduleEvent, View convertView, ViewGroup parent){
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.schedule_event, parent, false);

        TextView scheduleStartTime = (TextView) convertView.findViewById(R.id.schedule_start_time);
        TextView scheduleEndTime = (TextView) convertView.findViewById(R.id.schedule_end_time);
        TextView scheduleName = (TextView) convertView.findViewById(R.id.schedule_name);
        TextView scheduleLecturers = (TextView) convertView.findViewById(R.id.schedule_author);

        scheduleStartTime.setText(scheduleEvent.getStartTime());
        scheduleEndTime.setText(scheduleEvent.getEndTime());
        scheduleName.setText(scheduleEvent.getName());
        scheduleLecturers.setText(scheduleEvent.getLecturersNames());

        return convertView;
    }

    private View getViewScheduleDayBanner (ScheduleDayBanner scheduleDayBanner, View convertView, ViewGroup parent){
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.schedule_day, parent, false);

        TextView scheduleTime = (TextView) convertView.findViewById(R.id.schedule_time);

        scheduleTime.setText(scheduleDayBanner.getTime());

        return convertView;
    }
}
