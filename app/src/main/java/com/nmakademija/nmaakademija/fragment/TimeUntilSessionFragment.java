package com.nmakademija.nmaakademija.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.api.API;
import com.nmakademija.nmaakademija.entity.TimeTillSession;
import com.nmakademija.nmaakademija.entity.TimeUntilSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeUntilSessionFragment extends Fragment {

    private CountDownTimer countDownTimer;

    private TextView timeUntilSessionTV;
    private TimeUntilSession timeUntilSession;

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
        View view = getView();
        if (view != null) {
            timeUntilSessionTV = (TextView) view.findViewById(R.id.timeUntilSession);
            API.nmaService.getTimeTillSession().enqueue(new Callback<TimeTillSession>() {
                @Override
                public void onResponse(Call<TimeTillSession> call, Response<TimeTillSession> response) {
                    TimeTillSession timeTillSession = response.body();
                    timeUntilSession = new TimeUntilSession(timeTillSession.getStartTime(), timeTillSession.getEndTime());

                    TextView timeUntilSessionTextTV = (TextView) getView().findViewById(R.id.timeUntilSessionText);

                    timeUntilSessionTextTV.setText(timeUntilSession.isSession() ? getString(R.string.timeUntilSessionEnd) : getString(R.string.timeUntilSessionStart));

                    countDownTimer = new CountDownTimer(timeTillSession.getEndTime(), 1000) {
                        public void onTick(long millisUntilFinished) {
                            timeUntilSessionTV.setText(timeUntilSession.returnTime(getContext()));
                        }

                        @Override
                        public void onFinish() {

                        }
                    };

                    countDownTimer.start();
                }

                @Override
                public void onFailure(Call<TimeTillSession> call, Throwable t) {
                    Context context = getContext();
                    View view1 = getView();
                    if (context != null) {
                        Toast.makeText(context, "Failed API call " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        TextView timeUntilSessionTextTV = (TextView) view1.findViewById(R.id.timeUntilSessionText);

                        timeUntilSessionTextTV.setText(R.string.failed);
                    }
                }
            });
        }
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
