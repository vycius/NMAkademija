package com.nmakademija.nmaakademija.api.listener;

import android.support.annotation.NonNull;

import com.nmakademija.nmaakademija.entity.Academic;

public interface AcademicUpdatedListener {

    void onAcademicUpdated(@NonNull Academic academic);

    void onAcademicUpdateFailed(Exception exception);

}
