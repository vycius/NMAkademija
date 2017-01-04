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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nmakademija.nmaakademija.ProfileActivity;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.AcademicsAdapter;
import com.nmakademija.nmaakademija.api.FirebaseRealtimeAPI;
import com.nmakademija.nmaakademija.entity.Section;
import com.nmakademija.nmaakademija.entity.User;
import com.nmakademija.nmaakademija.listener.SpinnerListener;
import com.nmakademija.nmaakademija.utils.AppEvent;

import java.util.ArrayList;
import java.util.List;

import static com.nmakademija.nmaakademija.R.id.supervisor;

public class UsersFragment extends Fragment implements AcademicsAdapter.OnAcademicSelectedListener, SpinnerListener.SectionSelectedListener {

    private AcademicsAdapter academicsRecyclerAdapter;

    private RecyclerView usersRecyclerView;
    private Spinner spinner;
    private TextView supervisorView;

    private AppEvent appEvent;

    private int sectionLastFilter = 0;


    private String EXTRA_LAST_FILTER = "pager_filter";

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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        appEvent = AppEvent.getInstance(getContext());
        appEvent.trackCurrentScreen(getActivity(), "open_users_list");
    }

    @Override
    public void onStart() {
        super.onStart();

        loadSections();
    }

    private void loadSections() {
        FirebaseRealtimeAPI.getSections()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (isAdded()) {
                            ArrayList<Section> sections = new ArrayList<>();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Section section = snapshot.getValue(Section.class);

                                sections.add(section);
                            }

                            setSectionsAdapter(sections);
                            loadUsers(null);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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

        Query academicsQuery;
        if (sectionId == null) {
            academicsQuery = FirebaseRealtimeAPI.getAllAcademics();
        } else {
            academicsQuery = FirebaseRealtimeAPI.getSectionAcademics(sectionId);
        }

        academicsRecyclerAdapter = new AcademicsAdapter(academicsQuery, this);
        usersRecyclerView.setAdapter(academicsRecyclerAdapter);
        academicsRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                onLoadCompleted();

                academicsRecyclerAdapter.unregisterAdapterDataObserver(this);
            }
        });
    }

    private void onLoadCompleted() {
        spinner.setVisibility(View.VISIBLE);
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
}
