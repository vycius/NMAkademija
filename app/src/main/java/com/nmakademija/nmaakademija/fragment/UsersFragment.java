package com.nmakademija.nmaakademija.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.api.API;
import com.nmakademija.nmaakademija.entity.Section;
import com.nmakademija.nmaakademija.entity.User;
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
        return inflater.inflate(R.layout.users_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //        tabs = (TabLayout) getView().findViewById(R.id.tabs);
        spinner = (Spinner) getView().findViewById(R.id.spinner);
        API.nmaService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                final List<User> users = response.body();

                API.nmaService.getSections().enqueue(new Callback<List<Section>>() {

                    @Override
                    public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {
                        List<Section> sections = response.body();
                        Collections.sort(sections, new Comparator<Section>() {
                            @Override
                            public int compare(Section section, Section t1) {
                                return section.getId() - t1.getId();
                            }
                        });
                        String[] sectionsString = new String[sections.size()];
                        for (int i = 0; i < sections.size(); i++) {
                            sectionsString[i] = sections.get(i).getName();
                        }
                        View view = getView();
                        if (view != null) {
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, sectionsString);
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            SpinnerListener spinnerListener = new SpinnerListener(view, new ArrayList<>(users), sections.size() /*, getChildFragmentManager()*/);
                            spinner.setOnItemSelectedListener(spinnerListener);
                            spinner.setAdapter(spinnerArrayAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Section>> call, Throwable t) {
                        Error.getData(getView());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Error.getData(getView());
            }
        });

    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//
//        inflater.inflate(R.menu.activity_user_list, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_item_profile) {
//            //TODO pass user
//            Intent i = new Intent(getActivity(), ProfileActivity.class);
//            i.putExtra(ProfileActivity.EXTRA_ALLOW_EDIT, true);
//            startActivity(i);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
