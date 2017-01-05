package com.nmakademija.nmaakademija.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.ScheduleEvent;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private Context context;
    private List<ScheduleEvent> events;

    public ScheduleAdapter(Context context, List<ScheduleEvent> events) {
        this.events = events;
        this.context = context;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_item_schedule_event, parent, false);
        return new ScheduleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        holder.bindData(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private TextView startTime, endTime, name, author;

        ScheduleViewHolder(View view) {
            super(view);
            startTime = (TextView) view.findViewById(R.id.schedule_start_time);
            endTime = (TextView) view.findViewById(R.id.schedule_end_time);
            name = (TextView) view.findViewById(R.id.schedule_name);
            author = (TextView) view.findViewById(R.id.schedule_author);
        }

        void bindData(ScheduleEvent scheduleEvent) {
            startTime.setText(scheduleEvent.getStartTime());
            endTime.setText(scheduleEvent.getEndTime());
            author.setText(scheduleEvent.getLecturerName());
            name.setText(scheduleEvent.getName());
        }
    }
}
