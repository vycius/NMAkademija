package com.nmakademija.nmaakademija.api.controllers;


import com.google.firebase.database.FirebaseDatabase;
import com.nmakademija.nmaakademija.api.listener.ApiReferenceListener;
import com.nmakademija.nmaakademija.api.listener.SectionsLoadedListener;
import com.nmakademija.nmaakademija.entity.Section;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SectionsController extends FirebaseController {
    private WeakReference<SectionsLoadedListener> listener;

    public SectionsController(SectionsLoadedListener listener) {
        this.listener = new WeakReference<>(listener);
        databaseReference = FirebaseDatabase.getInstance().getReference("sections");
    }

    public void onCreate() {
        eventListener = databaseReference.addValueEventListener(
                new ApiReferenceListener<Section, SectionsLoadedListener>(Section.class,
                        listener) {

                    @Override
                    public void onFailed(SectionsLoadedListener listener, Exception ex) {
                        listener.onSectionsLoadingFailed(ex);
                    }

                    @Override
                    public void onLoaded(ArrayList<Section> items, SectionsLoadedListener listener) {
                        listener.onSectionsLoaded(items);
                    }

                    @Override
                    public void onUpdated(ArrayList<Section> items, SectionsLoadedListener listener) {
                        listener.onSectionsUpdated(items);
                    }

                    @Override
                    public ArrayList<Section> order(ArrayList<Section> items) {
                        return items;
                    }
                });
    }

}
