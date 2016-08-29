package com.nmakademija.nmaakademija.listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nmakademija.nmaakademija.adapter.UserListAdapter;

public class SpinnerListener implements AdapterView.OnItemSelectedListener {
    private ListView listView;
    //    private ViewPager pager;
//    private ListView pager;
//    private ArrayList<Section> sections;
//    private UserListAdapter adapter;
//    private ArrayList<ArrayList<User>> users;
//    private FragmentManager childFragmentManager;

    public SpinnerListener(/*View view,ArrayList<Section> sections, */ListView listView /*, ArrayList<User> usersList, FragmentManager childFragmentManager*/) {
//        this.sections = sections;
//        this.adapter = adapter;
//        users = new ArrayList<>();
//        while (users.size() <= sectionsSize)
//            users.add(new ArrayList<User>());
//
//        for (User user : usersList)
//            users.get(user.getSection() - 1).add(user);

//        this.childFragmentManager = childFragmentManager;
//        pager = (ViewPager) view.findViewById(R.id.users_list);
//        pager = (ListView) view.findViewById(R.id.users_list);
        this.listView = listView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        if (view != null && users != null) {
        if (listView != null) {
            ((UserListAdapter) listView.getAdapter()).getFilter().filter(String.valueOf(i));
            listView.setSelectionAfterHeaderView();
        }
//            pager.setAdapter(new UserListAdapter(view.getContext(), users.get(i), childFragmentManager));
//            pager.setAdapter(new UserListAdapter(view.getContext(), users.get(i)));
//        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
