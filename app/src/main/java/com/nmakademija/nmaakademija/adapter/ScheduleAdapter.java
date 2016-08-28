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

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {
    private Context context;
    private List<ScheduleEvent> events;

    public ScheduleAdapter(Context context, List<ScheduleEvent> events) {
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
        final ScheduleEvent scheduleEvent = events.get(position);
        holder.startTime.setText(scheduleEvent.getStartTime());
        holder.endTime.setText(scheduleEvent.getEndTime());
        holder.author.setText(scheduleEvent.getLecturerName());
        holder.name.setText(scheduleEvent.getName());
//        holder.name.setBackgroundColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

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
}
