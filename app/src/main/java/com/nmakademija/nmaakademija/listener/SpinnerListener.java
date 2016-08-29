package com.nmakademija.nmaakademija.listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.UserListAdapter;
import com.nmakademija.nmaakademija.entity.User;

import java.util.ArrayList;

public class SpinnerListener implements AdapterView.OnItemSelectedListener {
    //    private ViewPager pager;
    private ListView pager;
    private ArrayList<ArrayList<User>> users;
//    private FragmentManager childFragmentManager;

    public SpinnerListener(View view, ArrayList<User> usersList, int sectionsSize /*, FragmentManager childFragmentManager*/) {
        users = new ArrayList<>();
        while (users.size() <= sectionsSize)
            users.add(new ArrayList<User>());

        for (User user : usersList)
            users.get(user.getSection() - 1).add(user);

//        this.childFragmentManager = childFragmentManager;
//        pager = (ViewPager) view.findViewById(R.id.users_list);
        pager = (ListView) view.findViewById(R.id.users_list);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (view != null && users != null) {
//            pager.setAdapter(new UserListAdapter(view.getContext(), users.get(i), childFragmentManager));
            pager.setAdapter(new UserListAdapter(view.getContext(), users.get(i)));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
