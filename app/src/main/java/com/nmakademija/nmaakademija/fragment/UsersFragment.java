package com.nmakademija.nmaakademija.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.nmakademija.nmaakademija.ProfileActivity;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.DividerItemDecoration;
import com.nmakademija.nmaakademija.adapter.UsersAdapter;
import com.nmakademija.nmaakademija.api.API;
import com.nmakademija.nmaakademija.entity.Section;
import com.nmakademija.nmaakademija.entity.User;
import com.nmakademija.nmaakademija.listener.ClickListener;
import com.nmakademija.nmaakademija.listener.RecyclerTouchListener;
import com.nmakademija.nmaakademija.listener.SpinnerListener;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.Error;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment {

    private Spinner spinner;
    private AppEvent appEvent;
    private ArrayList<User> users;
    private ArrayList<Section> sections;
    private int lastFilter = 0;

    private String EXTRA_USERS = "users";
    private String EXTRA_SECTIONS = "sections";
    private String EXTRA_LAST_FILTER = "pager_filter";

    public static UsersFragment getInstance() {
        return new UsersFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            users = savedInstanceState.getParcelableArrayList(EXTRA_USERS);
            sections = savedInstanceState.getParcelableArrayList(EXTRA_SECTIONS);
            lastFilter = savedInstanceState.getInt(EXTRA_LAST_FILTER);
            if(lastFilter == -1)
                lastFilter = 0;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(EXTRA_USERS, users);
        outState.putParcelableArrayList(EXTRA_SECTIONS, sections);
        if(spinner == null){
            spinner = (Spinner) getView().findViewById(R.id.spinner);
        }
        outState.putInt(EXTRA_LAST_FILTER, spinner.getSelectedItemPosition());

        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        appEvent = AppEvent.getInstance(getContext());

        appEvent.trackCurrentScreen(getActivity(), "open_users_list");

        if(users == null || sections == null)
            getData();
        else
            setData();
    }

    private void setData(){
        if (isAdded()) {
            spinner = (Spinner) getView().findViewById(R.id.spinner);
            getView().findViewById(R.id.spinner).setVisibility(View.VISIBLE);

            List<String> sectionNames = new ArrayList<>();
            sectionNames.add(getResources().getString(R.string.all_academics));
            for (Section section : sections) {
                sectionNames.add(section.getName());
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, sectionNames);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            final RecyclerView pager = (RecyclerView) getView().findViewById(R.id.users_list_view);
            pager.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutCompat.VERTICAL));
            pager.setItemAnimator(new DefaultItemAnimator());
            UsersAdapter usersAdapter = new UsersAdapter(users, sections.toArray(new Section[0]));
            pager.setAdapter(usersAdapter);
            usersAdapter.getFilter().filter(String.valueOf(lastFilter));

            pager.addOnItemTouchListener(new RecyclerTouchListener(
                    getContext(), pager, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ProfileActivity.class);
                    User user = ((UsersAdapter) pager.getAdapter()).getUser(position);
                    appEvent.trackUserClicked(user.getName());

                    intent.putExtra(ProfileActivity.EXTRA_USER, user);
                    context.startActivity(intent);
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

            spinner.setAdapter(spinnerArrayAdapter);
            spinner.setOnItemSelectedListener(new SpinnerListener(pager, getView(), lastFilter));
        }
    }

    private void getData() {
        API.nmaService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                users = new ArrayList<>( response.body() );
                Collections.sort(users, new Comparator<User>() {
                    @Override
                    public int compare(User a, User b) {
                        return a.getName().compareTo(b.getName());
                    }
                });
                API.nmaService.getSections().enqueue(new Callback<List<Section>>() {
                    @Override
                    public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {
                        sections = new ArrayList<>(response.body());
                        setData();
                    }
                    @Override
                    public void onFailure(Call<List<Section>> call, Throwable t) {
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
            public void onFailure(Call<List<User>> call, Throwable t) {
                Error.getData(getView(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getData();
                    }
                });
            }
        });
    }
}
