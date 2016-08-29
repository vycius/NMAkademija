package com.nmakademija.nmaakademija.listener;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.UserListAdapter;
import com.nmakademija.nmaakademija.entity.User;

import java.util.ArrayList;

public class SpinnerListener implements AdapterView.OnItemSelectedListener {
    private ViewPager pager;
    private ArrayList<ArrayList<User>> users;
    private FragmentManager childFragmentManager;

    public SpinnerListener(View view, ArrayList<User> usersList, FragmentManager childFragmentManager, int sectionsSize) {
        users = new ArrayList<>();
        while (users.size() <= sectionsSize)
            users.add(new ArrayList<User>());

        for (User user : usersList)
            users.get(user.getSection() - 1).add(user);

        this.childFragmentManager = childFragmentManager;
        pager = (ViewPager) view.findViewById(R.id.viewPager);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (view != null && users != null) {
            Log.d("sel", String.valueOf(i));
            pager.setAdapter(new UserListAdapter(view.getContext(), users.get(i), childFragmentManager));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}