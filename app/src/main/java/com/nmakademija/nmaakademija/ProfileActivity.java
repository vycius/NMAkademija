package com.nmakademija.nmaakademija;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nmakademija.nmaakademija.entity.Academic;
import com.nmakademija.nmaakademija.utils.AppEvent;

public class ProfileActivity extends BaseActivity {
    public static final String EXTRA_USER = "com.nmakademija.nmaakademija.user";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AppEvent.getInstance(this).trackCurrentScreen(this, "open_profile");

        int color = ContextCompat.getColor(this, R.color.bottomNavigationUsersTab);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(color));

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }

        Academic academic = getIntent().getParcelableExtra(EXTRA_USER);
        if (academic == null) academic = new Academic();

        ImageView imageView = (ImageView) findViewById(R.id.profile_pic_view);
        Glide.with(this).load(academic.getImage()).error(R.drawable.profile).into(imageView);

        TextView nameView = (TextView) findViewById(R.id.name_edit);
        nameView.setText(academic.getName());

        TextView emailView = (TextView) findViewById(R.id.email_edit);
        emailView.setText(academic.getEmail());

        TextView phoneView = (TextView) findViewById(R.id.phone_edit);
        phoneView.setText(academic.getPhone());

        TextView bioView = (TextView) findViewById(R.id.bio_edit);
        bioView.setText(academic.getBio());
    }
}
