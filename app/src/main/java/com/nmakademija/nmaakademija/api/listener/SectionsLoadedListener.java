package com.nmakademija.nmaakademija.api.listener;

import com.nmakademija.nmaakademija.entity.Section;

import java.util.ArrayList;

public interface SectionsLoadedListener {

    void onSectionsLoaded(ArrayList<Section> sections);

    void onSectionsLoadingFailed(Exception exception);

    void onSectionsUpdated(ArrayList<Section> sections);

}
