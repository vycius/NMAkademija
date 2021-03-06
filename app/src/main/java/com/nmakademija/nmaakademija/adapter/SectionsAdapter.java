package com.nmakademija.nmaakademija.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.Section;

import java.util.List;

public class SectionsAdapter extends RecyclerView.Adapter<SectionsAdapter.SectionViewHolder> {
    private List<Section> sections;

    public SectionsAdapter(List<Section> articlesList) {
        this.sections = articlesList;
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_section, parent, false);
        return new SectionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        final Section section = sections.get(position);
        holder.title.setText(section.getName());
    }

    @Override
    public long getItemId(int position) {
        return sections.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    public Section getSection(int position) {
        return sections.get(position);
    }

    class SectionViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        SectionViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.section_name);
        }
    }
}
