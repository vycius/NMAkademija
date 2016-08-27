package com.nmakademija.nmaakademija.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.ScheduleDayBanner;
import com.nmakademija.nmaakademija.entity.ScheduleEvent;
import com.nmakademija.nmaakademija.entity.ScheduleItem;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {
    private Context context;
    private List<ScheduleItem> events;

    public ScheduleAdapter(Context context, List<ScheduleItem> events) {
        this.events = events;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.schedule_event, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ScheduleItem scheduleItem = events.get(position);
        int isGone = View.VISIBLE;
        holder.event.setBackgroundColor(Color.WHITE);
        if (scheduleItem instanceof ScheduleEvent) {
            ScheduleEvent scheduleEvent = (ScheduleEvent) scheduleItem;
            holder.startTime.setText(scheduleEvent.getStartTime());
            holder.endTime.setText(scheduleEvent.getEndTime());
            holder.author.setText(scheduleEvent.getLecturerName());
            holder.name.setText(scheduleEvent.getName());
            holder.name.setTextAppearance(context, R.style.Schedule_item_name);
            holder.name.setBackgroundColor(Color.WHITE);
        } else {
            ScheduleDayBanner scheduleDayBanner = (ScheduleDayBanner) scheduleItem;
            holder.name.setTextAppearance(context, R.style.Schedule_day);
            isGone = View.GONE;
            holder.name.setText(scheduleDayBanner.getTime());
            holder.name.setBackgroundResource(R.drawable.schedule_event_date_border);
        }
        holder.endTime.setVisibility(isGone);
        holder.startTime.setVisibility(isGone);
        holder.author.setVisibility(isGone);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView startTime, endTime, name, author;
        public LinearLayout event;

        public MyViewHolder(View view) {
            super(view);
            startTime = (TextView) view.findViewById(R.id.schedule_start_time);
            endTime = (TextView) view.findViewById(R.id.schedule_end_time);
            name = (TextView) view.findViewById(R.id.schedule_name);
            author = (TextView) view.findViewById(R.id.schedule_author);
            event = (LinearLayout) view.findViewById(R.id.schedule_event);
        }
    }
}
