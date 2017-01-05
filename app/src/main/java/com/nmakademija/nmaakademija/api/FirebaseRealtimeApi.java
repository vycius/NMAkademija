package com.nmakademija.nmaakademija.api;


import com.google.firebase.database.FirebaseDatabase;
import com.nmakademija.nmaakademija.api.listener.AcademicsLoadedListener;
import com.nmakademija.nmaakademija.api.listener.ApiReferenceListener;
import com.nmakademija.nmaakademija.api.listener.SchedulesLoadedListener;
import com.nmakademija.nmaakademija.api.listener.SectionsLoadedListener;
import com.nmakademija.nmaakademija.entity.Academic;
import com.nmakademija.nmaakademija.entity.ScheduleEvent;
import com.nmakademija.nmaakademija.entity.Section;
import com.nmakademija.nmaakademija.utils.ScheduleEventComparator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

public class FirebaseRealtimeApi {


    public static void getAllAcademics(AcademicsLoadedListener listener) {
        final WeakReference<AcademicsLoadedListener> loadedListener =
                new WeakReference<>(listener);

        FirebaseDatabase.getInstance().getReference("academics")
                .addListenerForSingleValueEvent(
                        new ApiReferenceListener<Academic>(Academic.class) {

                            @Override
                            public void onLoaded(ArrayList<Academic> academics) {
                                AcademicsLoadedListener apiLoadedListener = loadedListener.get();
                                if (apiLoadedListener != null) {
                                    apiLoadedListener.onAcademicsLoaded(academics);
                                }
                            }

                            @Override
                            public void onFailed(Exception ex) {
                                AcademicsLoadedListener apiLoadedListener = loadedListener.get();
                                if (apiLoadedListener != null) {
                                    apiLoadedListener.onAcademicsLoadingFailed(ex);
                                }
                            }

                        });
    }

    public static void getSectionAcademics(AcademicsLoadedListener listener, final int sectionId) {
        final WeakReference<AcademicsLoadedListener> loadedListener =
                new WeakReference<>(listener);

        getAllAcademics(new AcademicsLoadedListener() {
            @Override
            public void onAcademicsLoaded(ArrayList<Academic> academics) {
                AcademicsLoadedListener apiLoadedListener = loadedListener.get();

                if (apiLoadedListener != null) {
                    ArrayList<Academic> filteredAcademics = new ArrayList<>();

                    for (Academic academic : academics) {
                        if (academic.getSection() == sectionId) {
                            filteredAcademics.add(academic);
                        }
                    }

                    apiLoadedListener.onAcademicsLoaded(filteredAcademics);
                }
            }

            @Override
            public void onAcademicsLoadingFailed(Exception exception) {
                AcademicsLoadedListener apiLoadedListener = loadedListener.get();
                if (apiLoadedListener != null) {
                    apiLoadedListener.onAcademicsLoadingFailed(exception);
                }
            }
        });
    }

    public static void getSections(SectionsLoadedListener listener) {
        final WeakReference<SectionsLoadedListener> loadedListener =
                new WeakReference<>(listener);

        FirebaseDatabase.getInstance().getReference("sections")
                .addListenerForSingleValueEvent(
                        new ApiReferenceListener<Section>(Section.class) {

                            @Override
                            public void onLoaded(ArrayList<Section> sections) {
                                SectionsLoadedListener apiLoadedListener = loadedListener.get();
                                if (apiLoadedListener != null) {
                                    apiLoadedListener.onSectionsLoaded(sections);
                                }
                            }

                            @Override
                            public void onFailed(Exception ex) {
                                SectionsLoadedListener apiLoadedListener = loadedListener.get();
                                if (apiLoadedListener != null) {
                                    apiLoadedListener.onSectionsLoadingFailed(ex);
                                }
                            }

                        });
    }

    public static void getSchedules(SchedulesLoadedListener listener, final int sectionId) {
        final WeakReference<SchedulesLoadedListener> loadedListener =
                new WeakReference<>(listener);

        FirebaseDatabase.getInstance().getReference("schedules")
                .addListenerForSingleValueEvent(
                        new ApiReferenceListener<ScheduleEvent>(ScheduleEvent.class) {

                            @Override
                            public void onLoaded(ArrayList<ScheduleEvent> scheduleEvents) {
                                SchedulesLoadedListener apiLoadedListener = loadedListener.get();
                                if (apiLoadedListener != null) {
                                    ArrayList<ScheduleEvent> filteredSchedules = new ArrayList<>();

                                    for (ScheduleEvent scheduleEvent : scheduleEvents) {
                                        if (scheduleEvent.getSectionId() == 0 ||
                                                scheduleEvent.getSectionId() == sectionId) {
                                            filteredSchedules.add(scheduleEvent);
                                        }
                                    }
                                    Collections.sort(filteredSchedules, new ScheduleEventComparator());

                                    apiLoadedListener.onSchedulesLoaded(filteredSchedules);
                                }
                            }

                            @Override
                            public void onFailed(Exception ex) {
                                SchedulesLoadedListener apiLoadedListener = loadedListener.get();
                                if (apiLoadedListener != null) {
                                    apiLoadedListener.onSchedulesLoadingFailed(ex);
                                }
                            }

                        });
    }

}
