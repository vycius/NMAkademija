package com.nmakademija.nmaakademija.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.nmakademija.nmaakademija.ProfileActivity;
import com.nmakademija.nmaakademija.entity.User;

public class UserOnClickListener implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Context context = view.getContext();
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(ProfileActivity.EXTRA_ALLOW_EDIT, false);
        intent.putExtra(ProfileActivity.EXTRA_USER, (User) adapterView.getAdapter().getItem(i));
        context.startActivity(intent);
    }
}
