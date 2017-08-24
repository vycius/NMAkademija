package com.nmakademija.nmaakademija.api;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nmakademija.nmaakademija.api.listener.AcademicLoadedListener;
import com.nmakademija.nmaakademija.api.listener.AcademicUpdatedListener;
import com.nmakademija.nmaakademija.api.listener.AcademicsLoadedListener;
import com.nmakademija.nmaakademija.api.listener.ApiReferenceListener;
import com.nmakademija.nmaakademija.api.listener.ArticleCreatedListener;
import com.nmakademija.nmaakademija.api.listener.ArticlesLoadedListener;
import com.nmakademija.nmaakademija.api.listener.SchedulesLoadedListener;
import com.nmakademija.nmaakademija.api.listener.SectionsLoadedListener;
import com.nmakademija.nmaakademija.entity.Academic;
import com.nmakademija.nmaakademija.entity.Article;
import com.nmakademija.nmaakademija.entity.ScheduleEvent;
import com.nmakademija.nmaakademija.entity.Section;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
                                return scheduleEvent.getSection() == -1 ||
                                        scheduleEvent.getSection() == sectionId;
                            }

                            @Override
                            public ArrayList<ScheduleEvent> order(ArrayList<ScheduleEvent> scheduleEvents) {
                                Collections.sort(scheduleEvents, new Comparator<ScheduleEvent>() {
                                    @Override
                                    public int compare(ScheduleEvent e1, ScheduleEvent e2) {
                                        return e1.getStartDate().compareTo(e2.getStartDate());
                                    }
                                });

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

    public static void addArticle(@NonNull final Article article, @NonNull final ArticleCreatedListener listener) {
        FirebaseDatabase.getInstance()
                .getReference("articles")
                .push().setValue(article, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null) {
                            listener.onArticleCreated(article);
                        } else {
                            listener.onArticleCreateFailed(databaseError.toException());
                        }
                    }
                });
    }

}
