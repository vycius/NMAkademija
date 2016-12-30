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
import com.nmakademija.nmaakademija.entity.User;
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

        User user = (User) getIntent().getSerializableExtra(EXTRA_USER);
        if (user == null) user = new User();

        ImageView imageView = (ImageView) findViewById(R.id.profile_pic_view);
        Glide.with(this).load(user.getImage()).error(R.drawable.profile).into(imageView);

        TextView nameView = (TextView) findViewById(R.id.name_edit);
        nameView.setText(user.getName());

        TextView emailView = (TextView) findViewById(R.id.email_edit);
        emailView.setText(user.getEmail());

        TextView phoneView = (TextView) findViewById(R.id.phone_edit);
        phoneView.setText(user.getPhone());

        TextView bioView = (TextView) findViewById(R.id.bio_edit);
        bioView.setText(user.getBio());
    }
}
