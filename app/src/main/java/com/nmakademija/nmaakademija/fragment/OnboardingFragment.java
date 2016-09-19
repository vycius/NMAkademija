package com.nmakademija.nmaakademija.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmakademija.nmaakademija.MainActivity;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.DividerItemDecoration;
import com.nmakademija.nmaakademija.adapter.SectionsAdapter;
import com.nmakademija.nmaakademija.api.API;
import com.nmakademija.nmaakademija.entity.Section;
import com.nmakademija.nmaakademija.listener.ClickListener;
import com.nmakademija.nmaakademija.listener.RecyclerTouchListener;
import com.nmakademija.nmaakademija.utils.Error;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnboardingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        API.nmaService.getSections().enqueue(new Callback<List<Section>>() {
            @Override
            public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {
                View v = getView();
                if (v != null) {

                    final RecyclerView rv = (RecyclerView) v.findViewById(R.id.sections);

                    rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutCompat.VERTICAL));
                    rv.setItemAnimator(new DefaultItemAnimator());
                    SectionsAdapter sectionsAdapter = new SectionsAdapter(response.body());
                    rv.setAdapter(sectionsAdapter);
                    rv.addOnItemTouchListener(new RecyclerTouchListener(
                            getContext(), rv, new ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            PreferenceManager.getDefaultSharedPreferences(view.getContext().getApplicationContext())
                                    .edit()
                                    .putString(getString(R.string.section_key),
                                            String.valueOf(((SectionsAdapter) rv.getAdapter())
                                                    .getSection(position).getId()))
                                    .apply();

                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }
            }

            @Override
            public void onFailure(Call<List<Section>> call, Throwable t) {
                Error.getData(getView());
            }
        });
    }
}
