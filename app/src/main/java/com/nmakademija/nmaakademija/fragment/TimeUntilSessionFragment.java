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
import com.nmakademija.nmaakademija.entity.TimeUntilSession;

public class TimeUntilSessionFragment extends Fragment {

    // TODO get session start, session end from server
    private long SessionStart = 1471023083;
    private long SessionEnd = 1473023083 ;

    TextView timeUntilSessionTV;
    TimeUntilSession timeUntilSession;

    private CountDownTimer countDownTimer;

    public static TimeUntilSessionFragment getInstance() {
        return new TimeUntilSessionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_time_until_session, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        timeUntilSessionTV = (TextView) getView().findViewById(R.id.timeUntilSession);

        timeUntilSession = new TimeUntilSession(SessionStart, SessionEnd);

        TextView timeUntilSessionTextTV = (TextView) getView().findViewById(R.id.timeUntilSessionText);

        timeUntilSessionTextTV.setText(timeUntilSession.isSession() ? getString(R.string.timeUntilSessionEnd) : getString(R.string.timeUntilSessionStart));

        countDownTimer = new CountDownTimer(SessionEnd, 1000) {
            public void onTick(long millisUntilFinished) {
                timeUntilSessionTV.setText(timeUntilSession.returnTime(getContext()));
            }

            @Override
            public void onFinish() {

            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();

        countDownTimer.start();
    }

    @Override
    public void onStop() {
        countDownTimer.start();

        super.onStop();
    }
}
