package com.nmakademija.nmaakademija;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.nmakademija.nmaakademija.api.FirebaseRealtimeApi;
import com.nmakademija.nmaakademija.api.listener.AcademicLoadedListener;
import com.nmakademija.nmaakademija.api.listener.AcademicUpdatedListener;
import com.nmakademija.nmaakademija.entity.Academic;
import com.nmakademija.nmaakademija.utils.AppEvent;

public class EditProfileActivity extends BaseActivity implements AcademicLoadedListener {

    private Academic academic;
    private TextInputEditText phoneView;
    private TextInputEditText emailView;
    private TextInputEditText bioView;
    private TextInputEditText roomView;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            saveUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        //findViewById(R.id.loading_view).setVisibility(View.VISIBLE);

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
    }


    @Override
    public void onAcademicLoaded(Academic loadedAcademic) {
        HideLoading();

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
                Snackbar.make(findViewById(R.id.content), R.string.saved, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onAcademicUpdateFailed(Exception exception) {
                View view = findViewById(R.id.content);
                Snackbar.make(view, R.string.save_request_failed, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.retry, new View.OnClickListener() {
                            @Override
                            public void onClick(View view1) {
                                saveUser();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void onAcademicLoadingFailed(Exception exception) {
        HideLoading();
        View view = findViewById(R.id.content);
        Snackbar.make(view, R.string.profile_request_failed, Snackbar.LENGTH_INDEFINITE).show();
    }

    private void HideLoading() {
        findViewById(R.id.loading_view).setVisibility(View.GONE);
        findViewById(R.id.content).setVisibility(View.VISIBLE);
    }
}
