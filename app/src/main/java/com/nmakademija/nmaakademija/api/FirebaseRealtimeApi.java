package com.nmakademija.nmaakademija.api;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nmakademija.nmaakademija.api.listener.AcademicUpdatedListener;
import com.nmakademija.nmaakademija.api.listener.AcademicsLoadedListener;
import com.nmakademija.nmaakademija.api.listener.ApiReferenceListener;
import com.nmakademija.nmaakademija.api.listener.ArticlesLoadedListener;
import com.nmakademija.nmaakademija.api.listener.SchedulesLoadedListener;
import com.nmakademija.nmaakademija.api.listener.SectionsLoadedListener;
import com.nmakademija.nmaakademija.api.listener.TimeUntilSessionLoadingListener;
import com.nmakademija.nmaakademija.entity.Academic;
import com.nmakademija.nmaakademija.entity.Article;
import com.nmakademija.nmaakademija.entity.ScheduleEvent;
import com.nmakademija.nmaakademija.entity.Section;
import com.nmakademija.nmaakademija.entity.TimeUntilSession;
import com.nmakademija.nmaakademija.utils.ScheduleEventComparator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

public class FirebaseRealtimeApi {


    public static void getAcademicByEmail(AcademicsLoadedListener listener, final String email) {
        FirebaseDatabase.getInstance().getReference("academics").orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(
                        new ApiReferenceListener<Academic, AcademicsLoadedListener>(Academic.class,
                                new WeakReference<>(listener)) {

                            @Override
                            public void onFailed(AcademicsLoadedListener listener, Exception ex) {
                                listener.onAcademicsLoadingFailed(ex);
                            }

                            @Override
                            public void onLoaded(ArrayList<Academic> academics, AcademicsLoadedListener listener) {
                                listener.onAcademicsLoaded(academics);
                            }
                        });
    }

    public static void getAcademics(AcademicsLoadedListener listener, @Nullable final Integer sectionId) {
        FirebaseDatabase.getInstance().getReference("academics")
                .addListenerForSingleValueEvent(
                        new ApiReferenceListener<Academic, AcademicsLoadedListener>(Academic.class,
                                new WeakReference<>(listener)) {

                            @Override
                            public void onFailed(AcademicsLoadedListener listener, Exception ex) {
                                listener.onAcademicsLoadingFailed(ex);
                            }

                            @Override
                            public void onLoaded(ArrayList<Academic> academics, AcademicsLoadedListener listener) {
                                listener.onAcademicsLoaded(academics);
                            }

                            @Override
                            public boolean where(Academic academic) {
                                return sectionId == null || sectionId.equals(academic.getSection());
                            }

                            @Override
                            public ArrayList<Academic> order(ArrayList<Academic> academics) {
                                Collections.sort(academics);
                                return academics;
                            }
                        });
    }

    public static void getSections(SectionsLoadedListener listener) {
        FirebaseDatabase.getInstance().getReference("sections")
                .addListenerForSingleValueEvent(
                        new ApiReferenceListener<Section, SectionsLoadedListener>(Section.class,
                                new WeakReference<>(listener)) {

                            @Override
                            public void onFailed(SectionsLoadedListener listener, Exception ex) {
                                listener.onSectionsLoadingFailed(ex);

                            }

                            @Override
                            public void onLoaded(ArrayList<Section> sections, SectionsLoadedListener listener) {
                                listener.onSectionsLoaded(sections);
                            }
                        });
    }

    public static void getSchedules(SchedulesLoadedListener listener, final int sectionId) {
        FirebaseDatabase.getInstance().getReference("schedules")
                .addListenerForSingleValueEvent(
                        new ApiReferenceListener<ScheduleEvent, SchedulesLoadedListener>(
                                ScheduleEvent.class, new WeakReference<>(listener)) {
                            @Override
                            public void onFailed(SchedulesLoadedListener listener, Exception ex) {
                                listener.onSchedulesLoadingFailed(ex);
                            }

                            @Override
                            public void onLoaded(ArrayList<ScheduleEvent> scheduleEvents,
                                                 SchedulesLoadedListener listener) {
                                listener.onSchedulesLoaded(scheduleEvents);
                            }

                            @Override
                            public boolean where(ScheduleEvent scheduleEvent) {
                                return scheduleEvent.getSectionId() == -1 ||
                                        scheduleEvent.getSectionId() == sectionId;
                            }

                            @Override
                            public ArrayList<ScheduleEvent> order(ArrayList<ScheduleEvent> scheduleEvents) {
                                Collections.sort(scheduleEvents, new ScheduleEventComparator());

                                return scheduleEvents;
                            }
                        });
    }

    public static void getArticles(ArticlesLoadedListener listener) {
        FirebaseDatabase.getInstance().getReference("articles")
                .addListenerForSingleValueEvent(
                        new ApiReferenceListener<Article, ArticlesLoadedListener>(Article.class,
                                new WeakReference<>(listener)) {

                            @Override
                            public void onFailed(ArticlesLoadedListener listener, Exception ex) {
                                listener.onArticlesLoadingFailed(ex);
                            }

                            @Override
                            public void onLoaded(ArrayList<Article> articles, ArticlesLoadedListener listener) {
                                listener.onArticlesLoaded(articles);
                            }

                            @Override
                            public ArrayList<Article> order(ArrayList<Article> articles) {
                                Collections.sort(articles);
                                return articles;
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

    public static void getTimeUntillSession(TimeUntilSessionLoadingListener listener) {
        final WeakReference<TimeUntilSessionLoadingListener> loadedListener =
                new WeakReference<>(listener);

        FirebaseDatabase.getInstance().getReference("tts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        TimeUntilSession untilSession = dataSnapshot.getValue(TimeUntilSession.class);

                        TimeUntilSessionLoadingListener apiLoadedListener = loadedListener.get();
                        if (apiLoadedListener != null) {

                            apiLoadedListener.onTimeUntilSessionLoaded(untilSession);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        TimeUntilSessionLoadingListener apiLoadedListener = loadedListener.get();
                        if (apiLoadedListener != null) {
                            apiLoadedListener.onTimeUntilSessionLoadingFailed(
                                    databaseError.toException());
                        }
                    }
                });
    }

}
