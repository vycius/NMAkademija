package com.nmakademija.nmaakademija.listener;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.nmakademija.nmaakademija.entity.Section;

import java.util.List;

public class SpinnerListener implements AdapterView.OnItemSelectedListener {

    private SectionSelectedListener sectionSelectedListener;
    private List<Section> sections;

    public SpinnerListener(SectionSelectedListener sectionSelectedListener, List<Section> sections) {
        this.sectionSelectedListener = sectionSelectedListener;
        this.sections = sections;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        if (pos == 0) {
            sectionSelectedListener.onSectionSelected(null);
        } else {
            sectionSelectedListener.onSectionSelected(sections.get(pos - 1));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface SectionSelectedListener {
        void onSectionSelected(@Nullable Section section);
    }
}
