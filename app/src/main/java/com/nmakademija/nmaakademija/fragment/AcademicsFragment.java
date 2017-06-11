package com.nmakademija.nmaakademija.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.nmakademija.nmaakademija.BaseActivity;
import com.nmakademija.nmaakademija.ProfileActivity;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.StartActivity;
import com.nmakademija.nmaakademija.adapter.AcademicsAdapter;
import com.nmakademija.nmaakademija.api.FirebaseRealtimeApi;
import com.nmakademija.nmaakademija.api.listener.AcademicsLoadedListener;
import com.nmakademija.nmaakademija.api.listener.SectionsLoadedListener;
import com.nmakademija.nmaakademija.entity.Academic;
import com.nmakademija.nmaakademija.entity.Section;
import com.nmakademija.nmaakademija.listener.SpinnerListener;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.Error;

import java.util.ArrayList;
import java.util.List;

import static com.nmakademija.nmaakademija.R.id.supervisor;

public class AcademicsFragment extends BaseSceeenFragment implements
        AcademicsAdapter.OnAcademicSelectedListener,
        SpinnerListener.SectionSelectedListener,
        AcademicsLoadedListener, SectionsLoadedListener {

    private static final String EXTRA_LAST_FILTER = "pager_filter";

    private View contentView;
    private View loadingView;
    private View needLoginView;
    private RecyclerView usersRecyclerView;
    private Spinner sectionsSpinner;
    private TextView supervisorView;
    private Button loginButton;

    private AppEvent appEvent;

    private int sectionSelectedPosition = 0;

    public static AcademicsFragment getInstance() {
        return new AcademicsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            sectionSelectedPosition = savedInstanceState.getInt(EXTRA_LAST_FILTER);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_LAST_FILTER, sectionSelectedPosition);

        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        sectionsSpinner = (Spinner) view.findViewById(R.id.spinner);
        usersRecyclerView = (RecyclerView) view.findViewById(R.id.users_list_view);
        supervisorView = (TextView) view.findViewById(supervisor);
        contentView = view.findViewById(R.id.content);
        loadingView = view.findViewById(R.id.loading_view);
        needLoginView = view.findViewById(R.id.need_login_view);
        loginButton = (Button) view.findViewById(R.id.go_to_login);

        return view;
    }

    private void openLogin() {
        ((BaseActivity) getActivity()).mAuth.signOut();
        startActivity(new Intent(getContext(), StartActivity.class));
        getActivity().finish();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FirebaseUser user = ((BaseActivity) getActivity()).mAuth.getCurrentUser();

        if (user == null || user.isAnonymous()) {
            showNeedLogin();
        } else {
            appEvent = AppEvent.getInstance(getContext());
            appEvent.trackCurrentScreen(getActivity(), "open_users_list");

            loadSections();
        }
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

        sectionsSpinner.setAdapter(spinnerArrayAdapter);
        sectionsSpinner.setOnItemSelectedListener(new SpinnerListener(this, sections));
        if (sectionSelectedPosition < sections.size()) {
            sectionsSpinner.setSelection(sectionSelectedPosition);
        }
    }

    private void loadUsers(@Nullable Integer sectionId) {
        FirebaseRealtimeApi.getAcademics(this, sectionId);
    }

    @Override
    public void onAcademicSelected(Academic academic) {
        appEvent.trackUserClicked(academic.getName());

        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra(ProfileActivity.EXTRA_USER, academic);

        startActivity(intent);
    }

    @Override
    public void onSectionSelected(@Nullable Section section, int position) {
        sectionSelectedPosition = position;

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
    public void onAcademicsLoaded(ArrayList<Academic> academics) {
        if (isAdded()) {
            AcademicsAdapter academicsAdapter = new AcademicsAdapter(academics, this);
            academicsAdapter.setHasStableIds(true);

            usersRecyclerView.setAdapter(academicsAdapter);
            usersRecyclerView.setHasFixedSize(true);

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

            Integer sectionId = getSectionId(sections, sectionSelectedPosition);

            loadUsers(sectionId);
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

    public void showNeedLogin() {
        loadingView.setVisibility(View.GONE);
        contentView.setVisibility(View.GONE);
        needLoginView.setVisibility(View.VISIBLE);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });
    }

    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

    @Nullable
    public static Integer getSectionId(List<Section> sections, int sectionIndex) {
        if (sectionIndex == 0 || sectionIndex > sections.size()) {
            return null;
        }

        return sections.get(sectionIndex).getId();
    }
}
