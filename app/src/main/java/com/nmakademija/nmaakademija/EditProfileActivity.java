package com.nmakademija.nmaakademija;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.nmakademija.nmaakademija.api.FirebaseRealtimeApi;
import com.nmakademija.nmaakademija.api.listener.AcademicsLoadedListener;
import com.nmakademija.nmaakademija.entity.Academic;
import com.nmakademija.nmaakademija.utils.AppEvent;

import java.util.ArrayList;

public class EditProfileActivity extends BaseActivity implements AcademicsLoadedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        AppEvent.getInstance(this).trackCurrentScreen(this, "open_edit_profile");

        int color = ContextCompat.getColor(this, R.color.bottomNavigationUsersTab);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(color));

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }

        FirebaseRealtimeApi.getAcademicByEmail(this, FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    @Override
    public void onAcademicsLoaded(ArrayList<Academic> academics) {
        if (academics.size() != 1) {
            onLoadingFailed();
        } else {
            Academic academic = academics.get(0);

            ImageView imageView = (ImageView) findViewById(R.id.profile_pic_view);
            Glide.with(this).load(academic.getImage()).error(R.drawable.profile).into(imageView);

            TextView nameView = (TextView) findViewById(R.id.name_edit);
            nameView.setText(academic.getName());

            EditText emailView = (EditText) findViewById(R.id.email_edit);
            emailView.setText(academic.getPublicEmail());

            EditText phoneView = (EditText) findViewById(R.id.phone_edit);
            phoneView.setText(academic.getPhone());

            EditText bioView = (EditText) findViewById(R.id.bio_edit);
            bioView.setText(academic.getBio());

            EditText roomView = (EditText) findViewById(R.id.room_edit);
            roomView.setText(academic.getRoom());
        }
    }

    public void saveUser(View view) {
        //save user
    }

    @Override
    public void onAcademicsLoadingFailed(Exception exception) {
        onLoadingFailed();
    }

    private void onLoadingFailed() {
        finish();
    }
}
