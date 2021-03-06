package com.nmakademija.nmaakademija.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nmakademija.nmaakademija.ArticleActivity;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.ArticlesAdapter;
import com.nmakademija.nmaakademija.api.controllers.ArticlesController;
import com.nmakademija.nmaakademija.api.controllers.SchedulesController;
import com.nmakademija.nmaakademija.api.listener.ArticlesLoadedListener;
import com.nmakademija.nmaakademija.api.listener.SchedulesLoadedListener;
import com.nmakademija.nmaakademija.entity.Article;
import com.nmakademija.nmaakademija.entity.ScheduleEvent;
import com.nmakademija.nmaakademija.listener.ClickListener;
import com.nmakademija.nmaakademija.listener.RecyclerTouchListener;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.NMAPreferences;

import java.util.ArrayList;
import java.util.Date;

public class NewsFragment extends BaseSceeenFragment implements ArticlesLoadedListener, SchedulesLoadedListener {

    private CountDownTimer countDownTimer;
    private ArrayList<ScheduleEvent> scheduleEvents;
    public ArticlesAdapter articlesAdapter;

    public static NewsFragment getInstance() {
        return new NewsFragment();
    }

    private AppEvent appEvent;
    private RecyclerView articlesRecyclerView;
    private TextView nowHappens;
    private TextView nextActivity;
    private View loadingView;
    private View content;
    private ArrayList<Article> articles;
    private int schedulePosition = 0;

    private ArticlesController articlesController;
    private SchedulesController schedulesController;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        articlesRecyclerView = view.findViewById(R.id.recyclerView);
        nowHappens = view.findViewById(R.id.now_happens);
        nextActivity = view.findViewById(R.id.next_activity);
        loadingView = view.findViewById(R.id.loading_view);
        content = view.findViewById(R.id.content);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        appEvent = AppEvent.getInstance(getContext());
        appEvent.trackCurrentScreen(getActivity(), "open_news");

        articlesController = new ArticlesController(this);
        schedulesController = new SchedulesController(this, NMAPreferences.getSection(getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();

        articlesController.attach();
        schedulesController.attach();
        showLoading();
    }

    private void hideLoading() {
        loadingView.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
    }


    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
    }

    @Override
    public void onArticlesLoaded(ArrayList<Article> articles) {
        if (isAdded()) {
            this.articles = articles;
            setArticlesView();

            onLoaded();
        }
    }

    private void onLoaded() {
        if (scheduleEvents != null && articles != null) {
            hideLoading();
        }
    }

    private void loadingFailed() {
        if (isAdded()) {
            hideLoading();

            //noinspection ConstantConditions
            Snackbar.make(getView(), R.string.get_request_failed, Snackbar.LENGTH_INDEFINITE).show();
        }
    }

    @Override
    public void onArticlesLoadingFailed(Exception exception) {
        loadingFailed();
    }

    @Override
    public void onArticlesUpdatedLoaded(ArrayList<Article> articles) {
        if (isAdded()) {
            articlesAdapter.articlesList = articles;
            articlesAdapter.notifyDataSetChanged();

            onLoaded();
        }
    }

    private void updateTime(long timeLeft) {
        if (isAdded()) {
            long minutesLeft = 0;
            if (timeLeft % (60000) > 0) {
                minutesLeft = 1;
            }
            timeLeft /= 60 * 1000;
            minutesLeft += timeLeft % 60;
            timeLeft /= 60;
            long hoursLeft = timeLeft % 24;
            timeLeft /= 24;

            if (timeLeft > 0) {
                nextActivity.setText(getString(R.string.after_days, timeLeft, hoursLeft, scheduleEvents.get(schedulePosition + 1).getName()));
            } else {
                nextActivity.setText(getString(R.string.after_hours, hoursLeft, minutesLeft, scheduleEvents.get(schedulePosition + 1).getName()));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        updateScheduleView();
    }

    @Override
    public void onPause() {
        stopTimer();

        super.onPause();
    }

    private void updateScheduleView() {
        stopTimer();

        if (scheduleEvents == null)
            return;

        Date now = new Date();

        schedulePosition = Math.max(schedulePosition, 0);
        while (schedulePosition < scheduleEvents.size() &&
                scheduleEvents.get(schedulePosition).getEndDate().before(now)) {
            schedulePosition++;
        }

        boolean showNow = true;

        if (schedulePosition >= 0 && schedulePosition < scheduleEvents.size() &&
                scheduleEvents.get(schedulePosition).getStartDate().after(now)) {
            schedulePosition--;
            showNow = false;
        }

        if (showNow && schedulePosition < scheduleEvents.size()) {
            nowHappens.setText(getString(R.string.now_happens, scheduleEvents.get(schedulePosition).getName()));
            nowHappens.setVisibility(View.VISIBLE);
        } else {
            nowHappens.setVisibility(View.GONE);
        }

        if (schedulePosition + 1 >= 0 && schedulePosition + 1 < scheduleEvents.size()) {
            long until = scheduleEvents.get(schedulePosition + 1).getStartDate().getTime();

            countDownTimer = new CountDownTimer(
                    until - now.getTime(), 30 * 1000) {
                public void onTick(long millisUntilFinished) {
                    updateTime(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    updateScheduleView();
                }
            };

            updateTime(until - now.getTime());
            countDownTimer.start();
            nextActivity.setVisibility(View.VISIBLE);
        } else {
            nextActivity.setVisibility(View.GONE);
        }

        onLoaded();
    }

    private void setArticlesView() {
        articlesRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutCompat.VERTICAL));
        articlesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        articlesAdapter = new ArticlesAdapter(articles);
        articlesAdapter.setHasStableIds(true);
        articlesRecyclerView.setAdapter(articlesAdapter);
        articlesRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(
                getContext(), articlesRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Context context = view.getContext();
                Intent intent = new Intent(context, ArticleActivity.class);
                Article article = ((ArticlesAdapter) articlesRecyclerView.getAdapter()).getArticle(position);
                appEvent.trackArticleClicked(article.getId());
                intent.putExtra(ArticleActivity.EXTRA_ARTICLE, article);
                context.startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                this.onClick(view, position);
            }
        }));
    }

    @Override
    public void onSchedulesLoaded(ArrayList<ScheduleEvent> scheduleEvents) {
        if (isAdded()) {
            this.scheduleEvents = scheduleEvents;
            schedulePosition = 0;

            updateScheduleView();

            onLoaded();
        }
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onSchedulesLoadingFailed(Exception exception) {
        loadingFailed();
    }

    @Override
    public void onSchedulesUpdated(ArrayList<ScheduleEvent> scheduleEvents) {
        if (isAdded()) {
            onSchedulesLoaded(scheduleEvents);

            onLoaded();
        }
    }
}
