package com.nmakademija.nmaakademija.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.nmakademija.nmaakademija.ProfileActivity;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.AcademicsAdapter;
import com.nmakademija.nmaakademija.api.FirebaseRealtimeApi;
import com.nmakademija.nmaakademija.api.listener.AcademicsLoadedListener;
import com.nmakademija.nmaakademija.api.listener.SectionsLoadedListener;
import com.nmakademija.nmaakademija.entity.Section;
import com.nmakademija.nmaakademija.entity.User;
import com.nmakademija.nmaakademija.listener.SpinnerListener;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.Error;

import java.util.ArrayList;
import java.util.List;

import static com.nmakademija.nmaakademija.R.id.supervisor;

public class UsersFragment extends Fragment implements
        AcademicsAdapter.OnAcademicSelectedListener,
        SpinnerListener.SectionSelectedListener,
        AcademicsLoadedListener, SectionsLoadedListener {

    private static final String EXTRA_LAST_FILTER = "pager_filter";

    private View contentView;
    private View loadingView;
    private RecyclerView usersRecyclerView;
    private Spinner spinner;
    private TextView supervisorView;

    private AppEvent appEvent;

    private int sectionLastFilter = 0;

    public static UsersFragment getInstance() {
        return new UsersFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            sectionLastFilter = savedInstanceState.getInt(EXTRA_LAST_FILTER);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_LAST_FILTER, spinner.getSelectedItemPosition() != -1 ? spinner.getSelectedItemPosition() : 0);

        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        usersRecyclerView = (RecyclerView) view.findViewById(R.id.users_list_view);
        supervisorView = (TextView) view.findViewById(supervisor);
        contentView = view.findViewById(R.id.content);
        loadingView = view.findViewById(R.id.loading_view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        appEvent = AppEvent.getInstance(getContext());
        appEvent.trackCurrentScreen(getActivity(), "open_users_list");

        loadSections();
    }

    private void loadSections() {
        showLoading();
        FirebaseRealtimeApi.getSections(this);
    }

    private void setSectionsAdapter(ArrayList<Section> sections) {
        List<String> sectionNames = new ArrayList<>();
        sectionNames.add(getResources().getString(R.string.all_academics));
        for (Section section : sections) {
            sectionNames.add(section.getName());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, sectionNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new SpinnerListener(this, sections));
    }

    private void loadUsers(@Nullable Integer sectionId) {
        if (sectionId == null) {
            FirebaseRealtimeApi.getAllAcademics(this);
        } else {
            FirebaseRealtimeApi.getSectionAcademics(this, sectionId);
        }
    }

    @Override
    public void onAcademicSelected(User user) {
        appEvent.trackUserClicked(user.getName());

        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra(ProfileActivity.EXTRA_USER, user);

        startActivity(intent);
    }

    @Override
    public void onSectionSelected(@Nullable Section section) {
        if (section == null) {
            supervisorView.setVisibility(View.GONE);

            loadUsers(null);
        } else {
            supervisorView.setVisibility(View.VISIBLE);
            supervisorView.setText(getString(R.string.before_supervisor_name, section.getSupervisor()));

            loadUsers(section.getId());
        }
    }

    @Override
    public void onAcademicsLoaded(ArrayList<User> academics) {
        if (isAdded()) {
            AcademicsAdapter academicsAdapter = new AcademicsAdapter(academics, this);

            usersRecyclerView.setAdapter(academicsAdapter);

            hideLoading();
        }
    }

    public void onLoadingFailed() {
        hideLoading();

        Error.getData(getView(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSections();
            }
        });
    }

    @Override
    public void onAcademicsLoadingFailed(Exception exception) {
        if (isAdded()) {
            onLoadingFailed();
        }
    }

    @Override
    public void onSectionsLoaded(ArrayList<Section> sections) {
        if (isAdded()) {
            setSectionsAdapter(sections);

            loadUsers(sectionLastFilter);
        }
    }

    @Override
    public void onSectionsLoadingFailed(Exception exception) {
        if (isAdded()) {
            onLoadingFailed();
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
