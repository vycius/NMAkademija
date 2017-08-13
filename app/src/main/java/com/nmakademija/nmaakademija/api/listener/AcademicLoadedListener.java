package com.nmakademija.nmaakademija.api.listener;

import com.nmakademija.nmaakademija.entity.Academic;

public interface AcademicLoadedListener {

    void onAcademicLoaded(Academic academic);

    void onAcademicLoadingFailed(Exception exception);

}
