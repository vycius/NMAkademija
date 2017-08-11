package com.nmakademija.nmaakademija;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.nmakademija.nmaakademija.api.FirebaseRealtimeApi;
import com.nmakademija.nmaakademija.api.listener.AcademicLoadedListener;
import com.nmakademija.nmaakademija.api.listener.AcademicUpdatedListener;
import com.nmakademija.nmaakademija.entity.Academic;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.Error;

public class EditProfileActivity extends BaseActivity implements AcademicLoadedListener {

    private Academic academic;
    private TextInputEditText phoneView;
    private TextInputEditText emailView;
    private TextInputEditText bioView;
    private TextInputEditText roomView;

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

        String academicEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        FirebaseRealtimeApi.getAcademicByEmail(this, academicEmail);
        Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });
    }


    @Override
    public void onAcademicLoaded(Academic loadedAcademic) {
        academic = loadedAcademic;
        ImageView imageView = (ImageView) findViewById(R.id.profile_pic_view);
        Glide.with(this).load(academic.getImage()).error(R.drawable.profile).into(imageView);

        TextView nameView = (TextView) findViewById(R.id.name_edit);
        nameView.setText(academic.getName());

        emailView = (TextInputEditText) findViewById(R.id.email_edit);
        emailView.setText(academic.getPublicEmail());

        phoneView = (TextInputEditText) findViewById(R.id.phone_edit);
        phoneView.setText(academic.getPhone());

        bioView = (TextInputEditText) findViewById(R.id.bio_edit);
        bioView.setText(academic.getBio());

        roomView = (TextInputEditText) findViewById(R.id.room_edit);
        roomView.setText(academic.getRoom());
    }

    public void saveUser() {
        academic.setPhone(phoneView.getText().toString());
        academic.setBio(bioView.getText().toString());
        academic.setPublicEmail(emailView.getText().toString());
        academic.setRoom(roomView.getText().toString());

        FirebaseRealtimeApi.updateAcademic(academic, new AcademicUpdatedListener() {
            @Override
            public void onAcademicUpdated(@NonNull Academic academic) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.content), R.string.saved, Snackbar.LENGTH_SHORT);
                TextView tv = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.WHITE);
                snackbar.show();
            }

            @Override
            public void onAcademicUpdateFailed(Exception exception) {
                onSaveFailed();
            }
        });
    }

    @Override
    public void onAcademicLoadingFailed(Exception exception) {
        onLoadFailed();
    }

    private void onLoadFailed() {
        Error.loadProfile(findViewById(R.id.content));
    }

    private void onSaveFailed() {
        Error.saveData(findViewById(R.id.content), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });
    }
}
