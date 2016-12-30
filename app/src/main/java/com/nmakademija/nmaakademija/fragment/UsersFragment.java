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

    public static UsersFragment getInstance() {
        return new UsersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spinner = (Spinner) getView().findViewById(R.id.spinner);

        getData();
    }

    private void getData() {

        API.nmaService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                final List<User> users = response.body();

                API.nmaService.getSections().enqueue(new Callback<List<Section>>() {

                    @Override
                    public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {

                        View view = getView();
                        if (view != null) {
                            view.findViewById(R.id.spinner).setVisibility(View.VISIBLE);

                            List<Section> sections = response.body();
                            Collections.sort(sections, new Comparator<Section>() {
                                @Override
                                public int compare(Section section, Section t1) {
                                    return section.getId() - t1.getId();
                                }
                            });
                            List<String> sectionNames = new ArrayList<>();
                            sectionNames.add(getResources().getString(R.string.all_academics));
                            for (Section section : sections) {
                                sectionNames.add(section.getName());
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, sectionNames);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            final RecyclerView pager = (RecyclerView) view.findViewById(R.id.users_list_view);
                            pager.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutCompat.VERTICAL));
                            pager.setItemAnimator(new DefaultItemAnimator());
                            pager.setAdapter(new UsersAdapter(users, sections.toArray(new Section[sections.size()])));
                            pager.addOnItemTouchListener(new RecyclerTouchListener(
                                    getContext(), pager, new ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    Context context = view.getContext();
                                    Intent intent = new Intent(context, ProfileActivity.class);
                                    intent.putExtra(ProfileActivity.EXTRA_ALLOW_EDIT, false);
                                    intent.putExtra(ProfileActivity.EXTRA_USER, ((UsersAdapter) pager.getAdapter()).getUser(position));
                                    context.startActivity(intent);
                                }

                                @Override
                                public void onLongClick(View view, int position) {
                                    this.onClick(view, position);
                                }
                            }));

                            SpinnerListener spinnerListener = new SpinnerListener(pager, getActivity());
                            spinner.setOnItemSelectedListener(spinnerListener);
                            spinner.setAdapter(spinnerArrayAdapter);
                        }
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
