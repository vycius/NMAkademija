package com.nmakademija.nmaakademija;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nmakademija.nmaakademija.entity.User;

public class ProfileActivity extends AppCompatActivity {
    public static final String EXTRA_ALLOW_EDIT = "com.nmakademija.nmaakademija.allow_edit";
    public static final String EXTRA_USER = "com.nmakademija.nmaakademija.user";

    private EditText nameEdit;
    private EditText emailEdit;
    private EditText phoneEdit;
    private EditText bioEdit;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        int color = ContextCompat.getColor(this, R.color.bottomNavigationUsersTab);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(color));

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }

        boolean allowEdit = getIntent().getBooleanExtra(EXTRA_ALLOW_EDIT, false);
        user = (User) getIntent().getSerializableExtra(EXTRA_USER);
        if (user == null) user = new User();

        ImageView imageView = (ImageView) findViewById(R.id.profile_pic_view);
        Glide.with(this).load(user.getImage()).into(imageView);

        nameEdit = (EditText) findViewById(R.id.name_edit);
        nameEdit.setText(user.getName());
        nameEdit.setEnabled(allowEdit);

        emailEdit = (EditText) findViewById(R.id.email_edit);
        emailEdit.setText(user.getEmail());
        emailEdit.setEnabled(allowEdit);

        phoneEdit = (EditText) findViewById(R.id.phone_edit);
        phoneEdit.setText(user.getPhone());
        phoneEdit.setEnabled(allowEdit);

        bioEdit = (EditText) findViewById(R.id.bio_edit);
        bioEdit.setText(user.getBio());
        bioEdit.setEnabled(allowEdit);

        Button saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setName(nameEdit.getText().toString());
                user.setEmail(emailEdit.getText().toString());
                user.setPhone(phoneEdit.getText().toString());
                user.setBio(bioEdit.getText().toString());
                finish();
            }
        });
        saveButton.setVisibility(allowEdit ? View.VISIBLE : View.INVISIBLE);
    }
}
