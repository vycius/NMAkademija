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

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss", Locale.US);
    private static final long millisecondsInDay = 86400000L;
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

            countDownTimer = new CountDownTimer(
                    timeUntilSession.getEndTime().getTime() - new Date().getTime(), 1000) {
                public void onTick(long millisUntilFinished) {
                    updateTime();
                }

                @Override
                public void onFinish() {

                }
            };
            updateTime();
            countDownTimer.start();
        }
    }

    private void updateTime() {
        if (isAdded()) {

            long now = new Date().getTime();
            long timeLeft = timeUntilSession.isSession() ? timeUntilSession.getEndTime().getTime() - now : timeUntilSession.getStartTime().getTime() - now;
            long daysLeft = timeLeft / millisecondsInDay;
            String timeLeftString = DATE_FORMAT.format(new Date(timeLeft));

            timeUntilSessionTV.setText(getContext().getString(R.string.timer_date_format, daysLeft, timeLeftString));
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
