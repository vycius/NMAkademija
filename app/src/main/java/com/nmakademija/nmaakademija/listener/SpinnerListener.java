package com.nmakademija.nmaakademija.listener;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.UsersAdapter;

public class SpinnerListener implements AdapterView.OnItemSelectedListener {
    private TextView supervisor;
    private RecyclerView listView;
    private int lastFilter = -1;

    public SpinnerListener(RecyclerView listView, View view, int lastFilter) {
        this.listView = listView;
        supervisor = (TextView) view.findViewById(R.id.supervisor);
        this.lastFilter = lastFilter;

        setCurrentSectionProperties(view, lastFilter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (view == null || lastFilter == i)
            return;

        setCurrentSectionProperties(view, i);

        ((UsersAdapter) listView.getAdapter()).getFilter().filter(String.valueOf(i));
        listView.scrollToPosition(0);
        lastFilter = i;
    }

    private void setCurrentSectionProperties(View view, int i){
        if (i == 0) {
            supervisor.setVisibility(View.GONE);
        } else {
            String supervisor = ((UsersAdapter) listView.getAdapter()).getSupervisor(i);
            if (!TextUtils.isEmpty(supervisor)) {
                this.supervisor.setText(
                        view.getContext().getString(R.string.before_supervisor_name, supervisor));
                this.supervisor.setVisibility(View.VISIBLE);
            } else
                this.supervisor.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
