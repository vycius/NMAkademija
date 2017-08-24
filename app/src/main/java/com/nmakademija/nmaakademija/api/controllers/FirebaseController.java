package com.nmakademija.nmaakademija.api.controllers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public abstract class FirebaseController {
    ValueEventListener eventListener;
    DatabaseReference databaseReference;

    public abstract void onCreate();

    public void onDestroy() {
        if (eventListener != null) {
            databaseReference.removeEventListener(eventListener);
        }
    }
}
