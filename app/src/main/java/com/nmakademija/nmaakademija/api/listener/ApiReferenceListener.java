package com.nmakademija.nmaakademija.api.listener;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public abstract class ApiReferenceListener<T> implements ValueEventListener {

    private Class<T> clz;

    protected ApiReferenceListener(Class<T> clz) {
        this.clz = clz;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        try {
            ArrayList<T> items = new ArrayList<>();

            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                T item = snapshot.getValue(clz);
                items.add(item);
            }

            onLoaded(items);
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        handleException(databaseError.toException());
    }

    private void handleException(Exception ex) {
        FirebaseCrash.report(ex);

        onFailed(ex);
    }


    public abstract void onFailed(Exception ex);

    public abstract void onLoaded(ArrayList<T> items);

}
