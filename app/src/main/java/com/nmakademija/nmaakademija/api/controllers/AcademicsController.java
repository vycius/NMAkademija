package com.nmakademija.nmaakademija.api.controllers;


import com.google.firebase.database.FirebaseDatabase;
import com.nmakademija.nmaakademija.api.listener.AcademicsLoadedListener;
import com.nmakademija.nmaakademija.api.listener.ApiReferenceListener;
import com.nmakademija.nmaakademija.entity.Academic;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

public class AcademicsController extends FirebaseController {
    private WeakReference<AcademicsLoadedListener> listener;

    public AcademicsController(AcademicsLoadedListener listener) {
        this.listener = new WeakReference<>(listener);
        databaseReference = FirebaseDatabase.getInstance().getReference("academics");
    }

    public void onCreate() {
        eventListener = databaseReference.addValueEventListener(
                new ApiReferenceListener<Academic, AcademicsLoadedListener>(Academic.class,
                        listener) {

                    @Override
                    public void onFailed(AcademicsLoadedListener listener, Exception ex) {
                        listener.onAcademicsLoadingFailed(ex);
                    }

                    @Override
                    public void onLoaded(ArrayList<Academic> items, AcademicsLoadedListener listener) {
                        listener.onAcademicsLoaded(items);
                    }

                    @Override
                    public void onUpdated(ArrayList<Academic> items, AcademicsLoadedListener listener) {
                        listener.onAcademicsUpdated(items);
                    }

                    @Override
                    public ArrayList<Academic> order(ArrayList<Academic> items) {
                        Collections.sort(items);
                        return items;
                    }
                });
    }

}
