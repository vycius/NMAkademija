package com.nmakademija.nmaakademija.api;


import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nmakademija.nmaakademija.api.listener.AcademicLoadedListener;
import com.nmakademija.nmaakademija.api.listener.AcademicUpdatedListener;
import com.nmakademija.nmaakademija.api.listener.ApiReferenceListener;
import com.nmakademija.nmaakademija.entity.Academic;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FirebaseRealtimeApi {

    public static void getAcademicByEmail(AcademicLoadedListener listener, final String email) {
        FirebaseDatabase.getInstance().getReference("academics").orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(
                        new ApiReferenceListener<Academic, AcademicLoadedListener>(Academic.class,
                                new WeakReference<>(listener)) {

                            @Override
                            public void onFailed(AcademicLoadedListener listener, Exception ex) {
                                listener.onAcademicLoadingFailed(ex);
                            }

                            @Override
                            public void onLoaded(ArrayList<Academic> academics, AcademicLoadedListener listener) {
                                if (academics.size() == 0) {
                                    listener.onAcademicLoadingFailed(new Exception("User doesn't exist"));
                                } else if (academics.size() > 1) {
                                    listener.onAcademicLoadingFailed(new Exception("Email isn't unique!!!"));
                                } else {
                                    listener.onAcademicLoaded(academics.get(0));
                                }
                            }

                            @Override
                            public void onUpdated(ArrayList<Academic> items, AcademicLoadedListener listener) {
                            }
                        });
    }

    public static void updateAcademic(@NonNull final Academic academic, @NonNull final AcademicUpdatedListener listener) {
        FirebaseDatabase.getInstance()
                .getReference("academics")
                .child(String.valueOf(academic.getId()))
                .setValue(academic, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            listener.onAcademicUpdated(academic);
                        } else {
                            listener.onAcademicUpdateFailed(databaseError.toException());

                        }
                    }
                });
    }

}
