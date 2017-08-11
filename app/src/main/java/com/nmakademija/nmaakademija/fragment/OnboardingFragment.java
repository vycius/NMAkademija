package com.nmakademija.nmaakademija.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.nmakademija.nmaakademija.api.FirebaseRealtimeApi;
import com.nmakademija.nmaakademija.api.listener.SectionsLoadedListener;
import com.nmakademija.nmaakademija.entity.Section;
import com.nmakademija.nmaakademija.listener.ClickListener;
import com.nmakademija.nmaakademija.listener.RecyclerTouchListener;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.NMAPreferences;

import java.util.ArrayList;

public class OnboardingFragment extends BaseSceeenFragment implements SectionsLoadedListener {

    private boolean isFirstTime;
    private AppEvent appEvent;

    private View contentView;
    private View loadingView;
    private RecyclerView sectionRecyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding, container, false);

        sectionRecyclerView = (RecyclerView) view.findViewById(R.id.sections);
        contentView = view.findViewById(R.id.content);
        loadingView = view.findViewById(R.id.loading_view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isFirstTime = NMAPreferences.isFirstTime(getContext());

        appEvent = AppEvent.getInstance(getContext());

        if (isFirstTime) {
            appEvent.trackCurrentScreen(getActivity(), "open_onboarding");
        } else {
            appEvent.trackCurrentScreen(getActivity(), "open_change_section");
        }

        loadSections();
    }

    public void loadSections() {
        showLoading();
        FirebaseRealtimeApi.getSections(this);
    }

    @Override
    public void onSectionsLoaded(ArrayList<Section> sections) {
        if (isAdded()) {
            sectionRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutCompat.VERTICAL));
            sectionRecyclerView.setItemAnimator(new DefaultItemAnimator());
            SectionsAdapter sectionsAdapter = new SectionsAdapter(sections);
            sectionRecyclerView.setAdapter(sectionsAdapter);
            sectionRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(
                    getContext(), sectionRecyclerView, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Section section = ((SectionsAdapter) sectionRecyclerView.getAdapter())
                            .getSection(position);
                    NMAPreferences.setSection(getContext(),
                            section.getId());

                    appEvent.trackSectionSelected(section.getName());

                    getActivity().finish();
                    if (isFirstTime) {
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

            hideLoading();
        }
    }

    @Override
    public void onSectionsLoadingFailed(Exception exception) {
        if (isAdded()) {
            hideLoading();
            //noinspection ConstantConditions
            Snackbar.make(getView(), R.string.request_failed, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadSections();
                        }
                    })
                    .show();
        }
    }

    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);

    }

    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }
}
