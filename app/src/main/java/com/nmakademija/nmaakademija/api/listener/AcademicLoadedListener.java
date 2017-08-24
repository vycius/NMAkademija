package com.nmakademija.nmaakademija.api.listener;

import com.nmakademija.nmaakademija.entity.Academic;

public interface AcademicLoadedListener {

    void onAcademicLoaded(Academic item);

    void onAcademicLoadingFailed(Exception exception);

}
