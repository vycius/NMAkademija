package com.nmakademija.nmaakademija.api.listener;

import com.nmakademija.nmaakademija.entity.User;

import java.util.ArrayList;

public interface AcademicsLoadedListener {

    void onAcademicsLoaded(ArrayList<User> academics);

    void onAcademicsLoadingFailed(Exception exception);

}
