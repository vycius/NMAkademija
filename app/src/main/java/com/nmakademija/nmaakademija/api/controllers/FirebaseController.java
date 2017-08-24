package com.nmakademija.nmaakademija.api.controllers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public abstract class FirebaseController {
    ValueEventListener eventListener;
    DatabaseReference databaseReference;

    public void attach() {
        eventListener = databaseReference.addValueEventListener(eventListener);
    }

    public void remove() {
        if (eventListener != null) {
            databaseReference.removeEventListener(eventListener);
        }
    }
}
