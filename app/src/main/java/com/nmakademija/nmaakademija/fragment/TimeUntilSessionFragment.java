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
import com.nmakademija.nmaakademija.entity.TimeTillSession;
import com.nmakademija.nmaakademija.entity.TimeUntilSession;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.Error;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeUntilSessionFragment extends Fragment {

    private CountDownTimer countDownTimer;

    private TimeUntilSession timeUntilSession;

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

        if(timeUntilSession == null)
            getData();
        else
            setData();
    }

    private void setData(){
        View view = getView();
        if (view != null) {
            TextView timeUntilSessionTextTV = (TextView) view.findViewById(R.id.timeUntilSessionText);

            timeUntilSessionTextTV.setText(timeUntilSession.isSession() ? getString(R.string.timer_session_end) : getString(R.string.timer_session_start));

            final TextView timeUntilSessionTV;
            timeUntilSessionTV = (TextView) getView().findViewById(R.id.timeUntilSession);
            countDownTimer = new CountDownTimer(( timeUntilSession.isSession() ? timeUntilSession.getEndTime() : timeUntilSession.getStartTime() ).getTime() - new Date().getTime(), 1000) {
                public void onTick(long millisUntilFinished) {
                    if (isVisible()) {
                        timeUntilSessionTV.setText(timeUntilSession.returnTime(getContext()));
                    }
                }

                @Override
                public void onFinish() {
                    setData();
                }
            };

            countDownTimer.start();
        }
    }

    private void getData() {

        API.nmaService.getTimeTillSession().enqueue(new Callback<TimeTillSession>() {
            @Override
            public void onResponse(Call<TimeTillSession> call, Response<TimeTillSession> response) {
                TimeTillSession timeTillSession = response.body();
                timeUntilSession = new TimeUntilSession(timeTillSession.getStartTime(), timeTillSession.getEndTime());
                setData();
            }

            @Override
            public void onFailure(Call<TimeTillSession> call, Throwable t) {
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
