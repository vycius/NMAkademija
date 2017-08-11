package com.nmakademija.nmaakademija;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.nmakademija.nmaakademija.api.FirebaseRealtimeApi;
import com.nmakademija.nmaakademija.api.listener.AcademicUpdatedListener;
import com.nmakademija.nmaakademija.api.listener.AcademicsLoadedListener;
import com.nmakademija.nmaakademija.entity.Academic;
import com.nmakademija.nmaakademija.utils.AppEvent;

import java.util.ArrayList;

public class EditProfileActivity extends BaseActivity implements AcademicsLoadedListener {

    private Academic academic;
    private String academicEmail;
   private EditText phoneView;


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

        academicEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        FirebaseRealtimeApi.getAcademicByEmail(this, academicEmail);
    }


    // No. This should return exaclty one academic instead of array
    @Override
    public void onAcademicsLoaded(ArrayList<Academic> academics) {
        if (academics.size() != 1) {
            onLoadingFailed();
        } else {
            academic = academics.get(0);

            ImageView imageView = (ImageView) findViewById(R.id.profile_pic_view);
            Glide.with(this).load(academic.getImage()).error(R.drawable.profile).into(imageView);

            TextView nameView = (TextView) findViewById(R.id.name_edit);
            nameView.setText(academic.getName());

            EditText emailView = (EditText) findViewById(R.id.email_edit);
            emailView.setText(academic.getPublicEmail());

            phoneView = (EditText) findViewById(R.id.phone_edit);
            phoneView.setText(academic.getPhone());

            EditText bioView = (EditText) findViewById(R.id.bio_edit);
            bioView.setText(academic.getBio());

            EditText roomView = (EditText) findViewById(R.id.room_edit);
            roomView.setText(academic.getRoom());
        }
    }

    // Do not set listener via XML.
    // Yup it's missing user
    public void saveUser(View view) {
        academic.setPhone(phoneView.getText().toString());

        FirebaseRealtimeApi.updateAcademic(academic, new AcademicUpdatedListener() {
            @Override
            public void onAcademicUpdated(@NonNull Academic academic) {
                Toast.makeText(EditProfileActivity.this, "Yup it works", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAcademicUpdateFailed(Exception exception) {
                Toast.makeText(EditProfileActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onAcademicsLoadingFailed(Exception exception) {
        onLoadingFailed();
    }

    private void onLoadingFailed() {
        finish();
    }
}
