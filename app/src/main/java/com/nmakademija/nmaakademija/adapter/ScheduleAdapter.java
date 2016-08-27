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

    private Context context;
    private List<ScheduleItem> events;

    public ScheduleAdapter(Context context, List<ScheduleItem> events) {
        this.events = events;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_event, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ScheduleItem scheduleItem = events.get(position);
        if (scheduleItem != null) {
            if (scheduleItem instanceof ScheduleEvent) {
                ScheduleEvent scheduleEvent = (ScheduleEvent) scheduleItem;
                holder.startTime.setText(scheduleEvent.getStartTime());
                holder.endTime.setText(scheduleEvent.getEndTime());
                holder.author.setText(scheduleEvent.getLecturerName());
                holder.name.setText(scheduleEvent.getName());
                holder.endTime.setVisibility(View.VISIBLE);
                holder.startTime.setVisibility(View.VISIBLE);
                holder.author.setVisibility(View.VISIBLE);
                holder.name.setTextAppearance(context, R.style.Schedule_item_name);
            } else {
                ScheduleDayBanner scheduleDayBanner = (ScheduleDayBanner) scheduleItem;
                holder.name.setTextAppearance(context, R.style.Schedule_day);
                holder.name.setText(scheduleDayBanner.getTime());
                holder.endTime.setVisibility(View.GONE);
                holder.startTime.setVisibility(View.GONE);
                holder.author.setVisibility(View.GONE);
            }
        } else {
            holder.startTime.setText(((ScheduleEvent) events.get(position - 1)).getEndTime());
            holder.endTime.setText("...");
            holder.author.setText("");
            holder.name.setText(R.string.session_end);
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
