package com.nmakademija.nmaakademija.api;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FirebaseRealtimeAPI {

    public static DatabaseReference getAllAcademics() {
        return FirebaseDatabase.getInstance().getReference("academics");
    }

    public static Query getSectionAcademics(int sectionId) {
        return getAllAcademics();
    }

    public static DatabaseReference getSections() {
        return FirebaseDatabase.getInstance().getReference("sections");
    }
}
