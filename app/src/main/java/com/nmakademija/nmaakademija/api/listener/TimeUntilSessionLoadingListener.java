package com.nmakademija.nmaakademija.api.listener;

import com.nmakademija.nmaakademija.entity.TimeUntilSession;

public interface TimeUntilSessionLoadingListener {

    void onTimeUntilSessionLoaded(TimeUntilSession timeUntilSession);

    void onTimeUntilSessionLoadingFailed(Exception exception);

}
