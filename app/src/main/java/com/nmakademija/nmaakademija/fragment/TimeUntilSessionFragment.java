package com.nmakademija.nmaakademija.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.api.API;
import com.nmakademija.nmaakademija.entity.TimeUntilSession;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.Error;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeUntilSessionFragment extends Fragment {

    private CountDownTimer countDownTimer;
    private TimeUntilSession timeUntilSession;
    private TextView timeUntilSessionTV;
    private TextView timeUntilSessionTextTV;
    private String EXTRA_TIME_UNTIL_SESSION = "time_until_session";

    public static TimeUntilSessionFragment getInstance() {
        return new TimeUntilSessionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time_until_session, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            timeUntilSession = savedInstanceState.getParcelable(EXTRA_TIME_UNTIL_SESSION);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_TIME_UNTIL_SESSION, timeUntilSession);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppEvent.getInstance(getContext()).trackCurrentScreen(getActivity(), "open_time_until_session");

        timeUntilSessionTV = (TextView) getView().findViewById(R.id.timeUntilSession);
        timeUntilSessionTextTV = (TextView) getView().findViewById(R.id.timeUntilSessionText);

        if(timeUntilSession == null)
            getData();
        else
            setData();
    }

    private void setData(){
        if (isAdded()) {

            timeUntilSessionTextTV.setText(
                    timeUntilSession.isSession() ?
                            getString(R.string.timer_session_end) : getString(R.string.timer_session_start));

            long until = (timeUntilSession.isSession() ?
                    timeUntilSession.getEndTime()
                    : timeUntilSession.getStartTime()).getTime();

            final long now = new Date().getTime();
            if (until < now)
                return;
            countDownTimer = new CountDownTimer(
                    until - now, 1000) {
                public void onTick(long millisUntilFinished) {
                    updateTime(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    setData();
                }
            };
            updateTime(until - now);
            countDownTimer.start();
        }
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

            timeUntilSessionTV.setText(getContext().getString(R.string.timer_date_format, timeLeft, hoursLeft, minutesLeft, secondsLeft));
        }
    }

    private void getData() {

        API.nmaService.getTimeTillSession().enqueue(new Callback<TimeUntilSession>() {
            @Override
            public void onResponse(Call<TimeUntilSession> call, Response<TimeUntilSession> response) {
                timeUntilSession = response.body();
                setData();
            }

            @Override
            public void onFailure(Call<TimeUntilSession> call, Throwable t) {
                Error.getData(getView(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getData();
                    }
                });
            }
        });
    }

    @Override
    public void onPause() {
        if (countDownTimer != null)
            countDownTimer.cancel();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (countDownTimer != null)
            countDownTimer.start();
    }
}
