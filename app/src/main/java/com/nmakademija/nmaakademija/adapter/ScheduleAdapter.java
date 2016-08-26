package com.nmakademija.nmaakademija.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.ScheduleDayBanner;
import com.nmakademija.nmaakademija.entity.ScheduleEvent;
import com.nmakademija.nmaakademija.entity.ScheduleItem;

import java.util.List;

/**
 * Created by dekedro on 16.8.22.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView startTime, endTime, name, author;

        public MyViewHolder(View view) {
            super(view);
            startTime = (TextView) view.findViewById(R.id.schedule_start_time);
            endTime = (TextView) view.findViewById(R.id.schedule_end_time);
            name = (TextView) view.findViewById(R.id.schedule_name);
            author = (TextView) view.findViewById(R.id.schedule_author);
        }
    }

    private List<ScheduleEvent> _events;
    private Context _context;

    public ScheduleAdapter(Context context, List<ScheduleEvent> events) {
        _events = events;
        _context = context;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        ScheduleItem scheduleItem = _events.get(position);

        if (scheduleItem instanceof ScheduleEvent) {
            return getViewScheduleEvent((ScheduleEvent) scheduleItem, convertView, parent);
        }
        if (scheduleItem instanceof ScheduleDayBanner) {
            return getViewScheduleDayBanner((ScheduleDayBanner) scheduleItem, convertView, parent);
        }
        return convertView;
    }

    private View getViewScheduleEvent (ScheduleEvent scheduleEvent, View convertView, ViewGroup parent){
        convertView = LayoutInflater.from(_context).inflate(R.layout.schedule_event, parent, false);

        TextView scheduleStartTime = (TextView) convertView.findViewById(R.id.schedule_start_time);
        TextView scheduleEndTime = (TextView) convertView.findViewById(R.id.schedule_end_time);
        TextView scheduleName = (TextView) convertView.findViewById(R.id.schedule_name);
        TextView scheduleLecturers = (TextView) convertView.findViewById(R.id.schedule_author);

        scheduleStartTime.setText(scheduleEvent.getStartTime());
        scheduleEndTime.setText(scheduleEvent.getEndTime());
        scheduleName.setText(scheduleEvent.getName());
        scheduleLecturers.setText(scheduleEvent.getLecturerName());

        return convertView;
    }

    private View getViewScheduleDayBanner (ScheduleDayBanner scheduleDayBanner, View convertView, ViewGroup parent){
        convertView = LayoutInflater.from(_context).inflate(R.layout.schedule_day, parent, false);

        TextView scheduleTime = (TextView) convertView.findViewById(R.id.schedule_time);

        scheduleTime.setText(scheduleDayBanner.getTime());

        return convertView;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_event, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ScheduleEvent scheduleEvent = _events.get(position);
        holder.startTime.setText(scheduleEvent.getStartTime());
        holder.endTime.setText(scheduleEvent.getEndTime());
        holder.author.setText(scheduleEvent.getLecturerName());
        holder.name.setText(scheduleEvent.getName());
    }

    @Override
    public int getItemCount() {
        return _events.size();
    }
}
