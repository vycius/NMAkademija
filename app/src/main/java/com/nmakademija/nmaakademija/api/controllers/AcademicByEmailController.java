package com.nmakademija.nmaakademija.api.controllers;


import com.google.firebase.database.FirebaseDatabase;
import com.nmakademija.nmaakademija.api.listener.AcademicLoadedListener;
import com.nmakademija.nmaakademija.api.listener.ApiReferenceListener;
import com.nmakademija.nmaakademija.entity.Academic;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AcademicByEmailController extends FirebaseController {
    private String email;

    public AcademicByEmailController(AcademicLoadedListener listener, String email) {
        WeakReference<AcademicLoadedListener> weakListener = new WeakReference<>(listener);
        this.email = email;
        databaseReference = FirebaseDatabase.getInstance().getReference("academics");

        eventListener = new ApiReferenceListener<Academic, AcademicLoadedListener>(Academic.class,
                weakListener) {

            @Override
            public void onFailed(AcademicLoadedListener listener, Exception ex) {
                listener.onAcademicLoadingFailed(ex);
            }

            @Override
            public void onLoaded(ArrayList<Academic> items, AcademicLoadedListener listener) {
                if (items.size() == 0) {
                    listener.onAcademicLoadingFailed(new Exception("User doesn't exist"));
                } else if (items.size() > 1) {
                    listener.onAcademicLoadingFailed(new Exception("Email isn't unique!!!"));
                } else {
                    listener.onAcademicLoaded(items.get(0));
                }
            }

            @Override
            public void onUpdated(ArrayList<Academic> items, AcademicLoadedListener listener) {
                onLoaded(items, listener);
            }
        };
    }

    @Override
    public void attach() {
        databaseReference.orderByChild("email").equalTo(email)
                .addValueEventListener(eventListener);
    }

}
