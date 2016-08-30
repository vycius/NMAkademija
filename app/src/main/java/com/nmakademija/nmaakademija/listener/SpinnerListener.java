package com.nmakademija.nmaakademija.listener;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.UserListAdapter;
import com.nmakademija.nmaakademija.utils.Validate;

public class SpinnerListener implements AdapterView.OnItemSelectedListener {
    private final TextView supervisor;
    private RecyclerView listView;

    public SpinnerListener(RecyclerView listView, Activity activity) {
        this.listView = listView;
        if (activity != null)
            supervisor = (TextView) activity.findViewById(R.id.supervisor);
        else
            supervisor = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (listView != null) {
            if (i == 0) {
                supervisor.setVisibility(View.GONE);
            } else {
                String supervisor = ((UserListAdapter) listView.getAdapter()).getSupervisor(i);
                if (!Validate.isNullOrEmpty(supervisor)) {
                    this.supervisor.setText(
                            view.getContext().getString(R.string.before_supervisor_name, supervisor));
                    this.supervisor.setVisibility(View.VISIBLE);
                } else
                    this.supervisor.setVisibility(View.GONE);
            }
            ((UserListAdapter) listView.getAdapter()).getFilter().filter(String.valueOf(i));
            listView.scrollToPosition(0);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
