package com.nmakademija.nmaakademija.api.listener;

import com.nmakademija.nmaakademija.entity.Academic;

import java.util.ArrayList;

public interface AcademicsLoadedListener {

    void onAcademicsLoaded(ArrayList<Academic> academics);

    void onAcademicsLoadingFailed(Exception exception);

}
