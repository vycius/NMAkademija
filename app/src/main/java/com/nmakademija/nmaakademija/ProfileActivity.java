package com.nmakademija.nmaakademija;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

        Academic academic = getIntent().getParcelableExtra(EXTRA_USER);
        if (academic == null) academic = new Academic();

        ImageView imageView = (ImageView) findViewById(R.id.profile_pic_view);
        Glide.with(this).load(academic.getImage()).error(R.drawable.profile).into(imageView);

        TextView nameView = (TextView) findViewById(R.id.name_edit);
        nameView.setText(academic.getName());

        TextView emailView = (TextView) findViewById(R.id.email_edit);
        emailView.setText(academic.getPublicEmail());

        TextView phoneView = (TextView) findViewById(R.id.phone_edit);
        phoneView.setText(academic.getPhone());

        TextView bioView = (TextView) findViewById(R.id.bio_edit);
        bioView.setText(academic.getBio());

        TextView roomView = (TextView) findViewById(R.id.room_edit);
        roomView.setText(academic.getRoom());
    }
}
