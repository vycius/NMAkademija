package com.nmakademija.nmaakademija.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nmakademija.nmaakademija.R;

import java.util.Arrays;
import java.util.Comparator;

public class ScheduleSectionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private RecyclerView.Adapter recyclerViewAdapter;
    private SparseArray<Section> sections = new SparseArray<>();
    private boolean valid = true;

    public ScheduleSectionsAdapter(Context context, RecyclerView.Adapter adapter) {
        this.recyclerViewAdapter = adapter;
        this.context = context;
        recyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                valid = recyclerViewAdapter.getItemCount() > 0;
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                valid = recyclerViewAdapter.getItemCount() > 0;
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                valid = recyclerViewAdapter.getItemCount() > 0;
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                valid = recyclerViewAdapter.getItemCount() > 0;
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            final View view = LayoutInflater.from(context).inflate(R.layout.schedule_day, parent, false);
            return new ScheduleSectionViewHolder(view);
        } else {
            return recyclerViewAdapter.onCreateViewHolder(parent, viewType - 1);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isSectionHeaderPosition(position))
            ((ScheduleSectionViewHolder) holder).title.setText(sections.get(position).title);
        else recyclerViewAdapter.onBindViewHolder(holder, sectionedPositionToPosition(position));
    }

    @Override
    public int getItemViewType(int position) {
        return isSectionHeaderPosition(position)
                ? 0
                : recyclerViewAdapter.getItemViewType(sectionedPositionToPosition(position)) + 1;
    }

    public void setSections(Section[] sectionsarray) {
        sections.clear();

        Arrays.sort(sectionsarray, new Comparator<Section>() {
            @Override
            public int compare(Section o, Section o1) {
                return (o.firstPosition == o1.firstPosition)
                        ? 0
                        : ((o.firstPosition < o1.firstPosition) ? -1 : 1);
            }
        });

        int offset = 0;
        for (Section section : sectionsarray) {
            section.sectionedPosition = section.firstPosition + offset;
            sections.append(section.sectionedPosition, section);
            ++offset;
        }

        notifyDataSetChanged();
    }

    public int positionToSectionedPosition(int position) {
        int offset = 0;
        for (int i = 0; i < sections.size(); i++) {
            if (sections.valueAt(i).firstPosition > position) {
                break;
            }
            ++offset;
        }
        return position + offset;
    }

    public int sectionedPositionToPosition(int sectionedPosition) {
        if (isSectionHeaderPosition(sectionedPosition)) {
            return RecyclerView.NO_POSITION;
        }

        int offset = 0;
        for (int i = 0; i < sections.size(); i++) {
            if (sections.valueAt(i).sectionedPosition > sectionedPosition) {
                break;
            }
            --offset;
        }
        return sectionedPosition + offset;
    }

    public boolean isSectionHeaderPosition(int position) {
        return sections.get(position) != null;
    }

    @Override
    public long getItemId(int position) {
        return isSectionHeaderPosition(position)
                ? Integer.MAX_VALUE - sections.indexOfKey(position)
                : recyclerViewAdapter.getItemId(sectionedPositionToPosition(position));
    }

    @Override
    public int getItemCount() {
        return (valid ? recyclerViewAdapter.getItemCount() + sections.size() : 0);
    }

    public static class Section {
        int firstPosition;
        int sectionedPosition;
        CharSequence title;

        public Section(int firstPosition, CharSequence title) {
            this.firstPosition = firstPosition;
            this.title = title;
        }

        public CharSequence getTitle() {
            return title;
        }
    }

    public static class ScheduleSectionViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        public ScheduleSectionViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.schedule_day);
        }
    }
}
