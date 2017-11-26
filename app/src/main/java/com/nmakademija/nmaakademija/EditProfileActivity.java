package com.nmakademija.nmaakademija;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.nmakademija.nmaakademija.api.FirebaseRealtimeApi;
import com.nmakademija.nmaakademija.api.controllers.AcademicByEmailController;
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
    private AcademicByEmailController academicController;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            saveUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        AppEvent.getInstance(this).trackCurrentScreen(this, "open_edit_profile");

        String academicEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        academicController = new AcademicByEmailController(this, academicEmail);
    }

    @Override
    protected void onStart() {
        super.onStart();

        academicController.attach();
    }

    @Override
    public void onStop() {
        academicController.remove();

        super.onStop();
    }

    @Override
    public void onAcademicLoaded(Academic loadedAcademic) {
        hideLoading();

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
        hideLoading();
        View view = findViewById(R.id.content);
        Snackbar.make(view, R.string.profile_request_failed, Snackbar.LENGTH_INDEFINITE).show();
    }

    private void hideLoading() {
        findViewById(R.id.loading_view).setVisibility(View.GONE);
        findViewById(R.id.content).setVisibility(View.VISIBLE);
    }
}
