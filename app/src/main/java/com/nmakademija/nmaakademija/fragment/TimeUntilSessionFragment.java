package com.nmakademija.nmaakademija.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.api.FirebaseRealtimeApi;
import com.nmakademija.nmaakademija.api.listener.TimeUntilSessionLoadingListener;
import com.nmakademija.nmaakademija.entity.TimeUntilSession;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.Error;

import java.util.Date;

public class TimeUntilSessionFragment extends BaseSceeenFragment implements TimeUntilSessionLoadingListener {

    private CountDownTimer countDownTimer;
    private TextView timeUntilSessionTextTV;
    private TextView timeUntilSessionTimeTV;

    public static TimeUntilSessionFragment getInstance() {
        return new TimeUntilSessionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_until_session, container, false);
        timeUntilSessionTimeTV = (TextView) view.findViewById(R.id.timeUntilSession);
        timeUntilSessionTextTV = (TextView) view.findViewById(R.id.timeUntilSessionText);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppEvent.getInstance(getContext()).trackCurrentScreen(getActivity(), "open_time_until_session");

        loadTimeUntilSession();
    }

    private void loadTimeUntilSession() {
        FirebaseRealtimeApi.getTimeUntillSession(this);
    }

    private void updateTime(long timeLeft) {
        if (isAdded()) {
            timeLeft /= 1000;
            long secondsLeft = timeLeft % 60;
            timeLeft /= 60;
            long minutesLeft = timeLeft % 60;
            timeLeft /= 60;
            long hoursLeft = timeLeft % 24;
            timeLeft /= 24;

            timeUntilSessionTimeTV.setText(getContext().getString(R.string.timer_date_format, timeLeft, hoursLeft, minutesLeft, secondsLeft));
        }
    }

    @Override
    public void onPause() {
        stopTimer();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (countDownTimer != null)
            countDownTimer.start();
    }

    @Override
    public void onTimeUntilSessionLoaded(TimeUntilSession timeUntilSession) {
        if (isAdded()) {
            timeUntilSessionTextTV.setText(
                    timeUntilSession.isSession() ?
                            R.string.timer_session_end : R.string.timer_session_start);

            long until = (timeUntilSession.isSession() ?
                    timeUntilSession.getEndTime()
                    : timeUntilSession.getStartTime()).getTime();

            final long now = new Date().getTime();
            if (until < now) {
                timeUntilSessionTimeTV.setText(R.string.session_ended);
                timeUntilSessionTextTV.setVisibility(View.GONE);
                return;
            }
            stopTimer();

            countDownTimer = new CountDownTimer(
                    until - now, 1000) {
                public void onTick(long millisUntilFinished) {
                    updateTime(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    loadTimeUntilSession();
                }
            };
            updateTime(until - now);
            countDownTimer.start();
        }
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onTimeUntilSessionLoadingFailed(Exception exception) {
        if (isAdded()) {
            Error.getData(getView(), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadTimeUntilSession();
                }
            });
        }
    }
}
