package com.nmakademija.nmaakademija.api.listener;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.WorkerThread;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public abstract class ApiReferenceListener<T, L> implements ValueEventListener {

    private final Class<T> clz;
    private final WeakReference<L> listenerWeakReference;
    private boolean firstTime = true;

    protected ApiReferenceListener(Class<T> clz, WeakReference<L> listenerWeakReference) {
        this.listenerWeakReference = listenerWeakReference;
        this.clz = clz;
    }

    @Override
    public void onDataChange(final DataSnapshot dataSnapshot) {
        if (listenerWeakReference.get() != null) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    ArrayList<T> items = new ArrayList<>();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        T item = snapshot.getValue(clz);
                        if (where(item)) {
                            items.add(item);
                        }
                    }

                    final ArrayList<T> orderedItems = order(items);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            L listener = listenerWeakReference.get();

                            if (listener != null) {
                                if (firstTime) {
                                    onLoaded(orderedItems, listener);
                                    firstTime = false;
                                } else {
                                    onUpdated(orderedItems, listener);
                                }
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        handleException(databaseError.toException());
    }

    private void handleException(Exception ex) {
        FirebaseCrash.report(ex);

        L listener = listenerWeakReference.get();
        if (listener != null) {
            onFailed(listener, ex);
        }
    }

    @WorkerThread
    public boolean where(T item) {
        return true;
    }

    @WorkerThread
    public ArrayList<T> order(ArrayList<T> items) {
        return items;
    }


    public abstract void onFailed(L listener, Exception ex);

    public abstract void onLoaded(ArrayList<T> items, L listener);

    public abstract void onUpdated(ArrayList<T> items, L listener);

}
